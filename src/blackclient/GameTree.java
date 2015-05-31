package blackclient;

public class GameTree {
	static public chessNode[] tree;

	public int getParent(int currPos) {
		//root is considered as 0
		return ((currPos - 1) / config1.N);
	}

	public int getChildIndex(int currPos, int child_index) {
		return (config1.N * currPos + 1 + child_index);
	}

	public void createChild(int index,int depth) {
		if(depth==0)
		{
			tree[index].state_score = tree[index].tops[0].score;
			// tree[index] is leaf 
			return;
		}
		for (int i = 0; i < config1.N; i++){
			tree[getChildIndex(index, i)] = new chessNode(tree[index].tops[i].x, tree[index].tops[i].y, tree[index]);
			//treeNode[index]'s i th son
			createChild(getChildIndex(index, i), depth-1);
		}
	}
	
	

	public GameTree(int x,int y) {
		tree = new chessNode[1+5+25+125];//N=5,D=3
		tree[0] = new chessNode(x,y);
		createChild(0, config1.Depth);
	}
}