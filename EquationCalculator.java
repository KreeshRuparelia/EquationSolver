// Importing the java.util module so Scanner and ArrayLists can be used
import java.util.*;

class EquationCalculator {
    // COLOR VALUE CONSTANTS
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * The function returns an array of strings that are all the possible monomial terms for 
     * a linear polynomial equation
     * 
     * @return The possible terms that can be used in the equation.
     */
    private static String[] possibleTermsArray() {
        // Creating an array of all possible monomial instances
        String possibleTerms[] = {
            "-?[1-9][0-9]*x/-?[1-9][0-9]*",
            "-?[1-9][0-9]*\\(x (\\+|-) -?[1-9][0-9]*\\)/-?[1-9][0-9]*",
            "-?[1-9][0-9]*/-?x",
            "\\(x (\\+|-) -?[1-9][0-9]*\\)/-?[1-9][0-9]*",
            "-?x",
            "-?x/-?[1-9][0-9]*",
            "-?[1-9][0-9]*",
            "-?[1-9][0-9]*/-?[1-9][0-9]*",
            "-?[1-9][0-9]*x",
            "-?x-?[1-9][0-9]",
            "-?x-?[1-9][0-9]*/-?[1-9][0-9]*",
            "-?[1-9][0-9]*/-?[1-9][0-9]*x",
            "-?[1-9][0-9]*\\(x (\\+|-) -?[1-9][0-9]*\\)",
            "-?[1-9][0-9]*\\(x (\\+|-) -?[1-9][0-9]*/-?[1-9][0-9]*\\)",
            "-?[1-9][0-9]*\\(x (\\+|-) -?[1-9][0-9]*/-?[1-9][0-9]*\\)/-?[1-9][0-9]*",
            "-?[1-9][0-9]*/-?[1-9][0-9]*\\(x (\\+|-) -?[1-9][0-9]*)",
            "-?[1-9][0-9]*/-?[1-9][0-9]*\\(x (\\+|-) -?[1-9][0-9]*/-?[1-9][0-9]*\\)",
            "-?[1-9][0-9]*/-?[1-9][0-9]*\\(x (\\+|-) -?[1-9][0-9]*/-?[1-9][0-9]*\\)/-?[1-9][0-9]*",
            "\\(x (\\+|-) -?[1-9][0-9]*/-?[1-9][0-9]*\\)/-?[1-9][0-9]*",
            "-?[1-9][0-9]\\(x (\\+|-) -?[1-9][0-9]*/-?[1-9][0-9]*\\)/-?[1-9][0-9]*",
            "\\(-?[1-9][0-9]*x (\\+|-) -?[1-9][0-9]*/-?[1-9][0-9]*\\)/-?[1-9][0-9]*",
            "\\(-?[1-9][0-9]*/-?[1-9][0-9]*x (\\+|-) -?[1-9][0-9]*/-?[1-9][0-9]*\\)/-?[1-9][0-9]*",
        };

        // Returning Array
        return possibleTerms;
    }

    /**
     * This helper function updates the termsMatched variable for the inputValidator when there is a bracket in 
     * the equation
     * 
     * @param leftSide The left side of the equation, before the equals sign.
     * @param rightSide The right side of the equation, after the equals sign.
     * @param possibleTerms an array of strings that are possible terms that could be in the equation
     * @return The number of terms that match the possible terms.
     */
    private static int bracketScenario(String leftSide, String rightSide, String[] possibleTerms) {
        // Initalizing variable
        int termsMatched = 0;

        // Checking for the side that contains the unknown and counting how many terms match

        // Block for when x is on the left side
        if (leftSide.contains("x")){
            for (int i = 0; i < possibleTerms.length; i++) {
                if (leftSide.matches(possibleTerms[i])) {
                    termsMatched++;
                    break;
                }
            }
        }

        // Block for when x is on the right side
        else {
            for (int i = 0; i < possibleTerms.length; i++) {
                if (rightSide.matches(possibleTerms[i])) {
                    termsMatched++;
                    break;
                }
            }
        }

        // Returning the number of terms that matched
        return termsMatched;
    }

    /**
     * This helper function updates the termsMatched variable for the inputValidator when there is no bracket in 
     * the equation
     * 
     * @param userInput The user's input
     * @param possibleTerms An array of strings that are the possible terms that the user can enter.
     * @return An array consisting the overall amount of terms, and the amount of terms that matched
     */
    private static int[] noBracketsScenario(String userInput, String[] possibleTerms) {
        // Initializing variables
        int termsAccumulator = 0;
        int termsMatched = 0;
        
        // Splitting the string at every space and validating every single block
        for (String term : (userInput.replace("=", "+")).split(" ")) {
            // Updating accumulator
            termsAccumulator++;

            // If the accumulator is odd, it means that the current loop term should be a monomial, hence why,
            // we use the possibleTerms Array to verify if the term is valid
            if (termsAccumulator % 2 != 0) {
                // Looping through valid terms
                for (int i = 0; i < possibleTerms.length; i++) {
                    if (term.matches(possibleTerms[i])) {
                        // Updating accumulator as current term is valid
                        termsMatched++;
                        break;
                    }
                }
            }
            // Since accumulator is even, current loop term should be an operator, hence why we check if it is an
            // operator or not
            else if (term.matches("\\+") || term.matches("-")) {
                termsMatched++;
            }
            // Throwing exception if none of the terms match
            else {
                throw new IllegalArgumentException();
            }
        }
        // Initializing array that will be returned
        int returnArray[] = {termsAccumulator, termsMatched};

        // Returning Arrays
        return returnArray;
    }

    /**
     * This helper method validates if the user's input is valid, and can be made into an Equation object later.
     * 
     * @param userInput
     * @throws IllegalArgumentException
     */
    private static void inputValidator(String userInput) {
        // Initializing variables
        Boolean containsNumber = false;
        Boolean containsBrackets = false;
        int termsAccumulator = 0;
        int termsMatched = 0;

        // Checks if there is anything other than a 'x' unknown, numbers, and brackets in the input, and throwing an exception if there is
        for (int i = 0; i < userInput.length(); i++) {
            if (!(userInput.substring(i, i + 1).equals("x")) || !(userInput.substring(i, i + 1).equals("(")) || !(userInput.substring(i, i + 1).equals(")"))) {
                if (userInput.substring(i, i + 1).matches("[a-zA-Z]") && !(userInput.substring(i, i + 1).equals("x"))) {
                    throw new IllegalArgumentException();
                }
                else if (userInput.substring(i, i + 1).matches("[0-9]")) {
                    containsNumber = true;
                }
            } 
        }

        // An equation must contain "=", " ", "x" and numbers, and this if statement verifies that the condition is true
        if (userInput.contains("=") && userInput.contains(" ") && userInput.contains("x") && containsNumber == true) {
            // Assigning leftSide and rightSide strings using indexing
            String leftSide = userInput.substring(0, userInput.indexOf("=") - 1);
            String rightSide = userInput.substring(userInput.indexOf("=") + 2);
            
            // Throwing an exception if x is on both sides
            if (leftSide.contains("x") && rightSide.contains("x")) {
                throw new IllegalArgumentException();
            }

            // Getting the array of possible terms
            String possibleTerms[] = possibleTermsArray();

            // Block for when there is a bracket in the equation
            if (userInput.contains("(")) {
                containsBrackets = true;
                termsMatched = bracketScenario(leftSide, rightSide, possibleTerms);
            }

            // Block for when there is no bracket in the equation
            else {
                // Updating termsAccumulator and termsMatched values as assigned in the 'noBracketsScenario()' method
                int returnArray[] = noBracketsScenario(userInput, possibleTerms);
                termsAccumulator = returnArray[0];
                termsMatched = returnArray[1];
            }
        }
        // Throwing an exception
        else {
            throw new IllegalArgumentException();
        }

        // If it contains brackets, there is only acceptable term, hence why only one term should match. If that isn't the case
        // an exception is thrown
        if (containsBrackets == true) {
            if (termsMatched != 1) {
                throw new IllegalArgumentException();
            }
        }
        // Throwing an exception if total terms is not equal to correct terms
        else if (termsAccumulator != termsMatched) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * The function takes a string as an argument, and then parses the string into 
     * a linear equation object, which is then solved and the answer is printed to
     * the console
     * 
     * @param equationString The string that the user inputs
     */
    public static Fraction EquationSolver(String equationString) {
        // Creating an Equation object using the String inputted by the user
        Equation linearEquation = new Equation(equationString);

        // Obtaining the properties of the Equation
        Fraction slope = linearEquation.getSlope();
        Fraction constant = linearEquation.getConstant();                
        Fraction bracketConstant = linearEquation.getBracketConstant();        
        Fraction answer = linearEquation.getAnswer();

        // Setting up a reciprocal Fraction, used in cases when 'x' is in
        // the denominator
        Fraction reciprocal = new Fraction(1);

        // Switch block for different executions of the linear equations
        // depending upon the placement of the unknown variable
        switch (linearEquation.getUnknownVariablePlacementCase()) {
            // Block when unknownVariablePlacementCase is 1
            case 1:
                // Using the 'firstStep' helper method to do a part of the
                // equation which is done in all cases
                answer = firstStep(answer, slope, constant);
                // Subtracting bracketConstant to get final answer
                answer = answer.subtract(bracketConstant);
                break;

            // Block when unknownVariablePlacementCase is 2
            case 2:
                // Using the 'firstStep' helper method to do a part of the
                // equation which is done in all cases
                answer = firstStep(answer, slope, constant);
                // Subtracting bracketConstant
                answer = answer.subtract(bracketConstant);
                // Getting the reciprocal of the equation as 'x' was in denominator
                answer = reciprocal.divide(answer);
                break;

            // Block when unknownVariablePlacementCase is 3
            case 3:
                // Using the 'firstStep' helper method to do a part of the
                // equation which is done in all cases
                answer = firstStep(answer, slope, constant);
                // Getting the reciprocal of the equation as 'x' was in denominator and
                // in a bracket
                answer = reciprocal.divide(answer);
                // Subtracting bracketConstant to get final answer
                answer = answer.subtract(bracketConstant);
                break;
        }
        // Returning answer
        return answer;
    }

    /**
     * This function takes in a fraction, a slope, and a constant, and returns the fraction after
     * subtracting the constant from it and dividing it by the slope. This is one of the first steps in
     * solving any type of linear equation.
     * 
     * @param answer the answer to the equation
     * @param slope the slope of the line
     * @param constant the constant in the equation
     * @return The answer is being returned.
     */
    private static Fraction firstStep(Fraction answer, Fraction slope, Fraction constant) {
        // Subtracting the constant from the answer
        answer = answer.subtract(constant);
        // Dividing the answer by the slope
        answer = answer.divide(slope);
        return answer;
    }
    
    /**
     * This function is the main function of the program, it takes in user input and directs the user
     * to the quiz or the linear equation solver
     */
    public static void main(String[] args) {
        // Initializing answer
        Fraction answer;
        // Creating scanner object to take in input
        Scanner input = new Scanner(System.in);

        // Outputting directory
        System.out.println(ANSI_PURPLE + "\nKreesh's Designing Classes Project\n\nQ: Quiz your linear equation solving skills\n\nS: Solve linear equations\n" + ANSI_RESET);

        // Try and catch block for directory selection
        while(true) {
            try {
                // Getting user's selection for the directory
                System.out.print(ANSI_PURPLE + "CHOOSE: " + ANSI_RESET);
                String userInputString = input.nextLine();

                // When user wishes to do a quiz
                if (userInputString.equals("Q")) {
                    new Quiz();
                    break;
                }

                // When user wishes to solve a linear equation
                else if (userInputString.equals("S")) {
                    // Initializing user's input string
                    String userEquation = "";
                    
                    // Try and catch block for the linear equation
                    while(true) {
                            try {
                                // Getting input from the user and validating, breaking if no errors are caught
                                System.out.print("\nLinear Equation: ");
                                userEquation = input.nextLine();
                                inputValidator(userEquation);
                                break;
                            }
                            // Catching exceptions from the try block and outputting error message
                            catch (IllegalArgumentException e) {
                                System.out.println("TRY AGAIN!");
                            }
                    }
                    // Using the equations solver method to get the value of x
                    answer = EquationSolver(userEquation);
                    // Outputting answer
                    System.out.println("x = " + answer.toString());
                    break;
                }
                
                // Throwing an exception if user inputs an invalid entry for the directory
                else {
                    throw new IllegalArgumentException();
                }
            }
            // Catching any IllegalArgumentExceptions and outputting an error message
            catch (IllegalArgumentException e) {
                System.out.println("TRY AGAIN!");
            }
        }
        input.close();
    }
}