import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class LaughingAlien extends SpaceObject{
	static int l_alien_speed = 5;
	private int lives;
	
	private Image l_alien = new ImageIcon(getClass().getResource("laughing_invader.png")).getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
	
	
	public LaughingAlien(int x, int y) {
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
		
		y += l_alien_speed;
    	x -= l_alien_speed;
    	setX(x);
    	setY(y);
	}

	@Override
	public void draw_object(Graphics2D g2d) {
		g2d.drawImage(l_alien,getX(),getY(),null);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),80,50);	
	}
}
