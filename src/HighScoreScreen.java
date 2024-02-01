import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.*;

public class HighScoreScreen extends JPanel {

	private JLabel title;
	private JTextArea scores; 
	private ArrayList<String> lines = new ArrayList<String>();
	private ArrayList<Integer> highScores = new ArrayList<Integer>();
	private int highScore_numberToDisplay = 5;

	
	public HighScoreScreen() {
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		
		title = new JLabel();
		title.setText("HIGH SCORES");
		Font titleFont = new Font("Arial", Font.BOLD, 40);
		title.setFont(titleFont);
		title.setForeground(new Color(200,100,150));
		title.setBounds(200,0 , 400, 100);
		
		scores = new JTextArea();
		scores.setBounds(100,100,500,430);
		scores.setBackground(Color.darkGray);
		
		this.add(title);
		this.add(scores);
		
		Font highScoreFont = new Font("Arial", Font.BOLD, 30);
		scores.setFont(highScoreFont);
		scores.setForeground(Color.GREEN);
		try {
	    	FileInputStream fis = new FileInputStream("high_score.txt");
	        Scanner myReader = new Scanner(fis);
	        
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          String[] fields = data.split(",");
	          if(fields.length == 2) {
	        	  int highestScore=Integer.parseInt(fields[1]); 
	        	  highScores.add(highestScore);
	        	  lines.add(data);
	          }
	          
	        }
	        
	        Collections.sort(highScores);
	        Collections.reverse(highScores);
	   
	        for(int i=0; i<highScore_numberToDisplay;i++) {
	        	for(int j=0; j<lines.size(); j++) {
	        		String[] fields = lines.get(j).split(",");
	        		int score_in_line = Integer.parseInt(fields[1]);
	        		if(score_in_line == highScores.get(i)) {
	        			scores.append(" "+ (i+1) +". ");
	    		          scores.append(fields[0]);
	    		          scores.append("                          ");
	    		          scores.append(fields[1]);
	    		          scores.append("\n\n");
	        		}
	        	}
	        }
	        
	        myReader.close();
	      } catch (FileNotFoundException exception) {
	        System.out.println("An error occurred.");
	        exception.printStackTrace();
	      }
		
		scores.setEditable(false);
		
		
		
		
		this.setVisible(false);
		
	}

}
