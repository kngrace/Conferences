package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Copies files to be saved and opened from within the project folder. 
 * 
 * @author Nikhila Potharaj
 * @version 06.06.2014
 *
 */
public class FileCopier {
	
	/**
	 * Does nothing 
	 */
	public FileCopier() {
		//Nothing
	}
	
	/**
	 * Copies from the input file to the output file so that it can be saved to the project
	 * folder. 
	 * 
	 * @param input file given by the user. 
	 * @param output file that is in the database. 
	 * @throws IOException exception if the files are invalid. 
	 */
	public void copyFileFrom(File input, File output) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(input));
        BufferedWriter write = new BufferedWriter(new FileWriter(output));
        String line = "";
        while ((line=read.readLine()) != null) {
            write.write(line);
            write.newLine();   
        }
        read.close(); 
        write.close();  
    }

	/**
	 * Copies the input file to output so that i can be saved by the user. 
	 * 
	 * @param input from the database 
	 * @param output given to the user
	 * @throws IOException if files are not found or invalid
	 */
    public void copyFileTo(File input, File output) throws IOException {
        Scanner s = new Scanner(input);
        BufferedWriter write = new BufferedWriter(new FileWriter(output));
        String line = "";
        while (s.hasNextLine()) {
            line = s.nextLine();
            write.write(line);
            write.newLine(); 
        }
        s.close(); 
        write.close(); 
    }
}

		
	    
