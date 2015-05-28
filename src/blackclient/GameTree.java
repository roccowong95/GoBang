package blackclient;

public class GameTree {
	static public chessNode[] tree;

	static public int getParent(int currPos) {
		//root is considered as 0
		return ((currPos - 1) / config1.N);
	}

	static public int getChildIndex(int currPos, int child_index) {
		return (config1.N * currPos + 1 + child_index);
	}

	static public void createChild(int index) {
		for (int i = 0; i < config1.N; i++){
			tree[getChildIndex(index, i)] = new chessNode(tree[index].tops[i].x, tree[index].tops[i].y, tree[index]);
		}
	}
	
	

	public GameTree() {
	}
}
