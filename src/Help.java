
import java.io.IOException;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Help{
	@FXML
	Button b;
	
	public Help(Stage helpStage) throws Exception
	{
		start(helpStage);
	}
	
	public void start(Stage helpStage) throws Exception {
	
		helpStage.setTitle("HELP");
		try{
			Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("helpFXML.fxml"));
			Scene helpScene = new Scene(myPane);
			Button back = (Button) myPane.lookup("#back");
			
			back.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Menu menu = new Menu();
					menu.getItStarted(helpStage);
				}
				
			});
			
			
			helpStage.setScene(helpScene);
			helpStage.show();

		}catch(IOException e){
			throw new IOException();
		}
		
		
	}

}