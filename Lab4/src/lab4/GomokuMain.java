package lab4;

import lab4.client.*;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {
		int port;
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			port = 9874;
		}

		GomokuClient c = new GomokuClient(0);
		
		GomokuGameState s = new GomokuGameState(c);
		
		GomokuGUI g = new GomokuGUI(s, c);
	}
}
