/**

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
			
		
		
		
		default:
				
		}	
		*/

import java.util.EnumSet;
import java.util.Set;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Keyboard {

	private Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);
	private KeyCode[] keys; // 0 = up, 1 = down, 2 = left, 3 = right

	/**
	 * Creates a new keyboard with specified keys
	 * 
	 * @param up
	 *            The KeyCode representing up
	 * @param down
	 *            The KeyCode representing down
	 * @param left
	 *            The KeyCode representing left
	 * @param right
	 *            The KeyCode representing right
	 */
	public Keyboard(KeyCode up, KeyCode down, KeyCode left, KeyCode right) {
		keys = new KeyCode[4];
		keys[0] = up;
		keys[1] = down;
		keys[2] = left;
		keys[3] = right;
	}
	
	/**
	 * DEV
	 * @return a string of the keys pressed down
	 */
	public String getKeysDown() {
		return pressedKeys.toString();
	}

	/**
	 * KeyEventHandler for multiple inputs
	 * 
	 * @param event
	 *            The current KeyEvent
	 * @return An KeyCode array of every key
	 */
	public Set<KeyCode> handle(KeyEvent event) {

		if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
			if (hasKey(event.getCode()))
				pressedKeys.add(event.getCode());
		}
		else if (KeyEvent.KEY_RELEASED.equals(event.getEventType()))
			pressedKeys.remove(event.getCode());


		return pressedKeys;
	}

	/**
	 * Return true if the key is on the keyboard
	 * 
	 * @param key
	 *            The KeyCode for the key to check
	 * @return true if the key is on the keyboard
	 */
	private boolean hasKey(KeyCode key) {
		for (int i = 0; i < keys.length; i++)
			if (keys[i] == key)
				return true;
		return false;
	}

	/**
	 * @return The KeyCode representing up
	 */
	public KeyCode getUp() {
		return keys[0];
	}

	

	



	/**
	 * @return The KeyCode representing down
	 */
	public KeyCode getDown() {
		return keys[1];
	}


	/**
	 * @return The KeyCode representing left
	 */
	public KeyCode getLeft() {
		return keys[2];
	}

	/**
	 * @return The KeyCode representing right
	 */
	public KeyCode getRight() {
		return keys[3];
	}
}
