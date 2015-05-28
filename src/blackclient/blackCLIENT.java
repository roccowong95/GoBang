package blackclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
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
	public static Point bpointcan4, // 这个位置空，它旁边有四个黑棋
			wpointcan4, // 这个位置空，它旁边有四个白棋
			bpointcan3, // 这个位置空，它的旁边有三个黑棋
			wpointcan3, // 这个位置空，它的旁边有三个白棋
			bpointcan2, // 这个位置空，它的旁边有两个黑棋
			wpointcan2, // 这个位置空，它的旁边有两个白棋
			bpointcan1; // 不是以上情况，这个位置空
	public static Point vspoint, blacksetpoint;
	java.net.Socket client;

	/**********************************************/

	public int[][] Score = new int[15][15];
	public Point[] Highest = new Point[config1.N];

	/**********************************************/

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
			// 循环等待开始指令
			int x1;
			int y1;
			while (!config1.EXIT) {// 一直读取服务器指令，知道得到下棋命令为止
				flag = dins.readByte();
				System.out.println("INR:" + flag);
				if (flag == config1.FIRST) {
					// JOptionPane.showMessageDialog(null,"我是黑方");
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
					// JOptionPane.showMessageDialog(null,"我是白方");
					config1.EXIT = true;
					acceptCommand();
				}
			}
			while (config1.EXIT) {
				Thread.sleep(10);
				acceptCommand();
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("黑房退出服务器！");
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
				System.out.println("消息非法！");
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
				System.out.println("命令非法！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isWin(int x, int y) {
		int ch = MainUI.chesses[x][y];
		/* 横向判断 */
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
		/* 纵向判断 */
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
		/* 左下右上判断 */
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
		/* 右下左上判断 */
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

	/*
	 * TODO: 代码在这里 Team: 长颈鹿 Member: 王苏文 苏思韵
	 */

	public static Point computerdown() {

		// 搜索最好的落棋点
		// System.out.println("进入黑方下棋函");
		initial();
		for (int i = 0; i < config1.ROWS; i++)
			for (int j = 0; j < config1.ROWS; j++)
				bestputdown(i, j);
		// 判断放在哪里
		// 棋多的位置优先
		// 黑白一样多时黑先
		// 不是-1就表示已经被赋值！
		if (config1.REP == 1) {
			if (bpointcan4.x != -1) {

				return putdown(bpointcan4);
			} else if (wpointcan4.x != -1) {

				return putdown(wpointcan4);
			} else if (wpointcan3.x != -1) {

				return putdown(wpointcan3);
			} else if (bpointcan3.x != -1) {

				return putdown(bpointcan3);
			} else if (bpointcan2.x != -1) {

				return putdown(bpointcan2);
			} else if (wpointcan2.x != -1) {

				return putdown(wpointcan2);
			} else {

				return putdown(bpointcan1);
			}
		} else {
			if (wpointcan4.x != -1) {

				return putdown(wpointcan4);
			} else if (bpointcan4.x != -1) {

				return putdown(bpointcan4);
			} else if (bpointcan3.x != -1) {

				return putdown(bpointcan3);
			} else if (wpointcan3.x != -1) {

				return putdown(wpointcan3);
			} else if (bpointcan2.x != -1) {

				return putdown(bpointcan2);
			} else if (wpointcan2.x != -1) {

				return putdown(wpointcan2);
			} else {

				return putdown(bpointcan1);
			}
		}
	}

	public static Point putdown(Point point) {
		return point;
	}

	public static void bestputdown(int i, int j) {
		// 四个方向的值
		int[] num = new int[4];
		int a, k;
		// ///////////////////////////// num[0] -->
		a = 0;
		if (i < 11)
			for (k = 0; k < 5; k++)
				a = a + MainUI.chesses[i + k][j];
		num[0] = Math.abs(a);

		// //////////////////////////// num[1] "|"
		a = 0;
		if (j < 11)
			for (k = 0; k < 5; k++)
				a = a + MainUI.chesses[i][j + k];
		num[1] = Math.abs(a);

		// ///////////////////////////// num[2] "\"
		a = 0;
		if (i < 11 && j < 11)
			for (k = 0; k < 5; k++)
				a = a + MainUI.chesses[i + k][j + k];
		num[2] = Math.abs(a);

		// //////////////////////////// num[3] "/"
		a = 0;
		if ((i >= 4 && i < 15) && (j < 11) && j >= 0)
			for (k = 0; k < 5; k++)
				a = a + MainUI.chesses[i - k][j + k];
		num[3] = Math.abs(a);

		// 比较哪个方向同色棋最多
		// 由于我们搜索落棋点时用到最大值和方向，我们可以定义一个Cpoint类变量，
		// 让它返回两个值。可以说，这也是一种巧妙的想法，因为这样你就不用去写
		// 内联函数了
		Point numbig = new Point();
		// numbig.x表示方向
		// numbig.y表示最大值
		numbig = maxnum(num[0], num[1], num[2], num[3]);
		// 在得到最大值和方向上寻找落棋点
		switch (numbig.y) {
		case 4:
			searchcandown4(i, j, numbig.x);
			break;
		case 3:
			searchcandown3(i, j, numbig.x);
			break;
		case 2:
			searchcandown2(i, j, numbig.x);
			break;
		default:
			searchcandown1(i, j, numbig.x);
		}
		// System.out.println("进入黑方下BBB棋函");
	}

	public static Point maxnum(int a, int b, int c, int d) {
		// point.x为方向值
		// point.y为最大值
		Point point = new Point();
		if (a >= b) {
			point.x = 0;
			point.y = a;
		} else {
			point.x = 1;
			point.y = b;
		}
		if (c > point.y) {
			point.x = 2;
			point.y = c;
		}
		if (d > point.y) {
			point.x = 3;
			point.y = d;
		}
		return point;
	}

	public static void searchcandown4(int i, int j, int n) {
		int k;
		// /////////////////////////// num[0] "--"
		if (n == 0)
			for (k = 0; k < 5; k++)
				// 如果第一个是空
				if (MainUI.chesses[i][j] == 0) {
					// 如果下面有白棋
					if (MainUI.chesses[i + 1][j] == -1) {
						// 下面位置可以下棋，已经有四个白棋
						wpointcan4.x = i;
						wpointcan4.y = j;
						break;
					} else {
						// 下面位置可以下棋，已经有四个黑棋
						bpointcan4.x = i;
						bpointcan4.y = j;
						break;
					}
				}
				// 如果找到下棋位置，一定能找到！
				else if (MainUI.chesses[i + k][j] == 0) {
					// 如果第一个是白棋
					if (MainUI.chesses[i][j] == -1) {
						wpointcan4.x = i + k;
						wpointcan4.y = j;
						break;
					}
					// 否则第一个是黑棋
					else {
						bpointcan4.x = i + k;
						bpointcan4.y = j;
						break;
					}
				}

		// ////////////////////////// num[1] "|"
		if (n == 1)
			for (k = 0; k < 5; k++) {
				if (MainUI.chesses[i][j] == 0)
					if (MainUI.chesses[i][j + 1] == -1) {
						wpointcan4.x = i;
						wpointcan4.y = j;
						break;
					} else {
						bpointcan4.x = i;
						bpointcan4.y = j;
						break;
					}

				else if (MainUI.chesses[i][j + k] == 0) {
					if (MainUI.chesses[i][j] == -1) {
						wpointcan4.x = i;
						wpointcan4.y = j + k;
						break;
					} else {
						bpointcan4.x = i;
						bpointcan4.y = j + k;
						break;
					}
				}
			}
		// ///////////////////////////// num[2] "\"
		if (n == 2)
			for (k = 0; k < 5; k++) {
				if (MainUI.chesses[i][j] == 0)
					if (MainUI.chesses[i + 1][j + 1] == -1) {
						wpointcan4.x = i;
						wpointcan4.y = j;
						break;
					} else {
						bpointcan4.x = i;
						bpointcan4.y = j;
						break;
					}

				else if (MainUI.chesses[i + k][j + k] == 0) {
					if (MainUI.chesses[i][j] == -1) {
						wpointcan4.x = i + k;
						wpointcan4.y = j + k;
						break;
					} else {
						bpointcan4.x = i + k;
						bpointcan4.y = j + k;
						break;
					}
				}
			}
		// //////////////////////////// num[3] "/"
		if (n == 3)
			for (k = 0; k < 5; k++) {
				if (MainUI.chesses[i][j] == 0)
					if (MainUI.chesses[i - 1][j + 1] == -1) {
						wpointcan4.x = i;
						wpointcan4.y = j;
						break;
					} else {
						bpointcan4.x = i;
						bpointcan4.y = j;
						break;
					}

				else if (MainUI.chesses[i - k][j + k] == 0) {
					if (MainUI.chesses[i][j] == -1) {
						wpointcan4.x = i - k;
						wpointcan4.y = j + k;
						break;
					} else {
						bpointcan4.x = i - k;
						bpointcan4.y = j + k;
						break;
					}
				}
			}
	}

	public static void searchcandown3(int i, int j, int n) {
		int k = 0;
		// /////////////////////////// num[0] "--"
		if (n == 0)
			for (k = 0; k < 4; k++)
				// 找到位置
				if (MainUI.chesses[i + k][j] == 0) {
					// 下一个是白棋
					if (MainUI.chesses[i + k + 1][j] == -1) {
						// 下面位置可以下棋，已经有三个白棋
						wpointcan3.x = i + k;
						wpointcan3.y = j;
					}
					// 下一个是黑棋
					else if (MainUI.chesses[i + k + 1][j] == 1) {
						bpointcan3.x = i + k;
						bpointcan3.y = j;
					}
				}
		// ////////////////////////// num[1] "|"
		if (n == 1)
			for (k = 0; k < 4; k++)
				if (MainUI.chesses[i][j + k] == 0) {
					if (MainUI.chesses[i][j + k + 1] == -1) {
						wpointcan3.x = i;
						wpointcan3.y = j + k;
					} else if (MainUI.chesses[i][j + k + 1] == 1) {
						bpointcan3.x = i;
						bpointcan3.y = j + k;
					}
				}
		// ///////////////////////////// num[2] "\"
		if (n == 2)
			for (k = 0; k < 4; k++)
				if (MainUI.chesses[i + k][j + k] == 0) {
					if (MainUI.chesses[i + k + 1][j + k + 1] == -1) {
						wpointcan3.x = i + k;
						wpointcan3.y = j + k;
					} else if (MainUI.chesses[i + k + 1][j + k + 1] == 1) {
						bpointcan3.x = i + k;
						bpointcan3.y = j + k;
					}
				}
		// //////////////////////////// num[3] "/"
		if (n == 3)
			for (k = 0; k < 5; k++) {
				if (k > 0) {
					if (MainUI.chesses[i - k][j + k] == 0) {
						if (MainUI.chesses[i - k + 1][j + k - 1] == -1) {
							wpointcan3.x = i - k;
							wpointcan3.y = j + k;
						} else if (MainUI.chesses[i - k + 1][j + k - 1] == 1) {
							bpointcan3.x = i - k;
							bpointcan3.y = j + k;
						}
					}
				} else if (k == 0) {
					if (MainUI.chesses[i - k][j + k] == 0) {
						if (MainUI.chesses[i - k - 1][j + k + 1] == -1) {
							wpointcan3.x = i - k;
							wpointcan3.y = j + k;
						} else if (MainUI.chesses[i - k - 1][j + k + 1] == 1) {
							bpointcan3.x = i - k;
							bpointcan3.y = j + k;
						}
					}
				}
			}
	}

	public static void searchcandown2(int i, int j, int n) {
		int k = 0, m = 0, a = 0, b = 0;
		// /////////////////////////// num[0] "--"
		if (n == 0) {
			// 判断有多少个空位置
			for (k = 0; k < 5; k++)
				if (MainUI.chesses[i + k][j] == 0)
					m += 1;
			// 如果只有一个空位置
			if (m == 1)
				for (a = 0; a < 3; a++)
					// 找到空位置
					if (MainUI.chesses[i + a][j] == 0) {
						// 下面两个棋子值的和
						b = MainUI.chesses[i + a + 1][j]
								+ MainUI.chesses[i + a + 2][j];
						// 都是黑棋
						if (b == 2) {
							// 下面位置可以下棋，旁边有两个黑棋
							bpointcan2.x = i + a;
							bpointcan2.y = j;
						}
						// 都是白棋
						if (b == -2) {
							wpointcan2.x = i + a;
							wpointcan2.y = j;
						}
					}
			// 如果有三个空位置，说明另外两个同色
			if (m == 3) {
				for (a = 0; a < 5; a++)
					// 如果两个是黑棋
					if (MainUI.chesses[i + a][j] == 1) {
						for (b = 0; b < 5; b++)
							// 如果找到空位置
							if (MainUI.chesses[i + b][j] == 0) {
								// 下面位置可以下棋，旁边有两个黑棋
								bpointcan2.x = i + b;
								bpointcan2.y = j;
								break;
							}
					} else
					// 如果两个是白棋
					if (MainUI.chesses[i + a][j] == -1) {
						for (b = 0; b < 5; b++)
							if (MainUI.chesses[i + b][j] == 0) {
								wpointcan2.x = i + b;
								wpointcan2.y = j;
								break;
							}
					}
			}
		}
		// ////////////////////////// num[1] "|"
		m = 0;
		if (n == 1) {
			for (k = 0; k < 5; k++)
				if (MainUI.chesses[i][j + k] == 0)
					m++;
			if (m == 1)
				for (a = 0; a < 3; a++)
					if (MainUI.chesses[i][j + a] == 0) {
						b = MainUI.chesses[i][j + a + 1]
								+ MainUI.chesses[i][j + a + 2];
						if (b == 2) {
							bpointcan2.x = i;
							bpointcan2.y = j + a;
						}
						if (b == -2) {
							wpointcan2.x = i;
							wpointcan2.y = j + a;
						}
					}
			if (m == 3) {
				for (a = 0; a < 5; a++)
					if (MainUI.chesses[i][j + a] == 1) {
						for (b = 0; b < 5; b++)
							if (MainUI.chesses[i][j + b] == 0) {
								bpointcan2.x = i;
								bpointcan2.y = j + b;
								break;
							}
					} else if (MainUI.chesses[i][j + a] == -1) {
						for (b = 0; b < 5; b++)
							if (MainUI.chesses[i][j + b] == 0) {
								wpointcan2.x = i;
								wpointcan2.y = j + b;
								break;
							}
					}
			}

		}
		// ///////////////////////////// num[2] "\"
		m = 0;
		if (n == 2) {
			for (k = 0; k < 5; k++)
				if (MainUI.chesses[i + k][j + k] == 0)
					m++;
			if (m == 1)
				for (a = 0; a < 3; a++)
					if (MainUI.chesses[i + a][j + a] == 0) {
						b = MainUI.chesses[i + a + 1][j + a + 1]
								+ MainUI.chesses[i + a + 2][j + a + 2];
						if (b == 2) {
							bpointcan2.x = i + a;
							bpointcan2.y = j + a;
						}
						if (b == -2) {
							wpointcan2.x = i + a;
							wpointcan2.y = j + a;
						}
					}
			if (m == 3) {
				for (a = 0; a < 5; a++)
					if (MainUI.chesses[i + a][j + a] == 1) {
						for (b = 0; b < 5; b++)
							if (MainUI.chesses[i + b][j + b] == 0) {
								bpointcan2.x = i + b;
								bpointcan2.y = j + b;
								break;
							}
					} else if (MainUI.chesses[i + a][j + a] == -1) {
						for (b = 0; b < 5; b++)
							if (MainUI.chesses[i + b][j + b] == 0) {
								wpointcan2.x = i + b;
								wpointcan2.y = j + b;
								break;
							}
					}
			}

		}
		// //////////////////////////// num[3] "/"
		m = 0;
		if (n == 3) {
			for (k = 0; k < 5; k++)
				if (MainUI.chesses[i - k][j + k] == 0)
					m++;
			if (m == 1)
				for (a = 0; a < 3; a++)
					if (MainUI.chesses[i - a][j + a] == 0) {
						b = MainUI.chesses[i - a - 1][j + a + 1]
								+ MainUI.chesses[i - a - 2][j + a + 2];
						if (b == 2) {
							bpointcan2.x = i - a;
							bpointcan2.y = j + a;
						}
						if (b == -2) {
							wpointcan2.x = i - a;
							wpointcan2.y = j + a;
						}
					}
			if (m == 3) {
				for (a = 0; a < 5; a++)
					if (MainUI.chesses[i - a][j + a] == 1) {
						for (b = 0; b < 5; b++)
							if (MainUI.chesses[i - b][j + b] == 0) {
								bpointcan2.x = i - b;
								bpointcan2.y = j + b;
								break;
							}
					} else if (MainUI.chesses[i - a][j + a] == -1) {
						for (b = 0; b < 5; b++)
							if (MainUI.chesses[i - b][j + b] == 0) {
								wpointcan2.x = i - b;
								wpointcan2.y = j + b;
								break;
							}
					}
			}
		}
	}

	public static void searchcandown1(int i, int j, int n) {
		// 计算刚才白棋落棋点
		int ii = vspoint.x;
		int jj = vspoint.y;
		int x1 = new Random().nextInt(4);
		int a;
		int count = 0;
		while (true) {
			x1 = new Random().nextInt(4);
			switch (x1) {
			case 0:
				for (a = 0; a < 15; a++) {
					// 如果不到边界
					if (ii + a < 15) // 水平方向//
					{
						// 向右，如果有空位置
						if (MainUI.chesses[ii + a][jj] == 0) {
							// 在这个位置下黑棋
							bpointcan1.x = ii + a;
							bpointcan1.y = jj;
							return;
						}
					} else
						break;
				}
				for (a = 0; a < ii; a++) {
					// 到了边界
					if (ii - a >= 0)
						// 向左，如果有空位置
						if (MainUI.chesses[ii - a][jj] == 0) {
							bpointcan1.x = ii - a;
							bpointcan1.y = jj;
							return;
						}
				}
				break;

			case 1:
				for (a = 0; a < 15; a++) {
					if (jj + a < 15) // 竖直方向
					{
						// 向右，如果有空位置
						if (MainUI.chesses[ii][jj + a] == 0) {
							// 在这个位置下黑棋
							bpointcan1.x = ii;
							bpointcan1.y = jj + a;
							return;
						}
					} else
						break;
				}

				for (a = 0; a < jj; a++) {
					if (jj - a >= 0)
						// 向左，如果有空位置
						if (MainUI.chesses[ii][jj - a] == 0) {
							bpointcan1.x = ii;
							bpointcan1.y = jj - a;
							return;
						}
				}
				break;
			case 2:
				for (a = 0; a < ii; a++) {
					if (jj + a < 15 && ii - a >= 0) // 左斜方向
					{
						// 向右，如果有空位置
						if (MainUI.chesses[ii - a][jj + a] == 0) {
							// 在这个位置下黑棋
							bpointcan1.x = ii - a;
							bpointcan1.y = jj + a;
							return;
						}
					}
				}
				for (a = 0; a < jj; a++) {
					if (jj - a >= 0 && a + ii < 15) // 左斜方向
					{
						// 向右，如果有空位置
						if (MainUI.chesses[ii + a][jj - a] == 0) {
							// 在这个位置下黑棋
							bpointcan1.x = a + ii;
							bpointcan1.y = jj - a;
							return;
						}
					}
				}
				break;
			case 3:
				for (a = 0; a < ii; a++) {
					if (jj - a >= 0 && ii - a >= 0) // 左斜方向
					{
						// 向右，如果有空位置
						if (MainUI.chesses[ii - a][jj - a] == 0) {
							// 在这个位置下黑棋
							bpointcan1.x = ii - a;
							bpointcan1.y = jj - a;
							return;
						}
					}
				}
				for (a = 0; a + jj < 15 && ii + a < 15; a++) {
					if (jj + a < 15 && a + ii < 15) // 左斜方向
					{
						// 向右，如果有空位置
						if (MainUI.chesses[ii + a][jj + a] == 0) {
							// 在这个位置下黑棋
							bpointcan1.x = a + ii;
							bpointcan1.y = jj + a;
							return;
						}
					}
				}
				break;
			default:

				break;
			}
			count++;
			if (count >= 1) {
				for (int m = 0; m < 15; m++) {
					for (int k = 0; k < 15; k++) {
						if (MainUI.chesses[m][k] == 0) {
							bpointcan1.x = m;
							bpointcan1.y = k;
							return;
						}

					}
				}
			}
		}
	}

	public static void initial() {
		bpointcan4.x = bpointcan4.y = -1;
		wpointcan4.x = wpointcan4.y = -1;
		bpointcan3.x = bpointcan3.y = -1;
		wpointcan3.x = wpointcan3.y = -1;
		bpointcan2.x = bpointcan2.y = -1;
		wpointcan2.x = wpointcan2.y = -1;
		bpointcan1.x = bpointcan1.y = -1;
		// vspoint.x=vspoint.y=-1;
		// blacksetpoint.x=blacksetpoint.y=-1;
	}

	public static void main(String args[]) throws InterruptedException,
			IOException {
		UI = new MainUI();
		bpointcan4 = new Point();
		wpointcan4 = new Point();
		bpointcan3 = new Point();
		wpointcan3 = new Point();
		bpointcan2 = new Point();
		wpointcan2 = new Point();
		bpointcan1 = new Point();
		vspoint = new Point();
		blacksetpoint = new Point();
		blackCLIENT black = new blackCLIENT();
		black.conn2Server("localhost", config1.port);
	}
}
