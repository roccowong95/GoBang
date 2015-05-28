package blackclient;

public class chessNode {
	public int state_score;
	public int flag;// REP or -REP
	public int[][] score;
	public int[][] board;
	public Point currPoint;
	public Point[] tops;

	public int eval_model(int[] model, int color) {
		// TODO: evaluate chess mode here
		return 1;
	}

	public int eval(int x, int y, int color) {
		int[] model = new int[5];
		for (int i = 0; i < 5; i++)
			model[i] = 0;
		int max;
		Integer white_score = -10000, black_score = -10000;

		board[x][y] = config1.BLACK;
		for (int i = Math.max(x - 4, 0); i <= Math.min(x, 14 - 4); i++) {
			// -
			model[0] = board[x][i];
			model[1] = board[x][i + 1];
			model[2] = board[x][i + 2];
			model[3] = board[x][i + 3];
			model[4] = board[x][i + 4];
			black_score = Math.max(black_score,
					eval_model(model, config1.BLACK));
		}
		for (int i = Math.max(y - 4, 0); i <= Math.min(y, 14 - 4); i++) {
			// |
			model[0] = board[i][y];
			model[1] = board[i + 1][y];
			model[2] = board[i + 2][y];
			model[3] = board[i + 3][y];
			model[4] = board[i + 4][y];
			black_score = Math.max(black_score,
					eval_model(model, config1.BLACK));
		}
		for (int j = x - (y - Math.max(y - 4, 0)), i = Math.max(y - 4, 0); j <= Math
				.min(x, 10); i++, j++) {
			// \ upper half
			model[0] = board[i][j];
			model[1] = board[i + 1][j + 1];
			model[2] = board[i + 2][j + 2];
			model[3] = board[i + 3][j + 3];
			model[4] = board[i + 4][j + 4];
			black_score = Math.max(black_score,
					eval_model(model, config1.BLACK));
		}
		for (int j = Math.max(x - 4, 0), i = y - (x - Math.max(x - 4, 0)); i <= Math
				.min(y, 10); i++, j++) {
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
		board[x][y] = 0;
		return 1;
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
				if(board[i][j] == 0)
					eval(i, j, flag);
		}
		
		getTops();
	}

	public void getTops() {
		// tops has N elements
	}
}