package Menu;
import java.io.IOException;

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

public class Help {
	@FXML
	ImageView back;

	private ImageView sL;
	private ImageView sR;

	public Help(Stage helpStage) throws IOException {
		start(helpStage);
	}

	public void start(Stage helpStage) throws IOException {

		helpStage.setTitle("HELP");
		try {
			Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
					"Help.fxml"));
			Scene helpScene = new Scene(myPane);
			ImageView back = (ImageView) myPane.lookup("#back");

			Image selectArrowLeft = new Image("selectArrowLeft.png");
			Image selectArrowRight = new Image("selectArrowRight.png");
			sL = new ImageView(selectArrowLeft);
			sR = new ImageView(selectArrowRight);
			sL.setScaleX(0.5);
			sL.setScaleY(0.5);
			sR.setScaleX(0.5);
			sR.setScaleY(0.5);

			myPane.getChildren().addAll(sL, sR);
			sL.setLayoutX(back.getLayoutX() - 70);
			sL.setLayoutY(back.getLayoutY() - 20);
			sR.setLayoutX(back.getLayoutX() + back.getFitWidth() - 20);
			sR.setLayoutY(back.getLayoutY() - 20);

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
			throw new IOException();
		}

	}

}