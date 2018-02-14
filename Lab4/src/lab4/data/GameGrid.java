package lab4.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	OccupiedBy[][] grid;
	private int[][] neighborGrid;
	private int highestPoints = 0;
	private int size;
	public static final int INROW = 5; // Squares to win.
	
	public static void main(String[] args) {
		Node n1 = new Node(null, 0,0);
		Node n2 = new Node(null, 1,0);
		Node n3 = new Node(null, 0,1);
		n1.addNeighbor(n2, n2.getX(), n2.getY());
		n1.addNeighbor(n3, n3.getX(), n3.getY());
		
		System.out.println(n1.getNeighborCount());
	}

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		this.grid = new OccupiedBy[size][size];
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
	public OccupiedBy getLocation(int x, int y){
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
	public boolean move(int x, int y, OccupiedBy player){
		boolean result = true;
		if (this.getLocation(x, y) == OccupiedBy.EMPTY) {
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
				this.grid[x][y] = OccupiedBy.EMPTY;
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
	public boolean isWinner(OccupiedBy player){
		return false;
	}
	
	public static enum OccupiedBy {
		EMPTY, ME, OTHER
	}
}


class Vector2i {
	int x, y;
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2i copy() {
		return new Vector2i(this.getX(), this.getY());
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vector2i)) {
			return false;
		}
		if ((((Vector2i)o).getX() == this.getX()) && (((Vector2i)o).getY() == this.getY())) {
			return true;
		}
		return false;
	}
}
