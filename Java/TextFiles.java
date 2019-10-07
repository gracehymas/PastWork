// ---------------------------------------------------------------------------------
// TextFiles.java
// This class contains all the code for reading from and writing to the text file 
// ---------------------------------------------------------------------------------

import java.util.Scanner;
import java.io.PrintWriter;                 
import java.io.FileOutputStream;                       
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.FileInputStream;

public class TextFiles
{
    public static String File = "Projects.txt";
  
// ------------------------------------------------------------------------
// outputToFile method writes project information to text file from the 
// program
// ------------------------------------------------------------------------

    public static void outputToFile() 
    {
        PrintWriter outputStream = null; 
        
        try                
        {
            outputStream = new PrintWriter(new FileOutputStream(File));
        } 
        catch (FileNotFoundException e) 
        {        
            System.out.println("Error opening the file");
            System.exit(0);
        }
        
        outputStream.flush(); 
        outputStream.close();
        
        try                
        {
            outputStream = new PrintWriter(new FileOutputStream(File));
        } 
        catch (FileNotFoundException e) 
        {        
            System.out.println("Error opening the file");
            System.exit(0);
        }
        
        for (Project proj : Splittit.ProjectList)
        {
            outputStream.print(proj.name + "," + proj.numberOfMembers + ",");
            for (int i = 0; i < proj.numberOfMembers; i++)
            {
                outputStream.print(proj.teamNames[i] + ",");
            }
          
            for (int i = 0; i < proj.numberOfMembers; i++)
            {
                outputStream.print(proj.teamNames[i] + ",");
                for (int j = 0; j < proj.numberOfMembers; j++)
                {
                    if (i!=j)
                    {
                        if (i == 2 && j == 1)
                        {
                            outputStream.print(proj.teamNames[j] + "," + proj.teamVotes[i][j] + " ");
                        }
                        else
                        {
                            outputStream.print(proj.teamNames[j] + "," + proj.teamVotes[i][j] + ",");
                        }
                    }
                }
            }
            outputStream.println();
        }       
      
        outputStream.close();
    }
 
// ------------------------------------------------------------------------
// readFromFile method, reads the project information from the text file
// into the program
// ------------------------------------------------------------------------

    public static void readFromFile() 
    {
        ArrayList<String> readVotes = new ArrayList<>();
        Scanner inputStream = null;
        
        try
        {
            inputStream = new Scanner(new FileInputStream(File));
        }
        catch (FileNotFoundException e)
        {
            Splittit.changingPages();
        }
        
        try 
        {
            String line;
            while (inputStream.hasNextLine()) 
            {   
                line = inputStream.nextLine();
                if (line.length() > 0)
                { 
                    String[] lineArray = line.split(",");
                    int result = Integer.parseInt(lineArray[1]);
                    String[] readName = new String[3];
                    readName[0] = lineArray[2];
                    readName[1] = lineArray[3];
                    readName[2] = lineArray[4];
              
                    for (int i = 8; i < 20; i += 2)
                    {
                        readVotes.add(lineArray[i]); 
                       
                    }
                    
                    Project p = new Project(lineArray[0], result, readName);
                    Splittit.ProjectList.add(p);
                }
            }
            inputStream.close();
        }
        catch (NullPointerException e)
        {
            Splittit.changingPages();
        }
    }
}
    
  