package Game;


import java.util.HashSet;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

public class Computer extends Player{

	private Rectangle rect;
	private double speedY = 6;
	
	public Computer(Keyboard keyboard, Rectangle rect, Group root) {
		super(null, rect, root);
		this.rect=rect;
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
	
	private int count = 0;

	/**
	 * Makes the computer makes it move
	 */
	@Override
	public void movePlayer() {
		if(count > 60) {
			shooting(KeyCode.P);
			count = 0;
		}
		else {
			count++;
		}
		
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