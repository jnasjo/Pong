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
	private static int speedX = 4;
	private static int speedY = 4;

	// This thing will move the pad
	private Animator[] animator;

	// BAAAHÄÄÄÄÄHÄÄÄD lösning
	private Set<Ball> balls;

	public Player(Keyboard keyboard, Rectangle rect, Group root) {
		this.root = root;
		this.self = rect;
		this.keyboard = keyboard;
		xMove = rect.getLayoutX();
		yMove = rect.getLayoutY();
		balls = new HashSet<Ball>();

		animator = new Animator[this.keyboard.size() - 1]; // -1 for fire
		animator[0] = new Animator(0, -speedY); // UP
		animator[1] = new Animator(0, speedY); // DOWN
		animator[2] = new Animator(-speedX, 0); // LEFT
		animator[3] = new Animator(speedX, 0); // RIGHT
	}

	// DEV
	public String getSpeedDEV() {
		return "" + xMove + ", " + yMove;
	}
	
	public void movePlayer(int i, boolean keyDown) {
		if(i == -1)
			return;
		if(keyDown)
			animator[i].start();
		else
			animator[i].stop();
	}

	public void handle(KeyEvent event) {
		Set<KeyCode> keys = keyboard.handle(event);

		// Move player
		for (int i = 0; i < animator.length; i++) {
			if (keys.contains(keyboard.get(i)))
				animator[i].start();
			else
				animator[i].stop();
		}

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
						
						shoot.setLayoutY(120*Math.sin(shoot.getLayoutX()/50));

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
	 * @return This player
	 */
	protected Player getPlayer() {
		return this;
	}

	private class Animator {
		private AnimationTimer anim;

		public Animator(double speedX, double speedY) {
			anim = new AnimationTimer() {
				@Override
				public void handle(long now) {
					for (Ball ball : balls) {
						// We move the ball rather than us so the collision
						// algorithm will work, (does not supper player to
						// coordinate)
						Point start = new Point(ball.getX(), ball.getY());
						Point end = new Point(ball.getX() - speedX, ball.getY()
								- speedY);

						Intersection intersect = ball.checkCollision(start,
								end, getPlayer());
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
					self.setLayoutX(xMove);
					self.setLayoutY(yMove);
				}
			};
		}

		/**
		 * Starts the animator
		 */
		public void start() {
			anim.start();
		}

		/**
		 * Stops the animator
		 */
		public void stop() {
			anim.stop();

		}
	}
}
