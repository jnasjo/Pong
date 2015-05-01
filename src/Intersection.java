public class Intersection {

	public double ix, iy, time, nx, ny;

	public Intersection(double ix, double iy, double time, double nx, double ny) {
		this.ix = ix;
		this.iy = iy;
		this.time = time;
		this.nx = nx;
		this.ny = ny;
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

	public String toString() {
		return "ix: " + ix + ", iy: " + iy + ", time: " + time + ", nx: " + nx
				+ ", ny: " + ny;
	}
}
