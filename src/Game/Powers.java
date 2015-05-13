package Game;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;

public class Powers {
	private Player p1;
	private Player p2;
	private AnimationTimer timer;

	public Powers(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
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
