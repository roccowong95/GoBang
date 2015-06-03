package blackclient;

import java.util.HashMap;
import java.util.Map;

public class ChessValue {

    public Map<String, Integer> chessValue = new HashMap<String, Integer>();

    public ChessValue() {
        // TODO Auto-generated constructor stub
        // value for test
        chessValue.put("AAAAA", 50000);//5

        chessValue.put("EAAAAE", 4320);//4

        chessValue.put("AAAAE", 2000);
        chessValue.put("EAAAA", 2000);

        chessValue.put("AAEAA", 2000);

        chessValue.put("AEAAA", 2000);
        chessValue.put("AAAEA", 2000);

        chessValue.put("EAAAEE", 1050);//3
        chessValue.put("EEAAAE", 1050);

        chessValue.put("EAAEAE", 1000);
        chessValue.put("EAEAAE", 1000);

        chessValue.put("EEAEAE", 130);//2
        chessValue.put("EAEAEE", 130);

        chessValue.put("EEAAEE", 140);

        chessValue.put("EEEAEE", 20);//1
        chessValue.put("EEAEEE", 20);

//		rocco's values;
//        chessValue.put("");
    }

}
