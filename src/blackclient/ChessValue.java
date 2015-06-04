package blackclient;

import java.util.HashMap;
import java.util.Map;

public class ChessValue {

    public Map<String, Integer> chessValue = new HashMap<String, Integer>();

    public ChessValue() {
        // TODO Auto-generated constructor stub
        // value for test
        chessValue.put("AAAAA", 50000);//5

        chessValue.put("EAAAAE", 2000);//4

        chessValue.put("AAAAE", 500);
        chessValue.put("EAAAA", 500);

        chessValue.put("AAEAA", 500);

        chessValue.put("AEAAA", 500);
        chessValue.put("AAAEA", 500);

        chessValue.put("EAAAEE", 300);//3
        chessValue.put("EEAAAE", 300);

        chessValue.put("EAAEAE", 50);
        chessValue.put("EAEAAE", 50);

        chessValue.put("EEAEAE", 10);//2
        chessValue.put("EAEAEE", 10);

        chessValue.put("EEAAEE", 15);

        chessValue.put("EEEAEE", 1);//1
        chessValue.put("EEAEEE", 1);

//		rocco's values;
//        chessValue.put("");
    }

}
