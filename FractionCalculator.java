// Importing the Scanner class from the java.util package.
import java.util.Scanner;

/**
 * The calculator class of the Fraction Calculator which can add, subtract,
 * multiply and divide fractions.
 */
class FractionCalculator {
    /**
     * This function takes in a string from the user, and then uses the 
     * calculate method to get the output string. If the user enters an
     * invalid string, the function will ask the user to try again.
     */
    public static void main(String[] args) {
        // Initializing a Scanner to take in input
        Scanner input = new Scanner(System.in);

        // Initializing output String
        String answer = "";

        // Try and Catch block that infinitely loops to take in input from the user until it is correct
        while(true) {
            // Tries the block of code, if an exception is raised, user is redirected to catch block
            try {
                System.out.print("Enter an expression: ");
                // Takes in user input in the form of a String
                String userString = input.nextLine();
                // Uses the calculate method to get the output string
                answer = (Fraction.calculate(userString).toString());
                // Breaks out of while loop
                break;    
            } 
            // Catch block that checks for the IllegalArgumentException
            catch (IllegalArgumentException e) {
                // ERROR MESSAGE!
                System.out.println("PLEASE TRY AGAIN!\n");
            }
        }
        // Closing input to prevent memory leak 
        input.close();

        // Output string finally outputted
        System.out.println(answer);
    }    
}