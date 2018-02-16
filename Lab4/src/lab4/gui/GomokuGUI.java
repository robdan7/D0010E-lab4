package lab4.gui;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 *
 * @author Robin Danielsson, Zerophymyr Falk
 *
 */

public class GomokuGUI extends JFrame implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	
	private GamePanel gameGridPanel;
	JLabel messageLabel;
	JButton connectButton, newGameButton, disconnectButton;
	JPanel contentPanel;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.setPreferredSize(new Dimension(350,400));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		this.gameGridPanel = new GamePanel(this.gamestate.getGameGrid());
		this.gameGridPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int[] position = gameGridPanel.getGridPosition(e.getX(), e.getY());
				gamestate.move(position[0], position[1]);
			}
		});
		
		
		this.contentPanel = new JPanel();
		this.contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.initButtons();
		this.messageLabel = new JLabel("Welcome to Gomoku!");
		this.contentPanel.add(this.gameGridPanel);
		this.addButtons();
		this.contentPanel.add(this.messageLabel);
		
		this.setContentPane(this.contentPanel);
		
		this.pack();
		this.setVisible(true);
	}
	
	private void initButtons() {
		this.connectButton = new JButton("Connect");
		this.newGameButton = new JButton("New game");
		this.disconnectButton = new JButton("Disconnect");
		this.newGameButton.setEnabled(false);
		this.disconnectButton.setEnabled(false);
		
		this.connectButton.addActionListener(this.createAction(() -> new ConnectionWindow(this.client)));
		this.newGameButton.addActionListener(this.createAction(() -> gamestate.newGame()));
		this.disconnectButton.addActionListener(this.createAction(() -> gamestate.disconnect()));
	}
	
	private void addButtons() {
		this.contentPanel.add(this.connectButton);
		this.contentPanel.add(this.newGameButton);
		this.contentPanel.add(this.disconnectButton);
	}
	
	private ActionListener createAction(Runnable r) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				r.run();
			}
		};
	}
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}