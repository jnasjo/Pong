

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.*;


public class Keyboard implements EventHandler<KeyEvent> {
	Rectangle r;
	Group root;
	
	private static int xMove = 1;
	private static int yMove = 1;
	private int speedX = 8;
	private int speedY = 8;
	private int shootSpeed = 4;
	private Rectangle shoot;
	private int NumberOfShoots =0;
	

	public Keyboard(Rectangle r, Group root) {
		this.r = r;
		this.root = root;
	}

	// DEV
	public int[] getSpeed() {
		return new int[] { xMove, yMove };
	}
	
	

	@Override
	public void handle(KeyEvent event) {
		
		
		
		if(event.getCode().isWhitespaceKey() && NumberOfShoots >= 1){
			new AnimationTimer() {
				@Override
				public void handle(long args0) {
					NumberOfShoots--;
					shootSpeed += 1;
					if(shootSpeed > 500){  shootSpeed = 1; System.out.println("Hej"); root.getChildren().remove(shoot);stop();}
				}
			}.start();
			
		}
	
		
		
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
		case SPACE:
			Shape sp = new Shape(root);
			shoot = sp.drawRectangle((int)r.getLayoutX()-20, (int)r.getLayoutY()-20,20 , 20, Color.ORANGE);
			NumberOfShoots++;
			System.out.println(NumberOfShoots);
			new AnimationTimer() {
				@Override
				public void handle(long arg0) {
					shoot.setLayoutX(shootSpeed);
					if(shoot.getLayoutX() > 500){
						root.getChildren().remove(shoot);
						stop();
					}
				}
				
			
			}.start();
		
		
		
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


