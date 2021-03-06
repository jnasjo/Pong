package Network;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;

import Game.GameLoop;

public class Server extends NetworkNode {

	private ServerSocket server;

	private final int NR_OF_PLAYERS = 2;
	private int port;
	
	/**
	 * Creates a new server on specified port
	 * 
	 * @param port
	 *            The port to create the game on
	 */
	public Server(int port, GameLoop g) {
		this.port = port;
		game = g;
	}

	@Override
	public void start() {
		if (port > 65535 || port < 0)
			port = 6789;

		try {
			server = new ServerSocket(port, NR_OF_PLAYERS);
			try {
				waitForPartner();
				setup();
				game.setIsHost(true);
				listenOnSocket();
			} catch (EOFException e) {
				displayMessage(partnerName + " left the game");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Will wait for a partner to connect, this method will block until one
	 * does.
	 * 
	 * @throws IOException
	 */
	private void waitForPartner() throws IOException {
		displayMessage("Waiting for partner...");
		connection = server.accept();
		displayMessage("CONNECTED!");
	}
}