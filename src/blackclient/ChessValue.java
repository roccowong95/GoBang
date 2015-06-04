package blackclient;

import java.util.HashMap;
import java.util.Map;

public class ChessValue {

    public Map<String, Integer> chessValue = new HashMap<String, Integer>();

    public ChessValue() {
        // TODO Auto-generated constructor stub
        // value for test
        chessValue.put("AAAAA", 5000);//5

        chessValue.put("EAAAAE", 5000);//4

        chessValue.put("AAAAE", 2000);
        chessValue.put("EAAAA", 2000);

        chessValue.put("AAEAA", 2000);

        chessValue.put("AEAAA", 2000);
        chessValue.put("AAAEA", 2000);

        chessValue.put("EAAAE", 350);//3
        chessValue.put("EAAAE", 350);

        chessValue.put("EAAEAE", 350);
        chessValue.put("EAEAAE", 350);

        chessValue.put("AAAEE", 35);
        chessValue.put("EEAAA", 35);

        chessValue.put("EEAEAE", 20);//2
        chessValue.put("EAEAEE", 20);

        chessValue.put("EEAAEE", 35);

        chessValue.put("EAEEAE", 20);

//        chessValue.put("EEEAEE", 0);//1
//        chessValue.put("EEAEEE", 0);

//		rocco's values;
//        chessValue.put("");
    }

}
