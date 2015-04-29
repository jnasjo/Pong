import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Keyboard implements EventHandler<KeyEvent> {
	Rectangle r;
	private int xMove = 1;
	private int yMove = 1;
	private int speedX = 4;
	private int speedY = 4;

	public Keyboard(Rectangle r) {
		this.r = r;
	}

	// DEV
	public int[] getSpeed() {
		return new int[] { xMove, yMove };
	}

	@Override
	public void handle(KeyEvent event) {

		System.out.println("You pressed the " + event.getCode() + " key!");

		switch (event.getCode()) {
		case A:
			if (speedX > 0) {
				xMove -= speedX;
				speedX = -speedX;
				r.setLayoutX(xMove);
			} else {
				xMove -= speedX;
				r.setLayoutX(xMove);
			}
		case D:
			if (speedX < 0) {
				xMove += speedX;
				speedX = -speedX;
				r.setLayoutX(xMove);
			} else {
				xMove += speedX;
				r.setLayoutX(xMove);
			}
		case S:
			if (speedY < 0) {
				yMove -= speedY;
				speedY = -speedY;
				r.setLayoutY(yMove);
			} else {
				yMove -= speedY;
				r.setLayoutY(yMove);
			}
		case W:
			if (speedY > 0) {
				yMove += speedY;
				speedY = -speedY;
				r.setLayoutY(yMove);
			} else {
				yMove += speedY;
				r.setLayoutY(yMove);
			}
		default:

		}
		System.out.println("X-kord: " + r.getLayoutX() + " Y-kord: "
				+ r.getLayoutY());
		final long startTime = System.nanoTime();
		final AnimationTimer a = new AnimationTimer() {
			@Override
			public void handle(long now) {

				if (now > startTime + Math.pow(10, 9) * 10)
					this.stop();
			}
		};
		a.start();
	}
}
