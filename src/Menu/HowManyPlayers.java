package Menu;

import java.io.IOException;
import java.util.Iterator;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HowManyPlayers extends Application {
	private  ImageView ARROW;
	private  ImageView SELECTED_ONE_PLAYER, SELECTED_TWO_PLAYER;

	private Pane myPane;

	private Scene scene;
	@FXML
	ImageView pOne, pTwo, back, play;
	private int pos = 0;

	public HowManyPlayers(Stage stage) {
		
		try {
			myPane = (Pane) FXMLLoader.load(getClass().getResource(
					"twoOrOnePlayer.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start(stage);
	}
/**
 * Load menu images.
 * @param myPane
 */
	public void getFXML(Pane myPane) {
		pOne = (ImageView) myPane.lookup("#pOne");
		pTwo = (ImageView) myPane.lookup("#pTwo");
		back = (ImageView) myPane.lookup("#back");
		play = (ImageView) myPane.lookup("#play");
	}

	@Override
	public void start(Stage primaryStage) {
		

		getFXML(myPane); // load fxml pics

		scene = new Scene(myPane);
		myPane.setOnKeyPressed(select(primaryStage, myPane));
		setArrow menuArrow = new setArrow(myPane, pOne.getLayoutY() - 25,
				pOne.getLayoutX() - 90).setSelectArrows();
		
		myPane.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				if(pOne.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
					OnePlayerMenu one = new OnePlayerMenu(primaryStage, 1);
			}
			else if(pTwo.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
				OnePlayerMenu one = new OnePlayerMenu(primaryStage, 2);
			}
			
			
				
			}});

		primaryStage.setScene(scene);
		primaryStage.show();

	}
/**
 * Set the menu select arrow.
 * @author Eric
 *
 */
	public class setArrow {
		Pane root;
		double xKord;
		double yKord;

		private setArrow(Pane root, double xKord, double yKord) {
			this.root = root;
			this.xKord = xKord;
			this.yKord = yKord;
		}

		public setArrow setSelectArrows() {

			Image arrow = new Image("selectArrowRight.png");

			ARROW = new ImageView(arrow);

			root.getChildren().add(ARROW);
			ARROW.setId("ERIC");
			ARROW.setScaleX(0.6);
			ARROW.setScaleY(0.6);

			ARROW.setLayoutY(xKord);
			ARROW.setLayoutX(yKord);
			ARROW.setFocusTraversable(true);
			return new setArrow(root, xKord, yKord);
		}
	}

	private EventHandler<KeyEvent> select(Stage stage, Pane pane) {

		/**
		 * Positions for each object in the scene
		 */
		double[] xArrow = new double[] { pOne.getLayoutX() - 90,
				pTwo.getLayoutX() - 90, back.getLayoutX() - 90,
				play.getLayoutX() - 90 };
		double[] yArrow = new double[] { pOne.getLayoutY() - 25,
				pTwo.getLayoutY() - 25, back.getLayoutY() - 25,
				play.getLayoutY() - 25 };

		EventHandler<KeyEvent> e = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key){
				/**
				 * Move around the menu
				 */
				if (key.getCode().equals(KeyCode.DOWN)) {
					pos++;
					if (pos == xArrow.length) {
						pos = 0;
					}
					ARROW.setLayoutX(xArrow[pos]);
					ARROW.setLayoutY(yArrow[pos]);
				} else if (key.getCode().equals(KeyCode.UP)) {
					pos--;
					if (pos <= -1) {
						pos = xArrow.length - 1;
					}
					ARROW.setLayoutX(xArrow[pos]);
					ARROW.setLayoutY(yArrow[pos]);

				}
				/**
				 * Select one player
				 */
				if (key.getCode().equals(KeyCode.ENTER)
						&& ARROW.getLayoutY() == yArrow[0]) { // 1player
					SELECTED_ONE_PLAYER = selected(pane, SELECTED_ONE_PLAYER);
					SELECTED_ONE_PLAYER.setLayoutY(yArrow[0]);
					SELECTED_ONE_PLAYER.setLayoutX(xArrow[0]);
					if (SELECTED_TWO_PLAYER != null) {
						pane.getChildren().remove(SELECTED_TWO_PLAYER);	
					SELECTED_TWO_PLAYER = null;
					}

				}
				/**
				 * Select two players
				 */
				if (key.getCode().equals(KeyCode.ENTER)
						&& ARROW.getLayoutY() == yArrow[1]) { // 1player
					SELECTED_TWO_PLAYER = selected(pane, SELECTED_TWO_PLAYER);
					SELECTED_TWO_PLAYER.setLayoutY(yArrow[1]);
					SELECTED_TWO_PLAYER.setLayoutX(xArrow[1]);
					
					if (SELECTED_ONE_PLAYER != null) {
						pane.getChildren().remove(SELECTED_ONE_PLAYER);
						SELECTED_ONE_PLAYER = null;
					}

				}
				/**
				 * Check if one or two players are selected then go into the correct scene.
				 * if nothing is selected then just consume the event.
				 */
				if (key.getCode().equals(KeyCode.ENTER)	&& ARROW.getLayoutY() == yArrow[3]) {
					if(SELECTED_TWO_PLAYER != null){
						OnePlayerMenu one = new OnePlayerMenu(stage, 2);
					}
					else if(SELECTED_ONE_PLAYER != null){
						OnePlayerMenu one = new OnePlayerMenu(stage, 1);
						
					}else{key.consume();}
				}
				/**
				 * Go back
				 */
				if (key.getCode().equals(KeyCode.ENTER)
						&& ARROW.getLayoutX() == xArrow[2]) {
					
						pane.getChildren().removeAll(SELECTED_ONE_PLAYER,SELECTED_TWO_PLAYER);
						key.consume();
						Menu menu = new Menu();
						menu.getItStarted(stage);

				}
				
			}

		};
		return e;
	}

	public ImageView selected(Pane pane, ImageView player) {
		Image selected = new Image("selectArrowRight.png");
		player = new ImageView(selected);
		pane.getChildren().add(player);
		player.setScaleX(0.6);
		player.setScaleY(0.6);
		return player;
	}

}
