import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
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

	@Override
	public void handle(KeyEvent event) {
	
		
		System.out.println("You pressed the "+event.getCode()+" key!");
		
		
		yMove += speedY;
	
		
		switch(event.getCode()){
		case A:
			if(speedX > 0){
				xMove -= speedX;
				speedX = -speedX;
				r.setLayoutX(xMove);
			}else{
				xMove -= speedX;
				r.setLayoutX(xMove);
			}
		case D:
			if(speedX < 0){
				xMove += speedX;
				speedX = -speedX;
				r.setLayoutX(xMove);
			}else{
				xMove += speedX;
				r.setLayoutX(xMove);
			}
		
		case W:
			if(speedY < 0){
				yMove += speedY;
				speedY = -speedY;
				r.setLayoutY(yMove);
			}else{
				yMove += speedY;
				r.setLayoutY(yMove);
			}
		case S:
			if(speedY > 0){
				yMove -= speedY;
				speedY = -speedY;
				r.setLayoutY(yMove);
			}else{
				r.setLayoutY(yMove);
			}
			default:
				
		}
		System.out.println("XMOVE: " + xMove + " YMOVE: " + yMove);
		final long startTime = System.nanoTime();
		final AnimationTimer a = new AnimationTimer() {
			@Override
			public void handle(long now) {	
								
				
				if(now > startTime+Math.pow(10, 9)*10)
					this.stop();
			}
		};
		a.start();
	}
}
