import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class WriteCsv {
	
	public static void main(String[] args) {
		
		String Name = "failure";
		int Score = 9;
		String filepath = "scores.txt";
	}
	
	public static void saveRecord(String Name, int Score, String filepath) {
		try {
			FileWriter fw = new FileWriter(filepath,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(Name+","+Score);
			pw.flush();
			pw.close();
			
			JOptionPane.showMessageDialog(null,"High Score Saved!");
		}
		
		catch(Exception E) {
			
		}
	}
}