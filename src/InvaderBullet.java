import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class InvaderBullet extends SpaceObject{
	
	static int invader_bullet_speed = 30;
	
	private Image invader_bullet = new ImageIcon(getClass().getResource("invader_bullet.png")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
	
	public InvaderBullet(int x, int y) {
		super(x,y);
	}

	@Override
	public void change_pos() {
		int y = getY();
		y += invader_bullet_speed;
		setY(y);
		
	}

	@Override
	public void draw_object(Graphics2D g2d) {
		g2d.drawImage(invader_bullet,getX(),getY(),null);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),20,2);
	}

}
