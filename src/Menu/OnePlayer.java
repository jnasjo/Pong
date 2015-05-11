package Menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OnePlayer extends Application{
	
	
	
	public OnePlayer(Stage stage) throws Exception
	{
		
		
		start(stage);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
			Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
					"OnePlayerMenu.fxml"));
		
		
		Scene scene = new Scene(myPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
