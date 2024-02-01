import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class MagicianAlien extends SpaceObject{
	
	static int m_alien_speed = 5;
	private int lives;
	private Image m_alien = new ImageIcon(getClass().getResource("magician_invader.png")).getImage().getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
	    
	
	public MagicianAlien(int x, int y) {
		super(x,y);
		setLives(2);
	}
	
	public void setLives(int l) {
		this.lives = l;
	}
	public int getLives() {
		return lives;
	}

	@Override
	public void change_pos() {
		int x = getX();
		int y = getY();
		
		y += m_alien_speed;
    	x += m_alien_speed;
    	setX(x);
    	setY(y);
		
	}

	@Override
	public void draw_object(Graphics2D g2d) {
		
		g2d.drawImage(m_alien,getX(),getY(),null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),80,40);	
	}

}
