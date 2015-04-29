import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Keyboard implements EventHandler<KeyEvent> {
	Rectangle r;
	private int xMove = 1;
	private int yMove = 1;
	private int speed = 4;

	public Keyboard(Rectangle r) {
		this.r = r;
	}

	@Override
	public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.W) {

		}

		System.out.println("You pressed the " + event.getCode() + " key!");

		final long startTime = System.nanoTime();
		final AnimationTimer a = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// r.setX(r.getX()+1);
				// r.setRotate(r.getRotate()+.5);
				if (r.getLayoutX() > 400 || r.getLayoutX() < 0) {
					xMove = xMove * -1;
					speed--;
				} else if (r.getLayoutY() > 400 || r.getLayoutY() < 0) {
					yMove = yMove * -1;
					speed--;
				}

				switch (event.getCode()) {
				case A:
					if (xMove > 0) {
						xMove = xMove * -1;
						r.setLayoutX(xMove + speed);
					} else {
						r.setLayoutX(xMove + speed);
					}
				case D:
					if (xMove < 0) {
						xMove = xMove * -1;
						r.setLayoutX(xMove + speed);
					} else {
						r.setLayoutX(xMove + speed);
					}

				case W:
					if (yMove < 0) {
						yMove = yMove * -1;
						r.setLayoutY(yMove);
					} else {
						r.setLayoutY(yMove);
					}
				case S:
					if (yMove > 0) {
						yMove = yMove * -1;
						r.setLayoutY(yMove);
					} else {
						r.setLayoutY(yMove);
					}
				default:
					speed++;
				}

				System.out.println("XMOVE: " + xMove + " YMOVE: " + yMove);
				if (now > startTime + Math.pow(10, 9) * 10)
					this.stop();
			}
		};
		a.start();
	}
}
