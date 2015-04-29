import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Shape extends Parent {

	private Group root;

	/**
	 * Creates a new Shape object used to draw certain shapes
	 * 
	 * @param group
	 *            The group containing all objects/players
	 */
	public Shape(Group group) {
		this.root = group;
	}

	/**
	 * Draws a line from (x1, y1) to (x2, y2)
	 * 
	 * @param x1
	 *            Starting x-coordinate
	 * @param y1
	 *            Starting y-coordinate
	 * @param x2
	 *            Ending x-coordinate
	 * @param y2
	 *            Ending y-coordinate
	 * @param color
	 *            The color of the line, white if null
	 * @return The created line
	 */
	public Line drawLine(int x1, int y1, int x2, int y2, Color color) {
		Line line = new Line(x1, y1, x2, y2);
		line.setFill((color == null) ? Color.WHITE : color);
		root.getChildren().add(line);
		return line;
	}

	/**
	 * Draws a rectangle from (x, y) to (x+width, y+height)
	 * 
	 * @param x
	 *            Starting x-coordinate (upper left corner)
	 * @param y
	 *            Starting y-coordinate (upper left corner)
	 * @param width
	 *            The width of the rectangle
	 * @param height
	 *            The height of the rectangle
	 * @param color
	 *            The color of the rectangle, white if null
	 * @return The created rectangle
	 */
	public Rectangle drawRectangle(int x, int y, int width, int height,
			Color color) {
		Rectangle rect = new Rectangle(x, y, width, height);
		rect.setFill((color == null) ? Color.WHITE : color);
		root.getChildren().add(rect);
		return rect;
	}

	/**
	 * Draws a circle at (x, y) with the radius r
	 * 
	 * @param x
	 *            The x-coordinate of the middle point
	 * @param y
	 *            The y-coordinate of the middle point
	 * @param r
	 *            The radius of the circle
	 * @param color
	 *            The color of the circle, white if null
	 * @return The created circle
	 */
	public Circle drawCircle(int x, int y, int r, Color color) {
		Circle circle = new Circle(x, y, r, (color == null) ? Color.WHITE : color);
		root.getChildren().add(circle);
		return circle;
	}
}
