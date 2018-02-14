package lab4.data;

import java.util.ArrayList;
import java.util.HashMap;

class Node {	
	private MyHashMap<Node> neighbors;
	private Vector2i position;
	private GameGrid.OccupiedBy value;
	
	public Node(GameGrid.OccupiedBy e, int x, int y) {
		this.value = e;
		this.position = new Vector2i(x,y);
		this.neighbors = new MyHashMap<>();
	}
	
	public int getNeighborCount() {
		return this.getHorizontalNeighbors() + this.getVerticalNeighbors() + this.getSlopenNeighbors();
		//return this.getHorizontalNeighbors();
	}
	
	public int getX() {
		return this.position.getX();
	}
	
	public int getY() {
		return this.position.getY();
	}
	
	private int getHorizontalNeighbors() {
		return this.getHneib(this.position.copy(), 1);

	}

	private int getHneib(Vector2i v, int radius) {
		if (radius == 3) {
			return 0;
		}
		v.x += radius;
		int result = 0;
		if (this.neighbors.containsKey(v)) {
			result ++;
		}
		v.x -= 2*radius;
		if (this.neighbors.containsKey(v)) {
			result ++;
		}
		
		if (result != 0) {
			v.x += radius;
			result += this.getHneib(v, ++radius);
		}
		return result;
	}
	
	private int getVerticalNeighbors() {
		return this.getVneib(this.position.copy(),1 );
	}
	
	private int getVneib(Vector2i v, int radius) {
		if (radius == 3) {
			return 0;
		}
		v.y += radius;
		int result = 0;
		if (this.neighbors.containsKey(v)) {
			result ++;
		}
		v.y -= 2*radius;
		if (this.neighbors.containsKey(v)) {
			result ++;
		}
		if (result != 0) {
			v.y += radius;	
			result += this.getVneib(v, ++radius);
		}
		return result;
	}
	
	private int getSlopenNeighbors() {
		Vector2i v = this.position.copy();
		return this.getSlopeDown(v, 1) + this.getSlopeUp(v, 1);
	}
	
	private int getSlopeDown(Vector2i v, int radius) {
		if (radius == 3) {
			return 0;
		}
		v.x += radius;
		v.y -= radius;
		int result = 0;
		if (this.neighbors.containsKey(v)) {
			result ++;
		}
		v.x -= 2*radius;
		v.y += 2*radius;
		if (this.neighbors.containsKey(v)) {
			result ++;
		}
		if (result != 0) {
			result += this.getSlopeDown(v, radius++);
		}
		return result;
	}
	
	private int getSlopeUp(Vector2i v, int radius) {
		if(radius == 3) {
			return 0;
		}
		v.x += radius;
		v.y += radius;
		int result = 0;
		if (this.neighbors.containsKey(v)) {
			result ++;
		}
		v.x -= 2*radius;
		v.y -= 2*radius;
		if (this.neighbors.containsKey(v)) {
			result ++;
		}
		if (result != 0) {
			result += this.getSlopeDown(v, radius++);
		}
		return result;
	}
	
	public boolean addNeighbor(Node n, int x, int y) {
		Vector2i v = new Vector2i(x,y);
		if (!this.neighbors.containsKey(v)) {
			this.neighbors.put(v, n);
			return true;
		}
		return false;
	}
	
	public GameGrid.OccupiedBy getValue() {
		return this.value;
	}
	
	private class MyHashMap<V> extends HashMap<Vector2i, V> {
		private static final long serialVersionUID = 1L;
		
		private ArrayList<Vector2i> keys;
		
		public MyHashMap() {
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
			if (key instanceof Vector2i) {
				
				Vector2i k = (Vector2i) key;
				for(Vector2i v : this.keys) {
					if (v.getX() == k.getX() && v.getY() == k.getY()) {
						return true;
					}
				}
			}
			return false;
			
		}
	}
}

