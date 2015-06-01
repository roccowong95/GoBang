package blackclient;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class chessNode {
    public int state_score;
    public int flag;
    /*
     * flag表示的是在当前这个节点里,应该
     * 是哪个颜色的选手落子.
     */
    public int[][] score;
    public int[][] board;
    public Point currPoint;
    public Point[] tops;

    public Point[] sons;// 15*15 sons of this node

    public ChessValue chessvalue = new ChessValue();

    public int eval_model(int[] model, int color) {
        // TODO: evaluate chess mode here
        // 这里的color是说目前是对哪个颜色的棋型估分.这里的估分函数是不知道当前是max还是min的?
        char[] chessMode = new char[7];
        String Mode;
        String subMode;
        //遍历model，按照己方、对方、空给chessMode赋值
        for (int i = 0; i < 7; i++) {
            if (model[i] == color)
                chessMode[i] = 'A';// my color
            else if (model[i] == 0)
                chessMode[i] = 'E';// empty
            else
                chessMode[i] = 'B';// other color
            // System.out.println("mode: " + model[i] + " " + chessMode[i]);
        }
        Mode = new String(chessMode);
        //System.out.println("mode: "+Mode);
        //从chessValue中寻找匹配棋型的分数，找到则返回，找不到则分割子串，再找不到返回0
        int i, n;
        for (n = 7; n >= 1; n--) {
            for (i = 0; i + n <= 7; i++) {
                subMode = Mode.substring(i, i + n);
                if (chessvalue.chessValue.get(subMode) != null)
                    return chessvalue.chessValue.get(subMode);
            }
        }
        return 0;
        // hash map
    }

    public int eval(int x, int y) {
        int[] model = new int[7];
        for (int i = 0; i < 7; i++)
            model[i] = 0;
        int white_score = -10000, black_score = -10000;

        // /////////black start////////////
        board[x][y] = config1.BLACK;
        // check about the value of i,j , it might be wrong
        for (int i = Math.max(y - 6, 0); i <= Math.min(y, 14 - 6); i++) {
            // -
            model[0] = board[x][i];
            model[1] = board[x][i + 1];
            model[2] = board[x][i + 2];
            model[3] = board[x][i + 3];
            model[4] = board[x][i + 4];
            model[5] = board[x][i + 5];
            model[6] = board[x][i + 6];
            black_score = Math.max(black_score,
                    eval_model(model, config1.BLACK));
        }
        for (int i = Math.max(x - 6, 0); i <= Math.min(x, 14 - 6); i++) {
            // |
            model[0] = board[i][y];
            model[1] = board[i + 1][y];
            model[2] = board[i + 2][y];
            model[3] = board[i + 3][y];
            model[4] = board[i + 4][y];
            model[5] = board[i + 5][y];
            model[6] = board[i + 6][y];

            black_score = Math.max(black_score,
                    eval_model(model, config1.BLACK));
        }
        if (y >= x)
            for (int i = Math.max(x - 6, 0), j = y - (x - Math.max(x - 6, 0)); j <= Math
                    .min(y, 14 - 6); i++, j++) {
                // \ upper half
                model[0] = board[i][j];
                model[1] = board[i + 1][j + 1];
                model[2] = board[i + 2][j + 2];
                model[3] = board[i + 3][j + 3];
                model[4] = board[i + 4][j + 4];
                model[5] = board[i + 5][j + 5];
                model[6] = board[i + 6][j + 6];
                black_score = Math.max(black_score,
                        eval_model(model, config1.BLACK));
            }
        else
            for (int i = x - (y - Math.max(y - 6, 0)), j = Math.max(y - 6, 0); i <= Math
                    .min(x, 14 - 6); i++, j++) {
                // \ lower half
                model[0] = board[i][j];
                model[1] = board[i + 1][j + 1];
                model[2] = board[i + 2][j + 2];
                model[3] = board[i + 3][j + 3];
                model[4] = board[i + 4][j + 4];
                model[5] = board[i + 5][j + 5];
                model[6] = board[i + 6][j + 6];

                black_score = Math.max(black_score,
                        eval_model(model, config1.BLACK));
            }
        // TODO: / direction

        if (y <= 15 - x)
            for (int i = x + (y - Math.max(y - 6, 0)), j = Math.max(y - 6, 0); i < Math
                    .min(x, 14 - 6); i--, j++) {
                // i <= Math.min(x, 14 - 6)
                // / upper half
                model[0] = board[i][j];
                model[1] = board[i - 1][j + 1];
                model[2] = board[i - 2][j + 2];
                model[3] = board[i - 3][j + 3];
                model[4] = board[i - 4][j + 4];
                model[5] = board[i - 5][j + 5];
                model[6] = board[i - 6][j + 6];

                black_score = Math.max(black_score,
                        eval_model(model, config1.BLACK));
            }
        else
            for (int i = Math.max(x + 6, 14), j = y
                    + Math.abs((x - Math.max(x + 6, 14))); j < Math.min(y,
                    14 - 6); i--, j++) {
                // j <= Math.min(y,14 - 6)
                // / lower half
                model[0] = board[i][j];
                model[1] = board[i - 1][j + 1];
                model[2] = board[i - 2][j + 2];
                model[3] = board[i - 3][j + 3];
                model[4] = board[i - 4][j + 4];
                model[5] = board[i - 5][j + 5];
                model[6] = board[i - 6][j + 6];

                black_score = Math.max(black_score,
                        eval_model(model, config1.BLACK));

            }

        // /////////black end////////////
        // /////////white start//////////
        board[x][y] = config1.WHITE;
        // check about the value of i,j , it might be wrong
        for (int i = Math.max(y - 6, 0); i <= Math.min(y, 14 - 6); i++) {
            // -
            model[0] = board[x][i];
            model[1] = board[x][i + 1];
            model[2] = board[x][i + 2];
            model[3] = board[x][i + 3];
            model[4] = board[x][i + 4];
            model[5] = board[x][i + 5];
            model[6] = board[x][i + 6];

            white_score = Math.max(white_score,
                    eval_model(model, config1.WHITE));
        }
        for (int i = Math.max(x - 6, 0); i <= Math.min(x, 14 - 6); i++) {
            // |
            model[0] = board[i][y];
            model[1] = board[i + 1][y];
            model[2] = board[i + 2][y];
            model[3] = board[i + 3][y];
            model[4] = board[i + 4][y];
            model[5] = board[i + 5][y];
            model[6] = board[i + 6][y];

            white_score = Math.max(white_score,
                    eval_model(model, config1.WHITE));
        }
        if (y >= x)
            for (int i = Math.max(x - 6, 0), j = y - (x - Math.max(x - 6, 0)); j <= Math
                    .min(y, 14 - 6); i++, j++) {
                // \ upper half
                model[0] = board[i][j];
                model[1] = board[i + 1][j + 1];
                model[2] = board[i + 2][j + 2];
                model[3] = board[i + 3][j + 3];
                model[4] = board[i + 4][j + 4];
                model[5] = board[i + 5][j + 5];
                model[6] = board[i + 6][j + 6];
                white_score = Math.max(white_score,
                        eval_model(model, config1.WHITE));
            }
        else
            for (int i = x - (y - Math.max(y - 6, 0)), j = Math.max(y - 6, 0); i <= Math
                    .min(x, 14 - 6); i++, j++) {
                // \ lower half
                model[0] = board[i][j];
                model[1] = board[i + 1][j + 1];
                model[2] = board[i + 2][j + 2];
                model[3] = board[i + 3][j + 3];
                model[4] = board[i + 4][j + 4];
                model[5] = board[i + 5][j + 5];
                model[6] = board[i + 6][j + 6];
                white_score = Math.max(white_score,
                        eval_model(model, config1.WHITE));
            }
        if (y <= 15 - x)
            for (int i = x + (y - Math.max(y - 6, 0)), j = Math.max(y - 6, 0); i < Math
                    .min(x, 14 - 6); i--, j++) {
                // / upper half
                model[0] = board[i][j];
                model[1] = board[i - 1][j + 1];
                model[2] = board[i - 2][j + 2];
                model[3] = board[i - 3][j + 3];
                model[4] = board[i - 4][j + 4];
                model[5] = board[i - 5][j + 5];
                model[6] = board[i - 6][j + 6];

                white_score = Math.max(white_score,
                        eval_model(model, config1.WHITE));
            }
        else
            for (int i = Math.max(x + 6, 14), j = y
                    + Math.abs((x - Math.max(x + 6, 14))); j < Math.min(y,
                    14 - 6); i--, j++) {
                // / lower half
                model[0] = board[i][j];
                model[1] = board[i - 1][j + 1];
                model[2] = board[i - 2][j + 2];
                model[3] = board[i - 3][j + 3];
                model[4] = board[i - 4][j + 4];
                model[5] = board[i - 5][j + 5];
                model[6] = board[i - 6][j + 6];

                white_score = Math.max(white_score,
                        eval_model(model, config1.WHITE));
            }

        // /////////white end////////////
        board[x][y] = 0;
        // calculate score = my score - other score
        //System.out.println("white: " + white_score + " black: " + black_score);
        if (config1.REP == config1.WHITE)
            return white_score - black_score;
        else
            return black_score - white_score;
    }

    public chessNode(int x, int y) {
        //博弈树根节点的构造函数，传入点（x，y）为对方上一次的落子点
        currPoint = new Point();
        board = new int[15][15];
        score = new int[15][15];
        currPoint.x = x;
        currPoint.y = y;
        flag = config1.REP;//轮到我了


        //复制MainUI的两个初始数组
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                board[i][j] = MainUI.chesses[i][j];
                score[i][j] = MainUI.global_score[i][j];
            }
        board[x][y] = -config1.REP;//(x,y)点为对方落子点
        score[x][y] = -1000000;
        //如果该点已落子，它得分数是否需要置0？

        //当对方下棋了以后,对落子点周围的9*9区域内的未落子点进行分数的更新.
        for (int i = Math.max(x - 4, 0); i <= Math.min(x + 4, 14); i++) {
            for (int j = Math.max(y - 4, 0); j <= Math.min(y + 4, 14); j++)
                if (board[i][j] == 0)//如果该点为未落子点，更新它的分数
                // flag->REP , eval(i, j, flag);
                // update score 9×9 around point(x,y)
                {
                    score[i][j] = eval(i, j);
                    MainUI.global_score[i][j] = score[i][j];
                    System.out.println("i: " + i + " j: " + j + " score: " + score[i][j]);
                }
        }//对方的棋子(x,y)下下来了,也是需要更新它周围9×9的！

        getTops();//生成N个子节点
    }

    public chessNode(int x, int y, chessNode parent) {
        currPoint = new Point();
        board = new int[15][15];
        score = new int[15][15];
        currPoint.x = x;
        currPoint.y = y;
        flag = -parent.flag;
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                board[i][j] = parent.board[i][j];
                score[i][j] = parent.score[i][j];
            }
        //复制parent节点的棋盘与分数

        board[x][y] = -flag;//这个地方好像是flag？
//        score[x][y] = -1000000;
        //如果该点已落子，它得分数是否需要置0？
        for (int i = Math.max(x - 4, 0); i <= Math.min(x + 4, 14); i++) {
            for (int j = Math.max(y - 4, 0); j <= Math.min(y + 4, 14); j++)
                if (board[i][j] == 0)//如果该点为未落子点，更新它的分数
                // flag->REP , eval(i, j, flag);
                // update score 9×9 around point(x,y)
                {
                    score[i][j] = eval(i, j);
                    System.out.println("i: " + i + " j: " + j + " score: " + score[i][j]);
                }


        }
        getTops();
    }

    public void getTops() {
        // tops has N elements ， 获取chessNode子局面的15×15的最高分的5个点

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


        tops = new Point[config1.N];//tops是前N名分数的点的集合
        Queue<Point> priority_queue;
        if (flag == config1.REP) {
            priority_queue = new PriorityQueue<Point>(11, cmp_getmax);//把初始容量11和比较器cmp传入优先队列中
        } else
            priority_queue = new PriorityQueue<Point>(11, cmp_getmin);//把初始容量11和比较器cmp传入优先队列中
        sons = new Point[15 * 15];//sons是这个chessNode的子局面的所有点
        int index = 0;


        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                if (board[i][j] == 0) {
                    //把i，j，score[i][j]传入sons[index]中
                    sons[index] = new Point();
                    sons[index].setX(i);
                    sons[index].setY(j);
                    sons[index].setScore(score[i][j]);
                    priority_queue.add(sons[index]);//sons[index]加入优先队列中
                    //System.out.println("sons: "+ sons[index].x + " " + sons[index].y
                    //	+ " " +sons[index].score);
                    index++;
                }
            }

        Iterator<Point> it = priority_queue.iterator();//生成访问优先队列的迭代器
        int i = 0;

        while (it.hasNext() && (i++ != config1.N)) {//如果it存在下一个点，且指针i!=分支数N
            tops[i - 1] = it.next();//把it的next赋给tops[i-1]
            System.out.println("tops: " + " " + tops[i - 1].x + " " + tops[i - 1].y
                    + " " + tops[i - 1].score);
        }


    }

}