package Menu;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HowManyPlayers extends Application {
	ImageView LEFT_ARROW, ARROW;
	@FXML
	ImageView pOne, pTwo, back, play;
	private int pos = 1;

	public HowManyPlayers(Stage stage) throws Exception {
		start(stage);
	}

	public void getFXML(Pane myPane) {
		pOne = (ImageView) myPane.lookup("#pOne");
		pTwo = (ImageView) myPane.lookup("#pTwo");
		back = (ImageView) myPane.lookup("#back");
		play = (ImageView) myPane.lookup("#play");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
				"twoOrOnePlayer.fxml"));
		getFXML(myPane); // load fxml pics

		Scene scene = new Scene(myPane);
		myPane.setOnKeyPressed(select(primaryStage));
		scene.setOnKeyPressed(select(primaryStage));
		setSelectArrows(myPane); // load select arrows;
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void setSelectArrows(Pane root) {

		Image arrow = new Image("selectArrowRight.png");

		ARROW = new ImageView(arrow);

		root.getChildren().add(ARROW);

		ARROW.setScaleX(0.6);
		ARROW.setScaleY(0.6);

		ARROW.setLayoutY(pOne.getLayoutY() - 25);
		ARROW.setLayoutX(pOne.getLayoutX() - 90);

	}

	public EventHandler<KeyEvent> select(Stage stage) {
		double[] xArrow = new double[] { pOne.getLayoutX() - 90,
				pTwo.getLayoutX() - 90, back.getLayoutX() - 90,
				play.getLayoutX() - 90 };
		double[] yArrow = new double[] { pOne.getLayoutY() - 25,
				pTwo.getLayoutY() - 25, back.getLayoutY() - 25,
				play.getLayoutY() - 25 };
		

		EventHandler<KeyEvent> e = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.DOWN) && pos >= 0 && pos <= xArrow.length) {
			System.out.println("DOWN"  + pos);
					if(pos == xArrow.length){
						pos = 0;
					}
					ARROW.setLayoutX(xArrow[pos]);
					ARROW.setLayoutY(yArrow[pos]);
					pos++;
				}else if(key.getCode().equals(KeyCode.UP) && pos >= 0 && pos <= xArrow.length){
					System.out.println("UP"+pos);
					pos--;	
					if(pos == 0){
						pos = xArrow.length-1;
					}
					ARROW.setLayoutX(xArrow[pos]);
					ARROW.setLayoutY(yArrow[pos]);
					
	
				}
			}
		};
		return e;
	}
}
