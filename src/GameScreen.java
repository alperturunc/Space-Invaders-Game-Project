import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameScreen extends JPanel implements KeyListener,ActionListener,Runnable,MouseMotionListener,MouseListener{
	
	private JLabel backgroundLabel;
	private ImageIcon backgroundImage;
	private JLabel spship;
	private Timer timer;
	private int random;
	private Random rand; 
	private boolean is_running = true;
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private ArrayList<InvaderBullet> invaderBulletList = new ArrayList<InvaderBullet>();
	private ArrayList<SpaceObjectMethods> spaceObjectList = new ArrayList<SpaceObjectMethods>();
	private Thread gameThread;
	private int level = 1;
	private int progress = 0;
	private int lives = 3;
	private int score = 0;
	private JLabel levelLabel;
	private JLabel progressLabel;
	private JLabel livesLabel;
	private JLabel scoreLabel;
	private ImageIcon livesIcon;
	private JLabel gameOverLabel;
	private JLabel gameEndLabel;
	private String username;
	private Clip bulletSound;
	private Clip spshipDeathSound;
	private Clip coinSound;
	private Clip invaderDeathSound;
	
	
	public GameScreen(String username) {
		
		this.username = username;
		
		this.setLayout(null);
		backgroundImage = new ImageIcon(getClass().getResource("space_invaders_bg_700x600.jpeg"));
		backgroundLabel = new JLabel();
		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setBounds(0,0,700,600);
		
		timer = new Timer(1500,this);
		timer.start();
		
		
		ImageIcon spaceshipImage = new ImageIcon(getClass().getResource("spaceship.png"));
		spship = new JLabel();
		spship.setIcon(spaceshipImage);
		spship.setBounds(250, 450, 160, 80);
		
		this.add(spship);
		this.setFocusable(true);
		this.addKeyListener(this);
		
		Font informationFont = new Font("Arial", Font.BOLD, 20);
		levelLabel = new JLabel();
		progressLabel = new JLabel();
		livesLabel = new JLabel();
		scoreLabel = new JLabel();
		levelLabel.setText("Level "+Integer.toString(level));
		levelLabel.setFont(informationFont);
		levelLabel.setBounds(20,10,90,60);
		levelLabel.setForeground(Color.RED);
		livesLabel.setText("3");
		livesLabel.setFont(informationFont);
		livesLabel.setBounds(110,10,70,60);
		livesLabel.setForeground(Color.RED);
		progressLabel.setText("%0");
		progressLabel.setFont(informationFont);
		progressLabel.setBounds(210,10,50,60);
		progressLabel.setForeground(Color.RED);
		scoreLabel.setText("0");
		scoreLabel.setFont(informationFont);
		scoreLabel.setBounds(340,10,100,60);
		scoreLabel.setForeground(Color.RED);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("png-transparent-red-heart-illustration-pixel-art-minecraft-minecraft-love-heart-video-game-removebg-preview.png"));
		int width1 = 50; 
		int height1 = 30; 
		Image img = ((ImageIcon) icon).getImage().getScaledInstance(width1, height1, java.awt.Image.SCALE_SMOOTH);
		livesIcon = new ImageIcon(img);
		livesLabel.setIcon(livesIcon);
		
		Font gameOverFont = new Font("Arial", Font.BOLD, 70);
		gameOverLabel = new JLabel("GAME OVER");
		gameOverLabel.setFont(gameOverFont);
		gameOverLabel.setForeground(Color.RED);
		gameOverLabel.setBounds(125, 150, 450, 200);
		gameOverLabel.setVisible(false);
		
		gameEndLabel = new JLabel();
		gameEndLabel.setText("YOU WON");
		gameEndLabel.setFont(gameOverFont);
		gameEndLabel.setForeground(Color.YELLOW);
		gameEndLabel.setBounds(125, 150, 450, 200);
		gameEndLabel.setVisible(false);
		
		
		
		
		this.add(levelLabel);
		this.add(livesLabel);
		this.add(progressLabel);
		this.add(scoreLabel);
		this.add(gameOverLabel);
		this.add(gameEndLabel);
		this.add(backgroundLabel);
		
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		
		gameThread = new Thread(this);
        gameThread.start();
        
        
        
        
        
	}
	
	public void appendingHighScore(String username) {
		try {
	    	FileInputStream fis = new FileInputStream("high_score.txt");
	        Scanner myReader = new Scanner(fis);
	        boolean is_user_in = false;
	        String old_line = null;
	        String new_line = null;
	        StringBuffer buffer = new StringBuffer();
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          buffer.append(data+System.lineSeparator());
	          if(data.contains(username)) {
	        	  is_user_in = true;
	        	  String[] fields = data.split(",");
	        	  if(fields.length == 2) {
	        		  String user_highestScore_data = fields[1];
	        		  int highestScore=Integer.parseInt(user_highestScore_data); 
		        	  if(highestScore < score) {
		        		  old_line = data;
		        		  String s = Integer.toString(score);
		        		  new_line = username + "," + s; 
		        	  }
	        	  }
	        	  
	          }
	          
	        }
	        if(is_user_in) {
	        	String fileContents = buffer.toString();
	        	if(old_line  != null && new_line != null) {
	        		fileContents = fileContents.replaceAll(old_line, new_line);
		        	String filename = "high_score.txt";
		        	try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
			     		writer.append(fileContents);
			     		writer.flush();
			        } catch (IOException e1) {
			            System.out.println("An error occurred while appending the content to the file.");
			            e1.printStackTrace();
			        }
	        	}
	        	
	        }
	        
	        myReader.close();
	        if(!is_user_in) {
	        	String filename = "high_score.txt";
	        	String s=Integer.toString(score);
	        	try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
		     		writer.append(username + ',' + s);
		     		writer.newLine();
		        } catch (IOException e1) {
		            System.out.println("An error occurred while appending the content to the file.");
		            e1.printStackTrace();
		        }
	        }
	      } catch (FileNotFoundException exception) {
	        System.out.println("An error occurred.");
	        exception.printStackTrace();
	      }
	}
	
	
	
	public void createInvader() {
		rand = new Random();
		int which_alien;
		if(level == Levels.Novice.getLevelNumber()) {  // invader creation in level 1 
			which_alien = rand.nextInt(2)+1;
			if(which_alien == 1) {
				random = rand.nextInt(600-100)+100; 
				PurpleAlien purpleAlien = new PurpleAlien(random,0);
				spaceObjectList.add(purpleAlien);	
			}
			else {
				random = rand.nextInt(600-100)+100; 
				LaughingAlien laughingAlien = new LaughingAlien(random,0);
				spaceObjectList.add(laughingAlien);	
			}
		}
		else {
			which_alien = rand.nextInt(3)+1;
			if(which_alien == 1) {
				random = rand.nextInt(600-100)+100;
				PurpleAlien purpleAlien = new PurpleAlien(random,0);
				spaceObjectList.add(purpleAlien);	
			}
			else if(which_alien == 2) {
				random = rand.nextInt(600-100)+100;
				MagicianAlien magicianAlien = new MagicianAlien(random,0);
				spaceObjectList.add(magicianAlien);
			}
			else {
				random = rand.nextInt(600-100)+100;
				LaughingAlien laughingAlien = new LaughingAlien(random,0);
				spaceObjectList.add(laughingAlien);	
			}
			
		}
		
	}
	
	private void checkCollisions() {
		// checks if there is a collision between bullet and invaders
        for (int i=0; i<spaceObjectList.size();i++) { 
        	if(spaceObjectList.get(i) instanceof LaughingAlien) {
        		LaughingAlien l_alien = (LaughingAlien) spaceObjectList.get(i);
        		Rectangle alienBounds = l_alien.getBounds();
        		for (Bullet bullet : bulletList) {
                    Rectangle bulletBounds = bullet.getBounds();
                    if (alienBounds.intersects(bulletBounds)) {
                    	if(l_alien.getLives()==1) {
                    		score += 20;
                    		scoreLabel.setText(Integer.toString(score));
                    		progress += 10;
                    		progressLabel.setText("%"+Integer.toString(progress));
                    		spaceObjectList.remove(spaceObjectList.get(i));
                            bulletList.remove(bullet);
                            try {
                				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-space-coin-win-notification-271.wav"));
                				coinSound = AudioSystem.getClip();
                				coinSound.open(audioStream);
                				coinSound.start();
                			}catch(Exception ex) {
                				ex.printStackTrace();
                			}
                            try {
                				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("invaderkilled 3.wav"));
                				invaderDeathSound = AudioSystem.getClip();
                				invaderDeathSound.open(audioStream);
                				invaderDeathSound.start();
                			}catch(Exception ex) {
                				ex.printStackTrace();
                			}
                		
                            break;
                    	}
                    	else {
                    		score += 20;
                    		scoreLabel.setText(Integer.toString(score));
                    		int lives_left = l_alien.getLives();
                    		lives_left-=1;
                    		l_alien.setLives(lives_left);
                    		bulletList.remove(bullet);
                    		try {
                				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-space-coin-win-notification-271.wav"));
                				coinSound = AudioSystem.getClip();
                				coinSound.open(audioStream);
                				coinSound.start();
                			}catch(Exception ex) {
                				ex.printStackTrace();
                			}
                            break;
                    	}
                    }
            
        		}
            
        	}
        	else if(spaceObjectList.get(i) instanceof PurpleAlien) {
        		PurpleAlien p_alien = (PurpleAlien) spaceObjectList.get(i);
        		Rectangle alienBounds = p_alien.getBounds();
        		for (Bullet bullet : bulletList) {
                    Rectangle bulletBounds = bullet.getBounds();
                    if (alienBounds.intersects(bulletBounds)) {
                    	if(p_alien.getLives()==1) {
                    		score += 10;
                    		scoreLabel.setText(Integer.toString(score));
                    		progress += 5;
                    		progressLabel.setText("%"+Integer.toString(progress));
                    		spaceObjectList.remove(spaceObjectList.get(i));
                            bulletList.remove(bullet);
                            try {
                				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-space-coin-win-notification-271.wav"));
                				coinSound = AudioSystem.getClip();
                				coinSound.open(audioStream);
                				coinSound.start();
                			}catch(Exception ex) {
                				ex.printStackTrace();
                			}
                            break;
                    	}
                    	else {
                    		score += 10;
                    		scoreLabel.setText(Integer.toString(score));
                    		int lives_left = p_alien.getLives();
                    		lives_left-=1;
                    		p_alien.setLives(lives_left);
                    		bulletList.remove(bullet);
                    		try {
                				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-space-coin-win-notification-271.wav"));
                				coinSound = AudioSystem.getClip();
                				coinSound.open(audioStream);
                				coinSound.start();
                			}catch(Exception ex) {
                				ex.printStackTrace();
                			}
                            break;
                    	}
                   }
        		}
        	}
        	else if(spaceObjectList.get(i) instanceof MagicianAlien) {
        		MagicianAlien m_alien = (MagicianAlien) spaceObjectList.get(i);
        		Rectangle alienBounds = m_alien.getBounds();
        		for (Bullet bullet : bulletList) {
                    Rectangle bulletBounds = bullet.getBounds();
                    if (alienBounds.intersects(bulletBounds)) {
                    	if(m_alien.getLives()==1) {
                    		score += 40;
                    		scoreLabel.setText(Integer.toString(score));
                    		progress += 10;
                    		progressLabel.setText("%"+Integer.toString(progress));
                    		spaceObjectList.remove(spaceObjectList.get(i));
                            bulletList.remove(bullet);
                            try {
                				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-space-coin-win-notification-271.wav"));
                				coinSound = AudioSystem.getClip();
                				coinSound.open(audioStream);
                				coinSound.start();
                			}catch(Exception ex) {
                				ex.printStackTrace();
                			}
                            try {
                				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("invaderkilled 3.wav"));
                				invaderDeathSound = AudioSystem.getClip();
                				invaderDeathSound.open(audioStream);
                				invaderDeathSound.start();
                			}catch(Exception ex) {
                				ex.printStackTrace();
                			}
                            break;
                    	}
                    	else {
                    		score += 40;
                    		scoreLabel.setText(Integer.toString(score));
                    		int lives_left = m_alien.getLives();
                    		lives_left-=1;
                    		m_alien.setLives(lives_left);
                    		bulletList.remove(bullet);
                    		try {
                				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-space-coin-win-notification-271.wav"));
                				coinSound = AudioSystem.getClip();
                				coinSound.open(audioStream);
                				coinSound.start();
                			}catch(Exception ex) {
                				ex.printStackTrace();
                			}
                            break;
                    	}
                   }
        		}
        	}
        	// checking if there is a collision between the spaceship and invaders
        	Rectangle spshipBounds = new Rectangle(spship.getX(),spship.getY(),120,80);
        	
        	for(SpaceObjectMethods aobject : spaceObjectList) {
        		if(aobject instanceof PurpleAlien) {
        			PurpleAlien p_alien = (PurpleAlien) aobject;
            		Rectangle alienBounds = p_alien.getBounds();
            		if (alienBounds.getBounds().intersects(spshipBounds)) {
            			
            			if(lives > 1) {
            				lives -= 1;
            				livesLabel.setText(" "+Integer.toString(lives));
            			}
            			else if (lives == 1){
            				lives -= 1;
            				livesLabel.setText(" "+Integer.toString(lives));
            				is_running = false;
            				spship.setVisible(false);
            				if(is_running == false) {
            		        	gameOverLabel.setVisible(true);
            		        	appendingHighScore(username);
            		        	try {
                					AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("explosion.wav"));
                					spshipDeathSound = AudioSystem.getClip();
                					spshipDeathSound.open(audioStream);
                					spshipDeathSound.start();
                					bulletSound.close();
                				}catch(Exception ex) {
                					ex.printStackTrace();
                				}
            		        }
            				
            			}
            			
            			
            		}
        		}
        		else if(aobject instanceof LaughingAlien) {
        			LaughingAlien l_alien = (LaughingAlien) aobject;
            		Rectangle alienBounds = l_alien.getBounds();
            		if (alienBounds.getBounds().intersects(spshipBounds)) {
            			lives = 0;
        				livesLabel.setText(" "+Integer.toString(lives));
        				is_running = false;
        				spship.setVisible(false);
        				if(is_running == false) {
        		        	gameOverLabel.setVisible(true);
        		        	appendingHighScore(username);
        		        	try {
            					AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("explosion.wav"));
            					spshipDeathSound = AudioSystem.getClip();
            					spshipDeathSound.open(audioStream);
            					spshipDeathSound.start();
            					bulletSound.close();
            				}catch(Exception ex) {
            					ex.printStackTrace();
            				}
        				}
            		}
        		}
        		else if(aobject instanceof MagicianAlien) {
            		MagicianAlien m_alien = (MagicianAlien) aobject;
            		Rectangle alienBounds = m_alien.getBounds();
            		if (alienBounds.getBounds().intersects(spshipBounds)) {
            			if(lives > 1) {
            				lives -= 1;
            				livesLabel.setText(" "+Integer.toString(lives));
            			}
            			else if (lives == 1){
            				lives -= 1;
            				livesLabel.setText(" "+Integer.toString(lives));
            				is_running = false;
            				spship.setVisible(false);
            				if(is_running == false) {
            		        	gameOverLabel.setVisible(true);
            		        	appendingHighScore(username);
            		        	try {
                					AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("explosion.wav"));
                					spshipDeathSound = AudioSystem.getClip();
                					spshipDeathSound.open(audioStream);
                					spshipDeathSound.start();
                					bulletSound.close();
                				}catch(Exception ex) {
                					ex.printStackTrace();
                				}
            		        }
            			}
            			
            		}
        		}  			
        	}
        	
        	for(int j=0; j<invaderBulletList.size();j++) {
            	Rectangle invaderBulletBounds = invaderBulletList.get(j).getBounds();
            	if (invaderBulletBounds.intersects(spshipBounds)) {
            		if(lives > 1) {
            			lives -= 1;
            			livesLabel.setText(Integer.toString(lives));
            			invaderBulletList.remove(j);
            			break;
            		}
            		else if (lives == 1){
            			lives -= 1;
            			livesLabel.setText(Integer.toString(lives));
            			invaderBulletList.remove(j);
            			is_running = false;
            			spship.setVisible(false);
            			if(is_running == false) {
            	        	gameOverLabel.setVisible(true);
            	        	try {
            					AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("explosion.wav"));
            					spshipDeathSound = AudioSystem.getClip();
            					spshipDeathSound.open(audioStream);
            					spshipDeathSound.start();
            					bulletSound.close();
            				}catch(Exception ex) {
            					ex.printStackTrace();
            				}

            	        	appendingHighScore(username);
            	        }
            	        
            			break;
            		}
            			
            	}
            		
            }
        	
        	
        	
        }
	}
    
	public void createInvaderBullet(int w) {
			random = rand.nextInt(w-1)+1;
			if(random == 2 || random == 3 || random ==4) {
				random = rand.nextInt(spaceObjectList.size()-0) + 0 ;
				if(spaceObjectList.get(random) instanceof LaughingAlien) {
					LaughingAlien l_alien = (LaughingAlien) spaceObjectList.get(random);
					InvaderBullet invaderBullet = new InvaderBullet(l_alien.getX(),l_alien.getY()-2);
					invaderBulletList.add(invaderBullet);
					
					try {
						AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-sci-fi-laser-in-space-sound-2825.wav"));
						bulletSound = AudioSystem.getClip();
						bulletSound.open(audioStream);
						bulletSound.start();
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					
				
				}
				else if(spaceObjectList.get(random) instanceof PurpleAlien) {
					PurpleAlien p_alien = (PurpleAlien) spaceObjectList.get(random);
					InvaderBullet invaderBullet = new InvaderBullet(p_alien.getX(),p_alien.getY()-2);
					invaderBulletList.add(invaderBullet);
					
					try {
						AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-sci-fi-laser-in-space-sound-2825.wav"));
						bulletSound = AudioSystem.getClip();
						bulletSound.open(audioStream);
						bulletSound.start();
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					
				
				}
				else if(spaceObjectList.get(random) instanceof MagicianAlien) {
					MagicianAlien m_alien = (MagicianAlien) spaceObjectList.get(random);
					InvaderBullet invaderBullet = new InvaderBullet(m_alien.getX(),m_alien.getY()-2);
					invaderBulletList.add(invaderBullet);
					
					try {
						AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("mixkit-sci-fi-laser-in-space-sound-2825.wav"));
						bulletSound = AudioSystem.getClip();
						bulletSound.open(audioStream);
						bulletSound.start();
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					
				
				}
			
			}
			
	}
    

	
	public void paint(Graphics g) {    // painting the spaceObjects
		
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		
		for(int i=0; i<bulletList.size();i++) {
			bulletList.get(i).draw_object(g2D);
		}
		for(int i=0; i<invaderBulletList.size();i++) {
			invaderBulletList.get(i).draw_object(g2D);
		}
		for(int i=0; i<spaceObjectList.size();i++) {
			if(spaceObjectList.get(i) instanceof PurpleAlien) {
				PurpleAlien p_alien = (PurpleAlien) spaceObjectList.get(i);
				p_alien.draw_object(g2D);
				
			}
			if(spaceObjectList.get(i) instanceof LaughingAlien) {
				LaughingAlien l_alien = (LaughingAlien) spaceObjectList.get(i);
				l_alien.draw_object(g2D);
			}
			if(spaceObjectList.get(i) instanceof MagicianAlien) {
				MagicianAlien m_alien = (MagicianAlien) spaceObjectList.get(i);
				m_alien.draw_object(g2D);
			}
		}
		
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {  
		
	}
	
	

	@Override
	public void keyPressed(KeyEvent e) {    // movement of spaceship and bullet creation
		switch(e.getKeyCode()) {
		
		case 37: 
			if(spship.getX() > -60) {
				spship.setLocation(spship.getX()-20,spship.getY());
			}
			break;
		case 38: 
			if(spship.getY() > 0 ) {
				spship.setLocation(spship.getX(),spship.getY()-20);
			}
			break;
		case 40: 
			if(spship.getY() < 480 ) {
				spship.setLocation(spship.getX(),spship.getY()+20);
			}
			break;
		case 39: 
			if(spship.getX() < 550) {
				spship.setLocation(spship.getX()+20,spship.getY());
			}
			break;
		case 32:
			if(is_running) {
				Bullet bullet = new Bullet(spship.getX()+92,spship.getY()+5);
				bulletList.add(bullet);
				try {
					AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("shoot.wav"));
					bulletSound = AudioSystem.getClip();
					bulletSound.open(audioStream);
					bulletSound.start();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
			
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("You released key char "+ e.getKeyChar());
		//System.out.println("You released key char "+ e.getKeyCode());
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(is_running) {
			if(level == Levels.Novice.getLevelNumber()) {
				createInvader();
				createInvaderBullet(7);
			}
			else if (level == Levels.Intermediate.getLevelNumber()){
				createInvader();
				createInvader();
				createInvader();
				createInvaderBullet(5);
			}
			else if (level == Levels.Advance.getLevelNumber()) {
				createInvader();
				createInvader();
				createInvader();
				createInvader();
				createInvader();
				createInvader();
				createInvader();
				createInvader();
				createInvaderBullet(4);
				createInvaderBullet(4);
			}
		}
		
	}

	@Override
	public void run() {
		while(is_running) {
				
				if(progress >= 100) {
					if(level == 3) {
						is_running = false;
						gameEndLabel.setVisible(true);
						appendingHighScore(username);
						bulletSound.close();
					}
					else {
						level += 1;
						progress = 0;
						progressLabel.setText("%0");
					}
					
					PurpleAlien.p_alien_speed += 3;
					LaughingAlien.l_alien_speed += 3;
					MagicianAlien.m_alien_speed += 3;
					levelLabel.setText("Level "+Integer.toString(level));
		 		}
				
			
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int i=0; i<spaceObjectList.size();i++) {
					if(spaceObjectList.get(i) instanceof PurpleAlien) {
						PurpleAlien p_alien = (PurpleAlien) spaceObjectList.get(i);
						p_alien.change_pos();
						if(p_alien.getX() > 600 || p_alien.getX()< 0 || p_alien.getY() > 480) {
							spaceObjectList.remove(spaceObjectList.get(i));
							continue;
						}
						
					}
					if(spaceObjectList.get(i) instanceof LaughingAlien) {
						LaughingAlien l_alien = (LaughingAlien) spaceObjectList.get(i);
						l_alien.change_pos();
						if(l_alien.getX() > 600 || l_alien.getX()< 0 || l_alien.getY() > 480) {  // old values 700, 600
							spaceObjectList.remove(spaceObjectList.get(i));
							continue;
						}
					}
					if(spaceObjectList.get(i) instanceof MagicianAlien) {
						MagicianAlien m_alien = (MagicianAlien) spaceObjectList.get(i);
						m_alien.change_pos();
						if(m_alien.getX() > 600 || m_alien.getX()< 0 || m_alien.getY() > 480) {
							spaceObjectList.remove(spaceObjectList.get(i));
							continue;
						}
						
					}
				}
				
				for(int i=0; i<bulletList.size();i++) {
					bulletList.get(i).change_pos();
					if(bulletList.get(i).getY() < 0) {
						bulletList.remove(bulletList.get(i));
						
					}
				}
				for(int i=0; i<invaderBulletList.size();i++) {
					invaderBulletList.get(i).change_pos();
					if(invaderBulletList.get(i).getY() > 600) {
						invaderBulletList.remove(invaderBulletList.get(i));
						
					}
				}
				checkCollisions();
				
				repaint();
			
			
				
			}
		}

	@Override
	public void mouseDragged(MouseEvent e) {
			
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int spshipX = spship.getX();
		spshipX = e.getX()-80;
		int spshipY = spship.getY()+10;
		spshipY = e.getY();
		spship.setLocation(spshipX,spshipY);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(is_running) {
			Bullet bullet = new Bullet(spship.getX()+92,spship.getY()+5);
			bulletList.add(bullet);
			try {
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource("shoot.wav"));
				bulletSound = AudioSystem.getClip();
				bulletSound.open(audioStream);
				bulletSound.start();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
			
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
