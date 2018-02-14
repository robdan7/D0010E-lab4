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
	private HashMapV2i<Node> neighbors;
	private Vector2i position;
	private OccupiedBy value;

	public Node(OccupiedBy e, int x, int y) {
		this.value = e;
		this.position = new Vector2i(x, y);
		this.neighbors = new HashMapV2i<>();
	}

	public int getNeighborCount() {
		return this.getVerticalNeighbors(this) + this.getHorizontalNeighbors(this) + this.getDiagonalNeighbors(this);
	}

	/**
	 * 
	 * @return The longest chain of {@link Node} instances in either a horizontal,
	 *         vertical or diagonal direction. The longest chain will be returned.
	 */
	public int getMaximumChain() {
		int horizontal = this.getHorizontalNeighbors(this);
		int vertical = this.getVerticalNeighbors(this);
		int diagonal = this.getDiagonalNeighbors(this);

		int maximumRow = 0;

		if (horizontal > vertical) {
			if (horizontal > diagonal) {
				maximumRow = horizontal;
			} else {
				maximumRow = vertical;
			}
		} else if (vertical > diagonal) {
			maximumRow = vertical;
		} else {
			maximumRow = diagonal;
		}

		return maximumRow + 1; // +1 -> Include this node.
	}

	public int getX() {
		return this.position.getX();
	}

	public int getY() {
		return this.position.getY();
	}

	/**
	 * Search for horizontal neighbors with the same {@link #getValue()}. The value
	 * could be stored as a variable, but the solution is not required to be fast.
	 * 
	 * @param n
	 *            The previous node.
	 * @return How many horizontal neighbors there are in a row.
	 */
	private int getHorizontalNeighbors(Node n) {
		Vector2i v = this.position.copy(); // Don't mess with the position. Get a copy.
		int result = 0;
		for (int start = v.getX(), x = start - 1; x <= start + 1; x += 2) {
			v.x = x;
			if (this.neighbors.containsKey(v)) {
				Node n2 = this.neighbors.get(v);
				if ((n2.getValue() == this.getValue()) && !n2.equals(n)) {
					result++;
					result += this.neighbors.get(v).getHorizontalNeighbors(this);
				}
			}
		}
		return result;
	}

	/**
	 * Search for horizontal neighbors with the same {@link #getValue()}. The value
	 * could be stored as a variable, but the solution is not required to be fast.
	 * 
	 * @param n
	 *            The previous node.
	 * @return How many vertical neighbors there are in a row.
	 */
	private int getVerticalNeighbors(Node n) {
		Vector2i v = this.position.copy(); // Don't mess with the position. Get a copy.

		int result = 0;
		for (int start = v.getY(), y = start - 1; y <= start + 1; y += 2) {
			v.y = y;
			if (this.neighbors.containsKey(v)) {
				Node n2 = this.neighbors.get(v);
				if ((n2.getValue() == this.getValue()) && !n2.equals(n)) {
					result++;
					result += this.neighbors.get(v).getVerticalNeighbors(this);
				}
			}
		}
		return result;
	}

	/**
	 * Search for horizontal neighbors with the same {@link #getValue()}. The value
	 * could be stored as a variable, but the solution is not required to be fast.
	 * 
	 * @param n
	 *            The previous node.
	 * @return How many diagonal neighbors there are in a row.
	 */
	private int getDiagonalNeighbors(Node n) {
		Vector2i v = this.position.copy();
		int upSlopeResult = 0;
		int downSlopeResult = 0;

		int i = 0; // this takes care of the direction of the diagonal pattern.
		for (int startX = v.getX(), x = startX - 1; x <= startX + 1; x += 2) {
			v.x = x;
			for (int startY = v.getY(), y = startY - 1; y <= startY + 1; y += 2) {
				v.y = y;
				if (this.neighbors.containsKey(v)) {
					Node n2 = this.neighbors.get(v);
					int result = 0;
					if ((n2.getValue() == this.getValue()) && !n2.equals(n)) {
						result++;
						result += this.neighbors.get(v).getDiagonalNeighbors(this);
					}
					if (i == 0 || i == 3) {
						downSlopeResult += result;
					} else {
						upSlopeResult += result;
					}
				}

				i++;
			}
			v.y = this.position.getY(); // v.y has been altered. Reset it.
		}
		return upSlopeResult > downSlopeResult ? upSlopeResult : downSlopeResult;
	}

	public boolean addNeighbor(Node n, int x, int y) {
		Vector2i v = new Vector2i(x, y);
		if (!this.neighbors.containsKey(v)) {
			this.neighbors.put(v, n);
			n.addNeighbor(this, this.position.getX(), this.position.getY());
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return What this instance represents.
	 */
	public OccupiedBy getValue() {
		return this.value;
	}
	
	public static enum OccupiedBy {
		EMPTY, ME, OTHER
	}

	/**
	 * HashMap class with extra features. The keys are instances of {@link Vector2i}
	 * 
	 * @author Robin Danielsson
	 *
	 * @param <V>
	 */
	private class HashMapV2i<V> extends HashMap<Vector2i, V> {
		private static final long serialVersionUID = 1L;

		private ArrayList<Vector2i> keys;

		public HashMapV2i() {
			super();
			this.keys = new ArrayList<>();
		}

		@Override
		public V put(Vector2i v, V obj) {
			if (!this.containsKey(v)) {
				this.keys.add(v);
				super.put(v, obj);
			}
			return obj;
		}

		@Override
		public boolean containsKey(Object key) {
			boolean result = false;
			if (key instanceof Vector2i) {
				Vector2i keycopy = this.getKey((Vector2i) key);
				if (!super.containsKey(keycopy)) {
					this.keys.remove(keycopy);
				} else {
					result = true;
				}
			}
			return result;
		}

		@Override
		public V get(Object key) {
			return super.get(this.getKey((Vector2i) key));
		}

		public Vector2i getKey(Vector2i key) {
			Vector2i k = (Vector2i) key;
			for (Vector2i v : this.keys) {
				if (v.getX() == k.getX() && v.getY() == k.getY()) {
					return v;
				}
			}
			return null;
		}
	}
}
