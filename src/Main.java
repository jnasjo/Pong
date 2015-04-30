import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	private final static double SCREEN_SIZE = .5;
	private final static int SCREEN_HEIGHT = 1080;
	private final static int SCREEN_WIDTH = 1920;
	
	private final static Color BACKGROUND = Color.rgb(32, 32, 32);
	private final static Color BALLCOLOR = Color.rgb(0, 137, 139);
	private final static Color PLAYER1_COLOR = Color.rgb(26, 162, 97);
	private final static Color PLAYER2_COLOR = Color.rgb(217, 54, 114);

	// Drawing space, AKA window height and width
	public final static int CANVAS_HEIGHT = (int) (SCREEN_HEIGHT * SCREEN_SIZE);
	public final static int CANVAS_WIDTH = (int) (SCREEN_WIDTH * SCREEN_SIZE);

	private final static boolean SHOW_DEV_INFO = true;

	public static void main(String... args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pong");

		Group root = new Group();
		root.setFocusTraversable(true);

		Shape shape = new Shape(root);
		shape.drawLine(CANVAS_WIDTH/2-1, 0, CANVAS_WIDTH/2-1, CANVAS_HEIGHT, 2, Color.rgb(234, 234, 234));
		
		shape.drawText(CANVAS_WIDTH/2-20, 0, 40, true, "Pong_Master", PLAYER1_COLOR);
		shape.drawText(CANVAS_WIDTH/2+20, 0, 40, false, "Japanese_PING_", PLAYER2_COLOR);
		
		Rectangle p1 = shape.drawRectangle(30, CANVAS_HEIGHT/2-35, 20, 70, PLAYER1_COLOR);
		Rectangle p2 = shape.drawRectangle(CANVAS_WIDTH-30-20, CANVAS_HEIGHT/2-35, 20, 70, PLAYER2_COLOR);
		Keyboard keysP1 = new Keyboard(KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D);
		Keyboard keysP2 = new Keyboard(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT);
		
		Player player1 = new Player(keysP1, p1);
		Player player2 = new Player(keysP2, p2);

		Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);
		
		EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				player1.handle(key);
				player2.handle(key);
			}
		};
	
		scene.setOnKeyPressed(handler);
		scene.setOnKeyReleased(handler);

//		Rectangle r = shape.drawRectangle(0, 200, 20, 65, null);

		Ball ball = new Ball(shape.drawCircle(0, 0, 75, BALLCOLOR));
		ball.start(); // Useless, men tar bort "unused"

		if (SHOW_DEV_INFO) {
			Text text = shape.drawText(0, 0, 12, false, "-", null);
			Text p1Keys = shape.drawText(0, 14, 12, false, "-", null);
			Text p2Keys = shape.drawText(0, 28, 12, false, "-", null);
			text.setOpacity(0.4);

			new AnimationTimer() {
				@Override
				public void handle(long arg0) {
					text.setText(player1.getSpeed());
					p1Keys.setText(keysP1.getKeysDown());
					p2Keys.setText(keysP2.getKeysDown());
				}
			}.start();
		}



		primaryStage.setScene(scene);
		
		primaryStage.getScene().setFill(BACKGROUND);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene(); // Ensure that we get a correctly sized
									// canvas and not window
		primaryStage.show();
	}
}
