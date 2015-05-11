import java.util.HashSet;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {

	private Rectangle self;
	private Keyboard keyboard;

	private Group root;

	private double xMove = 1;
	private double yMove = 1;
	private final static int startSpeedX = 4;
	private final static int startSpeedY = 4;
	private static int speedX = 4;
	private static int speedY = 4;

	private int[][] animator;

	// BAAAD lsning
	private Set<Ball> balls;

	public Player(Keyboard keyboard, Rectangle rect, Group root) {
		this.root = root;
		this.self = rect;
		this.keyboard = keyboard;
		xMove = rect.getLayoutX();
		yMove = rect.getLayoutY();
		balls = new HashSet<Ball>();

		// UP, DOWN, LEFT, RIGHT
		animator = new int[][] { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
	}

	// DEV
	public String getSpeedDEV() {
		return "" + xMove + ", " + yMove;
	}

	/**
	 * Set's this players velocity
	 * 
	 * @param move
	 *            If the player should start or stop moving
	 * @param i
	 *            The index of the key corresponding with the movement
	 */
	public void setVel(boolean move, int i) {
		if(move) {
			speedX = animator[i][0] * startSpeedX;
			speedY = animator[i][1] * startSpeedY;
		} else {
			if(speedX == animator[i][0] * startSpeedX)
				speedX = 0;
			if(speedY == animator[i][1] * startSpeedY)
				speedY = 0;
		}
	}

	/**
	 * Handles input from the user
	 * 
	 * @param event
	 */
	public void handle(KeyEvent event) {
		Set<KeyCode> keys = keyboard.handle(event);

		// Move player
		boolean x = false, y = false;
		for (int i = 0; i < animator.length; i++) {
			if (keys.contains(keyboard.get(i))) {
				if (animator[i][0] != 0) {
					speedX = animator[i][0] * startSpeedX;
					x = true;
				} else if (animator[i][1] != 0) {
					speedY = animator[i][1] * startSpeedY;
					y = true;
				}
			}
		}
		if (!x)
			speedX = 0;
		if (!y)
			speedY = 0;

		for (KeyCode key : keys) {

			// WE BE FIRERIN' THE LAZORRZ
			if (key == keyboard.getFire()) {
				Shape sp = new Shape(root);
				@SuppressWarnings("unused")
				Rectangle shoot;

				new AnimationTimer() {
					Rectangle shoot = sp.drawRectangle(
							(int) self.getLayoutX() - 20,
							(int) self.getLayoutY() - 20, 20, 20, Color.ORANGE);
					int ShootspeedX = 4;

					@Override
					public void handle(long args0) {
						shoot.setLayoutX(self.getLayoutX());
						shoot.setLayoutY(self.getLayoutY());

						ShootspeedX += 5;
						shoot.setLayoutX(self.getLayoutX() + ShootspeedX);

						shoot.setLayoutY(120 * Math.sin(shoot.getLayoutX() / 50));

						if (ShootspeedX > Game.CANVAS_WIDTH) {
							root.getChildren().remove(shoot);
							ShootspeedX = (int) self.getLayoutX()
									- (int) self.getLayoutX() - 5;
						}
					}
				}.start();
			}
		}
	}

	/**
	 * @return The rectangle, or pad, of this player
	 */
	public Rectangle getRect() {
		return self;
	}

	/**
	 * Add's the provided ball to this pad's "knowledge"
	 *
	 * @param ball
	 *            A(nother) ball the player should collide with when moving
	 */
	public void setBall(Ball ball) {
		balls.add(ball);
	}

	/**
	 * Moves the player to the specified location (x, y)
	 *
	 * @param x
	 *            The new x-coordinate
	 * @param y
	 *            The new y-coordinate
	 */
	public void setPos(double x, double y) {
		self.setLayoutX(x);
		self.setLayoutY(y);
	}

	/**
	 * @return This player
	 */
	protected Player getPlayer() {
		return this;
	}

	/**
	 * @return The keyboad this player use
	 */
	public Keyboard getKeyboard() {
		return keyboard;
	}

	public void movePlayer() {
		for (Ball ball : balls) {
			// We move the ball rather than us so the collision
			// algorithm will work, (does not supper player to
			// coordinate)
			Point start = new Point(ball.getX(), ball.getY());
			Point end = new Point(ball.getX() - speedX, ball.getY() - speedY);

			Intersection intersect = ball.checkCollision(start, end,
					getPlayer());
			if (intersect == null) {
				xMove += speedX;
				yMove += speedY;
			}
		}
		// Check to make sure we are still on the map
		if (xMove < 0)
			xMove = 0;
		else if (xMove > Game.CANVAS_WIDTH - self.getWidth())
			xMove = Game.CANVAS_WIDTH - self.getWidth();
		if (yMove < 0)
			yMove = 0;
		else if (yMove > Game.CANVAS_HEIGHT - self.getHeight())
			yMove = Game.CANVAS_HEIGHT - self.getHeight();

		// Apply changes
		setPos(xMove, yMove);
	}
}