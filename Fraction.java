// STUDENT NAME: Kreesh Ruparelia
// STUDENT NUMBER: 848802
// PROJECT: Fractions Calculator
// COURSE CODE: ICS4UR

/**
 * This class is a fraction calculator that takes in a string, checks if it's valid, finds the
 * operator, creates two fraction objects, and then executes the operation
 */
class Fraction {
    // Inialising whole, numerator, and denominator variables
    private int whole = 0;
    private int num;
    private int den;

    /** This is a constructor for the Fraction class. It sets the whole number
     * to the value of the whole number, the numerator to 0, and the
     * denominator to 1.
     * 
     * @param wholeVal represents the whole value of the fraction
     */
    public Fraction(int wholeVal) {
        whole = wholeVal;
        num = 0;
        den = 1;
    }

    /** This is a constructor for the Fraction class. It formats the fraction
     * appropriately by placing the sign of the fraction on the numerator, and
     * converting the fraction to a mixed fraction and placing the sign on the
     * whole if it is an improper fraction.
     * 
     * @param numVal represents the numerator value of the fraction
     * @param denVal represents the denominator value of the fraction
     */ 
    public Fraction(int numVal, int denVal) {
        whole = 0;
        num = numVal;
        den = denVal;

        // Getting the sign of the fraction using the getSign method
        int fractionSign = getSign(whole, num, den);

        // Converting any improper fractions to mixed
        if (Math.abs(num) >= Math.abs(den)) {
            whole = Math.abs(num) / Math.abs(den);
            num = Math.abs(num) % Math.abs(den);
        }

        // Attaching the sign to the whole if whole is not equal to 0
        if (whole != 0) {
            whole = fractionSign * whole;
            num = Math.abs(num);
            den = Math.abs(den);
        }

        // Attaching the sign to the numerator if there is no whole number
        else {
            num = fractionSign * Math.abs(num);
            den = Math.abs(den);
        }

        // Obtaining the greatest common factor, in order to reduce the 
        // fraction to the lowest form
        int greatestCommonFactor = gcf(num, den);
        num = num / greatestCommonFactor;
        den = den / greatestCommonFactor;
    }

    /**
     * This is a constructor for the Fraction class. It calls the fraction
     * constructor above and assigning the appropriate sign to the whole,
     * obtained using the getSign method.
     * 
     * @param wholeVal represents the whole value of the fraction
     * @param numVal represents the numerator value of the fraction
     * @param denVal represents the denominator value of the fraction
     */
    public Fraction(int wholeVal, int numVal, int denVal) {
        // Using 'this' to call the constructor above
        this(numVal, denVal); 

        // Getting the sign of the fraction using the getSign method
        int fractionSign = getSign(wholeVal, numVal, denVal);
        
        // Multiplying the fraction's sign with the total of the two wholes in
        // their absolute forms to get the new whole value
        whole = fractionSign * (Math.abs(wholeVal) + Math.abs(whole));
    }

    /**
     * If the whole number, numerator, or denominator is negative, the 
     * function will return a negative sign. Otherwise, it will return a 
     * positive sign.
     * 
     * @param wholeVal The whole number value of the fraction
     * @param numVal The numerator of the fraction
     * @param denVal The denominator of the fraction
     * @return The sign of the fraction.
     */
    public int getSign(int wholeVal, int numVal, int denVal) {
        // Accumulator to count the amount of negative values in the fraction
        int negativeChecker = 0;

        // Checking if the whole, numerator, or denominator is negative and
        // updating the accumulator accordingly
        if (wholeVal < 0) {
            negativeChecker++;
        }

        if (num < 0) {
            negativeChecker++;
        }
        
        if (den < 0) {
            negativeChecker++;
        }
        
        // Since double negatives cancel out, a remainder of one on the 
        // accumulator when divided by 2, means the negatives don't cancel
        // and the fraction is negative
        if ((negativeChecker %= 2) == 1) {
            // Returning the negative sign
            return -1;
        }

        // If the remainder isn't one, it means there are no / two negatives
        // resulting in an overall positive fraction
        else {
            // Returning a positive sign
            return 1;
        }
    }

    /**
     * The function takes in two integers, and returns the greatest common 
     * factor of the two integers
     * 
     * @param numVal The numerator of the fraction
     * @param denVal The denominator of the fraction
     * @return The greatest common factor that evenly divides the numerator 
     * and denominator.
     */
    public int gcf(int numVal, int denVal) {
        // Returning 1 if there is a 0 or 1 in the numerator/denominator
        if (numVal == 0 || numVal == 1 || denVal == 0 || denVal == 1) {
            return 1;
        }
    
        // Setting up a counter which will then get reduced in the while loop
        int factor = Math.min(Math.abs(numVal), Math.abs(denVal));

        // A while loop that checks if the factor evenly divides into the 
        // numerator and denominator.
        while (true) {
            if (numVal % factor == 0 && denVal % factor == 0) {
                // Returns the greatest common factor that evenly divides the
                // numerator and denominator.
                return factor;
            }
            // Updating counter
            factor--;
        }
    }

    
    /**
     * This function returns the whole number of the fraction.
     * 
     * @return The whole number of the fraction.
     */
    public int getWhole() {
        return whole;
    }

    /**
     * This function returns the numerator value of the fraction.
     * 
     * @return The numerator value of the fraction.
     */
    public int getNum() {
        return num;
    }

    /**
     * This function returns the denominator of the fraction.
     * 
     * @return The denominator of the fraction.
     */
    public int getDen() {
        return den;
    }

    /**
     * This function takes in an integer and sets the whole value of the
     * fraction to the integer.
     * 
     * @param newWholeVal The new value for the whole number.
     */
    public void setWhole(int newWholeVal) {
        whole = newWholeVal;
    }

    /**
     * This function takes in an integer and sets the numerator value of the
     * fraction to the integer.
     * 
     * @param newNumVal The new value for the numerator.
     */
    public void setNum(int newNumVal) {
        num = newNumVal;
    }

   /**
    * This function takes in an integer and sets the denominator of the 
    * fraction to that value.
    * 
    * @param newDenVal The new value for the denominator.
    */
    public void setDen(int newDenVal) {
        den = newDenVal;
    }

    /**
     * This function returns a string representation of the fraction
     * 
     * @return The returnString is being returned.
     */
    public String toString() {
        // Initialising the return string
        String returnString = "";
        
        // String output format for when the answer is just a whole number
        if (num == 0) {
            return Integer.toString(whole); 
        }

        // String output format for a fraction with no whole number
        else if (whole == 0) {
            returnString = String.format("%d/%d", num, den);
        }

        // String output format for a mixed fraction
        else if (whole != 0) {
            returnString = String.format("%d %d/%d", whole, num, den);
        }

        // Returning return string
        return returnString;
    }

    /**
     * The clone() method creates a new Fraction object with the same whole,
     * numerator, and denominator as the original Fraction object
     * 
     * @return A new Fraction object with the same values as the original.
     */
    public Fraction clone() {
        return new Fraction(whole, num, den);
    }

    /**
     * The function takes in three parameters, the whole value, numerator 
     * value, and denominator value, and returns the numerator value of an 
     * improper fraction.
     * 
     * @param wholeVal The whole number value of the mixed fraction.
     * @param numVal The numerator value of the mixed fraction.
     * @param denVal The denominator value of the mixed fraction.
     * @return The numerator value of the improper fraction.
     */
    public int ImproperFractions(int wholeVal, int numVal, int denVal) {
        // In an improper fraction the numerator value must be subtracted if 
        // whole * den is negative, hence why this if condition tests that
        // and subtracts the numerator value.
        if (wholeVal * denVal < 0) {
            // Returning imprpoper fraction numerator value
            return ((wholeVal * denVal) - numVal); 
        }

        // In any other scenarios however, we add the numerator value, hence 
        // why the else block which does exactly that.
        else {
            // Returning imprpoper fraction numerator value
            return ((wholeVal * denVal) + numVal);
        }
    }

    /**
     * This function takes a string and returns a fraction object
     * 
     * @param Fraction String
     * @return A Fraction object.
     */
    public static Fraction stringToFraction(String Fraction) {
        // Returning the entire String as it is a whole number
        if (!(Fraction.contains("/"))) {
            return new Fraction(Integer.valueOf(Fraction));
        }

        // Return value for when Fraction is in mixed form
        else if (Fraction.contains(" ")) {
            int whole = Integer.valueOf(Fraction.substring(0, Fraction.indexOf(" ")));
            int numerator = Integer.valueOf(Fraction.substring(Fraction.indexOf(" ") + 1, Fraction.indexOf("/")));
            int denominator = Integer.valueOf(Fraction.substring(Fraction.indexOf("/") + 1));
            return new Fraction(whole, numerator, denominator);
        }

        // Return value for when Fraction is in improper form
        else {
            int numerator = Integer.valueOf(Fraction.substring(0, Fraction.indexOf("/")));
            int denominator = Integer.valueOf(Fraction.substring(Fraction.indexOf("/") + 1));
            return new Fraction(numerator, denominator);
        }
    }

    /**
     * This function takes in a fraction and returns the sum of the current 
     * and other fractions.
     * 
     * @param other The other fraction to add to this fraction
     * @return The sum of the two fractions
     */
    public Fraction add(Fraction other) {
        // Making both the fractions improper
        int numeratorOriginal = ImproperFractions(whole, num, den);
        int numeratorOther = ImproperFractions(other.getWhole(), other.getNum(), other.getDen());

        // Adding the Improper numerators and denominators
        int numerator = (numeratorOriginal * other.getDen()) + (numeratorOther * den);
        int denominator = den * other.getDen();

        // Returning the sum
        return new Fraction(numerator, denominator);
    }

    /**
     * This function takes in a fraction and returns the difference of the
     * current and other fractions.
     * 
     * @param other The Fraction being subtracted from the current Fraction.
     * @return The result of the subtraction of the two Fractions.
     */
    public Fraction subtract(Fraction other) {
        // Clones the Fraction being subtracted 
        Fraction switchedSigns = other.clone();

        // Flips the sign on the numerator value if there is no whole number
        if (other.getWhole() ==  0) {
            switchedSigns.setNum(-1 * other.getNum());
        }

        // Flips the sign on the whole value
        else {
            switchedSigns.setWhole(-1 * other.getWhole());
        }

        // Takes the Fraction with the switched signs and adds it to the current
        // Fraction, in order to get the result.
        return add(switchedSigns);
    }

    /**
     * This function takes in a Fraction and multiplies it with the current
     * Fraction.
     * 
     * @param other The other fraction that is being multiplied
     * @return The result of the multiplication of the two fractions.
     */
    public Fraction multiply(Fraction other) {
        // Converts the whole number and adds it to the numerator to make the fraction improper
        int numeratorOriginal = ImproperFractions(whole, num, den);
        int numeratorOther = ImproperFractions(other.getWhole(), other.getNum(), other.getDen());

        // Multiplying the Numerators and Denominators
        int numerator = numeratorOriginal * numeratorOther;
        int denominator = den * other.getDen();

        // Returns the result
        return new Fraction(numerator, denominator);
    }

    /**
     * The function takes in a fraction and divides the fraction by another 
     * fraction by flipping the fraction and multiplying it with the original
     * fraction
     * 
     * @param other The Fraction that is the divisor
     * @return The result of the current Fraction divided by the other Fraction.
     */
    public Fraction divide(Fraction other) {
        // Flips the Fraction
        int numerator = other.getDen();
        int denominator = ImproperFractions(other.getWhole(), other.getNum(), other.getDen());

        // Creates a new Fraction object that is the reciprocal of the Fraction in the parameter
        Fraction reciprocal = new Fraction(numerator, denominator);

        // Since the fractions is now flipped, multiplying it with the original would be
        // equivalent to dividing
        return multiply(reciprocal);
    }

    /**
     * This function takes in a string and checks if it matches the correct
     * fraction format for our calculator, returning true if it does, and
     * throwing an exception if it doesn't. 
     * 
     * @param userString The user input that is being tested
     * @throws IllegalArgumentException
     */
    public static void inputValidator(String userString) {
        // Setting up regex string variables to ensure that the user input is valid
        String wholeNum = "-*[0-9]*";
        String mixedFraction = "-*[1-9][0-9]* -*[0-9]*/-*[0-9]*";
        String improperFraction = "-*[0-9]*/-*[0-9]*";

        // Setting up the correct regex format that gets tested
        String correctFormat = String.format("(%s|%s|%s) (\\+|-|\\*|/) (%s|%s|%s)",
        wholeNum, mixedFraction, improperFraction, wholeNum, mixedFraction, improperFraction);

        // Returns error message if the user input isn't the correct format
        if (!userString.toString().matches(correctFormat)) {
            System.out.println("PLEASE ENSURE THE EXPRESSION IS CORRECTLY FORMATTED AND YOU ARE USING VALID CHARACTERS");
            throw new IllegalArgumentException("Enter in the correct format!");
        }
    }

    /**
     * Returns the index of the operator in the expression
     * 
     * @param userString The string that the user inputs
     * @return The index of the operator in the string.
     */
    public static int operatorIndexDetector(String userString){

        // Variables for the index of the operators, if no such substring
        // in the string, it returns -1
        int plusIndex = userString.indexOf("+ ");
        int subtractIndex = userString.indexOf("- ");
        int multiplyIndex = userString.indexOf("* ");
        int divideIndex = userString.indexOf("/ ");

        // Returning the index of whatever operator is in the expression.

        if (plusIndex != -1) {
            return plusIndex;
        }

        else if (subtractIndex != -1) {
            return subtractIndex;
        }

        else if (multiplyIndex != -1) {
            return multiplyIndex;
        }

        else if (divideIndex != -1) {
            return divideIndex;
        }

        // Obligatory return statement to not raise an exception
        return 0;
    }

    /**
     * This function takes in a string and returns a Fraction object
     * 
     * @param fractionString The string that is being parsed
     * @return A Fraction object
     * @throws IllegalArgumentException
     */
    public static Fraction fractionAssembly(String fractionString) {
        // Finding the index of the first space. Since we removed the space before/after the operator
        // the result of this allows us to determine if there is a whole number or not
        int spaceIndex = fractionString.indexOf(" ");

        // Finding the index of the fraction divider this is useful when indexing
        int dividerIndex = fractionString.indexOf("/");

        // Since a whole number would not have the fraction line, 
        // we can use this as a way to create a fraction with just the whole
        // number using our whole number constructor
        if (dividerIndex == -1) {
            return new Fraction(Integer.valueOf(fractionString));
        }

        // Initializing variables
        int whole = 0;
        int numerator = 0;

        // Since denominator is unaffected by the presence of a whole number,
        // we can assign it it's value right now.
        int denominator = Integer.valueOf(fractionString.substring(dividerIndex + 1));

        // Numerator value assignment for when there is no whole number
        if (spaceIndex == -1) {
            numerator = Integer.valueOf(fractionString.substring(0, dividerIndex));
        }

        // Numerator and whole number value assignment for when there is a whole number 
        else {
            whole = Integer.valueOf(fractionString.substring(0, spaceIndex));
            numerator = Integer.valueOf(fractionString.substring(spaceIndex + 1, dividerIndex));
        }

        // Ensuring the Fraction is properly formatted and that denominator
        // is not equal to 0
        if (denominator == 0) {
            // Custom case specific error message
            System.out.println("DENOMINATOR CANNOT EQUAL 0!");
            // Throwing the exception
            throw new IllegalArgumentException("DENOMINATOR CANNOT EQUAL 0");
        }
        
        // Ensuring there isn't a negative sign on the numerator or denominator
        // if the fraction is mixed.
        if ((numerator / Math.abs(numerator) == -1 || denominator / Math.abs(denominator) == -1) && whole != 0) {
            // Custom case specific error message
            System.out.println("PLEASE ENSURE THE SIGNS ARE PLACED ON THE WHOLE NUMBER!");
            // Throwing the exception
            throw new IllegalArgumentException();
        }

        // Returns just the whole if the numerator is equal to 0
        if (numerator == 0) {
            // Returning a Fraction object
            return new Fraction(whole);
        }
        
        // Else block for when numerator isn't 0
        else {
            if (whole == 0) {
                return new Fraction(numerator, denominator);
            }
            
            return new Fraction(whole, numerator, denominator);
        }
    }

    /**
     * This function takes in a string operator, and two Fraction objects, and returns the result of
     * the operation as a string
     * 
     * @param operator The operator that is being used in the expression
     * @param firstFraction Fraction One
     * @param secondFraction Fraction Two
     * @return The return statement is returning the result of the operation being performed on the two
     * fractions.
     * @throws IllegalArgumentException
     */
    public static String operationExecutor(String operator, Fraction firstFraction, Fraction secondFraction) {
        // Return statement for when the two fractions are being added
        if (operator.equals("+")) {
            return (firstFraction.add(secondFraction)).toString();
        }

        // Return statement for when the second fraction is being subtracted from the first
        else if (operator.equals("-")) {
            return (firstFraction.subtract(secondFraction)).toString();
        }

        // Return statement for when the first fraction is getting divided by the second fraction
        else if (operator.equals("/")) {

            // Raising an IllegalArgumentException if user is dividing by zero
            if (secondFraction.getNum() == 0 && secondFraction.getWhole() == 0) {
                // Custom case specific error message 
                System.out.println("CANNOT DIVIDE BY ZERO!");
                // Throwing the exception
                throw new IllegalArgumentException("DIVIDING BY ZERO");
            }

            // Return the answer if no exceptions thrown
            return (firstFraction.divide(secondFraction)).toString();
        }

        // Return statement for when the two fractions are being multiplied
        else if (operator.equals("*")) {
            return (firstFraction.multiply(secondFraction)).toString();
        }

        // Throwing an exception statement if none of the conditions above were met
        else {
            System.out.println("ENSURE THE EXPRESSION IS FORMATTED PROPERLY!");
            throw new IllegalArgumentException();
        }
    }

    /**
     * This function takes in a string, checks if it's valid, finds the operator, 
     * creates two fraction objects, and then executes the operation
     * 
     * @param userString The string that the user inputs
     * @return The method is returning a Fraction.
     */
    public static Fraction calculate(String userString) {
        // Checking if the input is valid using the inputValidator method
        inputValidator(userString);

        int operatorIndex = operatorIndexDetector(userString);
        String operator = userString.substring(operatorIndex, operatorIndex + 1);

        // Creating 2 fraction objects using indexing and the 'fractionAssembly' method 
        Fraction firstFraction = fractionAssembly(userString.substring(0, operatorIndex - 1));
        Fraction secondFraction = fractionAssembly(userString.substring(operatorIndex + 2));

        // Using the operationExecutor method to get the answer of the expression
        String answer = operationExecutor(operator, firstFraction, secondFraction);

        // Returning the answer
        return stringToFraction(answer);
    }
}