package lab4.data;

import java.util.HashMap;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable {

	private final Node[][] grid;
	private int size;
	public static final int INROW = 5; // Required points to win.

	private HashMap<Node.OccupiedBy, Integer> points; // Keep track of the score for all players.

	public static void main(String[] args) {
		GameGrid grid = new GameGrid(10);
		grid.move(0, 0, Node.OccupiedBy.ME);
		grid.move(1, 0, Node.OccupiedBy.ME);

		grid.move(2, 0, Node.OccupiedBy.ME);

		grid.move(3, 0, Node.OccupiedBy.ME);

		grid.move(4, 0, Node.OccupiedBy.ME);
		
		grid.move(5, 0, Node.OccupiedBy.ME);

		System.out.println(grid.isWinner(Node.OccupiedBy.ME));

	}

	/**
	 * Constructor
	 * 
	 * @param size
	 *            The width/height of the game grid
	 */
	public GameGrid(int size) {
		this.size = size;
		this.grid = new Node[size][size];
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid[x].length; y++) {
				this.grid[x][y] = new Node(Node.OccupiedBy.EMPTY, x, y);
			}
		}

		// Init score counter.
		this.points = new HashMap<>();
		for (Node.OccupiedBy o : Node.OccupiedBy.values()) {
			this.points.put(o, 0);
		}

	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @return the value of the specified location
	 */
	public Node.OccupiedBy getLocation(int x, int y) {
		return this.grid[x][y].getValue();
	}

	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Enters a move in the game grid
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, Node.OccupiedBy player) {
		boolean result = true;
		if (this.getLocation(x, y) == Node.OccupiedBy.EMPTY) {
			this.grid[x][y].setValue(player);

			int newScore = this.grid[x][y].getMaximumChain(this.grid);

			if (newScore > this.points.get(player)) {
				this.points.put(player, newScore);
			}

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
	public void clearGrid() {
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid[x].length; y++) {
				this.grid[x][y].setValue(Node.OccupiedBy.EMPTY);
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
	 * @param player
	 *            the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(Node.OccupiedBy player) {
		if (this.points.get(player) == INROW) {
			return true;
		}
		return false;
	}
}
