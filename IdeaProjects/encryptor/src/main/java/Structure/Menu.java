package Structure;

import Algorithms.*;
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
    private final ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    private final ResourceBundle independentAlgosCodes =
            ResourceBundle.getBundle("indep_algorithms");
    private final ResourceBundle dependentAlgosCodes =
            ResourceBundle.getBundle("dep_algorithms");
    public static HashMap<String,Algorithm> indepAlgosMap
            = new HashMap<String, Algorithm>();
    public static HashMap<String,Algorithm> depAlgosMap
            = new HashMap<String, Algorithm>();
    public static HashMap<String,Algorithm> allAlgosMap
            = new HashMap<String, Algorithm>();

    /**
     * Private constructor which avoids instantiation
     * and initializes algorithms map.
     */
    private Menu() {
        indepAlgosMap.put(independentAlgosCodes.getString("Caesar Algorithm"),
                new CaesarAlgo());
        indepAlgosMap.put(independentAlgosCodes.getString("XOR Algorithm"),
                new XORAlgo());
        indepAlgosMap.put(independentAlgosCodes.getString("Multiplication Algorithm"),
                new MwoAlgo());
        depAlgosMap.put(dependentAlgosCodes.getString("Double Algorithm"),
                new DoubleAlgo());
        allAlgosMap.putAll(indepAlgosMap);
        allAlgosMap.putAll(depAlgosMap);
    }

    /**
     * Execute encryption/decryption on a given file and print elapsed time.
     * Expects valid parameters.
     * @param actionCode action char('e'/'d')
     * @param file given file
     * @param algoCode String code of the requested encryption algorithm
     */
    private void executeAlgorithm(String actionCode, File file,
                                        String algoCode) {
        Algorithm algorithm = allAlgosMap.get(algoCode);
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
        Enumeration<String> indepAlgorithms = independentAlgosCodes.getKeys();
        Enumeration<String> depAlgorithms = dependentAlgosCodes.getKeys();
        // go over all algorithms and print them as choices
        while (indepAlgorithms.hasMoreElements()) {
            String algo = indepAlgorithms.nextElement();
            System.out.println("For " + algo + " Enter: "
                    + independentAlgosCodes.getString(algo));
        }
        while (depAlgorithms.hasMoreElements()) {
            String algo = depAlgorithms.nextElement();
            System.out.println("For " + algo + " Enter: "
                    + dependentAlgosCodes.getString(algo));
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
                chosenAction = UserInputUtils.getValidUserInput(
                        Arrays.asList(encOption, decOption, exOption));
                if (chosenAction.equals(exOption)) {
                    return;
                }
                // get file path and algorithm from the user
                inputFile = UserInputUtils.getInputFile();
                showAlgorithmsSelection();
                algoCode = UserInputUtils.getValidUserInput(allAlgosMap.keySet());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        //if the user entered 'e', execute Encryption. Otherwise, decryption
        executeAlgorithm(chosenAction, inputFile, algoCode);
    }
}
