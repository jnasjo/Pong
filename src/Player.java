import java.util.Set;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Player {

	private Rectangle self;
	private Keyboard keyboard;

	private double xMove = 1;
	private double yMove = 1;
	private static int speedX = 4;
	private static int speedY = 4;

	public Player(Keyboard keyboard, Rectangle rect) {
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
			if (key == keyboard.getUp())
				yMove -= speedY; // Move Up
			if (key == keyboard.getDown())
				yMove += speedY; // Move Down
			if (key == keyboard.getLeft())
				xMove -= speedX; // Move Left
			if (key == keyboard.getRight())
				xMove += speedX; // Move Right
		}

		// Apply changes
		self.setLayoutX(xMove);
		self.setLayoutY(yMove);
	}
}
