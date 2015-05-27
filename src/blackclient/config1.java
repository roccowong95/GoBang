package blackclient;

public class config1 {
       public static final int X0=30;//表格左上角起点的x值
       public static final int Y0=30;//表给我右上角起点的y值
       public static final int ROWS=15;//棋盘的横数
       public static final int COLUMNS=15;//棋盘的列数
       public static int CHESS_SIZE=20;//棋子大小
       public static int SIZE=30;//单元格大小
       public static int port=9090;
       public static byte FIRST=0;
       public static byte SECOND=7;
       public static byte MOVE=1;
       public static byte OVER=2;
       public static byte TURN=3;
       public static byte TIE=4;
       public static byte REPBLACK=5;
       public static byte REPWHITE=6;
       public static byte ABERRATION=8;
       public static boolean EXIT=false;
       public static byte BLACK=1;
       public static byte WHITE=-1;
       public static int REP;
       public static final int N = 5;//number of branches in each level of the game tree
       public static final int Depth = 3;//depth of game tree
}
