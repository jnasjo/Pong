
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
import javafx.*;

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
		PlayerName nameP1 = new PlayerName(root);
		PlayerName nameP2 = new PlayerName(root);
		
		nameP1.pName(100, 100, "Pong_Master");
		nameP2.pName(2400, 100, "Japanese_PING_");
		
		Rectangle p1 = shape.drawRectangle(0, 200, 20, 65, Color.RED);
		Rectangle p2 = shape.drawRectangle(100, 100,20 , 65, Color.BLUE);
		Keyboard keysP1 = new Keyboard(p1);
		
		
		Ball ball = new Ball(shape.drawCircle(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 50, Color.GREEN));
		
		Scene scene = new Scene(root);
	
		scene.setOnKeyPressed(keysP1);
		
		
		primaryStage.setScene(scene);
		
		primaryStage.getScene().setFill(Color.BLACK);
		primaryStage.show();
	}
}
