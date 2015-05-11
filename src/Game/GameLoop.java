package Game;

import javafx.stage.Stage;
import Network.Client;
import Network.NetworkNode;
import Network.Server;
 
public class GameLoop implements Runnable {
 
        private final int FPS = 60;
        private final Game UI;
 
        private String PLAYER_1_NAME = "Pong_Master";
        private String PLAYER_2_NAME = "Japanese_PING_";
        private int p1Score = 0;
        private int p2Score = 0;
 
        private Player player1;
        private Player player2;
        private Ball ball;
 
        // NETWORK TINGS
        private NetworkNode myConnection;
        private boolean isPlayer1 = true;
 
        public GameLoop(Stage stage, String p1Name, String p2Name) {
                if (!p1Name.equals(""))
                        PLAYER_1_NAME = p1Name;
                if (!p2Name.equals(""))
                        PLAYER_2_NAME = p2Name;
 
                // Create a new game with all components that we need
                // Get the UI components
                UI = new Game(stage);
                // Create the name-tags
                UI.updatePlayerNames(PLAYER_1_NAME, PLAYER_2_NAME);
 
                // Get the objects that we will use often
                player1 = UI.getPlayer1();
                player2 = UI.getPlayer2();
                ball = UI.getBall();
 
                // Start the game in it's own thread
                new Thread(this).start();
        }
       
        /*
         * ---------------------------------------
         * ---------------------------------------
         * ---------------------------------------
         * ---------------------------------------
         * -----REMEMBER TO SYNCRONIZE THINGS-----
         * ---------------------------------------
         * ---------------------------------------
         * ---------------------------------------
         * ---------------------------------------
         */
 
        /**
         * Hosts a game on the provided port
         *
         * @param port
         *            The port to host on
         */
        public void host(int port) {
                isPlayer1 = true;
                myConnection = new Server(port, this);
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                // Create our connection as a server
                                myConnection.start();
 
                                // Send our name to the opponent
                                myConnection.sendMessage(PLAYER_1_NAME);
                        }
                }).start();
        }
 
        /**
         * Connects to a host
         *
         * @param IP
         *            The IP to connect to
         * @param port
         *            The port to use
         */
        public void connect(String IP, int port) {
                isPlayer1 = false;
                myConnection = new Client(IP, port, this);
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                // Create our connection as a client
                                myConnection.start();
 
                                // Send our name to the opponent
                                myConnection.sendMessage(PLAYER_2_NAME);
                        }
                }).start();
        }
 
        /*
         * The actual game loop
         */
        @Override
        public void run() {
                long ticksPS = 1000 / FPS;
                long startTime;
                long sleepTime;
 
                while (true) {
                        startTime = System.currentTimeMillis();
 
                        //
                        // GAME CODE START
                        //
                       
                        // Move Ball
                       
                        // Try to move players
 
                        //
                        // GAME CODE STOP
                        //
 
                        sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                        try {
                                // To keep the 60 FPS
                                Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                }
        }
 
        /**
         * Set's the name of the partner
         *
         * @param name
         *            The new name for the partner
         */
        public void setName(String name) {
                if (isPlayer1)
                        PLAYER_2_NAME = name;
                else
                        PLAYER_1_NAME = name;
 
                // Update UI
                UI.updatePlayerNames(PLAYER_1_NAME, PLAYER_2_NAME);
        }
 
        /**
         * Add's a point to the specified player
         *
         * @param res
         *            Specified information
         */
        public void addPoint(String[] res) {
                if (res[1].equals("player1")) {
                        // Add point to player1
                        p1Score++;
                } else {
                        // Add point to player2
                        p2Score++;
                }
                UI.updatePlayerScore(p1Score, p2Score);
        }
 
        /**
         * Set's the new position of the provided object
         *
         * @param res
         *            The object and new position // TODO
         *            in the form of [command, object, x, y]
         */
        public void setPos(String[] res) {
                if (res[1].equals("ball")) {
                        // Move ball
                        ball.setPos(Double.valueOf(res[2]), Double.valueOf(res[3]));
                } else if (res[1].equals("player")) {
                        // Move opponent
                        if(isPlayer1) {
                                // Move player 2
                                player2.setPos(Double.valueOf(res[2]), Double.valueOf(res[3]));
                        } else {
                                // Move player 1
                                player1.setPos(Double.valueOf(res[2]), Double.valueOf(res[3]));
                        }
                }
        }
}