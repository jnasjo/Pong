import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
		primaryStage.setHeight(CANVAS_HEIGHT);
		primaryStage.setWidth(CANVAS_WIDTH);

		Group root = new Group();
		root.setFocusTraversable(true);

		Shape shape = new Shape(root);
		Rectangle r = shape.drawRectangle(0, 200, 20, 65, null);
		Keyboard keys = new Keyboard(r);

		Ball ball = new Ball(shape.drawCircle(0, 0, 50, Color.GREEN));
		ball.start(); // Useless, men tar bort "unused"

		if (SHOW_DEV_INFO) {
			Text text = shape.drawText(0, 0, 12, "-");
			text.setOpacity(0.4);

			new AnimationTimer() {
				@Override
				public void handle(long arg0) {
					text.setText(java.util.Arrays.toString(keys.getSpeed()));
					System.out.println();
				}
			}.start();
		}

		Scene scene = new Scene(root);
		scene.setOnKeyPressed(keys);

		primaryStage.setScene(scene);
		primaryStage.getScene().setFill(Color.BLACK);
		primaryStage.show();
	}
}
