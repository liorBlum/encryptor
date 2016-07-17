package Structure;

import Algorithms.Algorithm;
import Algorithms.CaesarAlgo;
import Algorithms.MwoAlgo;
import Algorithms.XORAlgo;
import java.io.File;
import java.util.*;

/**
 * Menu class with Singleton pattern
 */
public class Menu {
    private static Menu instance = null;
    private final char encOption = 'e';
    private final char decOption = 'd';
    private final char exOption = 'x';
    private HashMap<String,Algorithm> algosMap;
    private final ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    private final ResourceBundle algosCodes =
            ResourceBundle.getBundle("algorithms");

    /**
     * Private constructor which avoids instantiation
     * and initializes encryptors map.
     */
    private Menu() {
        algosMap = new HashMap<String, Algorithm>();
        algosMap.put(algosCodes.getString("Caesar Algorithm"),
                new CaesarAlgo());
        algosMap.put(algosCodes.getString("XOR Algorithm"),
                new XORAlgo());
        algosMap.put(algosCodes.getString("Multiplication Algorithm"),
                new MwoAlgo());
    }

    /**
     * Execute encryption/decryption on a given file and print elapsed time.
     * @param actionChar action char('e'/'d')
     * @param file given file
     * @param algoCode String code of the requested encryption algorithm
     */
    private void executeAction(char actionChar, File file, String algoCode) {
        Algorithm algorithm = algosMap.get(algoCode);
        // add an observer in order to notify
        // the user when action started\ended
        algorithm.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });
        long elapsedTime = 0;
        if (actionChar == encOption) {
            // execute action and measure the time it took
            elapsedTime = algorithm.encrypt(file);
        } else {
            elapsedTime = algorithm.decrypt(file);
        }
        System.out.println(strings.getString("elapsedTimeTxt") + " "
                + (float)elapsedTime/1000000 + " milliseconds");
    }

    /**
     * Display all algorithms found in properties
     */
    private void showAlgorithmsSelection() {
        System.out.println(strings.getString("algoMsg"));
        Enumeration<String> algorithms = algosCodes.getKeys();
        // go over all algorithms
        while (algorithms.hasMoreElements()) {
            String algo = algorithms.nextElement();
            System.out.println("For " + algo + " Enter: "
                    + algosCodes.getString(algo));
        }
    }

    /**
     * Get an encryption algorithm from the user
     * @param reader input reader
     * @return String code of the requested algorithm
     * @throws IllegalArgumentException when input is illegal
     */
    private String getInputAlgorithm(Scanner reader) throws
            IllegalArgumentException {
        showAlgorithmsSelection();
        String input = reader.nextLine();
        if (algosMap.containsKey(input)) {
            return input;
        } else {
            throw new IllegalArgumentException(
                    strings.getString("inputErrorMsg"));
        }
    }
    /**
     * Get action from the user input (enc/dec/exit)
     * @param reader input reader
     * @return action char
     * @throws IllegalArgumentException if input is invalid
     */
    private char getInputAction(Scanner reader) throws
            IllegalArgumentException {
        System.out.println(strings.getString("menuText"));
        String input = reader.nextLine();
        if (input.length() == 1) {
            char action = input.charAt(0);
            if (action == encOption || action == decOption
                    || action == exOption) {
                return action;
            } else {
                throw new IllegalArgumentException(
                        strings.getString("inputErrorMsg"));
            }
        } else {
            throw new IllegalArgumentException(
                    strings.getString("inputErrorMsg"));
        }
    }
    /**
     * Get a file path from the user and return the file
     * which is denoted by that filepath.
     * @param reader command line input reader
     * @return file which is denoted by the given filepath
     * @throws IllegalArgumentException if input is invalid
     */
    private File getInputFile(Scanner reader)
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
                    this.strings.getString("inputErrorMsg"));
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
        char chosenAction;
        File inputFile;
        String algoCode;
        while (true) {
            /*
            end the loop only when valid input is entered
             or when 'x' is entered
             */
            try {
                chosenAction = getInputAction(reader);
                if (chosenAction == exOption) {
                    return;
                }
                inputFile = getInputFile(reader);
                algoCode = getInputAlgorithm(reader);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        //if the user entered 'e', execute Encryption. Otherwise, decryption
        executeAction(chosenAction, inputFile, algoCode);
    }
}
