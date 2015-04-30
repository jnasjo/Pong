import java.awt.Color;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Keyboard implements EventHandler<KeyEvent> {
	Rectangle r;
	Ball ball;
	
	private static int xMove = 1;
	private static int yMove = 1;
	private int speedX = 8;
	private int speedY = 8;
	private int shootSpeed = 4;

	public Keyboard(Rectangle r) {
		this.r = r;
	
	}

	// DEV
	public int[] getSpeed() {
		return new int[] { xMove, yMove };
	}

	@Override
	public void handle(KeyEvent event) {
	
		shootSpeed += shootSpeed;
		//bounch(r, ball);
		
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
	
	public void bounch(Rectangle r, Rectangle r2)
	{
		System.out.println(r.getX());
		
	}
	
}


