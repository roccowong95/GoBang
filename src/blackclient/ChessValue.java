package blackclient;

import java.util.HashMap;
import java.util.Map;

public class ChessValue {
	
	public Map<String,Integer> chessValue = new HashMap<String, Integer>();
	
	public ChessValue() {
		// TODO Auto-generated constructor stub
		// value for test
		chessValue.put("AAAAA", 50000);

		chessValue.put("EAAAEE", 720);
		chessValue.put("EEAAAE", 720);

		chessValue.put("EAAAAE",4320);

		chessValue.put("EAAEAE",720);
		chessValue.put("EAEAAE",720);

		chessValue.put("AAAAE",720);
		chessValue.put("EAAAA",720);

		chessValue.put("AAEAA",720);

		chessValue.put("AEAAA",720);
		chessValue.put("AAAEA",720);

		chessValue.put("EEAEAE",120);
        chessValue.put("EAEAEE",120);

        chessValue.put("EEAAEE",120);

		chessValue.put("EEEAEE",20);
        chessValue.put("EEAEEE",20);

//		rocco's values;
//        chessValue.put("");
	}
	
}
