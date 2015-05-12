package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Game.GameLoop;

public abstract class NetworkNode {

	// Used to receive commands
	private ObjectInputStream input;
	// Used to send commands
	private ObjectOutputStream output;
	// The actual connection
	protected Socket connection;

	// The name of the partner
	protected String partnerName = "Partner";
	
	// Used to make changes to the game
//	protected OnlineGame game;
	protected GameLoop game;

	// Used for the command-functions
	private interface Actions {
		void action(String[] res);
	}

	// Array of command-functions
	private Actions[] action = new Actions[] { new Actions() {
		public void action(String[] res) {
			if(res.length > 1) {
				partnerName = res[1];
				game.setName(res[1]);
			}
		}
	}, new Actions() {
		public void action(String[] res) {
			game.setPoint(res);
		}
	}, new Actions() {
		public void action(String[] res) {
			game.setPos(res);
		}
	}, new Actions() {
		public void action(String[] res) {
			game.setVel(res);
		}
	}, new Actions() {
		public void action(String[] res) {
			game.setKeyDown(res);
		}
	}, new Actions() {
		public void action(String[] res) {
			game.setKeyUp(res);
		}
	}, };

	// Array of commands
	private String[] commands = { "setName", "setPoint", "setPos", "setVel", "keyDown", "keyUp"};

	/**
	 * Setup for both streams
	 */
	protected void setup() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}

	/**
	 * Sends a message to the partner over the socket
	 * 
	 * @param msg
	 *            The message to be sent
	 */
	public void sendMessage(String msg) {
		try {
			output.writeObject(msg);
			output.flush();
		} catch (IOException e) {
			showErrorMessage("Failed to send message");
		}
	}

	/**
	 * Closes certain things to ensure that nothing continues in the background
	 */
	protected void closeConnection() {
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Continuously listens on the sockets for new information
	 * 
	 * @throws IOException
	 */
	protected void listenOnSocket() throws IOException {
		String message = "";
		do {
			try {
				message = (String) input.readObject();
				handleMessage(message);
			} catch (ClassNotFoundException e) {
				// We should never see this due to the fact
				// That players can't send any traffic that they want
				e.printStackTrace();
			}
		} while (!message.equals("quit"));
	}

	/**
	 * This will handle every message that was sent to us
	 * it will match the provided string with valid commands
	 * 
	 * @param msg
	 *            The message that was received
	 */
	protected void handleMessage(String msg) {
		System.out.println(msg);
		
		msg = msg.trim();
		String[] res = msg.split("\\s+");
		for(int i=0; i<commands.length; i++) {
			if(commands[i].equals(res[0])) {
				action[i].action(res);;
				break;
			}
		}
	}

	/**
	 * Displays a message to the user about current events
	 * 
	 * @param msg
	 *            The message to display
	 */
	protected void displayMessage(String msg) {
		// TODO
		game.displayMessage(msg);
	}

	/**
	 * Tells the user what happen if something fails
	 * 
	 * @param msg
	 *            The error message
	 */
	protected void showErrorMessage(String msg) {
		// TODO
	}
	
	/**
	 * Sub-classes will have to implement this one on their own
	 */
	public void start() {
	}
}