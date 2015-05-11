import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class Game {
 
        // Timer for fade in
        private AnimationTimer timer;
 
        private final static double SCREEN_SIZE = .5;
        private final static int SCREEN_HEIGHT = 1080;
        private final static int SCREEN_WIDTH = 1920;
 
        private final static Color BACKGROUND = Color.rgb(32, 32, 32);
        private final static Color BALLCOLOR = Color.rgb(0, 137, 139);
        private final static Color PLAYER1_COLOR = Color.rgb(26, 162, 97);
        private final static Color PLAYER2_COLOR = Color.rgb(217, 54, 114);
        private final static Color SCORE_TEXT_COLOR = Color.rgb(183, 183, 183);
 
        // Drawing space, AKA window height and width
        public final static int CANVAS_HEIGHT = (int) (SCREEN_HEIGHT * SCREEN_SIZE);
        public final static int CANVAS_WIDTH = (int) (SCREEN_WIDTH * SCREEN_SIZE);
 
        // GAME OBJECTS
        private Ball ball;
        private Player player1;
        private Player player2;
        private Text p1Score;
        private Text p2Score;
        private Text p1Name;
        private Text p2Name;
        private Shape shape;
        private Group root;
 
        private final static boolean SHOW_DEV_INFO = false;
 
        public Game(Stage stage) {
                start(stage);
        }
 
        public void start(Stage primaryStage) {
                primaryStage.setTitle("Pong");
 
                primaryStage.setOpacity(0.1);
                fadeIn(primaryStage); // Game is fading in and i'm making
                                                                // comments!!!!!!!!!
 
                root = new Group();
                root.setFocusTraversable(true);
 
                Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);
                primaryStage.setScene(scene);
 
                shape = new Shape(root);
 
                createPlayingField();
 
                createPlayers();
 
                ball = new Ball(shape.drawCircle(0, 0, 60, BALLCOLOR), root, p1Score,
                                p2Score, player1, player2);
 
                EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent key) {
                                player1.handle(key);
                                player2.handle(key);
                        }
                };
 
                scene.setOnKeyPressed(handler);
                scene.setOnKeyReleased(handler);
 
                if (SHOW_DEV_INFO) {
                        showDevInfo();
                }
 
                primaryStage.getScene().setFill(BACKGROUND);
                primaryStage.setResizable(false);
                primaryStage.sizeToScene(); // Ensure that we get a correctly sized
                                                                        // canvas and not window
                primaryStage.show();
        }
 
        /**
         * I'll let you figure out what dis one does on your own
         */
        private void showDevInfo() {
                Text text = shape.drawText(0, 0, 12, Shape.TextDirection.LEFT_TO_RIGHT,
                                "-", null);
                Text p1Keys = shape.drawText(0, 14, 12,
                                Shape.TextDirection.LEFT_TO_RIGHT, "-", null);
                Text p2Keys = shape.drawText(0, 28, 12,
                                Shape.TextDirection.LEFT_TO_RIGHT, "-", null);
                Text col = shape.drawText(0, 42, 12, Shape.TextDirection.LEFT_TO_RIGHT,
                                "-", null);
                text.setOpacity(0.4);
                Line colL1 = shape.drawLine(-1, 0, -1, CANVAS_HEIGHT, 1, null);
                Line colL2 = shape.drawLine(0, -1, CANVAS_WIDTH, -1, 1, null);
 
                new AnimationTimer() {
                        @Override
                        public void handle(long arg0) {
                                text.setText(player1.getSpeedDEV());
                                p1Keys.setText(player1.getKeyboard().getKeysDown());
                                p2Keys.setText(player2.getKeyboard().getKeysDown());
 
                                // Collision cross
                                double[] cord = ball.colCoords();
                                if (cord != null) {
                                        colL1.setLayoutX(cord[0]);
                                        colL2.setLayoutY(cord[1]);
 
                                        int[] coords = new int[cord.length];
                                        for (int i = 0; i < coords.length; i++)
                                                coords[i] = (int) (cord[i]);
                                        col.setText(java.util.Arrays.toString(coords));
                                }
                        }
                }.start();
        }
 
        /**
         * Creates the line in the middle
         */
        private void createPlayingField() {
                // Draw middle line
                shape.drawLine(CANVAS_WIDTH / 2 - 1, 0, CANVAS_WIDTH / 2 - 1,
                                CANVAS_HEIGHT, 2, SCORE_TEXT_COLOR); // Color.rgb(234, 234, 234)
        }
 
        /**
         * Creates the players
         */
        private void createPlayers() {
                // Create the players score (0 VS. 0)
                updatePlayerScore(0, 0);
 
                // Create the actual pads
                Rectangle p1 = shape.drawRectangle(30, CANVAS_HEIGHT / 2 - 35, 20, 70,
                                PLAYER1_COLOR);
                Rectangle p2 = shape.drawRectangle(CANVAS_WIDTH - 30 - 20,
                                CANVAS_HEIGHT / 2 - 35, 20, 70, PLAYER2_COLOR);
                // Set the keyboards
                Keyboard keysP1 = new Keyboard(KeyCode.W, KeyCode.S, KeyCode.A,
                                KeyCode.D, KeyCode.SPACE);
                Keyboard keysP2 = new Keyboard(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT,
                                KeyCode.RIGHT, KeyCode.L);
 
                // Create players
                player1 = new Player(keysP1, p1, root);
                player2 = new Player(keysP2, p2, root);
        }
 
        /**
         * Updates the players name on the top of the screen
         *
         * @param nameP1
         *            Player1's name
         * @param nameP2
         *            Player2's name
         */
        public void updatePlayerNames(String nameP1, String nameP2) {
                // Remove old names
                root.getChildren().remove(p1Name);
                root.getChildren().remove(p2Name);
                // Add new names
                p1Name = shape.drawText(CANVAS_WIDTH / 2 - 20, 0, 40,
                                Shape.TextDirection.RIGHT_TO_LEFT, nameP1, PLAYER1_COLOR);
                p2Name = shape.drawText(CANVAS_WIDTH / 2 + 20, 0, 40,
                                Shape.TextDirection.LEFT_TO_RIGHT, nameP2, PLAYER2_COLOR);
        }
 
        /**
         * Updates the players score
         */
        public void updatePlayerScore(int score1, int score2) {
                p1Score = shape.drawText(CANVAS_WIDTH / 4, CANVAS_HEIGHT / 2, 100,
                                Shape.TextDirection.CENTER_TEXT, ""+score1, SCORE_TEXT_COLOR);
                p2Score = shape.drawText(CANVAS_WIDTH * 3 / 4, CANVAS_HEIGHT / 2, 100,
                                Shape.TextDirection.CENTER_TEXT, ""+score2, SCORE_TEXT_COLOR);
        }
       
        /**
         * Get's the first player
         * @return Player 1
         */
        public Player getPlayer1() {
                return player1;
        }
       
        /**
         * Get's the second player
         * @return Player 2
         */
        public Player getPlayer2() {
                return player2;
        }
       
        /**
         * Get's the ball
         * @return The ball
         */
        public Ball getBall() {
                return ball;
        }
 
        /**
         * Makes the play field fade in
         *
         * @param primaryStage
         *            The primary stage
         */
        public void fadeIn(Stage primaryStage) {
                Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
 
                timer = new AnimationTimer() {
                        double i = 0.01;
 
                        @Override
                        public void handle(long now) {
                                if (i <= 1) {
                                        primaryStage.setOpacity(i);
                                        i += 0.01;
                                }
                        }
 
                };
                timeline.play();
                timer.start();
                if (primaryStage.getOpacity() == 1) {
                        timeline.stop();
                        timer.stop();
                }
        }
}