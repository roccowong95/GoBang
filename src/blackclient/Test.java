
package blackclient;


import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        MainUI ui = new MainUI();
        int x = -1;
        int y = -1;
        Scanner put = new Scanner(System.in);
        while (1 != 0) {
            try {
                x = put.nextInt();
                System.out.println(x);
                y = put.nextInt();
                System.out.println(y);
            } catch (Exception e) {
            }
            MainUI.chesses[x][y] = -config1.REP;
            GameTree gt = new GameTree(x, y);
            Point p = blackCLIENT.maxmin(gt);
            System.out.println(p.x + " " + p.y + " " + p.score);
            MainUI.chesses[p.x][p.y] = config1.REP;

            int count = 0;
            for (int i = 0; i <= config1.Depth; i++) {
                for (int j = 0; j < Math.pow(config1.N, i); j++) {
                    System.out.print(gt.tree[count].currPoint.x + "|" + gt.tree[count].currPoint.y + " ");
                    count++;
                }
                System.out.print("\n");
            }

            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if (MainUI.chesses[i][j] == 0) {
                        System.out.print(" " + "\t");
                    } else if (MainUI.chesses[i][j] == -1) {
                        System.out.print("0" + "\t");
                    } else
                        System.out.print("T" + "\t");
                }
                System.out.println();
            }
        }
        
        /*
        for(int i=0;i<15;i++)
			for(int j=0;j<15;j++)
				//System.out.println("board :" + MainUI.chesses[i][j]);
				//System.out.println(gt.tree[i].currPoint.x+" "+gt.tree[i].currPoint.y+" "+gt.tree[i].currPoint.score);
	   */
    }

}

