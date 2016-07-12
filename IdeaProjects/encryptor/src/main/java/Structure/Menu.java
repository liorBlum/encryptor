package Structure;

import Decryptors.Decryptor;
import Encryptors.CaesarEncryptor;
import Encryptors.Encryptor;
import Encryptors.MwoEncryptor;
import Encryptors.XOREncryptor;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Menu class with Singleton pattern
 */
public class Menu {
    private static Menu instance = null;
    private final char encOption = 'e';
    private final char decOption = 'd';
    private final char exOption = 'x';
    private HashMap<String,Encryptor> encryptorsMap;
    private final ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    private final ResourceBundle algosCodes =
            ResourceBundle.getBundle("algorithms");

    /**
     * Private constructor which avoids instantiation
     * and initializes encryptors map.
     */
    private Menu() {
        encryptorsMap = new HashMap<String, Encryptor>();
        encryptorsMap.put(algosCodes.getString("Caesar Algorithm"),
                new CaesarEncryptor());
        encryptorsMap.put(algosCodes.getString("XOR Algorithm"),
                new XOREncryptor());
        encryptorsMap.put(algosCodes.getString("Multiplication Algorithm"),
                new MwoEncryptor());
    }

    /**
     * Execute encryption/decryption on a given file
     * @param actionChar action char('e'/'d')
     * @param file given file
     * @param algoCode String code of the requested encryption algorithm
     */
    private void executeAction(char actionChar, File file, String algoCode) {
        Encryptor algorithm = encryptorsMap.get(algoCode);
        if (actionChar == encOption) {
            algorithm.encrypt(file);
        } else {
            algorithm.getEquivalentDecryptor().decrypt(file);
        }
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
     * @param reader
     * @return
     * @throws IllegalArgumentException
     */
    private String getInputAlgorithm(Scanner reader) throws
            IllegalArgumentException {
        showAlgorithmsSelection();
        String input = reader.nextLine();
        if (encryptorsMap.containsKey(input)) {
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
                    strings.getString("inputErrorMsg"));
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
             */
            try {
                inputFile = getInputFile(reader);
                chosenAction = getInputAction(reader);
                algoCode = getInputAlgorithm(reader);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } finally {
                //reader.close();
            }
        }
        //if the user entered 'e', execute Encryption. Otherwise, decryption
        executeAction(chosenAction, inputFile, algoCode);
    }


}
