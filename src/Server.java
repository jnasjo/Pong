import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;

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
	public Server(int port) {
		this.port = port;
	}

	@Override
	public void start() {
		if (port > 65535 || port < 0)
			port = OnlineGame.USE_STANDARD_PORT;

		try {
			server = new ServerSocket(port, NR_OF_PLAYERS);
			try {
				waitForPartner();
				setup();
				listenOnSocket();
			} catch (EOFException e) {
				displayMessage(partnerName + " left the game");
			} finally {
				closeConnection();
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
