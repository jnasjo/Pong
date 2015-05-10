import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class connectOnline extends Application{

	@FXML
	ImageView connect, host ,name, port, back, play;
	
	@FXML
	String textName, portText;
	
	
	@Override
	public void start(Stage onlineStage) throws Exception {
		
		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
				"connectOnline.fxml"));
		Scene onlineScene = new Scene(myPane);
		onlineStage.setScene(onlineScene);
		onlineStage.show();
		
	}
	
	public void onlineStart(Stage stage) throws Exception
	{
		start(stage);
	}

}
