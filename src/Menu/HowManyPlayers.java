package Menu;

import java.net.ConnectException;

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
	ImageView pOne, pTwo, back, playOnline;
	private int pos = 0;

	public HowManyPlayers(Stage stage) throws Exception {
		start(stage);
	}

	public void getFXML(Pane myPane) {
		pOne = (ImageView) myPane.lookup("#pOne");
		pTwo = (ImageView) myPane.lookup("#pTwo");
		back = (ImageView) myPane.lookup("#back");
		playOnline = (ImageView) myPane.lookup("#playOnline");
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

	public EventHandler<KeyEvent> select(Stage stage) throws Exception{
		double[] xArrow = new double[] { pOne.getLayoutX() - 90,
				pTwo.getLayoutX() - 90, playOnline.getLayoutX() - 90,back.getLayoutX() - 90
				 };
		double[] yArrow = new double[] { pOne.getLayoutY() - 25,
				pTwo.getLayoutY() - 25, playOnline.getLayoutY() - 25,back.getLayoutY() - 25
				 };

		EventHandler<KeyEvent> e = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				System.out.println(pos);
				if (key.getCode().equals(KeyCode.DOWN) && pos != xArrow.length-1) {
					pos++;
					ARROW.setLayoutX(xArrow[pos]);
					ARROW.setLayoutY(yArrow[pos]);
					

				} else if (key.getCode().equals(KeyCode.UP) && pos != 0) {
					pos--;
					ARROW.setLayoutX(xArrow[pos]);
					ARROW.setLayoutY(yArrow[pos]);
					
				}
				
				if(key.getCode().equals(KeyCode.ENTER) && ARROW.getLayoutX() == xArrow[0]){ //1 player
				System.out.println("WT?");
						try {
							OnePlayer oneplayerGame = new OnePlayer(stage);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					
				}
				if(key.getCode().equals(KeyCode.ENTER) && ARROW.getLayoutX() == xArrow[1]){ //two players
					System.out.println("WT?");
							try {
								TwoPlayer twoPlayerGame = new TwoPlayer(stage);
							} catch (Exception e) {
								
								e.printStackTrace();
							}
						
					}
				if(key.getCode().equals(KeyCode.ENTER) && ARROW.getLayoutX() == xArrow[3]){ //back to menu
					System.out.println("WT?");
							try {
								Menu menu = new Menu();
								menu.getItStarted(stage);
							} catch (Exception e) {
								
								e.printStackTrace();
							}
						
					}
				if(key.getCode().equals(KeyCode.ENTER) && ARROW.getLayoutX() == xArrow[2]){ //back to menu
					System.out.println("WT?");
							try {
								connectOnline onlineGame = new connectOnline();
								onlineGame.onlineStart(stage);
							} catch (Exception e) {
								
								e.printStackTrace();
							}
						
					}
			}
		};
		return e;
	}
}
