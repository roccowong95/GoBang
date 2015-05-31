package blackclient;

import java.util.PriorityQueue;
import java.util.Queue;

public class chessNode {
	public int state_score;
	public int flag;// REP or -REP
	public int[][] score;
	public int[][] board;
	public Point currPoint;
	public Point[] tops;
	public Point[] sons;// 15*15 sons of this node
	public ChessValue chessvalue = new ChessValue();

	public int eval_model(int[] model, int color) {
		// TODO: evaluate chess mode here
		char[] chessMode = new char[7];
		String Mode;
		String subMode;
		for (int i = 0; i < 7; i++) {
			if (model[i] == color)
				chessMode[i] = 'A';//my color
			else if (model[i] == 0)
				chessMode[i] = 'E';//empty
			else
				chessMode[i] = 'B';//other color
		}
		Mode = new String(chessMode);
		if (chessvalue.chessValue.get(Mode) != null)
			return chessvalue.chessValue.get(Mode);
		else {
			subMode = Mode.substring(0, 6);
			if (chessvalue.chessValue.get(subMode) != null)
				return chessvalue.chessValue.get(subMode);
			else {
				subMode = Mode.substring(1, 7);
				if (chessvalue.chessValue.get(subMode) != null)
					return chessvalue.chessValue.get(subMode);
				else {
					subMode = Mode.substring(0, 5);
					if (chessvalue.chessValue.get(subMode) != null)
						return chessvalue.chessValue.get(subMode);
					else {
						subMode = Mode.substring(1, 6);
						if (chessvalue.chessValue.get(subMode) != null)
							return chessvalue.chessValue.get(subMode);
						else {
							subMode = Mode.substring(2, 7);
							if (chessvalue.chessValue.get(subMode) != null)
								return chessvalue.chessValue.get(subMode);
							else
								return 0;
						}
					}
				}
			}
		}
		// hashmap
	}

	public int eval(int x, int y, int color) {
		int[] model = new int[7];
		for (int i = 0; i < 7; i++)
			model[i] = 0;
		Integer white_score = -10000, black_score = -10000;

		// /////////black start////////////
		board[x][y] = config1.BLACK;
		// check about the value of i,j , it might be wrong
		for (int i = Math.max(x - 6, 0); i <= Math.min(x, 14 - 6); i++) {
			// -
			model[0] = board[x][i];
			model[1] = board[x][i + 1];
			model[2] = board[x][i + 2];
			model[3] = board[x][i + 3];
			model[4] = board[x][i + 4];
			black_score = Math.max(black_score,
					eval_model(model, config1.BLACK));
		}
		for (int i = Math.max(y - 6, 0); i <= Math.min(y, 14 - 6); i++) {
			// |
			model[0] = board[i][y];
			model[1] = board[i + 1][y];
			model[2] = board[i + 2][y];
			model[3] = board[i + 3][y];
			model[4] = board[i + 4][y];
			black_score = Math.max(black_score,
					eval_model(model, config1.BLACK));
		}
		if (y <= x)
			for (int i = Math.max(x - 6, 0), j = y - (x - Math.max(x - 6, 0)); j <= Math
					.min(y, 14 - 6); i++, j++) {
				// \ upper half
				model[0] = board[i][j];
				model[1] = board[i + 1][j + 1];
				model[2] = board[i + 2][j + 2];
				model[3] = board[i + 3][j + 3];
				model[4] = board[i + 4][j + 4];
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
				black_score = Math.max(black_score,
						eval_model(model, config1.BLACK));
			}
		// TODO: / direction
		if (y <= 15 - x)
			for (int i = x + (y - Math.max(y - 6, 0)), j = Math.max(y - 6, 0); i <= Math
					.min(x, 14 - 6); i--, j++) {
				// / upper half
				model[0] = board[i][j];
				model[1] = board[i + 1][j + 1];
				model[2] = board[i + 2][j + 2];
				model[3] = board[i + 3][j + 3];
				model[4] = board[i + 4][j + 4];
				black_score = Math.max(black_score,
						eval_model(model, config1.BLACK));
			}
		else
			for (int i = Math.max(x + 6, 14), j = y
					+ Math.abs((x - Math.max(x + 6, 14))); j <= Math.min(y,
					14 - 6); i--, j++) {
				// / lower half
				model[0] = board[i][j];
				model[1] = board[i + 1][j + 1];
				model[2] = board[i + 2][j + 2];
				model[3] = board[i + 3][j + 3];
				model[4] = board[i + 4][j + 4];
				black_score = Math.max(black_score,
						eval_model(model, config1.BLACK));
			}
		// /////////black end////////////
		// /////////white start//////////
		board[x][y] = config1.WHITE;
		// check about the value of i,j , it might be wrong
		for (int i = Math.max(x - 6, 0); i <= Math.min(x, 14 - 6); i++) {
			// -
			model[0] = board[x][i];
			model[1] = board[x][i + 1];
			model[2] = board[x][i + 2];
			model[3] = board[x][i + 3];
			model[4] = board[x][i + 4];
			white_score = Math.max(white_score,
					eval_model(model, config1.WHITE));
		}
		for (int i = Math.max(y - 6, 0); i <= Math.min(y, 14 - 6); i++) {
			// |
			model[0] = board[i][y];
			model[1] = board[i + 1][y];
			model[2] = board[i + 2][y];
			model[3] = board[i + 3][y];
			model[4] = board[i + 4][y];
			white_score = Math.max(white_score,
					eval_model(model, config1.WHITE));
		}
		if (y <= x)
			for (int i = Math.max(x - 6, 0), j = y - (x - Math.max(x - 6, 0)); j <= Math
					.min(y, 14 - 6); i++, j++) {
				// \ upper half
				model[0] = board[i][j];
				model[1] = board[i + 1][j + 1];
				model[2] = board[i + 2][j + 2];
				model[3] = board[i + 3][j + 3];
				model[4] = board[i + 4][j + 4];
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
				white_score = Math.max(white_score,
						eval_model(model, config1.WHITE));
			}
		if (y <= 15 - x)
			for (int i = x + (y - Math.max(y - 6, 0)), j = Math.max(y - 6, 0); i <= Math
					.min(x, 14 - 6); i--, j++) {
				// / upper half
				model[0] = board[i][j];
				model[1] = board[i + 1][j + 1];
				model[2] = board[i + 2][j + 2];
				model[3] = board[i + 3][j + 3];
				model[4] = board[i + 4][j + 4];
				white_score = Math.max(white_score,
						eval_model(model, config1.WHITE));
			}
		else
			for (int i = Math.max(x + 6, 14), j = y
					+ Math.abs((x - Math.max(x + 6, 14))); j <= Math.min(y,
					14 - 6); i--, j++) {
				// / upper half
				model[0] = board[i][j];
				model[1] = board[i + 1][j + 1];
				model[2] = board[i + 2][j + 2];
				model[3] = board[i + 3][j + 3];
				model[4] = board[i + 4][j + 4];
				white_score = Math.max(white_score,
						eval_model(model, config1.WHITE));
			}
		// /////////white end////////////
		board[x][y] = 0;
		// calculate score = my score - other score
		System.out.println("white: "+white_score+" black: "+black_score);
		if (color == config1.WHITE)
			return white_score - black_score;
		else
			return black_score - white_score;
	}

	public chessNode(int x, int y) {
		currPoint.x = x;
		currPoint.y = y;
		flag = config1.REP;
		board = MainUI.chesses.clone();
		score = MainUI.global_score.clone();
		getTops();
	}

	public chessNode(int x, int y, chessNode parent) {
		currPoint.x = x;
		currPoint.y = y;
		flag = -parent.flag;
		board = parent.board.clone();
		board[x][y] = config1.REP;
		score = parent.board.clone();

		for (int i = Math.max(x - 4, 0); i <= Math.min(x + 4, 14); i++) {
			for (int j = Math.max(y - 4, 0); j <= Math.min(y + 4, 14); j++)
				if (board[i][j] == 0)
					// flag->REP , eval(i, j, flag);
					// update score 9×9 around point(x,y) 
					score[i][j] = eval(i, j, config1.REP);

		}
		getTops();
	}

	public void getTops() {
		// tops has N elements , ���������֧
		tops = new Point[config1.N];
		Queue<Point> priority_queue = new PriorityQueue<Point>();
		sons = new Point[15 * 15];
		int index = 0;
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {
				sons[index].setX(i);
				sons[index].setY(j);
				sons[index].setScore(score[i][j]);
				priority_queue.add(sons[index]);
				index++;
			}
		for (int i = 0; i < config1.N; i++)
		{
			tops[i] = priority_queue.poll();
			System.out.println("tops: "+i+" "+tops[i]);
		}

	}
}