package lab4.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An instance of this class is a single node in a grid. When it is connected to
 * other nodes, they can find each other and form a chain of which the length is
 * returned.
 * 
 * @author Robin
 *
 */
class Node {
	// The position lets a node know where on the grid it is. This is easier than to search for the position.
	private final Vector2i position;

	private OccupiedBy value;

	public Node(OccupiedBy e, int x, int y) {
		this.value = e;
		this.position = new Vector2i(x, y);
	}

	/**
	 * 
	 * @return The longest chain of {@link Node} instances in either a horizontal,
	 *         vertical or diagonal direction. The longest chain will be returned.
	 */
	public int getMaximumChain(Node[][] grid) {
		if (!grid[this.position.x][this.position.y].equals(this)) {
			throw new IllegalArgumentException("Array does not contain the starting node.");
		}
		int horizontal = this.getHorizontalNeighbors(grid, this, 0);
		int vertical = this.getVerticalNeighbors(grid, this, 0);
		int[] diagonal = this.getDiagonalNeighbors(grid, this, new int[] { 0, 0 });
		int sumDiagonal = diagonal[0] > diagonal[1] ? diagonal[0] : diagonal[1];
		int maximumRow = 0;

		if (horizontal > vertical) {
			if (horizontal > sumDiagonal) {
				maximumRow = horizontal;
			} else {
				maximumRow = vertical;
			}
		} else if (vertical > sumDiagonal) {
			maximumRow = vertical;
		} else {
			maximumRow = sumDiagonal;
		}

		return maximumRow + 1; // +1 -> Include the starting node.
	}

	public int getX() {
		return this.position.getX();
	}

	public int getY() {
		return this.position.getY();
	}

	private Node fetchFromGrid(Node[][] grid, Vector2i v) {
		try {
			return grid[v.getX()][v.getY()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Search for horizontal neighbors with the same {@link #getValue()}. The value
	 * could be stored as a variable, but the solution is not required to be fast.
	 * <br>
	 * The number of times to search is decided by {@link GameGrid#INROW} minus 1.
	 * The starting node does not count as a neighbor because it is already a part
	 * of the row.
	 * 
	 * @param n
	 *            - The previous node.
	 * @param totalNeighbors
	 *            - How many horizontal neighbors there are in total.
	 * @return How many horizontal neighbors there are in a row.
	 */
	private int getHorizontalNeighbors(Node[][] grid, Node n, int totalNeighbors) {
		if (totalNeighbors == GameGrid.INROW - 1) {
			return 0;
		}
		Vector2i v = this.position.copy(); // Don't mess with the position. Get a copy.
		int result = 0;
		for (int start = v.getX(), x = start - 1; x <= start + 1; x += 2) {
			v.x = x;
			Node neighbor = this.fetchFromGrid(grid, v);
			if (neighbor != null) {
				if ((neighbor.getValue() == this.getValue()) && !neighbor.equals(n)) {
					totalNeighbors++;
					result++;
					// Get the neighbors neighbors.
					result += neighbor.getHorizontalNeighbors(grid, this, totalNeighbors);
				}
			}
		}
		return result;
	}

	/**
	 * Search for vertical neighbors with the same {@link #getValue()}. The value
	 * could be stored as a variable, but the solution is not required to be fast.
	 * <br>
	 * The number of times to search is decided by {@link GameGrid#INROW} minus 1.
	 * The starting node does not count as a neighbor because it is already a part
	 * of the row.
	 * 
	 * @param n
	 *            - The previous node.
	 * @param totalNeighbors
	 *            - How many vertical neighbors there are in total.
	 * @return How many vertical neighbors there are in a row.
	 */
	private int getVerticalNeighbors(Node[][] grid, Node n, int totalNeighbors) {
		if (totalNeighbors == GameGrid.INROW - 1) {
			return 0;
		}
		Vector2i v = this.position.copy(); // Don't mess with the position. Get a copy.

		int result = 0;
		for (int start = v.getY(), y = start - 1; y <= start + 1; y += 2) {
			v.y = y;
			Node neighbor = this.fetchFromGrid(grid, v);
			if (neighbor != null) {
				if ((neighbor.getValue() == this.getValue()) && !neighbor.equals(n)) {
					totalNeighbors++;
					result++;
					// Get the neighbors neighbors.
					result += neighbor.getVerticalNeighbors(grid, this, totalNeighbors);
				}
			}
		}
		return result;
	}

	/**
	 * Search for diagonal neighbors with the same {@link #getValue()}. The value
	 * could be stored as a variable, but the solution is not required to be fast.
	 * <br>
	 * The number of times to search is decided by {@link GameGrid#INROW} minus 1.
	 * The starting node does not count as a neighbor because it is already a part
	 * of the row.
	 * 
	 * @param n
	 *            - The previous node.
	 * @param totalNeighbors
	 *            - Two integers containing the number of neighbors found already.
	 * @return How many diagonal neighbors there are in total. Since there are two
	 *         directions a corresponding array is returned.
	 */
	private int[] getDiagonalNeighbors(Node[][] grid, Node n, int[] totalNeighbors) {
		if ((totalNeighbors[0] == GameGrid.INROW - 1) || (totalNeighbors[1] == GameGrid.INROW - 1)) {
			return new int[] { 0, 0 };
		} else if (totalNeighbors.length != 2) {
			throw new IllegalArgumentException("Array does not contain two integers");
		}

		Vector2i v = this.position.copy(); // Don't mess with the position. Get a copy.

		int[] result = new int[2];
		int loopPattern = 0; // this takes care of the direction of the diagonal pattern.

		int startX = v.getX();
		int startY = v.getY();
		for (int x = startX - 1; x <= startX + 1; x += 2) {
			v.x = x;
			for (int y = startY - 1; y <= startY + 1; y += 2) {
				v.y = y;
				Node neighbor = this.fetchFromGrid(grid, v);
				if (neighbor != null) {
					if ((neighbor.getValue() == this.getValue()) && !neighbor.equals(n)) {
						if ((loopPattern == 0) || (loopPattern == 3)) {
							totalNeighbors[0]++;
							result[0] ++;
						} else {
							totalNeighbors[1]++;
							result[1] ++;
						}
						// Get the neighbors neighbors.
						int[] temp = neighbor.getDiagonalNeighbors(grid, this, totalNeighbors);
						
						// Add the recursive result to the next one.
						result[0] += temp[0];
						result[1] += temp[1];
					}
				}
				loopPattern++;
			}
			v.y = startY; // v.y has been altered. Reset it.
		}
		return result;
	}

	/**
	 * 
	 * @return What this instance represents.
	 */
	public OccupiedBy getValue() {
		return this.value;
	}

	public void setValue(OccupiedBy o) {
		this.value = o;
	}

	public static enum OccupiedBy {
		EMPTY, ME, OTHER
	}

	/**
	 * Vector with two integers.
	 * 
	 * @author Robin Danielsson
	 *
	 */
	private class Vector2i {
		private int x, y;

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
	}
}
