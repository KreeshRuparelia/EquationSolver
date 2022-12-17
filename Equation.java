// Importing ArrayList to hold multiple constant and unknown terms
import java.util.ArrayList;

class Equation {
    // Declaring the variables storing the properties of an equation object.
    private Fraction slope;
    private Fraction bracketConstant;
    private Fraction constant;
    private Fraction answer;
    private int unknownVariablePlacementCase = 1;

    /**
     * A constructor for the Equation class, which takes in a string and represents it as a linear equation object with
     * individual properties such as slope, constant, answer, and the constant value in the bracket, the bracket constant.
     * 
     * @param expr A String representing the linear equation.
     */
    public Equation(String expr) {

        // Assigning slope 'm' value
        slope = setSlopeValue(expr);

        // Assigning bracketConstant 'b' value
        bracketConstant = setBracketConstantValue(expr);
        
        // Assigning constant 'b' value
        constant = setConstantValue(expr);
        
        // Assigning answer 'y' value
        answer = setAnswerValue(expr);
    }

    /**
     * This helper function ensures that the unknown variable 'x' is always on the left
     * side of the equation.
     * 
     * @param equation The equation to be solved.
     * @return The equation with the x on the left side.
     */
    private String equationSide(String equation) {
        // Obtaining the index of the equals sign
        int equalsIndex = equation.indexOf("= ");

        // If x is on the left side, then returning the equation as is
        if (equation.substring(0, equalsIndex - 1).contains("x")) {
            return equation;
        }

        // Modifying the equation so x is always on the left
        else {
            return ((equation.substring(equalsIndex + 2)) + " = " + equation.substring(0, equalsIndex - 1));
        }
    }

    /**
     * This is a helper function which checks if there are brackets in the expression, and removes
     * them and everything in between (which includes 'x'). If there are no brackets, it removes all
     * instances of 'x'
     * 
     * @param expression The expression to be cleaned
     * @return The method is returning the expression with the unknown variable removed.
     */
    private String unknownAndBracketCleaner(String expression) {
        // Obtaining the indexes of the brackets in the expression
        int openBracketIndex = expression.indexOf("(");
        int closeBracketIndex = expression.indexOf(")");

        // Checking if there is a bracket in the expression
        if (openBracketIndex != -1 && closeBracketIndex != -1) {
            // Returning a String with the unknown variable 'x' and the brackets removed
            return expression.replace(expression.substring(openBracketIndex, closeBracketIndex + 1), "");
        }

        else {
            // Returning the String with the unknown variable 'x' removed
            return expression.replace("x", "");
        }
    }
    
    /**
     * Determining where the unknown variable is placed, this is useful when doing
     * the calculations, as a switch block can be used to determine the appropriate steps. 
     * 
     * @param slopeString The slope equation in string form
     */
    private void setUnknownPlacement(String slopeString) {
        // When unknown is in numerator or when slope is written as "num1/num2x" since that means the x is on the side and not in denominator
        if (slopeString.indexOf("/") > slopeString.indexOf("x") || (slopeString.indexOf("x") - slopeString.indexOf("/") > 1 && slopeString.indexOf("-x") == -1)) {
            setunknownVariablePlacementCase(1);
        }

        // When unknown is in the denominator and but not in a bracket
        else if (slopeString.indexOf("(") == -1 && slopeString.indexOf("/") != -1) {
            setunknownVariablePlacementCase(2);
        }

        // When unknown is in the denominator and in a bracket
        else if (slopeString.indexOf(")") > slopeString.indexOf("/") && slopeString.indexOf("/") != -1) {
            setunknownVariablePlacementCase(3);
        }

        // Default assignment
        else {
            setunknownVariablePlacementCase(1);
        }
    }

    /* *********************************************************************************************************************
     * PROPERTY ASSIGNMENT SECTION
     * 
     * This section is wherein all the properties of an equation object are assigned their respective values.
     */

    /**
     * It takes in a expression and finds all the unknown (x) terms, and puts them into an array list
     * 
     * @param expr the expression that is scanned for unknown terms
     * @return An ArrayList of Strings.
     */
    private ArrayList<String> groupingUnknownTerms(String expr) {
        // Creating an array list
        ArrayList<String> unknownTermsArray = new ArrayList<String>();
        int numOfUnknowns = 0;
        int openBracketIndex = expr.indexOf("(");
        int closeBracketIndex = expr.indexOf(")");

        // Finding how many unknowns in the expression
        for (int i = 0; i < expr.length(); i++) {
            if (expr.substring(i, i + 1).equals("x")) {
                numOfUnknowns++;
            }
        }

        // Looping to figure out all the 'mx' blocks of the expression and adding them to the array
        for (int i = 1; i <= numOfUnknowns; i++) {
            int indexOfUnknown = expr.indexOf("x");

            // Updating the unknownVariablePlacementCase and removing the spaces in the brackets
            if (expr.indexOf("(") < indexOfUnknown && indexOfUnknown < expr.indexOf(")")) {
                if (expr.indexOf("/") != -1 && expr.indexOf("/") < openBracketIndex) {
                    setunknownVariablePlacementCase(3);
                }
                
                expr = expr.replace(expr.substring(openBracketIndex, closeBracketIndex), expr.substring(openBracketIndex, closeBracketIndex).replace(" ", ""));
                // Updating index of x
                indexOfUnknown = expr.indexOf("x");
            }

            // Initializing variables
            int beginningIndex = 0;
            int endingIndex = 0;

            // Looping in order to figure out the beginning index of the 'mx' substring
            for (int a = indexOfUnknown; a > 0; a--) {

                // Safety backup if 'mx' is in the start
                if (a < 0) {
                    beginningIndex = 0;
                    break;
                }

                // Checking for space
                else if (expr.substring(a, a + 3).equals(" + ") || expr.substring(a, a + 3).equals(" - ")) {
                    beginningIndex = a + 1;
                    break;
                }
            }

            // Finding the ending index of of the 'mx' substring
            for (int a = indexOfUnknown + 1; a < expr.length(); a++) {
                if (expr.substring(a, a + 1).equals(" ")) {
                    endingIndex = a;
                    break;
                }
            }

            // Indexing and adding 'mx' substring to the ArrayList of the unknownTermsArray
            unknownTermsArray.add(expr.substring(beginningIndex, endingIndex));
            // Modifying the expression string
            expr = expr.substring(endingIndex + 1);
        }
        // Returning ArrayList of 'mx' values
        return unknownTermsArray;
    }
    
    /**
     * This function takes in a linear equation and returns the overall slope value.  
     * 
     * @param mxTerm The linear equation
     * @return The slope value of the equation.
     */
    private Fraction setSlopeValue(String mxTerm) {
        // Formatting the equation so x is on the left side
        mxTerm = equationSide(mxTerm);
        // Getting the ArrayList with all the 'mx' values of the linear equation using the helper method
        // groupingUnknownTerms()
        ArrayList<String> unknowns = groupingUnknownTerms(mxTerm);
        
        // Initialising slope accumulator
        Fraction slopeVal = new Fraction(0);

        // Looping through the various 'mx' values and updating slope
        for (String i : unknowns) {
            // Assigning the appropriate unknownVariablePlacementCase using this helper method
            setUnknownPlacement(i);
            // Finding index of x
            int indexOfUnknown = i.indexOf("x");

            // Checking if x isn't the last index, and is directly followed by a division sign
            if (indexOfUnknown != i.length() - 1 && i.substring(indexOfUnknown + 1, indexOfUnknown + 2).equals("/")) {
                // Replacing 'x' with '1' if x is either the first index or if x is preceeded by a space
                if (indexOfUnknown == 0 || i.substring(indexOfUnknown - 1, indexOfUnknown).equals(" ")) {
                    i = i.replace("x", "1");
                }
            }

            // Checking if x isn't the first index and isnt preceeeded by a space
            else if (indexOfUnknown != 0 && i.substring(indexOfUnknown - 1, indexOfUnknown).equals("/")) {
                // Replacing 'x' with '1' if it is the last index or followed by a space 
                if (indexOfUnknown == i.length() - 1 || i.substring(indexOfUnknown + 1, indexOfUnknown + 2).equals(" ")) {
                    // setUnknownPlacement(i);
                    i = i.replace("x", "1");
                }
            }
            
            // Removing all the x and brackets from the String
            i = unknownAndBracketCleaner(i);

            // Changing string to "+ 1" if it was previously "x" as it is now empty
            if (i.length() == 0) {
                i = "+ 1";
            }

            // Adding "1" to the beginning of the string if it was previously "x/..." / "(x...)/..."
            if (i.indexOf("/") == 0) {
                i = "1" + i;
            }
            
            if (i.indexOf("-/") == 0) {
                i = "-1" + i.substring(i.indexOf("/"));
            }

            // Adding "1" to the end of the string if it was previously ".../x" / ".../(x...)"
            else if (i.indexOf("/") == i.length() - 1 || (i.indexOf("/-") == i.length() - 2 && i.length() != 1)) {
                i = i + "1";
            }

            // Modifying string to "- 1" if it was previously "-x" and became "-"
            else if (i.length() == 1 && i.equals("-")) {
                i = "- 1";
            }

            // Modifying string to "+ ..." if it was previously written with no operators in order to work with
            // the accumulator
            if (i.indexOf("+ ") == -1 && i.indexOf("- ") == -1) {
                i = "+ " + i;
            }
            
            slopeVal = (Fraction.calculate(slopeVal.toString() + " " + i));
        }
        // Returning slope
        return slopeVal;
    }

    /**
     * It takes a string, finds all the constant (b) terms, and returns them in an array
     * 
     * @param expr The left side of the equation
     * @return An ArrayList of constant (b) terms.
     */
    private ArrayList<String> groupingConstantTerms(String expr) {
        // Initialising ArrayLists
        ArrayList<String> constantTermsArray = new ArrayList<>();
        // Initialisng Variable which will get updated to hold the index of the last operator  
        int lastOperatorIndex = 0;
        // Regex for an operator
        String operator = String.format((" (\\+|-) "));

        // Getting the index of brackets
        int openBracketIndex = expr.indexOf("(");
        int closeBracketIndex = expr.indexOf(")");
        
        // Removing the space between the brackets
        if (openBracketIndex != -1 && closeBracketIndex != -1) {
            expr = expr.replace(expr.substring(openBracketIndex, closeBracketIndex), expr.substring(openBracketIndex, closeBracketIndex).replaceAll(" ", ""));
        }

        // Looping through the string
        for (int i = 0; i < expr.length(); i++) {

            // Ensuring the current index is at the end of the equation
            if (expr.substring(i).equals(" =")) {
                // Checking if there is a constant in the substring between the lastOperatorIndex and end of the String
                if (!(expr.substring(lastOperatorIndex, i).contains("x"))) {
                    constantTermsArray.add(expr.substring(lastOperatorIndex, i));
                }
                break;
            }

            // Checking if current index an operator block
            else if (expr.substring(i, i + 3).matches(operator)) {
                // Checking if there is a constant between the index of the last operator to current index
                if (!(expr.substring(lastOperatorIndex, i).contains("x"))) {
                    // Adding term to constantTermsArray ArrayList
                    constantTermsArray.add(expr.substring(lastOperatorIndex, i));
                }
                // Updating lastOperatorIndex
                lastOperatorIndex = i + 1;
            }
        }
        // Returning ArrayList of 'b' values
        return constantTermsArray;
    }

    /**
     * This function takes a string, and returns a fraction
     * 
     * @param constantB The string that is being evaluated.
     * @return The return type is Fraction.
     */
    private Fraction setConstantValue(String constantB) {
        // Getting the equation in the proper format
        constantB = equationSide(constantB);
        // Indexing to include left side upto the equal space
        constantB = constantB.substring(0, constantB.indexOf("= ") + 1);
        // Obtaining an ArrayList containing all the constant terms
        ArrayList<String> constantTermsArray = groupingConstantTerms(constantB);

        // Initialising constant value accumulator
        Fraction constantVal = new Fraction(0);

        // Looping through every constant term in the ArrayList
        for (String i: constantTermsArray) {

            // Checking if the constant term begins with an operator, and assigning a "+ " if not
            // in order to not raise an error when adding to the constantVal accumulator
            if (i.indexOf("+ ") == -1 && i.indexOf("- ") == -1) {
                i = "+ " + i;
            }

            // Adding/Subtracting the current constant term from the total constant value
            constantVal = Fraction.calculate(constantVal.toString() + " " + i);
        }
        // Returning constantVal
        return constantVal;
    }

    /**
     * This helper function returns the bracket constant in the equation as a string
     * 
     * @param equation The equation that is being evaluated
     * @return The bracket constant in the equation.
     */
    private String bracketConstantSubString(String equation) {
        // Obtaining the indexes of the brackets in the expression
        int openBracketIndex = equation.indexOf("(");
        int closeBracketIndex = equation.indexOf(")");

        // Checking if there is a bracket constant in the equation
        if (openBracketIndex == -1 && closeBracketIndex == -1) {
            // Return value for when no bracket constant
            return "";
        }

        // Return value for when there is a bracket constant
        else {
            return equation.substring(openBracketIndex, closeBracketIndex + 1);
        }
    }

    /**
     * This function takes in the value of the constant inside the bracket as a String 
     * and returns the value as a Fraction.
     * 
     * @param bracketConstant The constant inside the brackets.
     * @return The value of the constant.
     */
    private Fraction setBracketConstantValue(String bracketConstantB) {    
        // Getting the substring for the bracket constant
        bracketConstantB = bracketConstantSubString(bracketConstantB);
        
        // Returning zero if no bracket constant
        if (bracketConstantB.length() == 0) {
            return new Fraction(0);
        }
    
        // Isolating the constant being added/subtracted to x
        bracketConstantB = bracketConstantB.substring(3, bracketConstantB.indexOf(")"));
        
        // Adding the string to 0 using the calculate method from the fraction class to determine
        // the answer value
        Fraction bracketConstantValue = Fraction.calculate("0 " + bracketConstantB);

        // Returning the bracket constant as a Fraction
        return bracketConstantValue;
    }

    /**
     * It takes in a string, makes sure it's in the proper format, and returns everything right of the
     * equals sign
     * 
     * @param equation The equation to be solved
     * @return The answer to the equation
     */
    private String answerSubstring(String equation) {
        // Ensuring the equation is in the proper format
        equation = equationSide(equation);
        // Returning everything right of the equals sign
        return equation.substring(equation.indexOf("= ") + 2);
    }

    /**
     * This method takes in a linear equation and returns the answer 'y' value
     * as a Fraction
     * 
     * @param answerValueY The 'y' value of a linear equation.
     * @return The 'y' value as a Fraction object.
     */
    private Fraction setAnswerValue(String answerY) {
        // Getting the substring of the answer
        answerY = answerSubstring(answerY);

        // Obtaining the index of a division sign
        int dividerIndex = answerY.indexOf("/");

        // Checking if the answer is not an integer
        if (dividerIndex != -1) {
            // Indexing using the index of the division sign to assign values to the
            // numerator and denominator
            int numerator = Integer.valueOf(answerY.substring(0, dividerIndex));
            int denominator = Integer.valueOf(answerY.substring(dividerIndex + 1));
            // Returning constant / answer
            return new Fraction(numerator, denominator);
        }
        // Return value for when the answer is an integer
        else {
            return new Fraction(Integer.valueOf(answerY));
        }
    }

    /* *********************************************************************************************************************
     * GETTERS AND SETTERS SECTION
     * 
     * This section contains the getters for all the key properties of the equation class.
     */

    /**
     * This function returns the slope of the line
     * 
     * @return The slope of the line.
     */
    public Fraction getSlope() {
        return slope;
    }

    /**
     * This function returns the bracket constant
     * 
     * @return The bracketConstant is being returned.
     */
    public Fraction getBracketConstant() {
        return bracketConstant;
    }

    /**
     * The function returns the constant value of the fraction
     * 
     * @return The constant variable is being returned.
     */
    public Fraction getConstant() {
        return constant;
    }

    /**
     * This function returns the answer to the question
     * 
     * @return The answer to the question.
     */
    public Fraction getAnswer() {
        return answer;
    }

    /**
     * This function returns the value of the variable unknownVariablePlacementCase
     * 
     * @return The value of the variable unknownVariablePlacementCase.
     */
    public int getUnknownVariablePlacementCase() {
        return unknownVariablePlacementCase;
    }

    /**
     * This function sets the slope of the line to the value of the parameter slopeM
     * 
     * @param slopeM The slope of the line
     */
    public void setSlope(Fraction slopeM) {
        slope = slopeM;
    }

    /**
     * This function sets the bracket constant to the value of the parameter
     * 
     * @param bracketConstantB The constant that is added to the variable in the bracket
     */
    public void setBracketConstant(Fraction bracketConstantB) {
        bracketConstant = bracketConstantB;
    }

    /**
     * This function sets the constant of the equation to the value of the constantB parameter
     * 
     * @param constantB The constant to be set.
     */
    public void setConstant(Fraction constantB) {
        constant = constantB;
    }

    /**
     * This function sets the answer to the fraction that is passed in
     * 
     * @param answerY The answer to the question
     */
    public void setAnswer(Fraction answerY) {
        answer = answerY;
    }

    /**
     * This function sets the unknown variable placement case
     * 
     * @param Case The case of the unknown variable placement.
     */
    public void setunknownVariablePlacementCase(int Case) {
        unknownVariablePlacementCase = Case;
    }
}