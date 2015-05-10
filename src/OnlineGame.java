import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OnlineGame {

	private final static double SCREEN_SIZE = .5;
	private final static int SCREEN_HEIGHT = 1080;
	private final static int SCREEN_WIDTH = 1920;

	private final static Color BACKGROUND = Color.rgb(32, 32, 32);
	private final static Color BALLCOLOR = Color.rgb(0, 137, 139);
	private final static Color PLAYER1_COLOR = Color.rgb(26, 162, 97);
	private final static Color PLAYER2_COLOR = Color.rgb(217, 54, 114);
	private final static Color SCORE_TEXT_COLOR = Color.rgb(183, 183, 183);

	// Drawing space, AKA window height and width
	public final static int CANVAS_HEIGHT = (int) (SCREEN_HEIGHT * SCREEN_SIZE);
	public final static int CANVAS_WIDTH = (int) (SCREEN_WIDTH * SCREEN_SIZE);

	private String PLAYER_1_NAME;
	private String PLAYER_2_NAME;
	
	// Use the standard port (6789)
	public final static int USE_STANDARD_PORT = 6789;
	
	// This is our connection as a server or client
	private NetworkNode myConnection = null;
	
	/**
	 * Creates a new game online as server
	 * 
	 * @param playerName
	 *            This players name
	 * 
	 * @param port
	 *            The port to use
	 * @throws Exception if we fail something
	 */
	public OnlineGame(Stage stage, String playerName, int port) {
		PLAYER_1_NAME = playerName;
		PLAYER_2_NAME = "Japanese_PING_";

		// Create our connection as a server
		myConnection = new Server(port);
		
		// Send our name to the opponent
		myConnection.sendMessage(PLAYER_1_NAME);

		start(stage);
	}
	
	/**
	 * Creates a new game online as client
	 * 
	 * @param playerName
	 *            This players name
	 * 
	 * @param IP
	 *            The IP to connect to
	 * @throws Exception if we fail something
	 */
	public OnlineGame(Stage stage, String playerName, String IP, int port) {
		PLAYER_1_NAME = "Pong_Master";
		PLAYER_2_NAME = playerName;

		// Create our connection as a client
		myConnection = new Client(IP, port);
		
		// Send our name to the opponent
		myConnection.sendMessage(PLAYER_2_NAME);
		
		start(stage);
	}
	
	private void setupPlayfield(Shape shape) {
		shape.drawLine(CANVAS_WIDTH / 2 - 1, 0, CANVAS_WIDTH / 2 - 1,
				CANVAS_HEIGHT, 2, SCORE_TEXT_COLOR); // Color.rgb(234, 234, 234)

		shape.drawText(CANVAS_WIDTH / 2 - 20, 0, 40,
				Shape.TextDirection.RIGHT_TO_LEFT, PLAYER_1_NAME, PLAYER1_COLOR);
		shape.drawText(CANVAS_WIDTH / 2 + 20, 0, 40,
				Shape.TextDirection.LEFT_TO_RIGHT, PLAYER_2_NAME,
				PLAYER2_COLOR);
	}

	public void start(Stage primaryStage) {
		primaryStage.setTitle("Pong");

		Group root = new Group();
		root.setFocusTraversable(true);

		Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);

		primaryStage.setScene(scene);

		Shape shape = new Shape(root);
		
		setupPlayfield(shape);

		Text p1Score = shape.drawText(CANVAS_WIDTH / 4, CANVAS_HEIGHT / 2, 100,
				Shape.TextDirection.CENTER_TEXT, "0", SCORE_TEXT_COLOR);
		Text p2Score = shape.drawText(CANVAS_WIDTH * 3 / 4, CANVAS_HEIGHT / 2,
				100, Shape.TextDirection.CENTER_TEXT, "0", SCORE_TEXT_COLOR);

		Rectangle p1 = shape.drawRectangle(30, CANVAS_HEIGHT / 2 - 35, 20, 70,
				PLAYER1_COLOR);
		Rectangle p2 = shape.drawRectangle(CANVAS_WIDTH - 30 - 20,
				CANVAS_HEIGHT / 2 - 35, 20, 70, PLAYER2_COLOR);
		Keyboard keysP1 = new Keyboard(KeyCode.W, KeyCode.S, KeyCode.A,
				KeyCode.D, KeyCode.SPACE);
		Keyboard keysP2 = new Keyboard(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT,
				KeyCode.RIGHT, KeyCode.L);

		Player player1 = new Player(keysP1, p1, root);
		Player player2 = new Player(keysP2, p2, root);

		Ball ball = new Ball(shape.drawCircle(0, 0, 60, BALLCOLOR), root,
				p1Score, p2Score, player1, player2);

		ball.start();

		EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				player1.handle(key);
				player2.handle(key);
			}
		};

		scene.setOnKeyPressed(handler);
		scene.setOnKeyReleased(handler);

		primaryStage.getScene().setFill(BACKGROUND);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene(); // Ensure that we get a correctly sized
									// canvas and not window
		primaryStage.show();
	}
}
