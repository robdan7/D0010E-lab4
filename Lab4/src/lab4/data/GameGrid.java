package lab4.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	Node.OccupiedBy[][] grid;
	private int[][] neighborGrid;
	private int highestPoints = 0;
	private int size;
	public static final int INROW = 5; // Squares to win.

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		this.grid = new Node.OccupiedBy[size][size];
		this.neighborGrid = new int[size][size];
		this.size = size;
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public Node.OccupiedBy getLocation(int x, int y){
		return this.grid[x][y];
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
	public boolean move(int x, int y, Node.OccupiedBy player){
		boolean result = true;
		if (this.getLocation(x, y) == Node.OccupiedBy.EMPTY) {
			this.grid[x][y] = player;
			this.updateNeighbors(x, y);
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
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid[x].length; y++) {
				this.grid[x][y] = Node.OccupiedBy.EMPTY;
			}
		}
		this.setChanged();
		this.notifyObservers();
	}
	
	private void updateNeighbors(int x, int y) {
		if ((x == 0) || (x == this.size)) {
			this.checkX(-1);
		}
	}
	
	private boolean checkX(int direction) {
		return false;
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(Node.OccupiedBy player){
		return false;
	}
}
