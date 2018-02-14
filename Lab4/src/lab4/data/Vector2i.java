package lab4.data;

/**
 * Vector with two integers.
 * @author Robin Danielsson
 *
 */
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
}
