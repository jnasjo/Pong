import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Keyboard implements EventHandler<KeyEvent> {
	Rectangle r;
	
	private static int xMove = 1;
	private static int yMove = 1;
	private int speedX = 4;
	private int speedY = 4;

	public Keyboard(Rectangle r) {
		this.r = r;
	
	}

	@Override
	public void handle(KeyEvent event) {
	
	
		switch(event.getCode()){
		case D:
			xMove += speedX;
			r.setLayoutX(xMove);
			break;
		case A:
			xMove -= speedX;
			r.setLayoutX(xMove);
			break;
		case W:
			yMove -= speedY;
			r.setLayoutY(yMove);
			break;
		case S:
			yMove += speedY;
			r.setLayoutY(yMove);
			break;
		default:
				
		}	

	}
	public double getX(Rectangle r)
	{
		return r.getLayoutX();
	}
	
	public double getY(Rectangle r)
	{
		return r.getLayoutY();
	}
}

/**
 *final long startTime = System.nanoTime();
		final AnimationTimer a = new AnimationTimer() {
			@Override
			public void handle(long now) {	
								
				
				if(now > startTime+Math.pow(10, 9)*10)
					this.stop();
			}

		};
		
		
		a.start(); 
*/