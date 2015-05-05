import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game {

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

	private final static boolean SHOW_DEV_INFO = false;

	public Game(Stage stage) throws Exception {
		start(stage);
	}

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pong");

		Group root = new Group();
		root.setFocusTraversable(true);

		Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);

		primaryStage.setScene(scene);

		Shape shape = new Shape(root);
		shape.drawLine(CANVAS_WIDTH / 2 - 1, 0, CANVAS_WIDTH / 2 - 1,
				CANVAS_HEIGHT, 2, Color.rgb(234, 234, 234));

		shape.drawText(CANVAS_WIDTH / 2 - 20, 0, 40,
				Shape.TextDirection.RIGHT_TO_LEFT, "Pong_Master", PLAYER1_COLOR);
		shape.drawText(CANVAS_WIDTH / 2 + 20, 0, 40,
				Shape.TextDirection.LEFT_TO_RIGHT, "Japanese_PING_",
				PLAYER2_COLOR);

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

		Ball ball = new Ball(shape.drawCircle(0, 0, 75, BALLCOLOR), root,
				p1Score, p2Score, player1, player2);
		new Ball(shape.drawCircle(0, 0, 75, BALLCOLOR), root, p1Score, p2Score,
				player1, player2);
		ball.start(); // Useless, men tar bort "unused"

		EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				player1.handle(key);
				player2.handle(key);
			}
		};

		scene.setOnKeyPressed(handler);
		scene.setOnKeyReleased(handler);

		// Rectangle r = shape.drawRectangle(0, 200, 20, 65, null);

		if (SHOW_DEV_INFO) {
			Text text = shape.drawText(0, 0, 12,
					Shape.TextDirection.LEFT_TO_RIGHT, "-", null);
			Text p1Keys = shape.drawText(0, 14, 12,
					Shape.TextDirection.LEFT_TO_RIGHT, "-", null);
			Text p2Keys = shape.drawText(0, 28, 12,
					Shape.TextDirection.LEFT_TO_RIGHT, "-", null);
			Text col = shape.drawText(0, 42, 12,
					Shape.TextDirection.LEFT_TO_RIGHT, "-", null);
			text.setOpacity(0.4);
			Line colL1 = shape.drawLine(-1, 0, -1, CANVAS_HEIGHT, 1, null);
			Line colL2 = shape.drawLine(0, -1, CANVAS_WIDTH, -1, 1, null);

			new AnimationTimer() {
				@Override
				public void handle(long arg0) {
					text.setText(player1.getSpeedDEV());
					p1Keys.setText(keysP1.getKeysDown());
					p2Keys.setText(keysP2.getKeysDown());

					// Collision cross
					double[] cord = ball.colCoords();
					if (cord != null) {
						colL1.setLayoutX(cord[0]);
						colL2.setLayoutY(cord[1]);

						int[] coords = new int[cord.length];
						for (int i = 0; i < coords.length; i++)
							coords[i] = (int) (cord[i]);
						col.setText(java.util.Arrays.toString(coords));
					}
				}
			}.start();
		}

		primaryStage.getScene().setFill(BACKGROUND);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene(); // Ensure that we get a correctly sized
									// canvas and not window
		primaryStage.show();
	}
}
