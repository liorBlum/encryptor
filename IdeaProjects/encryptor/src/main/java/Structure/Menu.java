package Structure;

import Algorithms.*;
import Utilities.JAXBUtils;
import Utilities.UserInputUtils;
import org.xml.sax.SAXException;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.*;

/**
 * Menu class with Singleton pattern
 */
public class Menu {
    private static Menu instance = null;
    private final ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    private final ResourceBundle algoClasses =
            ResourceBundle.getBundle("algorithms_classes");
    private final ResourceBundle algoNames =
            ResourceBundle.getBundle("algorithms_names");

    /**
     * Private constructor which avoids instantiation
     */
    private Menu() {}

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
     * Execute encryption/decryption on a given file and print elapsed time
     * using a given algorithm.
     * @param actionCode action char('e'/'d')
     * @param file given file
     * @param algorithm Desired encryption algorithm
     * @param reader user input reader
     */
    private void executeAlgorithm(Algorithm algorithm, String actionCode,
                                  File file, Scanner reader) {
        // add an observer in order to notify
        // the user when action started\ended
        algorithm.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });
        System.out.println("Executing " + algorithm.getName() + "...");
        long elapsedTime = 0;
        if (actionCode.equals(strings.getString("encOption"))) {
            // execute action and measure the time it took
            elapsedTime = algorithm.encrypt(file, reader);
        } else if (actionCode.equals(strings.getString("decOption"))) {
            elapsedTime = algorithm.decrypt(file, reader);
        }
        if (elapsedTime == 0) {
            System.out.println(strings.getString("generalErrorMsg"));
        } else {
            System.out.println(strings.getString("elapsedTimeTxt") + " "
                    + (float) elapsedTime / 1000000 + " milliseconds\n");
        }
    }

    /**
     * Display all algorithms found in properties
     */
    private void showAlgorithmsSelection() {
        System.out.println(strings.getString("algoMsg"));
        Enumeration<String> algorithmCodes = algoNames.getKeys();
        // go over all algorithms and print them as choices
        while (algorithmCodes.hasMoreElements()) {
            String algoCode = algorithmCodes.nextElement();
            System.out.println("For " + algoNames.getString(algoCode)
                    + " Enter: " + algoCode);
        }
    }

    /**
     * Let the user choose algorithm in 3 possible methods:
     * using default algorithm (loaded from default.xml),
     * using an imported algorithm
     * @param reader user input reader
     * @return desired algorithm
     */
    private Algorithm chooseAlgorithm(Scanner reader) {
        File defXmlFile = new File(strings.getString("defAlgoDefFile"));
        String chosenOption;
        System.out.println(strings.getString("algoChooseMsg"));
        while (true) {
            try {
                // present the 3 options to the user
                System.out.println(strings.getString("menuAlgoTxt"));
                chosenOption = UserInputUtils.getValidUserInput(Arrays.asList(
                        strings.getString("defOpt"),
                        strings.getString("importOpt"),
                        strings.getString("manuOpt")), reader
                );
                if (chosenOption.equals(strings.getString("defOpt"))) {
                    // use default algorithm
                    return JAXBUtils.unmarshalAlgorithm(defXmlFile);
                } else if (chosenOption.equals(strings.getString("importOpt"))) {
                    // use imported algorithm
                    System.out.println(strings.getString("CfgFilePathMsg"));
                    File algoCfgFile = UserInputUtils.getInputFile(reader);
                    return JAXBUtils.unmarshalAlgorithm(algoCfgFile);
                } else {
                    // choose algorithm manually
                    showAlgorithmsSelection();
                    String algoCode = UserInputUtils.getValidUserInput(
                            Collections.list(algoClasses.getKeys()), reader);
                    // get the algorithm object from the given algoCode
                    String algoClassName = strings.getString("algoPack")
                            + algoClasses.getString(algoCode);
                    return (Algorithm)(Class.forName(algoClassName).newInstance());
                }
            }  catch (JAXBException e) {
                System.out.println(strings.getString("algoDefError"));
            }  catch (ClassNotFoundException e) {
                System.out.println(strings.getString("algoNotFound"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Export (marshal) the algorithm into an XML configuration file if
     * the user wants to.
     * @param algorithm given algorithm object
     * @param reader user input reader
     */
    private void exportAlgorithm(Algorithm algorithm, Scanner reader) {
        String chosenOption;
        while (true) {
            try {
                System.out.println(strings.getString("exportCfgMsg"));
                chosenOption = UserInputUtils.getValidUserInput(Arrays.asList(
                        strings.getString("yesOpt"),
                        strings.getString("noOpt")), reader);
                if (chosenOption.equals(strings.getString("yesOpt"))) {
                    // marshal the algorithm into the given file path
                    System.out.println(strings.getString("CfgFilePathMsg"));
                    File algoCfgFile = new File(reader.nextLine());
                    JAXBUtils.marshalAlgorithm(algorithm, algoCfgFile);
                    System.out.println(strings.getString("exportSuccessMsg"));
                    break;
                } else if (chosenOption.equals(strings.getString("noOpt"))) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (JAXBException e) {
                System.out.println(strings.getString("algoDefError"));
            } catch (SAXException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Show a menu to the user which allows him to choose
     * between encryption and decryption
     */
    public void showMenu() {
        Scanner reader = new Scanner(System.in);
        String chosenAction;
        File inputFile;
        Algorithm algorithm;
        while (true) {
            /*
            end the loop only when valid input is entered
             or when "x" is entered
             */
            try {
                // choose encryption/decryption
                System.out.println(strings.getString("menuActionText"));
                chosenAction = UserInputUtils.getValidUserInput(Arrays.asList(
                        strings.getString("encOption"),
                        strings.getString("decOption"),
                        strings.getString("exOption")), reader);
                if (chosenAction.equals(strings.getString("exOption"))) {
                    return;
                }
                // get file path
                System.out.println(strings.getString("srcPathText"));
                inputFile = UserInputUtils.getInputFile(reader);
                // choose algorithm
                algorithm = chooseAlgorithm(reader);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        //if the user entered 'e', execute Encryption. Otherwise, decryption
        executeAlgorithm(algorithm, chosenAction, inputFile, reader);
        exportAlgorithm(algorithm, reader);
    }
}
