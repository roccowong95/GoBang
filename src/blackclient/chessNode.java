package blackclient;

public class chessNode {

	static public int score_state;
	static public boolean flag;
	static public int[][] score;
	static public Point currPoint;

	static public int getParent(int currPos) {
		return (currPos - 1) / config1.N;
	}

	static public int getChild(int currPos, int child_index) {
		return (config1.N * currPos + 1 + child_index);
	}

}