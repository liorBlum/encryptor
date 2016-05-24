
import java.io.File;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Menu class with Singleton pattern
 */
public class Menu {
    private static Menu instance = null;
    private char encOption = 'e';
    private char decOption = 'd';
    private ResourceBundle strings = ResourceBundle.getBundle("strings");

    /**
     * Private constructor which avoids instantiation
     */
    private Menu() { }

    /**
     * Execute encryption/decryption on a given file
     * @param actionChar action char('e'/'d')
     * @param file given file
     */
    private void executeAction(char actionChar, File file) {
        if (actionChar == encOption) {
            System.out.println(strings.getString("encExecMsg")
                    + " " + file.getName());
        } else {
            System.out.println(strings.getString("decExecMsg")
                    + " " + file.getName());
        }
    }

    /**
     * Get a file path from the user and return the file
     * which is denoted by that filepath.
     * @param reader command line input reader
     * @return file which is denoted by the given filepath
     */
    private File getFilePath(Scanner reader) {
        String filePathString;
        File file;
        while (true) {
            System.out.println(strings.getString("srcPathText"));
            filePathString = reader.nextLine();
            file = new File(filePathString);
            // return a valid file object
            if (file.canRead()) {
                return file;
            } else {
                System.out.println(strings.getString("inputErrorMsg"));
            }
        }
    }
    /**
     * Get the single Menu object
     * @return Menu object
     */
    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    /**
     * Show a menu to the user which allows him to choose
     * between encryption and decryption
     */
    public void showMenu() {
        Scanner reader = new Scanner(System.in);
        boolean validOutput = false;
        String chosenOption = "";
        while (!validOutput) {
            System.out.println(strings.getString("menuText"));
            chosenOption = reader.nextLine();
            /*
            end the loop only when a valid answer is entered
             */
            if (chosenOption.length() == 1
                    && (chosenOption.charAt(0) == encOption
                        || chosenOption.charAt(0) == decOption)) {
                validOutput = true;
            } else {
                System.out.println(strings.getString("inputErrorMsg"));
            }
        }
        //if the user entered 'e', execute Encryption. Otherwise, decryption
        executeAction(chosenOption.charAt(0), getFilePath(reader));
    }


}
