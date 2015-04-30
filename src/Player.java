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
	private int shootSpeed = 4;
	private Rectangle shoot;
	private int NumberOfShoots = 0;

	public Player(Keyboard keyboard, Rectangle rect, Group root) {
		this.root = root;
		this.self = rect;
		this.keyboard = keyboard;
		xMove = rect.getLayoutX();
		yMove = rect.getLayoutY();
	}

	// DEV
	public String getSpeed() {
		return "" + xMove + ", " + yMove;
	}

	public void handle(KeyEvent event) {
		Set<KeyCode> keys = keyboard.handle(event);

		for (KeyCode key : keys) {

			if (event.getCode().isWhitespaceKey() && NumberOfShoots >= 1) {
				new AnimationTimer() {
					@Override
					public void handle(long args0) {
						NumberOfShoots--;
						shootSpeed += 1;
						if (shootSpeed > 500) {
							shootSpeed = 1;
							root.getChildren().remove(shoot);
							stop();
							System.out.println("Hej");
						}
					}
				}.start();
			}

			if (key == keyboard.getUp())
				yMove -= speedY; // Move Up
			if (key == keyboard.getDown())
				yMove += speedY; // Move Down
			if (key == keyboard.getLeft())
				xMove -= speedX; // Move Left
			if (key == keyboard.getRight())
				xMove += speedX; // Move Right

			// WE BE FIRERIN' THE LAZORRZ
			if (key == keyboard.getFire()) {
				Shape sp = new Shape(root);
				shoot = sp.drawRectangle((int) self.getLayoutX() - 20,
						(int) self.getLayoutY() - 20, 20, 20, Color.ORANGE);
				NumberOfShoots++;
				System.out.println(NumberOfShoots);
			}
		}

		// Apply changes
		self.setLayoutX(xMove);
		self.setLayoutY(yMove);
	}

	/**
	 * @return The rectangle, or pad, of this player
	 */
	public Rectangle getRect() {
		return self;
	}
}
