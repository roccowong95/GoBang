package blackclient;

import java.util.HashMap;
import java.util.Map;

public class ChessValue {

    public Map<String, Integer> chessValue = new HashMap<String, Integer>();

    public ChessValue() {
        // TODO Auto-generated constructor stub
        // value for test
        chessValue.put("AAAAA", 20000);//5

        chessValue.put("EAAAAE", 9000);//4

        chessValue.put("AAAAE", 2800);
        chessValue.put("EAAAA", 2800);

        chessValue.put("AAEAA", 2600);

        chessValue.put("AEAAA", 2500);
        chessValue.put("AAAEA", 2500);

        chessValue.put("EAAAE", 3000);//3

        chessValue.put("AAEAE", 800);
        chessValue.put("EAEAA", 800);

        chessValue.put("EAEAAE", 800);
        chessValue.put("EAAEAE", 800);

        chessValue.put("AEEAA", 600);
        chessValue.put("AAEEA", 600);

        chessValue.put("AEAEA", 550);
        chessValue.put("AEAEA", 550);

        chessValue.put("AAAEE", 500);
        chessValue.put("EEAAA", 500);

        chessValue.put("EEAEAE", 250);//2
        chessValue.put("EAEAEE", 250);

        chessValue.put("EEAAEE", 650);

        chessValue.put("EAEEAE", 250);

//        chessValue.put("EEEAEE", 0);//1
//        chessValue.put("EEAEEE", 0);

//		rocco's values;
//        chessValue.put("");
    }

}
