import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class connectOnline extends Application {
	private static AnimationTimer timer;
	@FXML
	ImageView connect;
	@FXML
	ImageView host;
	@FXML
	ImageView nameConnect;
	@FXML
	ImageView portConnect;
	@FXML
	ImageView back;
	@FXML
	ImageView play;
	@FXML
	ImageView sL;
	@FXML
	ImageView sR;
	@FXML
	ImageView nameHost;
	@FXML
	ImageView portHost;
	@FXML
	ImageView ip;

	@FXML
	TextField txtNameConnect, txtPortConnect, txtipTextConnect, txtNameHost,
			txtPortHost;

	@FXML
	Label connectLabel, hostLabel, backLabel, playLabel;

	private int posX = 0;
	private int posY = 0;

	@Override
	public void start(Stage onlineStage) throws Exception {

		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource(
				"connectOnline.fxml"));
		Label connectLabel = (Label) myPane.lookup("#connectLabel");
		Label hostLabel = (Label) myPane.lookup("#hostLabel");
		Label backLabel = (Label) myPane.lookup("#backLabel");
		Label playLabel = (Label) myPane.lookup("#playLabel");
		
		ImageView connect = (ImageView) myPane.lookup("#connect");
		ImageView host = (ImageView) myPane.lookup("#host");
		ImageView nameConnect = (ImageView) myPane.lookup("#nameConnect");
		ImageView portConnect = (ImageView) myPane.lookup("#portConnect");
		ImageView back = (ImageView) myPane.lookup("#back");
		ImageView play = (ImageView) myPane.lookup("#play");
		ImageView ip = (ImageView) myPane.lookup("#ipConnect");
		ImageView nameHost = (ImageView) myPane.lookup("#nameHost");
		ImageView portHost = (ImageView) myPane.lookup("#portHost");

		TextField txtNameConnect = (TextField) myPane
				.lookup("#textNameConnect");
		TextField txtPortConnect = (TextField) myPane
				.lookup("#portTextConnect");
		TextField txtipTextConnect = (TextField) myPane.lookup("#ipTextHost");

		TextField txtNameHost = (TextField) myPane.lookup("#txtHostName");
		TextField txtPortHost = (TextField) myPane.lookup("#txtHostPort");

		Scene onlineScene = new Scene(myPane);
		
		hostLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouse) {
				
				Timeline timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);

				timer = new AnimationTimer() {
					double i = 1;
					double up = 0.3;

					@Override
					public void handle(long arg0) {

						if (connect.getOpacity() >= 0.3) {
							connect.setOpacity(i); nameConnect.setOpacity(i); portConnect.setOpacity(i); ip.setOpacity(i);
							txtipTextConnect.setOpacity(i); txtNameConnect.setOpacity(i); txtPortConnect.setOpacity(i);
							i -= 0.1;
							txtNameHost.setFocusTraversable(true);
						}
						if (i <= 0.3) { timeline.stop(); timer.stop(); i = 1; mouse.consume();}
						if(host.getOpacity() < 1){
							host.setOpacity(up); nameHost.setOpacity(up); portHost.setOpacity(up); txtNameHost.setOpacity(up);
							txtPortHost.setOpacity(up);
							up +=0.1;
						}
					}

				};
				timeline.play();
				timer.start();
			}
		});
		

		connectLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouse) {

				Timeline timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);

				timer = new AnimationTimer() {
					double i = 1;
					double up = 0.3;

					@Override
					public void handle(long arg0) {

						if (host.getOpacity() >= 0.3) {
							host.setOpacity(i); nameHost.setOpacity(i); portHost.setOpacity(i); txtNameHost.setOpacity(i);
							txtPortHost.setOpacity(i);
							
							i -= 0.1;
						}
						if (i <= 0.3) { timeline.stop(); timer.stop(); i = 1; mouse.consume();}
						if(connect.getOpacity() < 0.9){
							connect.setOpacity(up);
							nameConnect.setOpacity(up);
							portConnect.setOpacity(up);
							ip.setOpacity(up);
							txtipTextConnect.setOpacity(up);
							txtNameConnect.setOpacity(up);
							txtPortConnect.setOpacity(up);
							up +=0.1;
						}
					}
				};
				timeline.play();
				timer.start();
			}
		});
		
		playLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				if(connect.getOpacity() < 1 && txtNameHost.getText() != null &&
						txtPortHost != null){
					String playerName = txtNameConnect.getText();
					int port = Integer.parseInt(txtPortConnect.getText());
					System.out.println("server");
					OnlineGame hostGame = new OnlineGame(onlineStage, playerName, port);
			}else if(host.getOpacity() < 1 && txtNameConnect.getText() != null &&
					txtPortConnect != null && txtipTextConnect != null){
				String name = txtNameConnect.getText();
				int port = Integer.parseInt(txtPortConnect.getText());
				String ip = txtipTextConnect.getText();
				System.out.println("client");
				OnlineGame hostGame = new OnlineGame(onlineStage, name,ip ,port);
				
			}
				
			}
			
		});
		
		backLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				Menu back = new Menu();
				back.getItStarted(onlineStage);
				
			}
			
		});

		onlineStage.setScene(onlineScene);
		onlineStage.show();

	}
	


	public void onlineStart(Stage stage) throws Exception {
		start(stage);
	}

}
