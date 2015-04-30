import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
		incrementPos(velX, velY);
		double prevXPos = getX();
		double prevYPos = getY();

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

		// Collision with player
		for (Player p : players) {
			double[] col = checkCollisionWithBall(p);
			if (col == null) // No collision with this player
				continue;
			collision = col;
			velX *= -1;
		}

		return false;
	}

	/**
	 * Checks if the ball has collided with a player and calculate where this
	 * happend
	 * 
	 * @param p
	 *            The player to check collision on
	 * @return A double array of the intersecting point where the ball hit the
	 *         player where index 0 being the x-coordinate and 1 the
	 *         y-coordinate, null if no collision
	 */
	private double[] checkCollisionWithBall(Player p) {
		double x = p.getRect().getLayoutX();
		double y = p.getRect().getLayoutY();
		double w = p.getRect().getWidth();
		double h = p.getRect().getHeight();

		double[] res;
		res = getBallLineCollisionPoint(x, y, x + w, y);
		if (res != null)
			for (int i = 0; i < 2; i += 2)
				if (checkPointInPlayer(p, res[i], res[i + 1]))
					return new double[] { res[i], res[i + 1] };
		res = getBallLineCollisionPoint(x, y, x, y + h);
		if (res != null)
			for (int i = 0; i < 2; i += 2)
				if (checkPointInPlayer(p, res[i], res[i + 1]))
					return new double[] { res[i], res[i + 1] };
		res = getBallLineCollisionPoint(x, y + h, x + w, y + h);
		if (res != null)
			for (int i = 0; i < 2; i += 2)
				if (checkPointInPlayer(p, res[i], res[i + 1]))
					return new double[] { res[i], res[i + 1] };
		res = getBallLineCollisionPoint(x + w, y, x + w, y + h);
		if (res != null)
			for (int i = 0; i < 2; i += 2)
				if (checkPointInPlayer(p, res[i], res[i + 1]))
					return new double[] { res[i], res[i + 1] };
		return null; // No collision found
	}

	/**
	 * Checks if the point (x, y) is inside the player
	 * 
	 * @param player
	 *            The player to look at
	 * @param x
	 *            The x-coordinate of the point
	 * @param y
	 *            The y-coordinate of the point
	 * @return true if the point is inside the player
	 */
	private boolean checkPointInPlayer(Player player, double x, double y) {
		Rectangle p = player.getRect();
		if (x >= p.getLayoutX() && x <= p.getLayoutX() + p.getWidth()
				&& y >= p.getLayoutY() && y <= p.getLayoutY() + p.getHeight())
			return true;
		return false;
	}

	/**
	 * Calculates the intersecting point of the line (x1, y1),(x2, y2) and the
	 * ball
	 * 
	 * @param x1
	 *            The x-coordinate for the first point
	 * @param y1
	 *            The y-coordinate for the first point
	 * @param x2
	 *            The x-coordinate for the second point
	 * @param y2
	 *            The y-coordinate for the second point
	 * @return a double array with the first and second index being the first
	 *         intersecting point and the third and forth index being the other
	 *         intersecting point
	 */
	private double[] getBallLineCollisionPoint(double x1, double y1, double x2,
			double y2) {
		double baX = x2 - x1;
		double baY = y2 - y1;
		double caX = getX() - x1;
		double caY = getY() - y1;

		double a = baX * baX + baY * baY;
		double bBy2 = baX * caX + baY * caY;
		double c = caX * caX + caY * caY - getRadius() * getRadius();

		double pBy2 = bBy2 / a;
		double q = c / a;

		double disc = pBy2 * pBy2 - q;
		if (disc < 0)
			return null;

		double abFa1 = -pBy2 + Math.sqrt(disc);
		double abFa2 = abFa1 - 2 * Math.sqrt(disc);

		double rx1 = x1 - baX * abFa1;
		double ry1 = y1 - baY * abFa1;

		if (disc == 0)
			return new double[] { rx1, ry1, -1, -1 };

		double rx2 = x1 - baX * abFa2;
		double ry2 = y1 - baY * abFa2;

		return new double[] { rx1, ry1, rx2, ry2 };
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
	}

	/**
	 * Increments the ball's x-coordinate with the provided value
	 * 
	 * @param value
	 *            The value to increment the x-coordinate with
	 */
	private void incrementX(double value) {
		incrementPos(value, 0);
	}

	/**
	 * Increments the ball's y-coordinate with the provided value
	 * 
	 * @param value
	 *            The value to increment the y-coordinate with
	 */
	private void incrementY(double value) {
		incrementPos(0, value);
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
