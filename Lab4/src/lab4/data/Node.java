package lab4.data;

/**
 * An instance of this class is a single node in a grid.
 * 
 * @author Robin Danielsson, Zerophymyr Falk
 *
 */
public class Node {
	// Keep track of where in the grid the node is. These variables must not be
	// altered, therefore they are final.
	private final int x, y;

	private OccupiedBy value;

	public Node(OccupiedBy e, int x, int y) {
		this.value = e;
		this.x = x;
		this.y = y;
	}

	/**
	 * find a chain of nodes with a specified length.
	 * 
	 * @param grid
	 *            - The array to search in. An exception is thrown if the starting
	 *            node is not in it.
	 * @param rows - The max length.
	 * @return The longest chain of {@link Node} instances in either a horizontal,
	 *         vertical or diagonal direction.
	 */
	public int getMaximumChain(Node[][] grid, int rows) {
		/*
		 * Lite svenska: Vi gjorde en version av denna metod var. En av oss gjorde denna
		 * och den andre på ett helt annat sätt, baserat på det senaste draget som letade reda på noder runt omkring sig. 
		 * Den enda lösningen som båda av oss kunde förstå är den nedan, så den behöll vi. Det är dock självklart att 
		 * den skrotade lösningen var mycket snabbare och generellt bättre. Det är vi enade om.
		 */
		int nextvalue = 1;
		int counter = 1;
		int maxNeighbors = 0;
		loop: for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
				if (grid[x][y].getValue() == this.getValue()) {

					whiledRight: while (counter < rows && x + nextvalue < grid.length && y - nextvalue >= 0) {
						// Diagonal to the upper right.
						if (this.fetchFromGrid(grid, x + nextvalue, y - nextvalue).getValue() == this.getValue()) {
							nextvalue++;
							counter++;
							if (counter == GameGrid.INROW) {
								maxNeighbors = counter;
								break loop;
							}

						} else {
							if (counter > maxNeighbors) {
								maxNeighbors = counter;
							}
							break whiledRight;
						}

					}
					nextvalue = 1;
					counter = 1;

					whiledDown: while (counter < GameGrid.INROW && x - nextvalue >= 0 && y - nextvalue >= 0) {
						// Diagonal to the upper left.
						if (this.fetchFromGrid(grid, x - nextvalue, y - nextvalue).getValue() == this.getValue()) {
							nextvalue++;
							counter++;
							if (counter == rows) {
								maxNeighbors = counter;
								break loop;
							}
						} else {
							if (counter > maxNeighbors) {
								maxNeighbors = counter;
							}
							break whiledDown;
						}
					}
					nextvalue = 1;
					counter = 1;

					whileUp: while (counter < rows && y - nextvalue >= 0) {
						if (this.fetchFromGrid(grid, x, y - nextvalue).getValue() == this.getValue()) { // Vertical
							nextvalue++;
							counter++;
							if (counter == rows) {
								maxNeighbors = counter;
								break loop;
							}
						} else {
							if (counter > maxNeighbors) {
								maxNeighbors = counter;
							}
							break whileUp;
						}
					}
					nextvalue = 1;
					counter = 1;
					whileRight: while (counter < rows && x + nextvalue < grid.length) {
						if (this.fetchFromGrid(grid, x + nextvalue, y).getValue() == this.getValue()) { // Horizontal
							nextvalue++;
							counter++;
							if (counter == rows) {
								maxNeighbors = counter;
								break loop;
							}
						} else {
							if (counter > maxNeighbors) {
								maxNeighbors = counter;
							}
							break whileRight;
						}
					}

				}
			}
		}
		return maxNeighbors;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	/**
	 * Retrieve a {@link Node} from a two-dimensional array. <b>null</b> is returned
	 * if no {@link Node} is found.
	 * 
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
