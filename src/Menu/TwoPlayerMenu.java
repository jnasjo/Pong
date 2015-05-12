package Menu;

import Game.GameLoop;
import Menu.HowManyPlayers.setArrow;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TwoPlayerMenu extends Application{
	private static ImageView ARROW;
	private int pos = 0;
	
	@FXML
	ImageView back, play;
	
	@FXML
	TextField p1, p2;
	
	public TwoPlayerMenu(Stage stage) throws Exception
	{
		start(stage);
	}
	public void getFXML(Pane myPane) {
		back = (ImageView) myPane.lookup("#back");
		play = (ImageView) myPane.lookup("#play");
		p1 = (TextField) myPane.lookup("#p1");
		p2 = (TextField) myPane.lookup("#p2");
		p1.setFocusTraversable(false);
		p2.setFocusTraversable(false);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
				"twoPlayerMenu.fxml"));
		getFXML(myPane);
	
		setArrow menuArrow = new setArrow(myPane, back.getLayoutY() - 25,
		back.getLayoutX() - 90).setSelectArrows();

		myPane.setOnKeyPressed(select(primaryStage, myPane));
		Scene scene = new Scene(myPane);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	private EventHandler<KeyEvent> select(Stage stage, Pane pane) {

		/**
		 * Positions for each object in the scene
		 */
		double[] xArrow = new double[] {back.getLayoutX() - 90,
				play.getLayoutX() - 90 };
		double[] yArrow = new double[] {back.getLayoutY() - 25,
				play.getLayoutY() - 25 };

		EventHandler<KeyEvent> e = new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key){
				/**
				 * Move around the menu
				 */
				if (key.getCode().equals(KeyCode.DOWN) ||key.getCode().equals(KeyCode.RIGHT)) {
					if(pos < 1){
						pos++;
					}
					ARROW.setLayoutX(xArrow[pos]);
				} else if (key.getCode().equals(KeyCode.UP) || key.getCode().equals(KeyCode.LEFT)) {
					if(pos > 0){
						pos--;
					}
					ARROW.setLayoutX(xArrow[pos]);
				}
				if(key.getCode().equals(KeyCode.ENTER) && ARROW.getLayoutX() == xArrow[0] || key.getCode().equals(KeyCode.ESCAPE)){//go back
					Menu menu = new Menu();
					menu.getItStarted(stage);
				}
				if(key.getCode().equals(KeyCode.ENTER) && ARROW.getLayoutX() == xArrow[1]){ //play
					GameLoop game = new GameLoop(stage, getP1Name(), getP2Name());
				}
			}
			
		};
		return e;
	}
	//28 255 251 RGB COLOR FOR TEXT...
	public String getP1Name()
	{
		return p1.getText();
	}
	public String getP2Name()
	{
		return p2.getText();
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
			ARROW.setId("ERIC");
			ARROW.setScaleX(0.6);
			ARROW.setScaleY(0.6);

			ARROW.setLayoutY(xKord);
			ARROW.setLayoutX(yKord);
			ARROW.setFocusTraversable(true);
			return new setArrow(root, xKord, yKord);
		}
	}
}