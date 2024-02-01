import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.*;

public class PurpleAlien extends SpaceObject{
	
	static int p_alien_speed = 5;
	private int lives;
	private Image p_alien = new ImageIcon(getClass().getResource("purple_invader.png")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
	    
	
	
	public PurpleAlien(int x, int y) {
		super(x,y);
		setLives(1);
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
		
		y += p_alien_speed;
    	x -= p_alien_speed;
    	setX(x);
    	setY(y);
		
	}
	@Override
	public void draw_object(Graphics2D g2d) {
		g2d.drawImage(p_alien,getX(),getY(),null);
		
	}


	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(),getY(),50,40);
	}

}
