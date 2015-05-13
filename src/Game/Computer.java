package Game;


import java.util.HashSet;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Computer extends Player{

	private Rectangle rect;
	private double speedY = 6;

	private Set<Ball> balls;
	
	public Computer(Keyboard keyboard, Rectangle rect, Group root) {
		super(null, rect, null);
		balls = new HashSet<Ball>();
		this.rect=rect;
	}

	

	/**
	 * Creates a new player that is controlled by the computer
	 * 
	 * @param rect
	 *            The computers pad
	 */
	//public Player(Keyboard keyboard, Rectangle rect, Group root)
	

	/**
	 * Adds a ball to this pads "awareness"
	 * 
	 * @param ball
	 *            The ball to add
	 */
	public void setBall(Ball ball) {
		this.balls.add(ball);
	}

	/**
	 * Set's the pads position
	 * 
	 * @param x
	 *            The new x-coordinate
	 * @param y
	 *            The new y-coordinate
	 */
	public void setPos(double x, double y) {
		rect.setLayoutX(x);
		rect.setLayoutY(y);
	}

	/**
	 * @return The pads x-coordinate
	 */
	public double getX() {
		return rect.getLayoutX();
	}

	/**
	 * @return The pads y-coordinate
	 */
	public double getY() {
		return rect.getLayoutY();
		
	}

	/**
	 * Makes the computer makes it move
	 */
	@Override
	public void movePlayer() {
		double shortestDistance = Double.MAX_VALUE;
		Ball closestBall = null;
		for (Ball ball : balls) {
			if (getX() - ball.getX() < shortestDistance) {
				closestBall = ball;
				shortestDistance = getX()  - ball.getX();
			}
		}
		double moveDistance = getY() + rect.getHeight() / 2;
		moveDistance = closestBall.getY() - moveDistance;
		if (moveDistance > speedY)
			moveDistance = speedY;
		else if (moveDistance < -speedY)
			moveDistance = -speedY;
		setPos(getX(), getY() + moveDistance);
	
		if (getY() < 0)
			setPos(getX(), 0);
		else if (getY()  > Game.CANVAS_HEIGHT - rect.getHeight())
			setPos(getX(),Game.CANVAS_HEIGHT - rect.getHeight() );

	
	}
}