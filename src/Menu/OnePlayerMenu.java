package Menu;

import java.io.IOException;

import Game.GameLoop;
import Menu.TwoPlayerMenu.setArrow;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OnePlayerMenu extends Application {
	private static ImageView ARROW;
	private int pos = 0;

	@FXML
	private ImageView back, play;

	private Pane myPane;

	private int playerCount;

	@FXML
	private TextField p1, p2;

	private Menu menu;

	public OnePlayerMenu(Stage stage, int playerCount) {
		this.playerCount = playerCount;
		try {
			if (playerCount == 1) {
				myPane = (Pane) FXMLLoader.load(getClass().getResource(
						"OnePlayerMenu.fxml"));
			} else {
				myPane = (Pane) FXMLLoader.load(getClass().getResource(
						"TwoPlayerMenu.fxml"));
			}
			start(stage);
		} catch (IOException e) {
		}
		;

	}

	public void getFXML(Pane myPane) {
		back = (ImageView) myPane.lookup("#back");
		play = (ImageView) myPane.lookup("#play");
		p1 = (TextField) myPane.lookup("#p1");
		p1.setFocusTraversable(false);
		if (playerCount == 2) {
			p2 = (TextField) myPane.lookup("#p2");
			p2.setFocusTraversable(false);
		}

	}

	public static class setArrow {
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
			ARROW.setScaleX(0.6);
			ARROW.setScaleY(0.6);

			ARROW.setLayoutY(xKord);
			ARROW.setLayoutX(yKord);
			ARROW.setFocusTraversable(true);
			return new setArrow(root, xKord, yKord);
		}
	}

	@Override
	public void start(Stage primaryStage) {

		getFXML(myPane);
		setArrow menuArrow = new setArrow(myPane, play.getLayoutY() - 25,
				play.getLayoutX() - 90).setSelectArrows();
		myPane.setOnKeyPressed(select(primaryStage, myPane));

		myPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (back.getBoundsInParent().contains(e.getSceneX(),
						e.getSceneY())) {
					Menu menu = new Menu();
					menu.getItStarted(primaryStage);
				}

				else if (play.getBoundsInParent().contains(e.getSceneX(),
						e.getSceneY())) {
					if(playerCount == 1){
						GameLoop game = new GameLoop(primaryStage, getP1Name(),"JAPNIZ3_PING_MASTER");
					}else{
						GameLoop game = new GameLoop(primaryStage, getP1Name(),getP2Name());
					}
				}
			}
		});

		Scene scene = new Scene(myPane);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private EventHandler<KeyEvent> select(Stage stage, Pane pane) {

		/**
		 * Positions for each object in the scene
		 */
		double[] xArrow = new double[] { back.getLayoutX() - 90,
				play.getLayoutX() - 90 };
		double[] yArrow = new double[] { back.getLayoutY() - 25,
				play.getLayoutY() - 25 };

		EventHandler<KeyEvent> e = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				/**
				 * Move around the menu
				 */
				if (key.getCode().equals(KeyCode.DOWN)
						|| key.getCode().equals(KeyCode.RIGHT)) {
					if (pos < 1) {
						pos++;
					}
					ARROW.setLayoutX(xArrow[pos]);
				} else if (key.getCode().equals(KeyCode.UP)
						|| key.getCode().equals(KeyCode.LEFT)) {
					if (pos > 0) {
						pos--;
					}
					ARROW.setLayoutX(xArrow[pos]);
				}
				if (key.getCode().equals(KeyCode.ENTER)
						&& ARROW.getLayoutX() == xArrow[0]
						|| key.getCode().equals(KeyCode.ESCAPE)) {// go back

					menu = new Menu();
					menu.getItStarted(stage);
				}
				if (key.getCode().equals(KeyCode.ENTER)
						&& ARROW.getLayoutX() == xArrow[1]) { // play
					if (playerCount == 1) {
						GameLoop gameOne = new GameLoop(stage, getP1Name(), "JAPNIZ3_PING_MASTER");
					} else {
						GameLoop game = new GameLoop(stage, getP1Name(),
								getP2Name());
					}

				}
			}

		};
		return e;
	}

	public String getP1Name() {
		return p1.getText();
	}

	public String getP2Name() {
		return p2.getText();
	}

}
