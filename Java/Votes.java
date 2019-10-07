//---------------------------------------------------------------------
// Votes.java 
// This class represents the votes associated with the project 
//---------------------------------------------------------------------

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.Arrays;  

class Votes
{ 
    private static final int MAXNUMBEROFVOTES = 100;
    private static final int MINNUMBEROFVOTES = 0;
    private static Scanner scan = new Scanner(System.in);
    
// --------------------------------------------------------------------
// This method allows the user to enter votes for a particular project
// and outputs them into an array  
// --------------------------------------------------------------------

    public static void enterVotes(Project chosenProject) 
    {
        // creating a stream of team votes
        int[][] arr = chosenProject.teamVotes;
        IntStream stream = Arrays.stream(arr).flatMapToInt(Arrays::stream);
        
        // checking if votes have been entered
        if (!stream.allMatch(n-> n==0)) 
        {
            System.out.print("\nVotes have already been entered for this project, do you wish to rewrite them? (Y/N): "); 
            char answer = scan.next().toLowerCase().charAt(0);
          
            switch (answer)
            {
                case 'y':
                    break;
                case 'n':
                    return; 
            } 
        }
        System.out.println("There are " + chosenProject.numberOfMembers + " team members.");
        boolean voteAdd;
        
        // iterating over team members to allow vote input
        for (int i = 0; i < chosenProject.numberOfMembers; i++) 
        {   
            voteAdd = false;
            int numberVotedOn;
            System.out.println("\nEnter " + chosenProject.teamNames[i] +"'s votes, points must add up to a 100:"); 
            System.out.println();

            while (!voteAdd)
            {
                numberVotedOn = 0;
                for (int j = 0; j < chosenProject.numberOfMembers; j++)
                {
                    if (i != j)
                    {
                        System.out.print("\tEnter " + chosenProject.teamNames[i] + "'s votes for " + chosenProject.teamNames[j] + ": ");
                        int voteNumber = Project.validateNumber();
                        while ( voteNumber < MINNUMBEROFVOTES || voteNumber > MAXNUMBEROFVOTES)
                        {
                            System.out.print("\tPlease enter a vote above 0 and below 100: ");
                            voteNumber = scan.nextInt(); 
                        }
                        chosenProject.teamVotes[i][j] = voteNumber; // 
                        if (numberVotedOn == (chosenProject.numberOfMembers-2))
                        {
                            int sum = Arrays.stream(chosenProject.teamVotes[i]).sum();

                            if (sum > 100 || sum < 100)
                            { 
                                voteAdd = false;
                                System.out.println("\tError: please enter votes that add up to 100! ");
                            }
                            else if (sum == 100)
                            {
                                voteAdd = true;                    
                            } 
                        }
                        numberVotedOn++;  
                    }  
                }
            }
        }
    }
  
// -----------------------------------------------------------------------------------
// This is a method to calculate the grade allocation based on the votes inputted 
// for each member
// ------------------------------------------------------------------------------------

    public static void calculateVotes()
    {   
        Project chosenProject = ProjectListClass.checkProject(); 
        
        // creating a stream of team votes
        int[][] arr = chosenProject.teamVotes;
        IntStream stream = Arrays.stream(arr).flatMapToInt(Arrays::stream);
        
        // checking if votes have been entered 
        if (stream.allMatch(n-> n==0)) 
        {
            System.out.println("\tNo votes have been entered for this project yet. "); 
            return;
        }
        
        else
        {
            System.out.print("There are " + chosenProject.numberOfMembers + " team members.");
          
            // assigning a variable to each vote value in the votes array
            double voteB1 = chosenProject.teamVotes[0][1];
            double voteC1 = chosenProject.teamVotes[0][2];
            double voteA1 = chosenProject.teamVotes[1][0];
            double voteC2 = chosenProject.teamVotes[1][2];
            double voteA2 = chosenProject.teamVotes[2][0];
            double voteB2 = chosenProject.teamVotes[2][1];
            
            // calculating the points for each member
            double pointsForA = 100 / (1 + (voteC2 / voteA1) + (voteB2 / voteA2)); 
            double pointsForB = 100 / (1 + (voteA2 / voteB2) + (voteC1 / voteB1));
            double pointsForC = 100 / (1 + (voteA1 / voteC2) + (voteB1 / voteC1));

            // point allocation
            System.out.println();
            System.out.println("\nThe point allocation based on votes is: ");
            DecimalFormat noDecPlcFormatter = new DecimalFormat("#"); 
            System.out.println("\n\t" + chosenProject.teamNames[0] + ": " + noDecPlcFormatter.format(pointsForA));
            System.out.println("\n\t" + chosenProject.teamNames[1] + ": " + noDecPlcFormatter.format(pointsForB));
            System.out.println("\n\t" + chosenProject.teamNames[2] + ": " + noDecPlcFormatter.format(pointsForC));

        }
    }

}
