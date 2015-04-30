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
		PlayerName nameP1 = new PlayerName(root);
		PlayerName nameP2 = new PlayerName(root);
		
		nameP1.pName(100, 100, "Pong_Master");
		nameP2.pName(2400, 100, "Japanese_PING_");
		
		Rectangle p1 = shape.drawRectangle(0, 200, 20, 65, Color.RED);
		Rectangle p2 = shape.drawRectangle(100, 100,20 , 65, Color.BLUE);
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

		Ball ball = new Ball(shape.drawCircle(0, 0, 50, Color.GREEN));
		ball.start(); // Useless, men tar bort "unused"

		if (SHOW_DEV_INFO) {
			Text text = shape.drawText(0, 0, 12, "-");
			Text p1Keys = shape.drawText(0, 14, 12, "-");
			Text p2Keys = shape.drawText(0, 28, 12, "-");
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
		
		primaryStage.getScene().setFill(Color.BLACK);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene(); // Ensure that we get a correctly sized
									// canvas and not window
		primaryStage.show();
	}
}
