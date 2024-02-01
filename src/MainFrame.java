import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;


public class MainFrame extends JFrame implements ActionListener,MouseListener{
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuItem registerItem;
	private JMenuItem playGameItem;
	private JMenuItem highScoreItem;
	private JMenuItem quitItem;
	private JMenuItem aboutItem;
	private boolean is_loggedIn = false;
	private LoginScreen loginScreen;
	private GameScreen gameScreen; 
	private HighScoreScreen highScoreScreen;
	private RegisterFrame registerFrame;
	private String username;
	private JLabel welcomePic;
	private ImageIcon startpage;
	
	private Clip mainSound;
	
	
	public MainFrame(){
		
		this.getContentPane().setBackground(new Color(0,0,0));
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		
		registerItem = new JMenuItem("Register");
		playGameItem = new JMenuItem("Play Game");
		highScoreItem = new JMenuItem("High Score");
		quitItem = new JMenuItem("Quit");
		aboutItem = new JMenuItem("About");
		
		fileMenu.add(registerItem);
		fileMenu.add(playGameItem);
		fileMenu.add(highScoreItem);
		fileMenu.add(quitItem);
		helpMenu.add(aboutItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		
		welcomePic = new JLabel();
		startpage = new ImageIcon(getClass().getResource("spaceInvaders_first_screen.jpeg"));
		welcomePic.setIcon(startpage);
		welcomePic.setBounds(0,0,700,600);
		welcomePic.addMouseListener(this);
		welcomePic.setHorizontalAlignment(JLabel.CENTER);
		add(welcomePic);
		
		
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("spaceinvaders1.wav"));
			mainSound = AudioSystem.getClip();
			mainSound.open(audioInputStream);
			mainSound.start();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		registerItem.addActionListener(this);
		playGameItem.addActionListener(this);
		highScoreItem.addActionListener(this);
		quitItem.addActionListener(this);
		aboutItem.addActionListener(this);
		
		this.setJMenuBar(menuBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Space Invaders");
		this.setSize(700,600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == registerItem) {
			registerFrame = new RegisterFrame();
			
		}
		
		else if(e.getSource() == playGameItem) {
			if(highScoreScreen != null) {
				highScoreScreen.setVisible(false);  
			}
			if(loginScreen != null) {
				loginScreen.setVisible(false);
				this.remove(loginScreen);
			}
			welcomePic.setVisible(false);
			loginScreen = new LoginScreen();
			loginScreen.setVisible(true);
			loginScreen.setFocusable(true);
			loginScreen.addMouseListener(this);
			loginScreen.requestFocusInWindow();
			getContentPane().add(loginScreen);	
			
			if(loginScreen.isVisible()) {
				is_loggedIn = true;
				welcomePic.setVisible(true);
				welcomePic.addMouseListener(this);
				welcomePic.setHorizontalAlignment(JLabel.CENTER);
				welcomePic.setVisible(true);
				welcomePic.setFocusable(true);
			    welcomePic.requestFocusInWindow();			    
			    getContentPane().add(welcomePic);		  
				
			}
		}
		if(e.getSource()== highScoreItem) {
			if(loginScreen != null) {
				loginScreen.setVisible(false);
			}
			if(highScoreScreen != null) {
				highScoreScreen.setVisible(false);
				this.remove(highScoreScreen);
			}
			welcomePic.setVisible(false);
			highScoreScreen = new HighScoreScreen();
			highScoreScreen.setVisible(true);
			highScoreScreen.setFocusable(true);
			highScoreScreen.addMouseListener(this);
			highScoreScreen.requestFocusInWindow();
			getContentPane().add(highScoreScreen);
			
		}
		
		if(e.getSource() == quitItem) {
			System.exit(0);
		}
		if(e.getSource()== aboutItem) {
			JOptionPane.showMessageDialog(null,""
					+ "ABOUT DEVELOPER\n"
					+ "Name: Alper \n"
					+ "Surname: Turunç\n"
					+ "Number: 20200702018\n"
					+ "E-mail: alperturunc682@gmail.com","About",JOptionPane.PLAIN_MESSAGE);
		}
		
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource() == loginScreen) {
			
	    }	
		
		else if(e.getSource() == welcomePic ) {
			if(loginScreen != null && is_loggedIn) {			
			welcomePic.setVisible(false);	
			username = loginScreen.getCurrentUsername();
			gameScreen = new GameScreen(username);
			gameScreen.setBounds(0, 0, 700,600);
			setContentPane(gameScreen);
			gameScreen.setFocusable(true);
	    	gameScreen.requestFocusInWindow();
	    	gameScreen.setVisible(true);
	    	mainSound.close();
			}
			
			else {
				JOptionPane.showMessageDialog(null,"Not logged in. Please log in first!");
			}
		
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {
			
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
