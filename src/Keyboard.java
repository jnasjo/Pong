import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Keyboard implements EventHandler<KeyEvent> {
	Rectangle r;

	public Keyboard(Rectangle r) {
		this.r = r;
	}

	@Override
	public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.W) {
			
		}
		System.out.println("You pressed the "+event.getCode()+" key!");
		
		final long startTime = System.nanoTime();
		final AnimationTimer a = new AnimationTimer() {
			@Override
			public void handle(long now) {	
				//r.setX(r.getX()+1);
				r.setRotate(r.getRotate()+.5);
				if(now > startTime+Math.pow(10, 9)*10)
					this.stop();
			}
		};
		a.start();
	}
}
