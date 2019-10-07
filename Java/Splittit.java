// Splittit.java
// Author: Grace Hymas and Aneesa Ali
// Adapated from feedforward by Rae Harbird 
// Program last changed: 5th April 2018

/* This program is a fair grade allocator which splits marks
   fairly. It allows a user to select an option from a main menu 
   and input details so this can be done                      */     

//-------------------------------------------------------------------------
// Importing java libraries 
// ------------------------------------------------------------------------
   
import java.util.Scanner;
import java.util.ArrayList;

//-------------------------------------------------------------------------
// Main method class, Splittit 
// ------------------------------------------------------------------------

public class Splittit 
{
    public static ArrayList<Project> ProjectList = new ArrayList<>();
    
    public static void main(String[] args)                                   
    {   
        TextFiles.readFromFile();
        changingPages();
    }

//-------------------------------------------------------------------------
// This is the method for 'changingPages' 
// ------------------------------------------------------------------------

    public static void changingPages()
    {
        char option;
        option = '*';

        while ('q' != option)
        {
            option = menuDisplay();
            switch (option)
            {
                case 'a':
                    about();
                    break;
                case 'c':
                    Project p = createProject();
                    System.out.println(p.toString());
                    break;
                case 'v':
                    p = ProjectListClass.checkProject();
                    Votes.enterVotes(p);
                    break;
                case 's':
                    showProject();
                    break;
                case 'q': 
                    TextFiles.outputToFile(); 
                    break;
                case 'd':
                    ProjectListClass.removeProject();
                    break;
                default:
                    System.out.println("Unknown option, please try again");
                    
                    // Rae Harbird: You don't need to call changingPages() again.
                    // changingPages();
                    break;
            }         
        }
        System.out.println("\nGoodbye!");
        System.exit(0);
    }

//----------------------------------------------------------------------
//  Prints a display menu and allows option input to change pages
//----------------------------------------------------------------------
 
    private static char menuDisplay() 
    {
        
        Scanner scan = new Scanner(System.in);
       
        System.out.println("\n\nWelcome to Split-it:");
        System.out.println();
        System.out.println("\tAbout (A)");
        System.out.println("\tCreate Project (C)");
        System.out.println("\tEnter Votes (V)");
        System.out.println("\tShow Project (S)");
        System.out.println("\tDelete Project (D)");
        System.out.println("\tQuit (Q)");
        
        System.out.print("\nPlease choose an option: ");
  
        return (scan.next().toLowerCase().charAt(0));  
    }
  
// ------------------------------------------------------------------
// This method displays the about page  
// ------------------------------------------------------------------

    private static void about() 
    {
        System.out.println();
        System.out.println("\t\tABOUT SPLIT-IT:");
        System.out.println();
        System.out.println("Split-it is a fair grade allocator. This app aims to help\nteams allocate the credit for projects fairly - so that\nall parties are satisfied with the outcome. This app ensures\nyou get the fairest grade possible - for all of your projects.\nAll you have to do is create your project, enter the number\nof team members, their names and votes per person and\nthe rest is done through the app.");
    }
  
// ------------------------------------------------------------------
// This method is for creating a project 
// ------------------------------------------------------------------

    private static Project createProject() 
    {
        String projectName = getProjectName();
        int numberOfparticipants = getNumberOfParticipants();
        String[] teamNames = getTeamNames(numberOfparticipants);
      
        Project p = new Project(projectName, numberOfparticipants, teamNames);
        ProjectList.add(p);
        return p;
    }

// ------------------------------------------------------------------
// This method allows a user to input a project name and the program
// to validate this input
// ------------------------------------------------------------------

    private static String getProjectName()
    {
        boolean valid1 = false;
        boolean valid2 = false;
        String projectName;
        Scanner scan = new Scanner(System.in);
        
        System.out.print("\nEnter the project name: ");
        projectName = scan.next();
        
        while (!valid2 && !valid1)
        {
            while (!Project.validateName(projectName))
            {
                System.out.println("\nProject name must contain alphanumeric characters only, try again.");
                System.out.print("\nEnter the project name: ");               
                projectName = scan.next();                
            }   
            valid1 = true;
            
            for (Project proj : Splittit.ProjectList)
            {
                while (projectName.equals(proj.getName()))
                {           
                    System.out.println("\nA project with this name already exists, please try again.");
                    System.out.print("\nEnter the project name: ");
                    projectName = scan.next();
                }
                valid2 = true;
            }
        }
        return projectName;
    }
  
// ------------------------------------------------------------------
// This method allows a user to input a number of team members, program
// will check if this is a valid number
// ------------------------------------------------------------------
    
    private static int getNumberOfParticipants() 
    {
        boolean valid = false;
        int numberOfMembers = 0;
        System.out.print("\nEnter the number of team members: ");
      
        while (!valid)
        {
            numberOfMembers = Project.validateNumber();
            if (Project.validateNumberOfMembers(numberOfMembers))
            {
                valid = true;
            }
            else 
            {
                System.out.print("\nProjects can have " + 
                Project.MINMEMBERS + " members, try again: ");
            }
        }
        return numberOfMembers;
    }

// ------------------------------------------------------------------
// This method allows a user to input team member names and checks if 
// these are valid 
// ------------------------------------------------------------------
  
    private static String[] getTeamNames(int numberOfparticipants) 
    {
        Scanner scan = new Scanner(System.in);
        boolean valid;
       
        String[] names = new String[Project.MAXMEMBERS];
       
        for (int i = 0; i < numberOfparticipants; i++)
        {
            valid = false;          
            while (!valid)
            {
                System.out.print("\tEnter the name of team member " + (i+1) + ": ");
                String name = scan.next();
                if (Project.validateName(name))
                {
                    valid = true;
                    names[i] = name;  
                }
                else
                {
                    System.out.println("\nA name must contain only alphanumeric characters and be between " + 
                                         Project.MINNAMELENGTH + " and " + Project.MAXNAMELENGTH + 
                                       " characters, try again.");
                }
            }  
        }
        return names;
    }

//-------------------------------------------------------------------
// Show Project allocation points for a particular project 
// ------------------------------------------------------------------

    private static void showProject()
    {
        Votes.calculateVotes();   
    }
}
  
