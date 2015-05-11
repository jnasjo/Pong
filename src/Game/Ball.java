package Game;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
 
public class Ball {
 
        private Circle ball;
        private AnimationTimer animator;
 
        private final int CANVAS_HEIGHT;
        private final int CANVAS_WIDTH;
 
        private double velX, velY;
 
        private final static double START_Y_DIFF = 1.5;
        private final static double START_X_VEL = 5;
 
        private static int player1Score = 0;
        private static int player2Score = 0;
 
        private Player[] players;
 
        private Random rand;
 
        // DEV THINGS, REMOVE L8ER
        private double[] collision = new double[2];
 
        private Group root;
 
        private Text p1Score;
        private Text p2Score;
        private SoundEffect effect;
 
        /**
         * Creates a new ball and re-locates the ball to the center of the screen
         *
         * @param ball
         *            The Circle object connected to this ball
         */
        public Ball(Circle ball, Group root, Text p1Score, Text p2Score,
                        Player... player) {
                this.root = root;
                this.ball = ball;
                this.players = player;
                for (Player p : players)
                        p.setBall(this);
                CANVAS_HEIGHT = Game.CANVAS_HEIGHT;
                CANVAS_WIDTH = Game.CANVAS_WIDTH;
                rand = new Random();
                effect = new SoundEffect("h4.wav", "h5.wav", "h6.wav");
//              effect = new SoundEffect("hit1.mp3", "hit2.mp3", "hit3.mp3",
//                              "hit4.mp3", "hit5.mp3");
 
                reset();
 
                animator = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                                animate(now);
                        }
                };
                start();
                this.p1Score = p1Score;
                this.p2Score = p2Score;
        }
 
        // DEV, REMOVE L8ER
        public double[] colCoords() {
                return collision;
        }
 
        /**
         * Animates the ball and does various checks such as collision
         *
         * @param now
         *            The current time in nanoseconds
         */
        private void animate(long now) {
                moveBall();
        }
 
        /**
         * Chaotic circle-circle collision checking the balls will "sink" and fling
         * each other away
         *
         * @param circle
         */
        private void chaoticCircleCollision(Circle circle) {
                double r = circle.getRadius();
                double myR = getRadius();
                double dx = circle.getLayoutX() - getX();
                double dy = circle.getLayoutY() - getY();
 
                if (Math.sqrt(dx * dx + dy * dy) <= r + myR) {
                        double k = -1 / (dy / dx);
                        double y = Math.sqrt(k * k / (k * k + 1));
                        double x = y / k;
                        y = Math.abs(y);
                        x = Math.abs(x);
                        if (Double.isNaN(x) || Double.isNaN(y))
                                return;
                        if (getX() > circle.getLayoutX()) {
                                if (getY() > circle.getLayoutY()) {
                                        velX += x;
                                        velY += y;
                                } else {
                                        velX += x;
                                        velY += -y;
                                }
                        } else {
                                if (getY() > circle.getLayoutY()) {
                                        velX += -x;
                                        velY += y;
                                } else {
                                        velX += -x;
                                        velY += -y;
                                }
                        }
                }
        }
 
        /**
         * Moves the ball and does collision checking as well as taking appropriate
         * action
         *
         * @return false if the ball went of the edge
         */
        private boolean moveBall() {
 
                // Ball-Ball collision
                for (Node n : root.getChildrenUnmodifiable()) {
                        if (n instanceof Circle && !n.equals(ball)) {
                                chaoticCircleCollision((Circle) n);
                        }
                }
 
                // Where we are and where we're going
                Point start = new Point(getX(), getY());
                Point end = new Point(getX() + velX, getY() + velY);
 
                boolean hitPlayer = false;
                // Collision with player
                for (Player p : players) {
 
                        Intersection intersect = checkCollision(start, end, p);
                        if (intersect != null && !intersect.hasNaN()) {
                                double c = (velX * velX + velY * velY)
                                                / (intersect.nx * intersect.nx + intersect.ny
                                                                * intersect.ny);
                                c = Math.sqrt(c);
                                velX = intersect.nx * c;
                                velY = intersect.ny * c;
                                hitPlayer = true;
 
                                // Project Future Position
                                double remainingTime = 1.0f - intersect.time;
                                double dx = end.x - start.x;
                                double dy = end.y - start.y;
                                double dot = dx * intersect.nx + dy * intersect.ny;
                                double ndx = dx - 2 * dot * intersect.nx;
                                double ndy = dy - 2 * dot * intersect.ny;
                                double newX = intersect.cx + ndx * remainingTime;
                                double newY = intersect.cy + ndy * remainingTime;
                                setPos(newX, newY);
 
                                effect.playRandom();
 
                                collision = new double[] { intersect.ix, intersect.iy };
                                break;
                        }
                }
 
                if (hitPlayer)
                        return false;
 
                // Move ball
                setPos(end.x, end.y);
 
                // Collision with walls
 
                if (getX() - getRadius() < 0) {
                        setX(getRadius());
                        velX *= -1;
 
                        // PLAYER 1 LOST
                        player2Score++;
                        p2Score.setText("" + player2Score);
                        p2Score.setLayoutX(CANVAS_WIDTH * 3 / 4
                                        - p2Score.getBoundsInLocal().getWidth() / 2);
                        // reset();
 
                        effect.playRandom();
                }
                if (getY() - getRadius() < 0) {
                        setY(getRadius());
                        velY *= -1;
 
                        effect.playRandom();
                }
                if (getX() + getRadius() > CANVAS_WIDTH) {
                        setX(CANVAS_WIDTH - getRadius());
                        velX *= -1;
 
                        // PLAYER 2 LOST
                        player1Score++;
                        p1Score.setText("" + player1Score);
                        p1Score.setLayoutX(CANVAS_WIDTH / 4
                                        - p1Score.getBoundsInLocal().getWidth() / 2);
                        // reset();
 
                        effect.playRandom();
                }
                if (getY() + getRadius() > CANVAS_HEIGHT) {
                        setY(CANVAS_HEIGHT - getRadius());
                        velY *= -1;
 
                        effect.playRandom();
                }
 
                return false;
        }
 
        /**
         * Calculates if the ball will collide with a player whilst moving from
         * point start to point end, it also calculates where this collision will
         * happen, and the circle's new position
         *
         * @param start
         *            The circles starting position before collision
         * @param end
         *            The circles final position
         * @param p
         *            The player to do collision checking on
         * @return null if no collision. Otherwise it will return the collision
         *         point (the circles position when the collision happend), the time
         *         from start to finish where the collision occurred, and the
         *         circles new position.
         */
        public Intersection checkCollision(Point start, Point end, Player p) {
                Bounds b = p.getRect().getBoundsInParent();
                final double L = b.getMinX();
                final double T = b.getMinY();
                final double R = b.getMaxX();
                final double B = b.getMaxY();
                final double radius = getRadius();
 
                // If the bounding box around the start and end points (+radius on all
                // sides) does not intersect with the rectangle, definitely not an
                // intersection
                if ((Math.max(start.x, end.x) + radius < L)
                                || (Math.min(start.x, end.x) - radius > R)
                                || (Math.max(start.y, end.y) + radius < T)
                                || (Math.min(start.y, end.y) - radius > B)) {
                        return null;
                }
 
                final double dx = end.x - start.x;
                final double dy = end.y - start.y;
                final double invdx = (dx == 0.0f ? 0.0f : 1.0f / dx);
                final double invdy = (dy == 0.0f ? 0.0f : 1.0f / dy);
                double cornerX = Double.MAX_VALUE;
                double cornerY = Double.MAX_VALUE;
 
                // Calculate intersection times with each side's plane
                // Check each side's quadrant for single-side intersection
                // Calculate Corner
 
                /** Left Side **/
                // Does the circle go from the left side to the right side of the
                // rectangle's left?
                if (start.x - radius < L && end.x + radius > L) {
                        double ltime = ((L - radius) - start.x) * invdx;
                        if (ltime >= 0.0f && ltime <= 1.0f) {
                                double ly = dy * ltime + start.y;
                                // Does the collisions point lie on the left side?
                                if (ly >= T && ly <= B) {
                                        return new Intersection(dx * ltime + start.x, ly, ltime,
                                                        -1, 0, L, ly);
                                }
                        }
                        cornerX = L;
                }
 
                /** Right Side **/
                // Does the circle go from the right side to the left side of the
                // rectangle's right?
                if (start.x + radius > R && end.x - radius < R) {
                        double rtime = (start.x - (R + radius)) * -invdx;
                        if (rtime >= 0.0f && rtime <= 1.0f) {
                                double ry = dy * rtime + start.y;
                                // Does the collisions point lie on the right side?
                                if (ry >= T && ry <= B) {
                                        return new Intersection(dx * rtime + start.x, ry, rtime, 1,
                                                        0, R, ry);
                                }
                        }
                        cornerX = R;
                }
 
                /** Top Side **/
                // Does the circle go from the top side to the bottom side of the
                // rectangle's top?
                if (start.y - radius < T && end.y + radius > T) {
                        double ttime = ((T - radius) - start.y) * invdy;
                        if (ttime >= 0.0f && ttime <= 1.0f) {
                                double tx = dx * ttime + start.x;
                                // Does the collisions point lie on the top side?
                                if (tx >= L && tx <= R) {
                                        return new Intersection(tx, dy * ttime + start.y, ttime, 0,
                                                        -1, tx, T);
                                }
                        }
                        cornerY = T;
                }
 
                /** Bottom Side **/
                // Does the circle go from the bottom side to the top side of the
                // rectangle's bottom?
                if (start.y + radius > B && end.y - radius < B) {
                        double btime = (start.y - (B + radius)) * -invdy;
                        if (btime >= 0.0f && btime <= 1.0f) {
                                double bx = dx * btime + start.x;
                                // Does the collisions point lie on the bottom side?
                                if (bx >= L && bx <= R) {
                                        return new Intersection(bx, dy * btime + start.y, btime, 0,
                                                        1, bx, B);
                                }
                        }
                        cornerY = B;
                }
                // No intersection at all!
                if (cornerX == Double.MAX_VALUE && cornerY == Double.MAX_VALUE)
                        return null;
 
                Intersection res[] = new Intersection[3];
 
                if (cornerX == L) {
                        if (cornerY == T) { // TOP LEFT CORNER
                                res[0] = checkCollisionWithCorners(L, T, dx, dy, L, R, T, B,
                                                start, radius);
                                res[1] = checkCollisionWithCorners(L, B, dx, dy, L, R, T, B,
                                                start, radius);
                                res[2] = checkCollisionWithCorners(R, T, dx, dy, L, R, T, B,
                                                start, radius);
                        } else { // BOTTOM LEFT CORNER
                                res[0] = checkCollisionWithCorners(L, B, dx, dy, L, R, T, B,
                                                start, radius);
                                res[1] = checkCollisionWithCorners(L, T, dx, dy, L, R, T, B,
                                                start, radius);
                                res[2] = checkCollisionWithCorners(R, B, dx, dy, L, R, T, B,
                                                start, radius);
                        }
                } else {
                        if (cornerY == T) { // TOP RIGHT CORNER
                                res[0] = checkCollisionWithCorners(R, T, dx, dy, L, R, T, B,
                                                start, radius);
                                res[1] = checkCollisionWithCorners(R, B, dx, dy, L, R, T, B,
                                                start, radius);
                                res[2] = checkCollisionWithCorners(L, T, dx, dy, L, R, T, B,
                                                start, radius);
                        } else { // BOTTOM RIGHT CORNER
                                res[0] = checkCollisionWithCorners(R, B, dx, dy, L, R, T, B,
                                                start, radius);
                                res[1] = checkCollisionWithCorners(R, T, dx, dy, L, R, T, B,
                                                start, radius);
                                res[2] = checkCollisionWithCorners(L, B, dx, dy, L, R, T, B,
                                                start, radius);
                        }
                }
 
                for (int i = 0; i < 3; i++)
                        if (res[i] == null && res[(i + 1) % 3] == null)
                                return res[(i + 2) % 3];
 
                for (int i = 0; i < 3; i++) {
                        if (res[i] == null) {
                                double cc = getDistance(res[(i + 1) % 3], res[(i + 2) % 3].ix,
                                                res[(i + 2) % 3].iy);
                                if (cc < radius)
                                        return res[(i + 2) % 3];
                                return res[(i + 1) % 3];
                        }
                }
 
                double c = radius;
                int closest = 0;
                double c2 = getDistance(res[0], res[1].ix, res[1].iy);
                if (c2 < c) {
                        c = c2;
                        closest = 1;
                }
                double c3 = getDistance(res[closest], res[2].ix, res[2].iy);
                if (c3 < c)
                        return res[2];
                return res[closest];
        }
 
        /**
         * Calculates the distance from the intersection res to the point (x, y)
         *
         * @param res
         *            The intersection to calculate distance to
         * @param x
         *            The x-coordinate of the point
         * @param y
         *            The y-coordinate of the point
         * @return The distance from (x, y) to the intersect
         */
        private double getDistance(Intersection res, double x, double y) {
                double dx = res.cx - x;
                double dy = res.cy - y;
                double c = (double) Math.sqrt(dx * dx + dy * dy);
                return c;
        }
 
        /**
         * Helping-hand-function for checkCollision to avoid code-repeating
         */
        private Intersection checkCollisionWithCorners(double cornerX,
                        double cornerY, double dx, double dy, double L, double R, double T,
                        double B, Point start, double radius) {
                double inverseRadius = 1.0 / radius;
                double lineLength = Math.sqrt(dx * dx + dy * dy);
                double cornerdx = cornerX - start.x;
                double cornerdy = cornerY - start.y;
                double cornerDistance = Math.sqrt(cornerdx * cornerdx + cornerdy
                                * cornerdy);
                double innerAngle = Math.acos((cornerdx * dx + cornerdy * dy)
                                / (lineLength * cornerDistance));
 
                // If the circle is too close, no intersection.
                if (cornerDistance < radius) {
                        return null;
                }
 
                // If inner angle is zero, it's going to hit the corner straight on.
                if (innerAngle == 0.0f) {
                        double time = (double) ((cornerDistance - radius) / lineLength);
 
                        // If time is outside the boundaries, return null. This algorithm
                        // can
                        // return a negative time which indicates a previous intersection,
                        // and
                        // can also return a time > 1.0f which can predict a corner
                        // intersection.
                        if (time > 1.0f || time < 0.0f) {
                                return null;
                        }
 
                        double ix = time * dx + start.x;
                        double iy = time * dy + start.y;
                        double nx = (double) (cornerdx / cornerDistance);
                        double ny = (double) (cornerdy / cornerDistance);
 
                        return new Intersection(ix, iy, time, nx, ny, cornerX, cornerY);
                }
 
                double innerAngleSin = Math.sin(innerAngle);
                double angle1Sin = innerAngleSin * cornerDistance * inverseRadius;
 
                // The angle is too large, there cannot be an intersection
                if (Math.abs(angle1Sin) > 1.0f)
                        return null;
 
                double angle1 = Math.PI - Math.asin(angle1Sin);
                double angle2 = Math.PI - innerAngle - angle1;
                double intersectionDistance = radius * Math.sin(angle2) / innerAngleSin;
 
                // Solve for time
                double time = (double) (intersectionDistance / lineLength);
 
                // If time is outside the boundaries, return null. This algorithm can
                // return a negative time which indicates a previous intersection, and
                // can also return a time > 1.0f which can predict a corner
                // intersection.
                if (time > 1.0f || time < 0.0f) {
                        return null;
                }
 
                // Solve the intersection and normal
                double ix = time * dx + start.x;
                double iy = time * dy + start.y;
                double nx = (double) ((ix - cornerX) * inverseRadius);
                double ny = (double) ((iy - cornerY) * inverseRadius);
 
                return new Intersection(ix, iy, time, nx, ny, cornerX, cornerY);
        }
 
        /**
         * @return The bounds in parent for this ball
         */
        public Bounds getBounds() {
                return ball.getBoundsInParent();
        }
 
        /**
         * @return Player 1's score
         */
        public int getPlayer1Score() {
                return player1Score;
        }
 
        /**
         * @return Player 2's score
         */
        public int getPlayer2Score() {
                return player2Score;
        }
 
        /**
         * Stops the ball
         */
        public void stop() {
                animator.stop();
        }
 
        /**
         * Starts/continues the ball
         */
        public void start() {
                animator.start();
        }
 
        /**
         * Moves the ball to the middle of the canvas
         */
        public void reset() {
                setPos(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
//              velX = rand.nextBoolean() ? -START_X_VEL : START_X_VEL;
//              velY = rand.nextDouble() * (rand.nextBoolean() ? -1 : 1) * START_Y_DIFF;
                velX = START_X_VEL;
                velY = START_Y_DIFF;
                // velX = 0.7075466219424853;
                // velY = 0.7066666666666667;
        }
 
        /**
         * Set's the new x-coordinate of the ball
         *
         * @param x
         *            The ball's new x-coordinate
         */
        private void setX(double x) {
                setPos(x, getY());
        }
 
        /**
         * Set's the new y-coordinate of the ball
         *
         * @param y
         *            The ball's new y-coordinate
         */
        private void setY(double y) {
                setPos(getX(), y);
        }
 
        /**
         * Set's the ball's postition to the provided values
         *
         * @param x
         *            The ball's new x-coordinate
         * @param y
         *            The ball's new y-coordinate
         */
        public void setPos(double x, double y) {
                ball.setLayoutX(x);
                ball.setLayoutY(y);
        }
 
        /**
         * @return The balls radius
         */
        private double getRadius() {
                return ball.getRadius();
        }
 
        /**
         * @return The current x-coordinate of the ball's layout
         */
        public double getX() {
                return ball.getLayoutX();
        }
 
        /**
         * @return The current y-coordinate of the ball's layout
         */
        public double getY() {
                return ball.getLayoutY();
        }
}