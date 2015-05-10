public class GameLoop implements Runnable {

	private final int FPS = 60;

	public GameLoop() {

	}

	@Override
	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;

		while (true) {
			startTime = System.currentTimeMillis();

			
			
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try {
				// To keep the 60 FPS
				wait(sleepTime);
			} catch (InterruptedException e) { }
		}
	}

}
