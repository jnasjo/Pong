package Game;
public class Intersection {

	public double cx, cy, time, nx, ny, ix, iy;

	/**
	 * Creates a new intersection where a collision has occurred
	 * 
	 * @param ix
	 *            The new x-coordinate
	 * @param iy
	 *            The new y-coordinate
	 * @param time
	 *            The time [0..1] from start to end where the collision occurred
	 * @param nx
	 *            The new x-velocity
	 * @param ny
	 *            The new y-velocity
	 */
	public Intersection(double x, double y, double time, double nx, double ny,
			double ix, double iy) {
		this.cx = x;
		this.cy = y;
		this.time = time;
		this.nx = nx;
		this.ny = ny;
		this.ix = ix;
		this.iy = iy;
	}

	/**
	 * Checks if this intersection contains a NaN value
	 * 
	 * @return true if it contains a NaN value
	 */
	public boolean hasNaN() {
		if (Double.isNaN(ix) || Double.isNaN(iy) || Double.isNaN(time)
				|| Double.isNaN(nx) || Double.isNaN(ny))
			return true;
		return false;
	}

	/**
	 * @return A String representation of this intersection
	 */
	public String toString() {
		return "ix: " + ix + ", iy: " + iy + ", time: " + time + ", nx: " + nx
				+ ", ny: " + ny;
	}
}
