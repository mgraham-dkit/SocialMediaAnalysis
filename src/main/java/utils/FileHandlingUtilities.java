package utils;

import model.PostEngagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michelle
 */
public class FileHandlingUtilities {
    // NOTE: The use of the arraylist here DOES NOT MEAN you should use it 
    // elsewhere. This is just to keep the code short and readable for you

    /**
     * Parses the contents of a supplied file of PostEngagement data into an array.
     * @param filename The name of the file to parse.
     * @return An array of PostEngagements representing the file's data.
     */
    public static PostEngagement[] readPostEngagementFile(String filename){
        ArrayList<PostEngagement> temp = new ArrayList<>();

        Scanner inputFile;
        try
        {
            inputFile = new Scanner(new FileReader(filename));
            while(inputFile.hasNextLine()){
                String line = inputFile.nextLine();
                PostEngagement pe = PostUtils.parsePostEngagement(line);
                if(pe != null) {
                    temp.add(pe);
                }else{
                    System.out.println("Line \"" + line + "\" cannot be parsed. Entry skipped.");
                }
            }
        } catch (FileNotFoundException ex){
            // This is not the way to handle this issue in proper code!! I'm just avoiding complexity here.
            Logger.getLogger(FileHandlingUtilities.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception occurred when reading from file: " + ex.getMessage());
            ex.printStackTrace();
            // We usually DO NOT WANT TO DO THIS
            System.exit(1);
        }
        PostEngagement [] engagements = new PostEngagement[temp.size()];
        return temp.toArray(engagements);
    }

    public static void main(String[] args) {
        PostEngagement[] engagements = readPostEngagementFile("datasets/engagements_2.txt");
        // Display the information from the array
        for (PostEngagement engagement : engagements) {
            System.out.println(engagement.format());
        }
    }
}
