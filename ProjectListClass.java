//-------------------------------------------------------------------
// ProjectList.java
// This class contains methods that are actions that relate to the 
// list of Projects. 
// This includes:
// * to find a project in the list
// * to add a project to the list
// * to delete a project from the list
// ------------------------------------------------------------------  
import java.util.Scanner;

class ProjectListClass 
{   
    private static Scanner scan = new Scanner(System.in);
  
// --------------------------------------------------------------------------
// CheckProject finds a project in the list, checks the option entered 
// is a previously created project and outputs the name and the number 
// of team members
//--------------------------------------------------------------------------

    public static Project checkProject()
    {
        // allowing user input of project details (picking a project)
        //Scanner scan = new Scanner(System.in);
        
        System.out.println();
        System.out.print("Enter the project name: ");
        String projectOption;
        Project chosenProject = null;
        boolean choice = false;
      
        // reading through projects array to check if the user entered project has previously been created
        while (!choice)
        { 
            projectOption = scan.next();
            System.out.println("");

            while (!Project.validateName(projectOption))
            {   
                System.out.print("\nProject name must contain alphanumeric characters only, try again: ");
                projectOption = scan.next();
            }
 
            for (Project proj : Splittit.ProjectList)
            {
                if (proj.getName().equals(projectOption))
                {           
                    chosenProject = proj;
                    choice = true;
                }
            }
            if (!choice)
            {
                System.out.print("\tProject not recognised. Please enter a project already created: " );
            }
        }
        return chosenProject;
    }  

//-------------------------------------------------------------------------------------------
// This is a method to allow the user to delete a project from the project list
// ------------------------------------------------------------------------------------------

    public static void removeProject()
    {

        String projectOption;
     
        System.out.println();
        System.out.print("Enter the project you wish to delete: ");
        projectOption = scan.next();
        int originalSize = Splittit.ProjectList.size();
          
        for (int i = Splittit.ProjectList.size() - 1; i >= 0; i--) 
        {
            Project p = Splittit.ProjectList.get(i);
            if (projectOption.equals(p.getName())) 
            {
                Splittit.ProjectList.remove(i);
                System.out.println("\n\tThis project has been removed.");
            }
        }
        if (Splittit.ProjectList.size() == originalSize) {
            System.out.println("\n\tThis project does not exist.");
        }
    }
}