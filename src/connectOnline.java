import javafx.application.Application;
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



public class connectOnline extends Application{

	@FXML
	ImageView connect, host ,nameConnect, portConnect, back, play, sL, sR,nameHost, portHost ;
	
	@FXML
	String txtNameConnect, txtPortConnect, txtNameHost, txtPortHost;
	
	
	private int posX = 0;
	private int posY = 0;
	
	
	
	
	@Override
	public void start(Stage onlineStage) throws Exception {
		
		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
				"connectOnline.fxml"));
		ImageView connect = (ImageView) myPane.lookup("#connect");
		ImageView host = (ImageView) myPane.lookup("#host");
		ImageView nameConnect = (ImageView) myPane.lookup("#nameConnect");
		ImageView portConnect = (ImageView) myPane.lookup("#portConnect");
		ImageView back = (ImageView) myPane.lookup("#back");
		ImageView play = (ImageView) myPane.lookup("#play");
		ImageView nameHost = (ImageView) myPane.lookup("#nameHost");
		ImageView portHost = (ImageView) myPane.lookup("#portHost");
		
		
	
		

	
		
		Scene onlineScene = new Scene(myPane);
		onlineScene.setOnMouseClicked(select(onlineStage));
		onlineStage.setScene(onlineScene);
		onlineStage.show();
		
		
	}

	public EventHandler<MouseEvent> select(Stage stage)
	{
		EventHandler<MouseEvent> e = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) 
			{
				System.out.println(key.getSceneX());
				if(key.getButton().equals(MouseEvent.MOUSE_CLICKED)){
					
					
				}
			}
		};
		return e;
	}
	
	public void onlineStart(Stage stage) throws Exception
	{
		start(stage);
	}

}
