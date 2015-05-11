package Menu;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Game.GameLoop;

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
		connectLabel = (Label) myPane.lookup("#connectLabel");
		hostLabel = (Label) myPane.lookup("#hostLabel");
		backLabel = (Label) myPane.lookup("#backLabel");
		playLabel = (Label) myPane.lookup("#playLabel");

		connect = (ImageView) myPane.lookup("#connect");
		host = (ImageView) myPane.lookup("#host");
		nameConnect = (ImageView) myPane.lookup("#nameConnect");
		portConnect = (ImageView) myPane.lookup("#portConnect");
		back = (ImageView) myPane.lookup("#back");
		play = (ImageView) myPane.lookup("#play");
		ip = (ImageView) myPane.lookup("#ipConnect");
		nameHost = (ImageView) myPane.lookup("#nameHost");
		portHost = (ImageView) myPane.lookup("#portHost");

		txtNameConnect = (TextField) myPane.lookup("#textNameConnect");
		txtPortConnect = (TextField) myPane.lookup("#portTextConnect");
		txtipTextConnect = (TextField) myPane.lookup("#ipTextHost");

		txtNameHost = (TextField) myPane.lookup("#txtHostName");
		txtPortHost = (TextField) myPane.lookup("#txtHostPort");

		Scene onlineScene = new Scene(myPane);

		hostLabel.setOnMouseClicked(new setLeftOpacity());

		connectLabel.setOnMouseClicked(new setRightOpacity());


		playLabel.setOnMouseClicked(new createOnlineGame(onlineStage));

			
		backLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Menu back = new Menu();
				back.getItStarted(onlineStage);

			}

		});

		onlineStage.setScene(onlineScene);
		onlineStage.show();

	}
	
	public class createOnlineGame implements EventHandler<MouseEvent>
	{
		Stage onlineStage;
		createOnlineGame(Stage onlineStage)
		{
			this.onlineStage = onlineStage;
		}

		@Override
		public void handle(MouseEvent event) {
			if (connect.getOpacity() < 1 && txtNameHost.getText() != null
					&& txtPortHost != null) {
				String playerName = txtNameHost.getText();
				int port = Integer.parseInt(txtPortHost.getText());
				System.out.println("server");
				GameLoop hostGame = new GameLoop(onlineStage, playerName, "");
				hostGame.host(port);
			} else if (host.getOpacity() < 1
					&& txtNameConnect.getText() != null
					&& txtPortConnect != null && txtipTextConnect != null) {
				String name = txtNameConnect.getText();
				int port = Integer.parseInt(txtPortConnect.getText());
				String ip = txtipTextConnect.getText();
				System.out.println("client");
				GameLoop connectGame = new GameLoop(onlineStage, "",name);
				connectGame.connect(ip, port);
				

			}
			
		}
		
	}
	public class setLeftOpacity implements EventHandler<MouseEvent>
	{
		
		double i = 1;
		double up = 0.3;
		AnimationTimer 	timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {

				if (connect.getOpacity() >= 0.3) {
					connect.setOpacity(i);
					nameConnect.setOpacity(i);
					portConnect.setOpacity(i);
					ip.setOpacity(i);
					txtipTextConnect.setOpacity(i);
					txtNameConnect.setOpacity(i);
					txtPortConnect.setOpacity(i);
					i -= 0.1;
					

				}
				if (i <= 0.3) {
					timer.stop();
					i = 1;
					
				}
				if (host.getOpacity() < 1) {
					host.setOpacity(up);
					nameHost.setOpacity(up);
					portHost.setOpacity(up);
					txtNameHost.setOpacity(up);
					txtPortHost.setOpacity(up);
					up += 0.1;
				}
			}

		};
		@Override
		public void handle(MouseEvent mouse) {
			timer.start();
			txtipTextConnect.setEditable(false);
			txtNameConnect.setEditable(false);
			txtPortConnect.setEditable(false);
			txtPortConnect.setText(""); txtNameConnect.setText("");txtipTextConnect.setText("");
			txtNameHost.setEditable(true);
			txtPortHost.setEditable(true);
			
			
		}
	}

			
		
		
	
	
	public class setRightOpacity implements EventHandler<MouseEvent>
	{
		double i = 1;
		double up = 0.3;
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {

				if (host.getOpacity() >= 0.3) {
					host.setOpacity(i);
					nameHost.setOpacity(i);
					portHost.setOpacity(i);
					txtNameHost.setOpacity(i);
					txtPortHost.setOpacity(i);
					i -= 0.1;
				}
				if (i <= 0.3) {
					timer.stop();
					i = 1;
					
				}
				if (connect.getOpacity() < 0.9) {
					connect.setOpacity(up);
					nameConnect.setOpacity(up);
					portConnect.setOpacity(up);
					ip.setOpacity(up);
					txtipTextConnect.setOpacity(up);
					txtNameConnect.setOpacity(up);
					txtPortConnect.setOpacity(up);
					up += 0.1;
				}
			}

		};
		
		@Override
		public void handle(MouseEvent mouse) {
			timer.start();
			txtNameHost.setEditable(false);
			txtPortHost.setEditable(false);
			txtipTextConnect.setEditable(true);
			txtNameConnect.setEditable(true);
			txtPortConnect.setEditable(true);
			txtNameHost.setText("");
			txtPortHost.setText("");
		}
	}
			
		
		

			
	

	public void onlineStart(Stage stage) throws Exception {
		start(stage);
	}

}
