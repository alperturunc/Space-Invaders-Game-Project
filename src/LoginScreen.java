import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

public class LoginScreen extends JPanel implements ActionListener {
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel titleLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JButton loginButton;
	private JPanel loginPanel;
	private String currentUsername;
	
	public LoginScreen() {
		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		usernameLabel = new JLabel("Username");
		usernameLabel.setForeground(Color.GREEN);
		passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.GREEN);
		titleLabel = new JLabel("Login");
		titleLabel.setForeground(Color.GREEN);
		
		Font titleFont = new Font("Arial", Font.BOLD, 50);
		Font fieldFont = new Font("Arial", Font.BOLD, 30);
		titleLabel.setFont(titleFont);
		usernameLabel.setFont(fieldFont);
		passwordLabel.setFont(fieldFont);
		loginButton = new JButton("Start Game");
		loginButton.setPreferredSize(new Dimension(100,40));
		loginButton.setBackground(Color.BLUE);
		loginPanel = new JPanel();
		
		loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		loginPanel.setBackground(new Color(100,90,97));
		loginPanel.setBounds(200,200,300,200);
		titleLabel.setBounds(280, 50, 400, 60);
		
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
		loginPanel.add(loginButton);
		
		this.setLayout(null);
		this.add(titleLabel);
		this.add(loginPanel);
		this.setBounds(0,0,700,600);
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		
		loginButton.addActionListener(this);
		
		
	}
	
	public String getCurrentUsername() {
		return currentUsername;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== loginButton) {
			
			String username = usernameField.getText(); 
			currentUsername = username;
			char[] password = passwordField.getPassword();
		    String passwordString =  new String(password);
			
		    try {
		    	FileInputStream fis = new FileInputStream("registered_users.txt");
		        Scanner myReader = new Scanner(fis);
		        boolean is_user_exist = false;
		        while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		          if(data.equals("Username: " + username)) {
		        	  if(myReader.nextLine().equals("Password: " + passwordString)){
		        		  is_user_exist = true;
		        		  
		        	  }
		          }
		          
		        }
		        if(is_user_exist) {
		        	this.setVisible(false);
		        	
		        }
		        else {
		        	JOptionPane.showMessageDialog(null,"User not found. Try again");
		        	System.out.println("Not Exists");
		        }
		        myReader.close();
		      } catch (FileNotFoundException exception) {
		        System.out.println("An error occurred.");
		        exception.printStackTrace();
		      }
			
			
		}
		
	}


}
