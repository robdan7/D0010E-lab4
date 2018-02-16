package lab4;

import lab4.client.*;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {
		int port;
		if (args.length > 1) {
			throw new IllegalArgumentException("two arguments!");
		}
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			port = 9874;
		}

		init(port);
	}
	
	public static void init(int i) {
		GomokuClient c = new GomokuClient(i);
		
		GomokuGameState s = new GomokuGameState(c);
		
		GomokuGUI g = new GomokuGUI(s, c);
	}
}
