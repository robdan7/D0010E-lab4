/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
	
    //Possible game states
	private GameState currentState;
	
	private GomokuClient client;
	
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = GameState.NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		return this.message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return this.gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		boolean move = false;
		if (this.currentState == GameState.MY_TURN) {
			move = this.gameGrid.move(x, y, Node.OccupiedBy.ME);
		} else {
			this.setMessage("It's not your turn.");
		}
		
		if (move) {
			this.client.sendMoveMessage(x, y);
			this.setMessage("your turn is over.");
			
			if (this.gameGrid.isWinner(Node.OccupiedBy.ME)) {
				this.currentState = GameState.FINISHED;
				this.setMessage("Congrats, you won!");
			} else {
				this.currentState = GameState.OTHERS_TURN;
			}
			
			this.updateChanges();
		}
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		if (client.getConnectionStatus() != 0) {
			this.gameGrid.clearPoints();
			this.gameGrid.clearGrid();
			this.currentState = GameState.OTHERS_TURN;
			this.client.sendNewGameMessage();
			this.setMessage("New game created.");
			this.updateChanges();
		} else {
			this.setMessage("No connection was found. Can't create game.");
		}
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly.
	 */
	public void receivedNewGame(){
		this.gameGrid.clearPoints();
		this.gameGrid.clearGrid();
		this.currentState = GameState.MY_TURN;
		this.setMessage("Other guy created a game.");
		this.updateChanges();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted.
	 */
	public void otherGuyLeft(){
		this.gameGrid.clearGrid();
		this.currentState = GameState.NOT_STARTED;
		this.setMessage("Opponent left.");
		this.updateChanges();
	}
	
	/**
	 * The player disconnects from the client.
	 */
	public void disconnect(){
		if(this.client.getConnectionStatus() != 0) {
			this.client.disconnect();
			this.gameGrid.clearGrid();
			this.setMessage("You have disconnected.");
			this.currentState = GameState.NOT_STARTED;
			this.updateChanges();
		} else {
			this.setMessage("You are already disconnected");
		}
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		this.gameGrid.move(x, y, Node.OccupiedBy.OTHER);
		if (this.gameGrid.isWinner(Node.OccupiedBy.OTHER)) {
			this.currentState = GameState.FINISHED;
			this.setMessage("Other player won");
		} else {
			this.setMessage("Your turn.");
			this.currentState = GameState.MY_TURN;
		}
		
		this.updateChanges();
	}
	
	public void update(Observable o, Object arg) {
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			this.currentState = GameState.MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			this.currentState = GameState.OTHERS_TURN;
			break;
		}
		this.updateChanges();
	}
	
	private void updateChanges() {
		this.setChanged();
		this.notifyObservers();
	}
	
	private void setMessage(String message) {
		this.message = message;
		this.updateChanges();
	}
	
	private enum GameState {
		NOT_STARTED, MY_TURN, OTHERS_TURN, FINISHED
	}
	
}
