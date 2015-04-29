import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;

public class Ball {

	private Circle ball;
	private AnimationTimer animator;

	private final int CANVAS_HEIGHT;
	private final int CANVAS_WIDTH;
	
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

		reset();
		
		animator = new AnimationTimer() {
			@Override
			public void handle(long now) {
				animate(now);
			}
		};
		start();
	}

	private void animate(long now) {
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
	  // SOMETHING IS WRONG SOMEWHERE HERA
		System.out.println(ball.getLayoutX()+", "+ball.getTranslateX()+", "+ball.getCenterX());
		setPos(CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
		System.out.println(CANVAS_WIDTH/2);
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
