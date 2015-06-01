package blackclient;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MainUI ui = new MainUI();
        MainUI.chesses[7][7] = -config1.REP;//模拟对方下了一步棋
        GameTree gt = new GameTree(7, 7);

		/*
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++)
				//System.out.println("board :" + MainUI.chesses[i][j]);
				//System.out.println(gt.tree[i].currPoint.x+" "+gt.tree[i].currPoint.y+" "+gt.tree[i].currPoint.score);
	    */
    }

}
