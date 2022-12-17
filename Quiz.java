// Importing the java.util module so Scanner and ArrayLists can be used
import java.util.*;

class Quiz {
    // COLOUR VALUE CONSTANTS
    public final String ANSI_YELLOW = "\u001B[33m";
    public final String ANSI_PURPLE = "\u001B[35m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_CYAN = "\u001b[36m";
    public final String ANSI_RESET = "\u001B[0m";
    // Local Scanner object used to take in input
    private Scanner input = new Scanner(System.in);
    // Initializing variables for the elapsed time feature
    private long initialStartingTime;
    private long elapsedTimeSinceStart;

    /** 
     * The constructor for the Quiz class. It is used to create a new Quiz object, and redirect the user to
     * to their choice of equation quiz (one/two/multistep).
     */
    public Quiz() {
        // While loop to get input from the user
        while (true) {
            // Outputting Quiz directory
            System.out.println(ANSI_PURPLE + "\nTYPE \'O\' for One Step Equations.\nTYPE \'T\' for Two Step Equations.\nTYPE \'M\' for Multi Step Equations." + ANSI_RESET);
            // Output prompt
            System.out.print(ANSI_CYAN + "CHOOSE: " + ANSI_RESET);
            // Obtaining user choice from the directory
            String equationType = input.nextLine();

            // Redirecting to one step equations
            if (equationType.equals("O")) {
                oneStepEquations();
                break;
            }

            // Redirecting to two step equations
            else if (equationType.equals("T")) {
                twoStepEquations();
                break;
            }

            // Redirecting to multi step equations
            else if (equationType.equals("M")) {
                multiStepEquations();
                break;
            }

            // Outputting error message and asking the user to retry
            else {
                System.out.println(ANSI_RED + "TRY AGAIN!" + ANSI_RESET);
            }
        }
    }


    /**
     * Generating a random number between -100 to 100 (exclusive) excluding 0 and 1.
     * @return Random integer 
     */
    private int randomNumberGenerator() {
        // Creating a random object
        Random rand = new Random();
        // Generating either 0 or 1
        int numberType = rand.nextInt(2);
        
        // If random number is 0, a random number betweeen -99 to -1 (inclusive) is returned
        if (numberType == 0) {
            return rand.nextInt(-99, 0);
        }

        // Since the random number generator isn't 0, a random number between 2, 100 (inclusive) is returned
        else {
            return rand.nextInt(2, 100);
        }
    }

    /**
     * This function creates an ArrayList of two potential signs, shuffles the ArrayList, and returns
     * the first index of the shuffled ArrayList. This is useful because when making an equation, the format
     * for adding and subtracting is the same, and this prevents repetetive code with minor modifications.
     * 
     * @return The first index of the ArrayList.
     */
    private String addOrSubtract() {
        // Creating an ArrayList
        ArrayList<String> operatorSigns = new ArrayList<>();
        // Adding two potential signs
        operatorSigns.add("+");
        operatorSigns.add("-");
        // Shuffling ArrayList
        Collections.shuffle(operatorSigns);
        // Shuffling first index
        return operatorSigns.get(0);
    }

    /**
     * This helper method takes in the random question generated, and the question number,
     * and asks the user for their answer, and verifies if the user's answer is right or not, or if the user
     * wishes to quit and relays that information back. 
     * 
     * @param question String
     * @param questionNum The question number
     * @return A string.
     */
    private String answerVerifier(ArrayList<String> equations, int questionNum) {
        // Initialising Fraction
        Fraction userAnswer;

        // Shuffling array list
        Collections.shuffle(equations);
        // Since Array list is randomised, we assign the question asked to be the first index of the Array list
        String question = equations.get(0);

        // Outputting the question for the user
        System.out.println(ANSI_YELLOW + "Q" + Integer.toString(questionNum + 1) + ". " + question + ANSI_RESET);

        // Setting up a while loop to take in input from the user
        while (true) {
            System.out.print("x = ");
            // Getting user's answer
            String userGuess = input.nextLine();
            // Quitting if user wants to quit
            if (userGuess.equals("QUIT")) {
                // Returning a terminate message
                return "TERMINATE";
            }

            // Block for when user doesn't want to quit
            else {
                // Trying to make the user's answer a fraction
                try {
                    userAnswer = Fraction.stringToFraction(userGuess);
                    break;        
                } 
                // Catching any errors and outputting error message
                catch (IllegalArgumentException e) {
                    System.out.println("INVALID INPUT! TRY AGAIN!");
                }
            }
        }
        
        // Solving the question and getting the answer using the EquationSolver method from the EquationCalculator class
        Fraction answer = EquationCalculator.EquationSolver(question);

        // Checking if user's answer is correct
        if (answer.toString().equals(userAnswer.toString())) {
            // Returning that answer is correct
            return "CORRECT";
        }

        else {
            // Returning that answer is wrong
            return "INCORRECT";
        }
    }

    /**
     * This function calculates the time elapsed since the quiz program started running.
     */
    private void elapsedTime() {
        // Getting the time in nano seconds
        elapsedTimeSinceStart = System.nanoTime();
        // Calculating how many nano seconds have passed since the start
        long totalTimeElapsed = elapsedTimeSinceStart - initialStartingTime;

        // Converting the elapsed time from nano seconds to seconds
        totalTimeElapsed = totalTimeElapsed / 1_000_000_000;

        // Checking if more than 60 seconds have passed, since that warrants writing the time
        // in a "MM:SS" format 
        if (totalTimeElapsed > 60) {
            long minutesElapsed = totalTimeElapsed / 60;
            long secondsElapsed = totalTimeElapsed % 60;

            System.out.printf(ANSI_CYAN + "Time Elapsed: %d minute(s) and %d seconds\n\n" + ANSI_RESET, minutesElapsed, secondsElapsed);
        }

        // If 60 seconds or less, time is written in seconds only
        else {
            System.out.printf(ANSI_CYAN + "Time Elapsed: %d seconds\n\n" + ANSI_RESET, totalTimeElapsed);
        }
    }

    private void messageOutput(String userInputResult) {
        // Checking if user was correct
        if (userInputResult.equals("CORRECT")) {
            // Letting the user know about the result
            System.out.println(ANSI_GREEN + "CORRECT!" + ANSI_RESET);
            // Outputting elapsed time
            elapsedTime();
        }

        // Checking if user was incorrect
        else if (userInputResult.equals("INCORRECT")) {
            // Letting the user know about the result
            System.out.println(ANSI_RED + "INCORRECT!" + ANSI_RESET);
            // Outputting elapsed time
            elapsedTime();
        }
    }

    private void finalOutputMessage(int correctAnswers, int totalQuestions) {
        // Outputting total score
        System.out.println(ANSI_PURPLE + "\nSCORE: " + correctAnswers + "/" + totalQuestions + ANSI_RESET);

        // Writing an additional message if user got perfect!
        if (correctAnswers == totalQuestions && totalQuestions != 0) {
            System.out.println(ANSI_GREEN + "PERFECT!" + ANSI_RESET);
        }
        // Outputting elapsed time
        elapsedTime();
    }

    /**
     * This method asks the user one step linear equations and grades them on their answers.
     */
    public void oneStepEquations() {
        // Initialising variables
        int totalQuestions = 0;
        int correctAnswers = 0;
        
        // Explaining how to break out and get result
        System.out.println(ANSI_PURPLE + "\nTYPE \'QUIT\' to leave and get results.\n" + ANSI_RESET);

        // Initialising ArrayList
        ArrayList<String> equations = new ArrayList<>();
        
        // Assigning initial starting time using System.nanoTime() which means 
        // the times is stored in nano seconds
        initialStartingTime = System.nanoTime();
        // While loop to ask multiple questions
        while (true) {
            // Generating random equations
            equations.add(String.format("x %s %d = %d", addOrSubtract(), randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("%dx = %d", randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("x/%d = %d", randomNumberGenerator(), randomNumberGenerator()));

            // Getting user input and checking if it is correct
            String userInputResult = answerVerifier(equations, totalQuestions);

            // Breaking out if user chose to quit
            if (userInputResult.equals("TERMINATE")) {
                break;
            }

            // Outputting the result of their guess to the user and updating correct answer accumulator accordingly
            else if (userInputResult.equals("CORRECT") || userInputResult.equals("INCORRECT")) {
                messageOutput(userInputResult);
                // Updating accumulator if correct
                if (userInputResult.equals("CORRECT")) {
                    // Updating accumulator
                    correctAnswers++;
                }
            }

            // Updating totalQuestions accumulator
            totalQuestions++;
            // Emptying ArrayList for next loop
            equations.removeAll(equations);
        }
        // Ending output message informing user of their grade and a special message if they scored perfect
        finalOutputMessage(correctAnswers, totalQuestions);
    }

    /**
     * This method asks the user two step linear equations and grades them on their answers.
     */
    public void twoStepEquations() {
        // Initialising variables
        int totalQuestions = 0;
        int correctAnswers = 0;
        
        // Explaining how to break out and get result
        System.out.println(ANSI_PURPLE + "\nTYPE \'QUIT\' to leave and get results.\n" + ANSI_RESET);

        // Initialising ArrayList
        ArrayList<String> equations = new ArrayList<>();

        // Assigning initial starting time using System.nanoTime() which means 
        // the times is stored in nano seconds
        initialStartingTime = System.nanoTime();
        while (true) {
            // Generating random equations
            equations.add(String.format("%dx %s %d = %d", randomNumberGenerator(), addOrSubtract(), randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("x/%d %s %d = %d", randomNumberGenerator(), addOrSubtract(), randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("(x %s %d)/%d = %d", addOrSubtract(), randomNumberGenerator(), randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("%d(x %s %d) = %d", randomNumberGenerator(), addOrSubtract(), randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("%d/x = %d", randomNumberGenerator(), randomNumberGenerator()));
        
            // Getting user input and checking if it is correct
            String userInputResult = answerVerifier(equations, totalQuestions);

            // Breaking out if user chose to quit
            if (userInputResult.equals("TERMINATE")) {
                break;
            }

            // Outputting the result of their guess to the user and updating correct answer accumulator accordingly
            else if (userInputResult.equals("CORRECT") || userInputResult.equals("INCORRECT")) {
                messageOutput(userInputResult);
                // Updating accumulator if correct
                if (userInputResult.equals("CORRECT")) {
                    // Updating accumulator
                    correctAnswers++;
                }
            }

            // Updating totalQuestions accumulator
            totalQuestions++;
            // Emptying ArrayList for next loop
            equations.removeAll(equations);
        }
        // Ending output message informing user of their grade and a special message if they scored perfect
        finalOutputMessage(correctAnswers, totalQuestions);
    }

    /**
     * This method asks the user multi step linear equations and grades them on their answers.
     */
    public void multiStepEquations() {
        // Initialising variables
        int totalQuestions = 0;
        int correctAnswers = 0;
        
        // Explaining how to break out and get result
        System.out.println(ANSI_PURPLE + "\nTYPE \'QUIT\' to leave and get results.\n" + ANSI_RESET);

        // Initialising ArrayList
        ArrayList<String> equations = new ArrayList<>();

        // Assigning initial starting time using System.nanoTime() which means 
        // the times is stored in nano seconds
        initialStartingTime = System.nanoTime();
        while (true) {
            // Generating random equations
            equations.add(String.format("%dx/%d %s %d = %d", randomNumberGenerator(), randomNumberGenerator(), addOrSubtract(), randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("%d(x %s %d)/%d = %d", randomNumberGenerator(), addOrSubtract(), randomNumberGenerator(), randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("%d/x = %d/%d", randomNumberGenerator(), randomNumberGenerator(), randomNumberGenerator()));
            equations.add(String.format("%dx/%d %s %d/%d = %d/%d", randomNumberGenerator(), randomNumberGenerator(), addOrSubtract(), randomNumberGenerator(), randomNumberGenerator(), randomNumberGenerator(), randomNumberGenerator()));
    
            // Getting user input and checking if it is correct
            String userInputResult = answerVerifier(equations, totalQuestions);

            // Breaking out if user chose to quit
            if (userInputResult.equals("TERMINATE")) {
                break;
            }

            // Outputting the result of their guess to the user and updating correct answer accumulator accordingly
            else if (userInputResult.equals("CORRECT") || userInputResult.equals("INCORRECT")) {
                messageOutput(userInputResult);
                // Updating accumulator if correct
                if (userInputResult.equals("CORRECT")) {
                    // Updating accumulator
                    correctAnswers++;
                }
            }

            // Updating totalQuestions accumulator
            totalQuestions++;
            // Emptying ArrayList for next loop
            equations.removeAll(equations);
        }
        // Ending output message informing user of their grade and a special message if they scored perfect
        finalOutputMessage(correctAnswers, totalQuestions);
    }
}