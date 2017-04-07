/**
 * TITLE:              NumberGuesser.java
 * VERSION:            2017-01-09
 * AUTHOR:             Setti Villamor
 * STUDENT ID:         100258354
 * COURSE & SECTION:   1181-001
 * DESCRIPTION:    Correctly guesses the number the user is thinking about after a series of mathematical operations depending on the difficulty chosen.
 ***/

import javax.swing.JOptionPane;

public class NumberGuesser {

    //Arbitrary numbers used for equations later
    public static final int EASY_NUM = 10;
    public static final int NORMAL_NUM = 16;

    public static void main(String[] args) {
        int playAgain = 0;

        toExit: //breaks whenever the user chooses close (x) in an input dialog
        do {
            int difficulty = promptDifficulty(); //0 = easy, 1 = normal, 2 = hard
            String in = ""; //user input of final answer
            double finalAns, originalNum; //finalAns parses in into double;

            switch (difficulty) {
                case 0: //Easy
                    sayThinkNum(); //instructs user to think of a num
                    sayMultiplyEasyNum(); //easy equation

                    //Prompts user for the final answer
                    try {
                        in = promptAns();

                        //If user chooses close (x)
                    } catch (NullPointerException f) {
                        sayBye();
                        playAgain = 1;
                        break toExit;
                    }

                    //Calculates original number and prints it
                    finalAns = Double.parseDouble(in);
                    originalNum = divideEasyNum(finalAns);
                    sayOriginalNum(originalNum);

                    playAgain = promptPlayAgain();
                    break;
                case 1: //normal
                    sayThinkNum();

                    //Equations for normal difficulty
                    sayMultiplyEasyNum();
                    sayAddNormalNum();

                    //Prompts user for the final answer
                    try {
                        in = promptAns();
                    } catch (NullPointerException f) {
                        sayBye();
                        playAgain = 1;
                        break toExit;
                    }

                    //Calculates the original number
                    finalAns = Double.parseDouble(in);
                    originalNum = subtractNormalNum(finalAns);
                    originalNum = divideEasyNum(originalNum);
                    sayOriginalNum(originalNum);

                    playAgain = promptPlayAgain();
                    break;
                case 2: //hard
                    sayThinkNum();

                    //Equations for hard difficulty
                    sayMultiplyEasyNum();
                    sayAddNormalNum();
                    sayToSquare();

                    //Prompts user for the final answer
                    try {
                        in = promptAns("hard");
                        /* NOTE: Includes an error message for negative numbers
                        * because square numbers can never be negative.*/
                    } catch (NullPointerException f) {
                        sayBye();
                        playAgain = 1;
                        break toExit;
                    }

                    //Calculates the original number
                    finalAns = Double.parseDouble(in);
                    originalNum = Math.sqrt(finalAns);
                    originalNum = subtractNormalNum(originalNum);
                    originalNum = divideEasyNum(originalNum);
                    sayOriginalNum(originalNum);

                    playAgain = promptPlayAgain();
                    break;
                default: //If user clicks close (x)
                    sayBye();
                    playAgain = 1;
            }
        } while (playAgain == 0);
    }

    /**
     * Prompts user to select difficulty.
     * @return An int representing user's difficulty choice: 0 = Easy, 1 = Normal, 2 = Hard.
     */
    public static int promptDifficulty() {
        Object[] diffArr = {"Easy", "Normal", "Hard"};
        int difficulty = JOptionPane.showOptionDialog(
                null,
                "I can read your mind! Don't believe me? Well, let's test it out.\n\nBut before that, choose a difficulty.",
                "Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                diffArr,
                null);
        return difficulty;
    }

    /**
     * Instructs user to think of a number.
     */
    public static void sayThinkNum() {
        JOptionPane.showMessageDialog(
                null,
                "To start, think of a number, any number. When you're ready, click Ok.",
                "Instruction",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prompts user to square a number.
     */
    public static void sayToSquare() {
        JOptionPane.showMessageDialog(
                null,
                "Now, square that number.",
                "Instruction",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prompts user to add NORMAL_NUM to a number.
     */
    public static void sayAddNormalNum() {
        JOptionPane.showMessageDialog(
                null,
                "Then, add " + NORMAL_NUM + " to that number.",
                "Instruction",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prompts user to multiply EASY_NUM to a number.
     */
    public static void sayMultiplyEasyNum() {
        JOptionPane.showMessageDialog(
                null,
                "Now, multiply " + EASY_NUM + " to that number.",
                "Instruction",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prompts user to enter the final answer, and continues to ask for it while it is invalid.
     * @return A String containing the user's valid answer.
     */
    public static String promptAns() {
        String in = "";
        do {
            in = JOptionPane.showInputDialog(
                    null,
                    "Finally, please enter the final answer and I shall guess what your original number was.",
                    "Enter Final Answer",
                    JOptionPane.INFORMATION_MESSAGE);
        } while (!isValidAns(in));
        return in;
    }

    /**
     * Intended only if the difficulty is hard. Prompts the user to enter the final answer, and continues to ask for it while it is invalid.
     * @param ifHard A String "hard" adds an error message for negative inputs.
     * @return A string containing the user's valid answer.
     */
    public static String promptAns(String ifHard) {
        String in = "";
        do {
            in = JOptionPane.showInputDialog(
                    null,
                    "Finally, please enter the final answer and I shall guess what your original number was.",
                    "Enter Final Answer",
                    JOptionPane.INFORMATION_MESSAGE);
        } while (!isValidAns(in, "hard"));
        return in;
    }

    /**
     * Attempts to parse a String into double and produces error messages if it can't.
     * @param in A String input to be validated.
     * @return A boolean true if the input is a valid double, false otherwise.
     */
    public static boolean isValidAns(String in) {
        //If user entered an empty string
        if (in.equals("")) {
            JOptionPane.showMessageDialog(
                    null,
                    "ERROR: No input. Please try again.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double ans = Double.parseDouble(in);

            //If user entered a word
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "ERROR: Invalid input. Please try again and make sure NOT to use words.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //If user enters a valid number
        return true;
    }

    public static boolean isValidAns(String in, String ifHard) {
        //If user entered an empty string
        if (in.equals("")) {
            JOptionPane.showMessageDialog(
                    null,
                    "ERROR: No input. Please try again.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double ans = Double.parseDouble(in);

            //If user entered a negative number
            if (ans < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "ERROR: Invalid input. Squaring a number never produces a negative. I will repeat the instructions again.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                sayMultiplyEasyNum();
                sayAddNormalNum();
                sayToSquare();
                return false;
            }

            //If user entered a word
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "ERROR: Invalid input. Please try again and make sure NOT to use words.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        //If user enters a valid number
        return true;
    }

    /**
     * Divides a number by the constant EASY_NUM.
     * @param num A double containing the number to be divided.
     * @return A double containing the num divided by EASY_NUM.
     */
    public static double divideEasyNum(double num) {
        return num / EASY_NUM;
    }

    /**
     * Subtracts a number by the arbitrary constant NORMAL_NUM.
     * @param num A double containing the number to be subtracted.
     * @return A double containing the num minus NORMAL_NUM.
     */
    public static double subtractNormalNum(double num) {
        return num - NORMAL_NUM;
    }

    /**
     * Prints the computer's guess of what the original number is.
     * @param num A double number containing the computer's guess.
     */
    public static void sayOriginalNum(double num) {
        JOptionPane.showMessageDialog(
                null,
                String.format("Hmm... your original number must be %.2f!", num),
                "Mystery Number",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static int promptPlayAgain() {
        int ans = JOptionPane.showConfirmDialog(
                null,
                "Do you want to play again?",
                "Play Again",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return ans;
    }

    /**
     * Tells user goodbye. Signifies that the program has ended.
     */
    public static void sayBye() {
        JOptionPane.showMessageDialog(
                null,
                "Thanks for playing! \n\nA program by mrvillamor.",
                "Exit",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

