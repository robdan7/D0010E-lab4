package lab4;

import lab4.client.*;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {
		int port;
		if (args.length > 1) {
			
		}
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			port = 9874;
		}

		init(7000);
		init(8000);
	}
	
	public static void init(int i) {
		GomokuClient c = new GomokuClient(i);
		
		GomokuGameState s = new GomokuGameState(c);
		
		GomokuGUI g = new GomokuGUI(s, c);
	}
}
