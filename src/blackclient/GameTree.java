package blackclient;

public class GameTree {
	static public chessNode[] tree;

	static public int getParent(int currPos) {
		return ((currPos - 1) / config1.N);
	}

	static public int getChild(int currPos, int child_index) {
		return (config1.N * currPos + 1 + child_index);
	}

	static public void getTop(int index) {
	}

	static public void GameTree() {
	}
}
