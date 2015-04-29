
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
	
	private final static double SCREEN_SIZE = .1;
	private final static int SCREEN_HEIGHT = 1080;
	private final static int SCREEN_WIDTH = 1920;
	
	// Drawing space, AKA window height and width
	public final static int CANVAS_HEIGHT = (int) (SCREEN_HEIGHT*SCREEN_SIZE);
	public final static int CANVAS_WIDTH = (int) (SCREEN_WIDTH*SCREEN_SIZE);

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
		
		Scene scene = new Scene(root);
		scene.setOnKeyPressed(keys);
		
		primaryStage.setScene(scene);
		primaryStage.getScene().setFill(Color.BLACK);
		primaryStage.show();
	}
}
