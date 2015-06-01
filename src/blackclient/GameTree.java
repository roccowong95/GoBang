package blackclient;

public class GameTree {
    public chessNode[] tree;

    public int getParent(int currPos) {
        //root is considered as 0
        return ((currPos - 1) / config1.N);
    }

    public int getChildIndex(int currPos, int child_index) {
        return (config1.N * currPos + 1 + child_index);
    }

    public void createChild(int index, int depth) {
        if (depth == 0) {
            tree[index].state_score = tree[index].tops[0].score;
            // tree[index] is leaf ,把state_score设置成最高分的点的分数
            return;
        }
        for (int i = 0; i < config1.N; i++) {
            tree[getChildIndex(index, i)] = new chessNode(tree[index].tops[i].x, tree[index].tops[i].y, tree[index]);
            //treeNode[index]'s i th son
            createChild(getChildIndex(index, i), depth - 1);
            //递归生成子节点
        }
    }


    public GameTree(int x, int y) {
        int n = config1.N;
        int d = config1.Depth;
        int num = 1;
        while (d > 0) {
            num += n;
            n *= config1.N;
            d--;
//            System.out.println(num);
        }
        tree = new chessNode[num];//N=5,D=3
        tree[0] = new chessNode(x, y);
        createChild(0, config1.Depth);
        /*
        for(int i=0;i<tree.length;i++)
		{
			System.out.println(tree[i].currPoint.x+" "+tree[i].currPoint.y+" "+tree[i].currPoint.score);
		}
		*/
    }
}