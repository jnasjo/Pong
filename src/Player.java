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
				Rectangle shoot;
				
				
				
				new AnimationTimer() {
					Rectangle shoot = sp.drawRectangle((int) self.getLayoutX() - 20,
							(int) self.getLayoutY() - 20, 20, 20, Color.ORANGE);
					int ShootspeedX = 4;
					@Override
					public void handle(long args0) {
						shoot.setLayoutX(self.getLayoutX());
						shoot.setLayoutY(self.getLayoutY());
						
						ShootspeedX += 5;
						shoot.setLayoutX(self.getLayoutX() + ShootspeedX);
						
						if(ShootspeedX > Game.CANVAS_WIDTH/2){
							root.getChildren().remove(shoot);
							ShootspeedX = (int) self.getLayoutX() - (int)self.getLayoutX()-5;
						}
						
					}
				}.start();
				
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
