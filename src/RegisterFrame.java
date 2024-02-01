import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class RegisterFrame extends JFrame implements ActionListener{
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel titleLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JButton submitButton;
	private JPanel registerPanel;
	
	
	public RegisterFrame() {
		
		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		usernameLabel = new JLabel("Username");
		usernameLabel.setForeground(Color.GREEN);
		passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.GREEN);
		titleLabel = new JLabel("Register");
		titleLabel.setForeground(Color.GREEN);
		
		Font titleFont = new Font("Arial", Font.BOLD, 30);
		Font fieldFont = new Font("Arial", Font.BOLD, 20);
		titleLabel.setFont(titleFont);
		usernameLabel.setFont(fieldFont);
		passwordLabel.setFont(fieldFont);
		submitButton = new JButton("Submit");
		submitButton.setPreferredSize(new Dimension(100,40));
		submitButton.setBackground(Color.BLUE);
		registerPanel = new JPanel();
		registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		registerPanel.setBackground(new Color(87,8,97));
		registerPanel.setBounds(50,120,300,200);
		titleLabel.setBounds(150, 40, 400, 40);
		
		registerPanel.add(usernameLabel);
		registerPanel.add(usernameField);
		registerPanel.add(passwordLabel);
		registerPanel.add(passwordField);
		registerPanel.add(submitButton);
		
		this.setLayout(null);
		this.add(titleLabel);
		this.add(registerPanel);
		this.getContentPane().setBackground(new Color(87,8,97));
		this.setVisible(true);
		this.setSize(400,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		submitButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitButton) {
			 String username = usernameField.getText(); 
			 char[] password = passwordField.getPassword();
		     String passwordString =  new String(password);		    
		     String filename = "registered_users.txt";
		     try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
		            writer.append("Username: " + username);
		            writer.newLine(); 
		            writer.append("Password: " + passwordString);
		            writer.newLine(); 
		        } catch (IOException e1) {
		            System.out.println("An error occurred while appending the content to the file.");
		            e1.printStackTrace();
		        }
		     
		     setVisible(false);
		     
		}
		
	}

}
