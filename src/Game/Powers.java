package Game;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;

public class Powers {
	private Player p1;
	private Player p2;
	private Game UI;
	private GameLoop names;
	private AnimationTimer timer;
	
	private int oldP1Score = 0;
	private int oldP2Score = 0;

	public Powers(Player p1, Player p2, Game UI, GameLoop names) {
		this.p1 = p1;
		this.p2 = p2;
		this.UI = UI;
		this.names = names;
	}

	/**
	 * 4 methods to set players width and height
	 * 
	 * @param double
	 */
	public void setPlayerOneWidth(double width, Player p) {

	}

	public void setPlayerOneHeight(double width, Player p) {
		p.getRect().setWidth(width);
	}

	public void setPlayeTwoHeight(double height, Player p) {

	}
	
	public void checkPowers(int p1Score, int p2Score) {
		int score = 0;
		
		Player p = null;
		String name = "";
		if(p1Score > oldP1Score) {
			oldP1Score = p1Score;
			p = p1;
			score = p1Score;
			name = names.getP1Name();
		}
		else if(p2Score > oldP2Score) {
			oldP2Score = p2Score;
			p = p2;
			score = p2Score;
			name = names.getP2Name();
		} else {
			return;
		}
		
		switch (score) {
		case 1:
			animateHeight(100, p);
			break;
		case 5:
			if(p.getKeyboard() == null)
				addFirePower(p,30);
			else{
				addFirePower(p, 10);
			}
			if(p.getKeyboard() != null)
				UI.displayMessage(name +" FIIIRE PRESS "+ p.getKeyboard().getFire());
			else
				UI.displayMessage(name+" SHOOTS : 30" );
			break;
		case 8:
			animateHeight(20, p);
			break;
		}
	}
	
	
	
	/**
	 * Adds 50 shots to the player
	 * @param p
	 * The player to recieve the ammo
	 */
	public void addFirePower(Player p, int amount) {
		p.addShots(amount);
	}

	public void animateHeight(double height, Player p) {
		double currentHeight = getPlayerTwoWHeight();
		final double heightt = getPlayerTwoWHeight() + height;

		final long start = System.nanoTime();
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				long timeTick = now - start;
				double tick = timeTick / Math.pow(10, 9);
				double dh = 200 * Math.abs(Math.sin(tick));
				p.getRect().setHeight(dh + currentHeight);
				if (tick >= 9.4245) {
					p.getRect().setHeight(height);
					timer.stop();
				}

			}
		};
		timer.start();
	}

	public void setPlayerTwoWidth(double width) {
		p2.getRect().setWidth(width);
	}

	/**
	 * Get methods for players width and height
	 * 
	 * @return double
	 */
	public double getPlayerOneWidth() {
		return p1.getRect().getWidth();
	}

	public double getPlayerOneHeight() {
		return p1.getRect().getHeight();
	}

	public double getPlayerTwoWidth() {
		return p2.getRect().getWidth();
	}

	public double getPlayerTwoWHeight() {
		return p2.getRect().getHeight();
	}

}
