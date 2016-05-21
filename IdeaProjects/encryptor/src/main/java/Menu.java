import java.util.Scanner;

/**
 * Menu class with Singleton pattern
 */
public class Menu {
    private static Menu instance = null;
    private char encOption = 'e';
    private char decOption = 'd';
    private String menuText = "For Encryption - enter " + encOption + '\n'
            + "For Decryption - enter " + decOption;
    private String errorMsg = "Invalid Input!";

    /**
     * Private constructor which avoids instantiation
     */
    private Menu() { }

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
    public void showMenu() {
        Scanner reader = new Scanner(System.in);
        boolean validOutput = false;
        String chosenOption = "";
        while (!validOutput) {
            System.out.println(menuText);
            chosenOption = reader.nextLine();
            /*
            end the loop only when a valid answer is entered
             */
            if (chosenOption.length() == 1
                    && (chosenOption.charAt(0) == encOption
                        || chosenOption.charAt(0) == decOption)) {
                validOutput = true;
            }
            else {
                System.out.println(errorMsg);
            }
        }
        //if the user entered 'e', go to Encryption
        if (chosenOption.charAt(0) == encOption) {

        }
        // Otherwise, go to Decryption
        else {

        }
    }

}
