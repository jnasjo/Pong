
	
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Menu extends Application{
	
	private String name;
	
	@FXML
	Label header;
	
	@FXML
	Label newGame;
	
	@FXML
	Label help;
	
	@FXML
	Label quit;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Pong");
		
		try {
		
		Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("MenuFXML.fxml"));
			
		Label header =  (Label) myPane.lookup("#Header");
		Label startGame = (Label) myPane.lookup("#newGame");
		Label quit = (Label) myPane.lookup("#quit");
		Label help = (Label) myPane.lookup("#help");
		
		quit.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				final Stage pop = new Stage();
				pop.initModality(Modality.APPLICATION_MODAL);
				pop.initOwner(primaryStage);
				VBox dialogBox = new VBox(20);
				
				Text tx = new Text("Are you really sure????+");
				dialogBox.getChildren().add(tx);
				
				Scene dialogScene = new Scene(dialogBox,300,200);
				pop.setScene(dialogScene);
				pop.show();
				
			}
			
		});
		
		help.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					
					Help help = new Help(primaryStage);
				} catch (Exception e) {
					System.out.println("FEL");
					e.printStackTrace();
				}
			}
			
		});
		
	
		
		
		startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					Game game = new Game(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		header.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			
				header.setScaleX(1.2);
				header.setScaleY(1.2);
		
			}
		});
		
		
		header.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				
				header.setScaleX(1);
				header.setScaleY(1);
				
			}
			
		});
			
			Scene myScene = new Scene(myPane);
			primaryStage.setScene(myScene);
			primaryStage.show();
			
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	public void getItStarted(Stage primaryStage){
		start(primaryStage);
		
	}
}
