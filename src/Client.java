import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends NetworkNode {

	private String IP;
	private int port;
	
	public Client(String IP, int port, OnlineGame g) {
		this.IP = IP;
		this.port = port;
		game = g;
	}
	
	@Override
	public void start() {
		try {
			connect(IP, port);
			setup();
			listenOnSocket();
		} catch (EOFException e) {
			displayMessage("You abandoned the game");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void connect(String IP, int port) throws IOException {
		displayMessage("Connecting to "+IP+":"+port+"...");
		connection = new Socket(InetAddress.getByName(IP), port);
		displayMessage("CONNECTED!");
	}
}
