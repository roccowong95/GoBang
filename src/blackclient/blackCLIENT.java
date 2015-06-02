package blackclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.lang.Thread;

import javax.swing.JOptionPane;

import blackclient.config1;
import blackclient.MainUI;

public class blackCLIENT {
    public static MainUI UI;
    public static int x;
    public static int y;
    public static byte flag;
    final static int BLACK = 1;
    final static int WHITE = -1;
    public static DataOutputStream dous;
    public static DataInputStream dins;
    public static GameTree gameTree;
    public static Point vspoint, blacksetpoint;
    public static chessNode max, min;

    java.net.Socket client;

    public void conn2Server(String ip, int port) throws IOException {
        try {
            client = new java.net.Socket(ip, port);

            java.io.InputStream ins = client.getInputStream();
            java.io.OutputStream ous = client.getOutputStream();
            dous = new DataOutputStream(ous);
            dins = new DataInputStream(ins);
            flag = dins.readByte();
            System.out.println("INR:" + flag);
            setDepartment(flag);
            // Scanner sc = new Scanner(System.in);
            // ѭ���ȴ�ʼָ��
            int x1;
            int y1;
            while (!config1.EXIT) {// һֱ��ȡ������ָ�֪���õ���������Ϊֹ
                flag = dins.readByte();
                System.out.println("INR:" + flag);
                if (flag == config1.FIRST) {
                    // JOptionPane.showMessageDialog(null," ");
                    config1.EXIT = true;
                    x1 = new Random().nextInt(config1.ROWS);
                    y1 = new Random().nextInt(config1.ROWS);
                    MainUI.chesses[7][7] = config1.REP;
                    dous.writeByte(config1.MOVE);
                    dous.writeInt(7);
                    dous.writeInt(7);
                    dous.flush();
                    UI.redraw();
                } else if (flag == config1.SECOND) {
                    config1.EXIT = true;
                    acceptCommand();
                }
            }
            while (config1.EXIT) {
                Thread.sleep(10);
                acceptCommand();
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println("�ڷ��˳���������");
            client.close();
        }
    }

    public static void acceptCommand() {
        try {
            flag = dins.readByte();
            System.out.println("INR:" + flag);
            if (flag == config1.TURN) {
                // x1 = new Random().nextInt(config1.ROWS) ;
                // y1 = new Random().nextInt(config1.ROWS) ;
                // int x= sc.nextInt();
                // int y=sc.nextInt();
                // blacksetpoint.x=x;blacksetpoint.y=y;
                blacksetpoint = computerdown();
                // for(;MainUI.chesses[blacksetpoint.x][blacksetpoint.y]!=0;){
                // x1 = new Random().nextInt(config1.ROWS) ;
                // y1 = new Random().nextInt(config1.ROWS) ;
                // blacksetpoint=computerdown();
                // }
                if (MainUI.chesses[blacksetpoint.x][blacksetpoint.y] == 0) {
                    MainUI.chesses[blacksetpoint.x][blacksetpoint.y] = config1.REP;
                    dous.writeByte(config1.MOVE);
                    dous.writeInt(blacksetpoint.x);
                    dous.writeInt(blacksetpoint.y);
                    dous.flush();
                    UI.redraw();
                    if (isWin(blacksetpoint.x, blacksetpoint.y)) {
                        // JOptionPane.showMessageDialog(null,"BLACK WIN!");
                        // config1.EXIT=false;
                    }
                }
            } else if (flag == config1.FIRST) {
                System.out.println("��Ϣ�Ƿ���");
            } else if (flag == config1.MOVE) {
                x = dins.readInt();
                y = dins.readInt();
                vspoint.x = x;
                vspoint.y = y;
                MainUI.chesses[x][y] = -config1.REP;
                if (isWin(x, y)) {
                    // JOptionPane.showMessageDialog(null,"WHITE WIN!");
                    // config1.EXIT=false;
                }
                UI.redraw();
            } else if (flag == config1.ABERRATION) {
                MainUI.chesses[blacksetpoint.x][blacksetpoint.y] = 0;
                UI.redraw();
                blacksetpoint = computerdown();
                if (MainUI.chesses[blacksetpoint.x][blacksetpoint.y] == 0) {
                    MainUI.chesses[blacksetpoint.x][blacksetpoint.y] = config1.REP;
                    dous.writeByte(config1.MOVE);
                    dous.writeInt(blacksetpoint.x);
                    dous.writeInt(blacksetpoint.y);
                    dous.flush();
                    UI.redraw();
                }
            } else if (flag == config1.OVER) {
                config1.EXIT = false;
                byte win = dins.readByte();
                if (win == config1.BLACK) {
                    // JOptionPane.showMessageDialog(null,"BLACK WIN!");
                } else if (win == config1.WHITE) {
                    // JOptionPane.showMessageDialog(null,"WHITE WIN!");
                }
                // dous.writeByte(88);
                dous.flush();

            } else if (flag == config1.TIE) {
                config1.EXIT = false;
                // JOptionPane.showMessageDialog(null,"TIE!");
                // dous.writeByte(88);
                dous.flush();
            } else if (flag == config1.SECOND) {
                System.out.println("����Ƿ���");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isWin(int x, int y) {
        int ch = MainUI.chesses[x][y];
        /* �����ж� */
        int RLastX = x;
        while (RLastX >= 0 && MainUI.chesses[RLastX][y] == ch) {
            RLastX--;
        }
        int Rnum = 0;
        RLastX++;
        while (RLastX < config1.ROWS && MainUI.chesses[RLastX][y] == ch) {
            Rnum++;
            RLastX++;
        }
        /* �����ж� */
        int LLastY = y;
        while (LLastY >= 0 && MainUI.chesses[x][LLastY] == ch) {
            LLastY--;
        }
        int Lnum = 0;
        LLastY++;
        while (LLastY < config1.ROWS && MainUI.chesses[x][LLastY] == ch) {
            Lnum++;
            LLastY++;
        }
        /* ���������ж� */
        int LDLastX = x;
        int RULastY = y;
        while (LDLastX >= 0 && RULastY < config1.ROWS
                && MainUI.chesses[LDLastX][RULastY] == ch) {
            LDLastX--;
            RULastY++;
        }
        int LDnum = 0;
        LDLastX++;
        RULastY--;
        while (LDLastX < config1.ROWS && RULastY >= 0
                && MainUI.chesses[LDLastX][RULastY] == ch) {
            LDnum++;
            LDLastX++;
            RULastY--;
        }
		/* ���������ж� */
        int RULastX = x;
        int LDLastY = y;
        while (RULastX >= 0 && LDLastY >= 0
                && MainUI.chesses[RULastX][LDLastY] == ch) {
            RULastX--;
            LDLastY--;
        }
        int RUnum = 0;
        RULastX++;
        LDLastY++;
        while (RULastX < config1.ROWS && LDLastY < config1.ROWS
                && MainUI.chesses[RULastX][LDLastY] == ch) {
            RUnum++;
            RULastX++;
            LDLastY++;
        }
        if (Rnum >= 5 || Lnum >= 5 || LDnum >= 5 || RUnum >= 5)
            return true;
        else
            return false;

    }

    public static void setDepartment(byte flag) {
        if (flag == config1.REPBLACK)
            config1.REP = 1;
        else if (flag == config1.REPWHITE)
            config1.REP = -1;

    }

    public static Point computerdown() {
        //
        initial();
        gameTree = new GameTree(x, y);
        // TODO:find best putdowm
        return maxmin(gameTree);
    }

    public static Point putdown(Point point) {
        return point;
    }


    public static Point maxmin(GameTree gameTree1) {
        int d = config1.Depth;
        int n = 0;
        int count = 0;
        int length = gameTree1.tree.length - 1;
        Point return_point = new Point();

        Comparator<Point> cmp_getmax = new Comparator<Point>() {
            //Point类的比较器，实现compare方法比较两个点之间的分数，大于返回1，小于返回-1
            @Override
            public int compare(Point o1, Point o2) {
                // TODO Auto-generated method stub
                int score1 = o1.score;
                int score2 = o2.score;
                if (score1 > score2)
                    return -1;
                else if (score1 == score2)
                    return 0;
                else
                    return 1;
            }
        };

        Comparator<Point> cmp_getmin = new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                int score1 = o1.score;
                int score2 = o2.score;
                if (score1 > score2)
                    return 1;
                else if (score1 == score2)
                    return 0;
                else
                    return -1;
            }
        };

        Queue<Point> max_queue = new PriorityQueue<Point>(11, cmp_getmax);
        Queue<Point> min_queue = new PriorityQueue<Point>(11, cmp_getmin);

        while (d != 0) {
            while (n <= Math.pow(config1.N, d)) {
//                System.out.println("d: "+d+" n: "+n+" count: "+count+" len: "+length);
                if (count == config1.N) {
                    if (gameTree1.tree[(length - n) / config1.N].flag == config1.REP) {
                        return_point = max_queue.poll();
                        gameTree1.tree[(length - n) / config1.N].state_score = return_point.score;
                        max_queue.clear();
                    } else {
                        return_point = min_queue.poll();
                        gameTree1.tree[(length - n) / config1.N].state_score = return_point.score;
                        min_queue.clear();
                    }
                    count = 0;
                }
                else {
                    if (gameTree1.tree[length - n].flag == config1.REP) {
                        Point tmp = new Point(
                                gameTree1.tree[length - n].currPoint.x,
                                gameTree1.tree[length - n].currPoint.y,
                                gameTree1.tree[length - n].state_score
                        );
                        min_queue.add(tmp);
                    } else {
                        Point tmp = new Point(
                                gameTree1.tree[length - n].currPoint.x,
                                gameTree1.tree[length - n].currPoint.y,
                                gameTree1.tree[length - n].state_score
                        );
                        max_queue.add(tmp);
                    }
                    count++;
                }
                n++;
            }
            length -= Math.pow(config1.N, d);
            d--;
            n = 0;
            count = 0;
        }
        return return_point;
    }

    public static void initial() {
        // vspoint.x=vspoint.y=-1;
        // blacksetpoint.x=blacksetpoint.y=-1;
    }

    public static void main(String args[]) throws InterruptedException,
            IOException {
        UI = new MainUI();
        vspoint = new Point();
        blacksetpoint = new Point();
        blackCLIENT black = new blackCLIENT();
        black.conn2Server("localhost", config1.port);
    }
}
