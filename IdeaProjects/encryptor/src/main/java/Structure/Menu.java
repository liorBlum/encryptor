package Structure;

import Algorithms.Algorithm;
import Algorithms.CaesarAlgo;
import Algorithms.MwoAlgo;
import Algorithms.XORAlgo;
import Utilities.UserInputUtils;

import java.io.File;
import java.util.*;

/**
 * Menu class with Singleton pattern
 */
public class Menu {
    private static Menu instance = null;
    private final String encOption = "e";
    private final String decOption = "d";
    private final String exOption = "x";
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
     * @param actionCode action char('e'/'d')
     * @param file given file
     * @param algoCode String code of the requested encryption algorithm
     */
    private void executeAction(String actionCode, File file, String algoCode) {
        Algorithm algorithm = algosMap.get(algoCode);
        // add an observer in order to notify
        // the user when action started\ended
        algorithm.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });
        long elapsedTime;
        if (actionCode.equals(encOption)) {
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
        String chosenAction;
        File inputFile;
        String algoCode;
        while (true) {
            /*
            end the loop only when valid input is entered
             or when "x" is entered
             */
            try {
                System.out.println(strings.getString("menuText"));
                chosenAction = UserInputUtils.getInputFromList(
                        Arrays.asList(encOption, decOption, exOption));
                if (chosenAction.equals(exOption)) {
                    return;
                }
                // get file path and algorithm from the user
                inputFile = UserInputUtils.getInputFile();
                showAlgorithmsSelection();
                algoCode = UserInputUtils.getInputFromList(algosMap.keySet());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        //if the user entered 'e', execute Encryption. Otherwise, decryption
        executeAction(chosenAction, inputFile, algoCode);
    }
}
