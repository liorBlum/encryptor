package Structure;

import Algorithms.*;
import Utilities.JAXBCustomEventHandler;
import Utilities.JAXBUtils;
import Utilities.UserInputUtils;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.xml.sax.SAXException;


import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.*;

/**
 * Menu class with Singleton pattern
 */
@Singleton
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
     * @param executor AlgoExecutor tht holds the desired algorithm
     * @param actionCode action char('e'/'d')
     * @param file given file
     * @param reader user input reader
     */
    private void executeAlgorithm(AlgoExecutor executor, String actionCode,
                                  File file, Scanner reader) {
        System.out.println("Executing " + executor.getAlgoName() + "...");
        double elapsedTime = 0.0;
        if (actionCode.equals(strings.getString("encOption"))) {
            // execute action and measure the time it took
            elapsedTime = executor.encrypt(file, reader);
        } else if (actionCode.equals(strings.getString("decOption"))) {
            elapsedTime = executor.decrypt(file, reader);
        }
        if (elapsedTime == 0) {
            System.out.println(strings.getString("generalErrorMsg"));
        } else {
            // print elapsed time in milliseconds
            System.out.println(strings.getString("elapsedTimeTxt") + " "
                    + elapsedTime + " milliseconds\n");
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
                    JAXBUtils.marshalObject(algorithm, algoCfgFile,
                            new File(strings.getString("algoSchemaFile")),
                            new JAXBCustomEventHandler(),
                            new Class[]{Algorithm.class});
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
        AlgoExecutor executor;
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
                // get AlgoExecutor injected with the desired Algorithm
                Injector injector = Guice.createInjector(new AlgoInjector());
                executor = injector.getInstance(AlgoExecutor.class);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        //if the user entered 'e', execute Encryption. Otherwise, decryption
        executeAlgorithm(executor, chosenAction, inputFile, reader);
        exportAlgorithm(executor.getAlgorithm(), reader);
    }
}
