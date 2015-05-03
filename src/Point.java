public class Point {

	public double x;
	public double y;

	/**
	 * Creates a new Point
	 * 
	 * @param x
	 *            The x-coordinate for this point
	 * @param y
	 *            The y-coordinate for this point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return A String representation of this Point in the format of (x, y)
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
