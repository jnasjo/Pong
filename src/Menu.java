import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Menu extends Application {
	private AnimationTimer timer;

	private String name;
	private double arrowDown;
	private double arrowXL;
	private double arrowXR;
	private ImageView sL;
	private ImageView sR;

	@FXML
	ImageView head, smallHead, newGame, help, settings, quit, playonline, keys,
			confirm;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Pong");

		try {

			Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
					"Menu.fxml"));

			ImageView header = (ImageView) myPane.lookup("#head");
			ImageView smallHead = (ImageView) myPane.lookup("#smallhead");
			ImageView newGame = (ImageView) myPane.lookup("#newGame");
			ImageView help = (ImageView) myPane.lookup("#help");
			ImageView settings = (ImageView) myPane.lookup("#settings");
			ImageView quit = (ImageView) myPane.lookup("#quit");
			ImageView playonline = (ImageView) myPane.lookup("#playonline");
			ImageView keys = (ImageView) myPane.lookup("#keys");
			ImageView confirm = (ImageView) myPane.lookup("#confirm");

			Image selectArrowLeft = new Image("selectArrowLeft.png");
			Image selectArrowRight = new Image("selectArrowRight.png");
			sL = new ImageView(selectArrowLeft);
			sR = new ImageView(selectArrowRight);
			sL.setScaleX(0.3);
			sL.setScaleY(0.3);
			sR.setScaleX(0.3);
			sR.setScaleY(0.3);
			arrowDown = (int) (newGame.getLayoutY() - 40);
			arrowXL = newGame.getLayoutX() - 60;
			arrowXR = newGame.getLayoutX() + newGame.getFitWidth() - 25;
			sL.setLayoutX(arrowXL);
			sL.setLayoutY(arrowDown);
			sR.setLayoutX(arrowXR);
			sR.setLayoutY(arrowDown);

			newGame.setOnKeyPressed(select(primaryStage));

			myPane.getChildren().add(sL);
			myPane.getChildren().add(sR);
			Scene myScene = new Scene(myPane);
			myScene.setOnKeyPressed(select(primaryStage));
			primaryStage.setScene(myScene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public EventHandler<KeyEvent> select(Stage stage) {

		EventHandler<KeyEvent> e = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.DOWN) && arrowDown != 430) {

					arrowDown += 60;
					sL.setLayoutY(arrowDown);
					sR.setLayoutY(arrowDown);
				}
				if (key.getCode().equals(KeyCode.UP) && arrowDown != 190) {

					arrowDown -= 60;
					sL.setLayoutY(arrowDown);
					sR.setLayoutY(arrowDown);
				}
				if (key.getCode().equals(KeyCode.ENTER) && arrowDown == 190) {
					GameLoop newGame = new GameLoop(stage, "Erkan","jonas");
				}

				if (key.getCode().equals(KeyCode.ENTER) && arrowDown == 370) {

					connectOnline connect = new connectOnline();

					try {
						connect.start(stage);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				if (key.getCode().equals(KeyCode.ENTER) && arrowDown == 250) {
					try {
						Help help = new Help(stage);

					} catch (IOException e) { // should never happen
						e.printStackTrace();
					}
				}
				if (key.getCode().equals(KeyCode.ENTER) && arrowDown == 430) {
					
						stage.close();

				}
				
				System.out.println(arrowDown);
			}
		};
		return e;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void getItStarted(Stage primaryStage) {
		start(primaryStage);
	}
}
