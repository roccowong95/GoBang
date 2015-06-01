package blackclient;

import java.util.HashMap;
import java.util.Map;

public class ChessValue {
	
	public Map<String,Integer> chessValue = new HashMap<String, Integer>();
	
	public ChessValue() {
		// TODO Auto-generated constructor stub
		// value for test
		chessValue.put("EAAAAE",3000);
		chessValue.put("EEAEE", 10);
		chessValue.put("AEEEA", 100);
		chessValue.put("EEAAE", 20);
		chessValue.put("EEABE", 15);
	}
	
}
