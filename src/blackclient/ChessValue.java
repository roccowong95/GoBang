package blackclient;

import java.util.HashMap;
import java.util.Map;

public class ChessValue {

    public Map<String, Integer> chessValue = new HashMap<String, Integer>();

    public ChessValue() {
        // TODO Auto-generated constructor stub
        // value for test
        chessValue.put("AAAAA", 100000);//5

        chessValue.put("EAAAAE", 9000);//4

        chessValue.put("AEAEAEA", 1300);
        chessValue.put("AAAAE", 1200);
        chessValue.put("EAAAA", 1200);

        chessValue.put("AAEAA", 1200);

        chessValue.put("AEAAA", 1200);
        chessValue.put("AAAEA", 1200);

        chessValue.put("EAAAE", 1200);//3

        chessValue.put("AAEAE", 800);
        chessValue.put("EAEAA", 800);

        chessValue.put("EAEAAE", 800);
        chessValue.put("EAAEAE", 800);
        chessValue.put("EAEAEAE", 800);

        chessValue.put("AEEAA", 600);
        chessValue.put("AAEEA", 600);

        chessValue.put("AEAEA", 550);
        chessValue.put("AEAEA", 550);

        chessValue.put("AAAEE", 500);
        chessValue.put("EEAAA", 500);

        chessValue.put("EEAEAE", 200);//2
        chessValue.put("EAEAEE", 200);

        chessValue.put("EEAAEE", 550);

        chessValue.put("EAEEAE", 200);

//        chessValue.put("EEEAEE", 0);//1
//        chessValue.put("EEAEEE", 0);

//		rocco's values;
//        chessValue.put("");
    }

}
