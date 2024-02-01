import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.*;


public class Bullet extends SpaceObject{
	
    private static int bullet_speed = 30;
    
    
    private Image bullet = new ImageIcon(getClass().getResource("spaceinvaders_bullet.png")).getImage().getScaledInstance(20, 30, java.awt.Image.SCALE_SMOOTH);
    
    
    
    public Bullet(int x, int y) {
    	super(x,y);
    	
    }

	@Override
	public void change_pos() {
		int y = getY();
		y -= bullet_speed;
		setY(y);
	}

	@Override
	public void draw_object(Graphics2D g2d) {
		g2d.drawImage(bullet,getX(),getY(),null);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),20,2);
	}
    

}
