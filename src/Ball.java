import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;

public class Ball {

	private Circle ball;
	private AnimationTimer animator;

	private final int CANVAS_HEIGHT;
	private final int CANVAS_WIDTH;

	private double velX, velY;
	
	private final static double START_Y_DIFF = 1.5;
	private final static double START_X_VEL = 5;

	private Random rand;

	/**
	 * Creates a new ball and re-locates the ball to the center of the screen
	 * 
	 * @param ball
	 *            The Circle object connected to this ball
	 */
	public Ball(Circle ball) {
		this.ball = ball;
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

		// Collision with walls
		
		if (getX() - ball.getRadius() < 0) {
			setX(ball.getRadius());
			velX *= -1;

			// PLAYER 1 LOST
		}
		if (getY() - ball.getRadius() < 0) {
			setY(ball.getRadius());
			velY *= -1;
		}
		if (getX() + ball.getRadius() > CANVAS_WIDTH) {
			setX(CANVAS_WIDTH - ball.getRadius());
			velX *= -1;
			
			// PLAYER 2 LOST
		}
		if (getY() + ball.getRadius() > CANVAS_HEIGHT) {
			setY(CANVAS_HEIGHT - ball.getRadius());
			velY *= -1;
		}
		
		// Collision with player

		return false;
	}
	
	private int collision() {
		return 1;
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
	 * @return The current x-coordinate of the ball's layout
	 */
	public double getX() {
		return ball.getLayoutX();
	}

	/**
	 * @return The current y-coordinate of the ball's layout
	 */
	public double getY() {
		return ball.getLayoutY();
	}
	
	public double getRadius()
	{
		return ball.getRadius();
	}
}
