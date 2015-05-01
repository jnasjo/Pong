import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.shape.Circle;

public class Ball {

	private Circle ball;
	private AnimationTimer animator;

	private final int CANVAS_HEIGHT;
	private final int CANVAS_WIDTH;

	private double velX, velY;

	private final static double START_Y_DIFF = 1.5;
	private final static double START_X_VEL = 5;

	private Player[] players;

	private Random rand;

	// DEV THINGS, REMOVE L8ER
	private double[] collision = new double[2];

	/**
	 * Creates a new ball and re-locates the ball to the center of the screen
	 * 
	 * @param ball
	 *            The Circle object connected to this ball
	 */
	public Ball(Circle ball, Player... player) {
		this.ball = ball;
		this.players = player;
		CANVAS_HEIGHT = Main.CANVAS_HEIGHT;
		CANVAS_WIDTH = Main.CANVAS_WIDTH;
		rand = new Random();

		reset();

		animator = new AnimationTimer() {
			@Override
			public void handle(long now) {
				animate(now);
			}
		};
		start();
	}

	// DEV, REMOVE L8ER
	public double[] colCoords() {
		return collision;
	}

	/**
	 * Animates the ball and does various checks such as collision
	 * 
	 * @param now
	 *            The current time in nanoseconds
	 */
	private void animate(long now) {
		moveBall();
	}

	/**
	 * Moves the ball and does collision checking
	 * 
	 * @return false if the ball went of the edge
	 */
	private boolean moveBall() {
		// Where we are and where we're going
		Point start = new Point(getX(), getY());
		Point end = new Point(getX() + velX, getY() + velY);

		// Collision with player
		for (Player p : players) {

			Intersection intersect = checkCollision(start, end, p);
			if (intersect != null && !intersect.hasNaN()) {
				double c = (velX * velX + velY * velY)
						/ (intersect.nx * intersect.nx + intersect.ny
								* intersect.ny);
				c = Math.sqrt(c);
				velX = intersect.nx * c;
				velY = intersect.ny * c;
				setPos(intersect.ix, intersect.iy);
				System.out.println(intersect);
				collision = new double[] { intersect.ix, intersect.iy };
				break;
			}
		}

		// Move ball
		incrementPos(velX, velY);

		// Collision with walls

		if (getX() - getRadius() < 0) {
			setX(getRadius());
			velX *= -1;

			// PLAYER 1 LOST
		}
		if (getY() - getRadius() < 0) {
			setY(getRadius());
			velY *= -1;
		}
		if (getX() + getRadius() > CANVAS_WIDTH) {
			setX(CANVAS_WIDTH - getRadius());
			velX *= -1;

			// PLAYER 2 LOST
		}
		if (getY() + getRadius() > CANVAS_HEIGHT) {
			setY(CANVAS_HEIGHT - getRadius());
			velY *= -1;
		}

		return false;
	}

	/**
	 * Clamps input between -1 and 1 [-1, 1]
	 * 
	 * @param value
	 *            value to be clamped
	 * @return The clamped value
	 */
	private double clamp(double value) {
		if (value < -1)
			return -1;
		else if (value > 1)
			return 1;
		return value;
	}

	private Intersection checkCollision(Point start, Point end, Player p) {
		Bounds b = p.getRect().getBoundsInParent();
		double L = b.getMinX(); // Min x-coordinate
		double T = b.getMinY(); // Min y-coordinate
		double R = b.getMaxX(); // Max x-coordinate
		double B = b.getMaxY(); // Max y-coordinate
		double dx = end.x - start.x;
		double dy = end.y - start.y;
		double radius = getRadius();

		double ltime = Double.MAX_VALUE;
		double rtime = Double.MAX_VALUE;
		double ttime = Double.MAX_VALUE;
		double btime = Double.MAX_VALUE;

		if (start.x - radius < L && end.x + radius > L)
			ltime = (L - radius - start.x) / dx;
		if (start.x + radius > R && end.x - radius < R)
			rtime = (start.x - R - radius) / -dx;
		if (start.y - radius < T && end.y + radius > T)
			ttime = (T - radius - start.y) / dy;
		if (start.y + radius > B && end.y - radius < B)
			btime = (start.y - B - radius) / -dy;

		if (ltime >= 0.0f && ltime <= 1.0f) {
			double ly = dy * ltime + start.y;
			if (ly >= T && ly <= B) {
				return new Intersection(dx * ltime + start.x, ly, ltime, -1, 0);
			}
		} else if (rtime >= 0.0f && rtime <= 1.0f) {
			double ry = dy * rtime + start.y;
			if (ry >= T && ry <= B) {
				return new Intersection(dx * rtime + start.x, ry, rtime, 1, 0);
			}
		}

		if (ttime >= 0.0f && ttime <= 1.0f) {
			double tx = dx * ttime + start.x;
			if (tx >= L && tx <= R) {
				return new Intersection(tx, dy * ttime + start.y, ttime, 0, -1);
			}
		} else if (btime >= 0.0f && btime <= 1.0f) {
			double bx = dx * btime + start.x;
			if (bx >= L && bx <= R) {
				return new Intersection(bx, dy * btime + start.y, btime, 0, 1);
			}
		}

		// If there was a collision it was with a corner

		double cornerX = Double.MAX_VALUE;
		double cornerY = Double.MAX_VALUE;

		if (ltime != Double.MAX_VALUE)
			cornerX = L;
		else if (rtime != Double.MAX_VALUE)
			cornerX = R;

		if (ttime != Double.MAX_VALUE)
			cornerY = T;
		else if (btime != Double.MAX_VALUE)
			cornerY = B;

		// Account for the times where we don't pass over a side but we do hit
		// it's corner
		if (cornerX != Double.MAX_VALUE && cornerY == Double.MAX_VALUE)
			cornerY = dy > .0d ? B : T;
		if (cornerY != Double.MAX_VALUE && cornerX == Double.MAX_VALUE)
			cornerX = dx > .0d ? R : L;

		double invRadius = 1 / radius;
		double lineLength = Math.sqrt(dx * dx + dy * dy);
		double cornerDx = cornerX - start.x;
		double cornerDy = cornerY - start.y;
		double cornerDist = Math
				.sqrt(cornerDx * cornerDx + cornerDy * cornerDy);
		double v1 = (cornerDx * dx + cornerDy * dy);
		double innerAngle;
		if (v1 == Double.POSITIVE_INFINITY
				&& cornerDist == Double.POSITIVE_INFINITY
				|| v1 == Double.NEGATIVE_INFINITY
				&& cornerDist == Double.NEGATIVE_INFINITY)
			innerAngle = 1;
		else if (Double.isInfinite(v1) && Double.isInfinite(cornerDist))
			innerAngle = -1;
		else
			innerAngle = v1 / lineLength / cornerDist;
		innerAngle = clamp(innerAngle);
		innerAngle = Math.acos(innerAngle);
		double innerAngleSin = Math.sin(innerAngle);
		double angle1Sin;
		if (innerAngleSin == 0 && Double.isInfinite(cornerDist))
			angle1Sin = 0;
		else
			angle1Sin = innerAngleSin * cornerDist * invRadius;

		if (Math.abs(angle1Sin) > 1.0d) // Angle too large, no collision
			return null;

		double angle1 = Math.PI - Math.asin(angle1Sin);
		double angle2 = Math.PI - innerAngle - angle1;
		double intersectionDist = radius * Math.sin(angle2) / innerAngleSin;

		double time = intersectionDist / lineLength;
		if (time > 1.0d || time < .0d)
			return null;

		double ix = time * dx + start.x;
		double iy = time * dy + start.y;
		double nx = (ix - cornerX) * invRadius;
		double ny = (iy - cornerY) * invRadius;

		return new Intersection(ix, iy, time, nx, ny);
	}

	/**
	 * Stops the ball
	 */
	public void stop() {
		animator.stop();
	}

	/**
	 * Starts/continues the ball
	 */
	public void start() {
		animator.start();
	}

	/**
	 * Moves the ball to the middle of the canvas
	 */
	public void reset() {
		setPos(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
		velX = rand.nextBoolean() ? -START_X_VEL : START_X_VEL;
		velY = rand.nextDouble() * (rand.nextBoolean() ? -1 : 1) * START_Y_DIFF;
		// velX = 0.7075466219424853;
		// velY = 0.7066666666666667;
	}

	/**
	 * Increments the ball's position with values
	 * 
	 * @param x
	 *            The value to increment the x-coordinate with
	 * @param y
	 *            The value to increment the y-coordinate with
	 */
	private void incrementPos(double x, double y) {
		setPos(getX() + x, getY() + y);
	}

	/**
	 * Set's the new x-coordinate of the ball
	 * 
	 * @param x
	 *            The ball's new x-coordinate
	 */
	private void setX(double x) {
		setPos(x, getY());
	}

	/**
	 * Set's the new y-coordinate of the ball
	 * 
	 * @param y
	 *            The ball's new y-coordinate
	 */
	private void setY(double y) {
		setPos(getX(), y);
	}

	/**
	 * Set's the ball's postition to the provided values
	 * 
	 * @param x
	 *            The ball's new x-coordinate
	 * @param y
	 *            The ball's new y-coordinate
	 */
	private void setPos(double x, double y) {
		ball.setLayoutX(x);
		ball.setLayoutY(y);
	}

	/**
	 * @return The balls radius
	 */
	private double getRadius() {
		return ball.getRadius();
	}

	/**
	 * @return The current x-coordinate of the ball's layout
	 */
	private double getX() {
		return ball.getLayoutX();
	}

	/**
	 * @return The current y-coordinate of the ball's layout
	 */
	private double getY() {
		return ball.getLayoutY();
	}
}
