package Menu;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Game.GameLoop;

public class Menu extends Application {
	private AnimationTimer timer;

	private String name;
	private int arrow_pos = 0;
	private ImageView sL;
	private ImageView sR;

	@FXML
	ImageView head, smallHead, newGame, help, settings, quit, playonline, keys,
			confirm;

	@FXML
	Pane newGameLabel;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Pong");

		try {

			Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
					"Menu.fxml"));

			head = (ImageView) myPane.lookup("#head");
			smallHead = (ImageView) myPane.lookup("#smallhead");
			newGame = (ImageView) myPane.lookup("#newGame");
			newGameLabel =(Pane) myPane.lookup("#newGameLabel");
			help = (ImageView) myPane.lookup("#help");
			settings = (ImageView) myPane.lookup("#settings");
			quit = (ImageView) myPane.lookup("#quit");
			playonline = (ImageView) myPane.lookup("#playonline");
			keys = (ImageView) myPane.lookup("#keys");
			confirm = (ImageView) myPane.lookup("#confirm");

			
			selectArrows(myPane);
			
			myPane.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e) {
					if(newGame.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
						HowManyPlayers game = new HowManyPlayers(primaryStage);
				}
				else if(help.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
					Help help = new Help(primaryStage);
				}
				else if(playonline.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())){
					connectOnline connect = new connectOnline();
					connect.start(primaryStage);
				}else if(playonline.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())){
					primaryStage.close();
					
				}
				
					
				}});

			newGame.setOnKeyPressed(select(primaryStage));

			Scene myScene = new Scene(myPane);
			
			myScene.setOnKeyPressed(select(primaryStage));
			primaryStage.setScene(myScene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleMouse(MouseEvent e, Stage stage) {
		select(stage);
	}

	public void selectArrows(Pane myPane) {
		Image selectArrowLeft = new Image("selectArrowLeft.png");
		Image selectArrowRight = new Image("selectArrowRight.png");
		sL = new ImageView(selectArrowLeft);
		sR = new ImageView(selectArrowRight);
		myPane.getChildren().add(sL);
		myPane.getChildren().add(sR);

		sL.setScaleX(0.3); sL.setScaleY(0.3); sR.setScaleX(0.3); sR.setScaleY(0.3);
		sL.setLayoutX(newGame.getLayoutX() + newGame.getFitWidth() - 25);
		sL.setLayoutY(newGame.getLayoutY() - 40);
		sR.setLayoutX(newGame.getLayoutX() - 60);
		sR.setLayoutY(newGame.getLayoutY() - 40);
	}

	public EventHandler<KeyEvent> select(Stage stage) {
		double[] xArrow = new double[] {newGame.getLayoutX() - 90,
				help.getLayoutX() - 90, playonline.getLayoutX()-90 , quit.getLayoutX()-90};
		double[] yArrow = new double[] {newGame.getLayoutY()-40,
				help.getLayoutY()-40 ,playonline.getLayoutY()-40, quit.getLayoutY()-40};

		EventHandler<KeyEvent> e = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.DOWN) && sL.getLayoutY() != yArrow[3]) {
					arrow_pos++;
					sL.setLayoutY(yArrow[arrow_pos]);
					sR.setLayoutY(yArrow[arrow_pos]);
				}
				if (key.getCode().equals(KeyCode.UP) && sL.getLayoutY() != yArrow[0]) {
					arrow_pos--;
					sL.setLayoutY(yArrow[arrow_pos]);
					sR.setLayoutY(yArrow[arrow_pos]);
				}
				if (key.getCode().equals(KeyCode.ENTER) && sL.getLayoutY() == yArrow[0]) { // new game was clicked
					HowManyPlayers game = new HowManyPlayers(stage);
				}

				if (key.getCode().equals(KeyCode.ENTER) && sL.getLayoutY() == yArrow[1]) { // help was clicked
					Help help = new Help(stage);
				}

				if (key.getCode().equals(KeyCode.ENTER) && sL.getLayoutY() == yArrow[2]) { // online was clicked
					connectOnline connect = new connectOnline();
					connect.start(stage);
				}
				if (key.getCode().equals(KeyCode.ENTER) && sL.getLayoutY() == yArrow[3]) { // quit wtf?
					stage.close();
				}
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
