package Menu;
import java.io.IOException;

import Game.GameLoop;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Help {
	@FXML
	ImageView back;

	private ImageView sL;
	private ImageView sR;

	public Help(Stage helpStage) {
		start(helpStage);
	}

	public void start(Stage helpStage) {

		helpStage.setTitle("HELP");
		try {
			Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
					"Help.fxml"));
			Scene helpScene = new Scene(myPane);
			ImageView back = (ImageView) myPane.lookup("#back");

		

			myPane.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e) {
					if(back.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
						Menu m = new Menu();
						m.getItStarted(helpStage);
				}
						
				}});
			EventHandler<KeyEvent> select = new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					if (event.getCode().equals(KeyCode.ENTER)) {
						Menu m = new Menu();
						m.getItStarted(helpStage);
					}
				}

			};
			helpScene.setOnKeyPressed(select);

			helpStage.setScene(helpScene);
			helpStage.show();

		} catch (IOException e) {
			
		}

	}

}