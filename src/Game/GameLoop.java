package Game;

import Network.*;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GameLoop implements Runnable {

	private final int FPS = 60;
	private final Game UI;

	private String PLAYER_1_NAME = "Pong_Master";
	private String PLAYER_2_NAME = "Japanese_PING_";
	private int p1Score = 0;
	private int p2Score = 0;

	private Player player1;
	private Player player2;
	private Ball ball;

	// NETWORK TINGS
	private NetworkNode myConnection;
	private boolean isHosting = false;
	private boolean isClient = false;
	// A set containing the keys pressed down by the player
	private Set<KeyCode> keysDown;
	// A set containing the keys pressed down that has been sent
	private Set<KeyCode> keysDownSent = EnumSet.noneOf(KeyCode.class);

	public GameLoop(Stage stage, String p1Name, String p2Name) {
		if (!p1Name.equals(""))
			PLAYER_1_NAME = p1Name;
		if (!p2Name.equals(""))
			PLAYER_2_NAME = p2Name;

		// Create a new game with all components that we need
		// Get the UI components
		UI = new Game(stage);
		// Create the name-tags
		UI.updatePlayerNames(PLAYER_1_NAME, PLAYER_2_NAME);

		// Get the objects that we will use often
		player1 = UI.getPlayer1();
		player2 = UI.getPlayer2();
		ball = UI.getBall();

		// Start the game in it's own thread
		new Thread(this).start();
	}

	/*
	 * ---------------------------------------
	 * ---------------------------------------
	 * ---------------------------------------
	 * --------------------------------------- -----REMEMBER TO SYNCRONIZE
	 * THINGS----- ---------------------------------------
	 * ---------------------------------------
	 * ---------------------------------------
	 * ---------------------------------------
	 */

	/**
	 * Hosts a game on the provided port
	 *
	 * @param port
	 *            The port to host on
	 */
	public void host(int port) {
		isHosting = true;
		isClient = false;
		UI.setOnAction(true, false);
		myConnection = new Server(port, this);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// Create our connection as a server
				myConnection.start();

				// Send our name to the opponent
				myConnection.sendMessage(PLAYER_1_NAME);
			}
		}).start();
	}

	/**
	 * Connects to a host
	 *
	 * @param IP
	 *            The IP to connect to
	 * @param port
	 *            The port to use
	 */
	public void connect(String IP, int port) {
		isHosting = false;
		isClient = true;
		UI.setOnAction(false, true);
		myConnection = new Client(IP, port, this);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// Create our connection as a client
				myConnection.start();

				// Send our name to the opponent
				myConnection.sendMessage(PLAYER_2_NAME);
			}
		}).start();
	}

	/*
	 * The actual game loop
	 */
	@Override
	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;

		while (true) {
			startTime = System.currentTimeMillis();

			//
			// GAME CODE START
			//

			// Move Ball
			int[] res = ball.moveBall();
			// Check if we need to modify a player's score
			if (isHosting) {
				if (res[0] != 0) {
					if (res[0] == -1) {
						p2Score++;
						myConnection.sendMessage("setPoint player2 " + p2Score);
					} else if (res[0] == 1) {
						p1Score++;
						myConnection.sendMessage("setPoint player1 " + p1Score);
					}
					UI.updatePlayerScore(p1Score, p2Score);
				}
				// Check if we need to correct the balls velocity
				if (res[1] != 0) {
					myConnection.sendMessage("setPos ball " + ball.getX() + " "
							+ ball.getY());
					myConnection.sendMessage("setVel ball " + ball.getVelX()
							+ " " + ball.getVelY());
				}
			}

			// Move players
			player1.movePlayer();
			player2.movePlayer();
			if (isHosting || isClient) {
				if (isHosting)
					keysDown = player1.getKeyboard().getPressedKeys();
				else
					keysDown = player2.getKeyboard().getPressedKeys();

				Set<KeyCode> clone = EnumSet.noneOf(KeyCode.class);
				for (Iterator<KeyCode> it = keysDownSent.iterator(); it
						.hasNext();) {
					clone.add(it.next());
				}

				// Send keys that we have not sent yet
				for (Iterator<KeyCode> it = keysDown.iterator(); it.hasNext();) {
					KeyCode key = it.next();
					if (!keysDownSent.contains(key)) {
						// We have not sent this key!
						myConnection.sendMessage("keyDown " + key.toString());
						keysDownSent.add(key);
					} else {
						clone.remove(key);
					}
				}

				// Notify that we have released a key
				for (Iterator<KeyCode> it = clone.iterator(); it.hasNext();) {
					KeyCode key = it.next();
					myConnection.sendMessage("keyUp " + key.toString());
					keysDownSent.remove(key);
				}
			}

			//
			// GAME CODE STOP
			//

			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try {
				// To keep the 60 FPS
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Set's the name of the partner
	 *
	 * @param name
	 *            The new name for the partner
	 */
	public void setName(String name) {
		if (isHosting)
			PLAYER_2_NAME = name;
		else
			PLAYER_1_NAME = name;

		// Update UI
		UI.updatePlayerNames(PLAYER_1_NAME, PLAYER_2_NAME);
	}

	/**
	 * Add's a point to the specified player
	 *
	 * @param res
	 *            Specified information
	 */
	public void setPoint(String[] res) {
		if (res[1].equals("player1")) {
			// Add point to player1
			p1Score = Integer.valueOf(res[2]);
		} else {
			// Add point to player2
			p2Score = Integer.valueOf(res[2]);
		}
		UI.updatePlayerScore(p1Score, p2Score);
	}

	/**
	 * Set's the new position of the provided object
	 *
	 * @param res
	 *            The object and new position // TODO in the form of [command,
	 *            object, x, y]
	 */
	public void setPos(String[] res) {
		if (res[1].equals("ball")) {
			// Move ball
			ball.setPos(Double.valueOf(res[2]), Double.valueOf(res[3]));
		} else if (res[1].equals("player")) {
			// Move opponent (player 1)
			player1.setPos(Double.valueOf(res[2]), Double.valueOf(res[3]));
		}
	}

	/**
	 * Set's the new velocity of the provided object
	 *
	 * @param res
	 *            The object and new velocity // TODO in the form of [command,
	 *            object, x, y]
	 */
	public void setVel(String[] res) {
		if (res[1].equals("ball")) {
			ball.setVel(Double.valueOf(res[2]), Double.valueOf(res[3]));
		}
	}

	/**
	 * Indicates that the opponent is moving in provided direction
	 * 
	 * @param res
	 */
	public void keyDown(String[] res) {
		if (isHosting) {
			KeyCode key = player2.getKeyboard().valueOf(res[1]);
			int idx = player2.getKeyboard().getIndex(key);
			player2.setVel(true, idx);
		} else {
			KeyCode key = player1.getKeyboard().valueOf(res[1]);
			int idx = player1.getKeyboard().getIndex(key);
			player1.setVel(true, idx);
		}
	}

	/**
	 * Indicates that the opponent is no longer moving in provided direction
	 * 
	 * @param res
	 */
	public void keyUp(String[] res) {
		if (isHosting) {
			KeyCode key = player2.getKeyboard().valueOf(res[1]);
			int idx = player2.getKeyboard().getIndex(key);
			player2.setVel(false, idx);
		} else {
			KeyCode key = player1.getKeyboard().valueOf(res[1]);
			int idx = player1.getKeyboard().getIndex(key);
			player1.setVel(false, idx);
		}
	}
}