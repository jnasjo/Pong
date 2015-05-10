import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

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
	public void start(Stage primaryStage) throws Exception {
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

	public EventHandler select(Stage stage) throws Exception{

		EventHandler e = new EventHandler<KeyEvent>() {
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
				if(key.getCode().equals(KeyCode.ENTER) && arrowDown == 190)
				{
						try {
							Game newGame = new Game(stage);
						} catch (Exception e) { //should never happen
							e.printStackTrace(); 
						}
					
				}
				if(key.getCode().equals(KeyCode.ENTER) && arrowDown == 250)
				{
						try {
							Help help = new Help(stage);
							
						} catch (Exception e) { //should never happen
							e.printStackTrace(); 
						}
					
				}

				System.out.println(arrowDown);
			}

		};
		return e;
	}

	
	
	public static void main(String[] args) {
		launch(args);
	}

	public void getItStarted(Stage primaryStage) throws Exception {
		start(primaryStage);

	}
}
