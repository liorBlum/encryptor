package Utilities;

import java.io.File;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * A utilities class of methods involving inputs from the user (console)
 */
public class UserInputUtils {
    /**
     * A private constructor to avoid instantiation
     */
    private UserInputUtils() {}
    private final static ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    private final static Scanner reader = new Scanner(System.in);

    /**
     * Get an input from the user (String) and return it only if it is
     * included in the valid inputs list
     * @param validInputs valid inputs list
     * @return input string
     * @throws IllegalArgumentException when input is illegal
     */
    public static String getValidUserInput(Collection<String> validInputs) throws
            IllegalArgumentException {
        String input = reader.nextLine();
        if (validInputs.contains(input)) {
            return input;
        } else {
            throw new IllegalArgumentException(
                    strings.getString("inputErrorMsg"));
        }
    }

    /**
     * Get a file path from the user and return the file
     * which is denoted by that filepath.
     * @return file which is denoted by the given filepath
     * @throws IllegalArgumentException if input is invalid
     */
    public static File getInputFile()
            throws IllegalArgumentException {
        System.out.println(strings.getString("srcPathText"));
        String filePathString = reader.nextLine();
        File file;
        file = new File(filePathString);
        // return a valid file object (file size must be in int range)
        if (file.canRead() && file.length() <= Integer.MAX_VALUE) {
            return file;
        } else {
            throw new IllegalArgumentException(
                    strings.getString("inputErrorMsg"));
        }
    }
}
