package lab4.data;

/**
 * An instance of this class is a single node in a grid.
 * 
 * @author Robin
 *
 */
class Node {
	// Keep track of where in the grid the node is. These variables must not be altered, therefore they are final.
	private final int x, y;

	private OccupiedBy value;

	public Node(OccupiedBy e, int x, int y) {
		this.value = e;
		this.x = x;
		this.y = y;
	}

	/**
	 * find a chain of nodes with the maximum length of {@link GameGrid#INROW}.
	 * 
	 * @param grid
	 *            - The array to search in. An exception is thrown if the starting
	 *            node is not in it.
	 * @return The longest chain of {@link Node} instances in either a horizontal,
	 *         vertical or diagonal direction.
	 */
	public int getMaximumChain(Node[][] grid) {
		if (!grid[this.getX()][this.getY()].equals(this)) {
			throw new IllegalArgumentException("Array does not contain the starting node.");
		}

		int largestChain = 0;
		int temp = 0; // Temporary variable for chain comparison.

		largestChain = this.getHorizontalNeighbors(grid, this, 1);
		if (largestChain == GameGrid.INROW) {
			return largestChain; // n.b: return statement.
		}

		temp = this.getVerticalNeighbors(grid, this, 1);
		if (temp == GameGrid.INROW) {
			return temp; // n.b: return statement.
		} else if (temp > largestChain) {
			largestChain = temp;
		}

		int[] tempArray = this.getDiagonalNeighbors(grid, this, new int[] { 1, 1 });
		temp = tempArray[0] > tempArray[1] ? tempArray[0] : tempArray[1];
		if (temp == GameGrid.INROW) {
			return temp; // n.b: return statement.
		} else if (temp > largestChain) {
			largestChain = temp;
		}

		return largestChain;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	/**
	 * Retrieve a {@link Node} from a two-dimensional array. <b>null</b> is returned if no {@link Node} is found.
	 * @param grid
	 * @param v
	 * @param x
	 * @param y
	 * @return
	 */
	private Node fetchFromGrid(Node[][] grid, int x, int y) {
		try {
			return grid[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Search for horizontal neighbors with the same {@link #getValue()}. The number
	 * of neighbors could be stored as a variable, but the solution is not required
	 * to be fast. <br>
	 * The number of times to search is decided by {@link GameGrid#INROW}.
	 * 
	 * @param n
	 *            - The previous node.
	 * @param totalNeighbors
	 *            - How many horizontal neighbors there are in total. Set to 1 to
	 *            include the starting node.
	 * @param grid
	 *            - The grid to search in. The starting node is presumed to be in
	 *            the grid.
	 * @return How many horizontal neighbors there are in a row.
	 */
	private int getHorizontalNeighbors(Node[][] grid, Node n, int totalNeighbors) {
		if (totalNeighbors == GameGrid.INROW) { // The required neighbors have been found.
			return totalNeighbors;
		}

		for (int x = this.getX() - 1; x <= this.getX() + 1; x += 2) {
			Node neighbor = this.fetchFromGrid(grid, x, this.getY());
			if (neighbor != null) {
				if ((neighbor.getValue() == this.getValue()) && !neighbor.equals(n)) {
					totalNeighbors++;
					// Get the neighbors neighbors.
					totalNeighbors = neighbor.getHorizontalNeighbors(grid, this, totalNeighbors);
				}
			}
		}
		return totalNeighbors;
	}

	/**
	 * Search for vertical neighbors with the same {@link #getValue()}. The number
	 * of neighbors could be stored as a variable, but the solution is not required
	 * to be fast. <br>
	 * The number of times to search is decided by {@link GameGrid#INROW}.
	 * 
	 * @param n
	 *            - The previous node.
	 * @param totalNeighbors
	 *            - How many vertical neighbors there are in total. Set to 1 to
	 *            include the starting node.
	 * @param grid
	 *            - The grid to search in. The starting node is presumed to be in
	 *            the grid.
	 * @return How many vertical neighbors there are in a row.
	 */
	private int getVerticalNeighbors(Node[][] grid, Node n, int totalNeighbors) {
		if (totalNeighbors == GameGrid.INROW) {
			return totalNeighbors;
		}

		for (int y = this.getX() - 1; y <= this.getY() + 1; y += 2) {
			Node neighbor = this.fetchFromGrid(grid, this.getX(), y);
			if (neighbor != null) {
				if ((neighbor.getValue() == this.getValue()) && !neighbor.equals(n)) {
					totalNeighbors++;
					// Get the neighbors neighbors.
					totalNeighbors = neighbor.getVerticalNeighbors(grid, this, totalNeighbors);
				}
			}
		}
		return totalNeighbors;
	}

	/**
	 * Search for diagonal neighbors with the same {@link #getValue()}. The number
	 * of neighbors could be stored as a variable, but the solution is not required
	 * to be fast. <br>
	 * The number of times to search is decided by {@link GameGrid#INROW} minus 1.
	 * 
	 * @param n
	 *            - The previous node.
	 * @param totalNeighbors
	 *            - Two integers containing the number of neighbors found already.
	 *            Set to {1,1} to include the starting node.
	 * @param grid
	 *            - The grid to search in. The starting node is presumed to be in
	 *            the grid.
	 * @return How many diagonal neighbors there are in total. Since there are two
	 *         directions a corresponding array is returned.
	 */
	private int[] getDiagonalNeighbors(Node[][] grid, Node n, int[] totalNeighbors) {
		if (totalNeighbors.length != 2) {
			throw new IllegalArgumentException("Array does not contain two integers");
		} else if ((totalNeighbors[0] == GameGrid.INROW) || (totalNeighbors[1] == GameGrid.INROW)) {
			return new int[] { totalNeighbors[0], totalNeighbors[1] };
		} 

		int loopPattern = 0; // this takes care of the direction of the diagonal pattern.

		for (int x = this.getX() - 1; x <= this.getX() + 1; x += 2) {
			for (int y = this.getY() - 1; y <= this.getY() + 1; y += 2) {
				Node neighbor = this.fetchFromGrid(grid, x,y);
				if (neighbor != null) {
					if ((neighbor.getValue() == this.getValue()) && !neighbor.equals(n)) {
						if ((loopPattern == 0) || (loopPattern == 3)) {
							totalNeighbors[0]++;
						} else {
							totalNeighbors[1]++;
						}
						// Get the neighbors neighbors.
						totalNeighbors = neighbor.getDiagonalNeighbors(grid, this, totalNeighbors);
					}
				}
				loopPattern++;
			}
		}
		return totalNeighbors;
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
}
