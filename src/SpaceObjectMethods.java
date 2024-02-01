import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface SpaceObjectMethods {
	public abstract void change_pos();
	public abstract void draw_object(Graphics2D g2D);
	public Rectangle getBounds();

}
