import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Group;
public class PlayerName extends Parent{
	private Group root;
	
	public PlayerName(Group grp)
	{
		this.root = grp;
	}
	
	public Text pName(int x, int y, String theName)
	{
		Text name = new Text(x, y, theName);
		name.setFont(new Font(100));
		name.setFill(Color.WHITE);
		
		root.getChildren().add(name);
		return name;
	}
}
