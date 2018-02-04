package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	OccupiedBy[][] grid;
	private int size;
	public static final int INROW = 5;

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		grid = new OccupiedBy[size][size];
		this.size = size;
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return 0;
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return this.size;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, OccupiedBy player){
		boolean result = true;
		if (this.grid[x][y] == null) {
			this.grid[x][y] = player;
			this.setChanged();
			this.notifyObservers();
		} else {
			result = false;
		}
		
		
		return result;
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(OccupiedBy player){
		return false;
	}
	
	public static enum OccupiedBy {
		EMPTY, ME, OTHER
	}
}
