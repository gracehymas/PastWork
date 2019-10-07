//--------------------------------------------------------------------
//  Project.java
//  This is the project class, that contains the project information
//  Creates and prints new project. 
//--------------------------------------------------------------------

import java.util.InputMismatchException;
import java.util.Scanner;

class Project 
{   
    private static Scanner scan = new Scanner(System.in);
  
// ------------------------------------------------------------------------
// fixed variables and other variables 
// ------------------------------------------------------------------------  
    public static final int MAXMEMBERS = 3;
    public static final int MINMEMBERS = 3;
    public static final int MAXNAMELENGTH = 30;
    public static final int MINNAMELENGTH = 1;
  
    public String name;
    public int numberOfMembers;
    public String[] teamNames = new String[MAXMEMBERS];
    public int[][] teamVotes;
    
//-----------------------------------------------------------------------------------
// Creating a project with a specified name, number of participants and team members
// @param theName The project name.
// @param theNumberOfParticipants The number of people in a team
// @param theTeam The names of team members.
//-----------------------------------------------------------------------------------
  
    
    Project (String theName, int theNumberOfParticipants, String[] theTeam)
    {
        if (validateName(theName))
        {
            name = theName;
        }
        else
        {
            fatalError("Constructor passed malformed project name.");
        }
        
        if (validateNumberOfMembers(theNumberOfParticipants) )
        {
            numberOfMembers = theNumberOfParticipants;
            this.teamVotes = new int[numberOfMembers][numberOfMembers];
        }       
        else
        {
            fatalError("Constructor passed invalid number of participants.");
        }
        
        if (validateTheTeam(theTeam, theNumberOfParticipants))
        {
            teamNames = theTeam;  
        }
        else
        {
            fatalError("Constructor passed malformed team names.");
        }
    }
  
//-----------------------------------------------------------------------
// Error generated when constructor is passed invalid parameter.
// The program will ask user to re-enter a value or terminate 
//-----------------------------------------------------------------------

    private static void fatalError(String errorMessage)
    {
        System.out.println("Fatal error: " + errorMessage);
        // A non-zero status indicates an abnormal termination.
        Splittit.changingPages();
    }
    
//--------------------------------------------------------------------------
// Create a String to represent a Project object.
// @Override
//--------------------------------------------------------------------------  

    public String toString()
    {
        return ("\nProject name: " + name + ", has " + numberOfMembers + " team members named: " + getTeamNames());
    }

    // return the name of the project 
    public String getName()   
    {
        return name;
    }

    // return the names of the team members 
    private String getTeamNames()
    {
        String theNames = "";
        
        for (int i = 0; i < numberOfMembers; i++)
        {
            theNames += teamNames[i];
            if (i < (numberOfMembers - 1))
            {
                theNames += ", ";
            }
        }
        return theNames;
    } 
   
//-----------------------------------------------------------------------------
// Validate a name by checking whether the name is empty and 
// whether the name is composed of alphanumeric characters.
// @param theName The name might represent a project or team member.
// @return A boolean value indicating whether the name was valid or not.
//----------------------------------------------------------------------------

    public static boolean validateName(String theName)
    {
        boolean valid = true;
        if (theName.isEmpty())
        {
            valid = false;
        }
        for (int i = 0; i < theName.length(); i++)          
        {
            if (!Character.isLetterOrDigit(theName.charAt(i)))
            {
                valid = false;  
            }
        } 
        return valid;
    }

//---------------------------------------------------------------------------------------
// Check whether the number of team members is between MINMEMBERS and MAXMEMBERS.
// @param theNumberOfMembers
//----------------------------------------------------------------------------------------
    
    static boolean validateNumberOfMembers(int theNumberOfMembers) 
    {
        return (theNumberOfMembers >= MINMEMBERS && theNumberOfMembers <= MAXMEMBERS);
    }
  
//---------------------------------------------------------------------------------------
// Check whether the number of team members is a number
//----------------------------------------------------------------------------------------

    public static int validateNumber()
    {
        boolean isNumber = false; 
        int numberOfMembers = 0;
        while (!isNumber)
        {
            try 
            {
                numberOfMembers = scan.nextInt();
                System.out.println();
                isNumber = true;
            }
            catch (InputMismatchException e)
            {
                scan.next();
                System.out.println();
                System.out.print("Error! Please enter a number: ");
            }
        }
        return(numberOfMembers);
    }
  
// ----------------------------------------------------------------------------------------
// Validate the names of team members.
// For each name in the array, up to the number of participants:
// Check whether the name is empty. 
// Check whether the name is composed of alphanumeric characters.
// @param theTeam An array containing the names of team members.
// @param theNumberOfParticipants The number of team members.
// @return A boolean value indicating whether the names are valid or not.
//-----------------------------------------------------------------------------------------

    private boolean validateTheTeam(String[] theTeam, int theNumberOfParticipants) 
    {
        String theName;       
        boolean valid = true;
        if (valid)
        {
            for (int i = 0; i < theNumberOfParticipants; i++)
            {
                theName = theTeam[i];
                valid = validateName(theName);
            }
        }  
        return valid;
    }
}
