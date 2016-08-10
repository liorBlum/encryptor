package Structure;

import Algorithms.Algorithm;
import Algorithms.DoubleAlgo;
import Utilities.JAXBCustomEventHandler;
import Utilities.JAXBUtils;
import Utilities.UserInputUtils;
import com.google.inject.AbstractModule;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.*;

/**
 * A class that injects the appropriate algorithm instance to AlgoExecutor
 */
public class AlgoInjector extends AbstractModule{
    private final ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    private final ResourceBundle algoNames =
            ResourceBundle.getBundle("algorithms_names");
    private final ResourceBundle algoClasses =
            ResourceBundle.getBundle("algorithms_classes");

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
     * using an imported algorithm or choosing algorithm
     * manually (with a console menu)
     * @return desired algorithm
     */
    private Algorithm chooseAlgorithm() {
        Scanner reader = new Scanner(System.in);
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
                    return  (Algorithm) JAXBUtils.unmarshalObject(defXmlFile,
                            new File(strings.getString("algoSchemaFile")),
                            new JAXBCustomEventHandler(),
                            new Class[]{Algorithm.class});
                } else if (chosenOption.equals(strings.getString("importOpt"))) {
                    // use imported algorithm
                    System.out.println(strings.getString("CfgFilePathMsg"));
                    File algoCfgFile = UserInputUtils.getInputFile(reader);
                    return (Algorithm) JAXBUtils.unmarshalObject(algoCfgFile,
                            new File(strings.getString("algoSchemaFile")),
                            new JAXBCustomEventHandler(),
                            new Class[]{Algorithm.class});
                } else {
                    // choose algorithm manually
                    showAlgorithmsSelection();
                    String algoCode = UserInputUtils.getValidUserInput(
                            Collections.list(algoClasses.getKeys()), reader);
                    // get the algorithm object from the given algoCode
                    String algoClassName = strings.getString("algoPack")
                            + algoClasses.getString(algoCode);
                    return (Algorithm)(
                            Class.forName(algoClassName).newInstance());
                }
            }  catch (JAXBException e) {
                System.out.println(e.getMessage());
            }  catch (ClassNotFoundException e) {
                System.out.println(strings.getString("algoNotFound"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    @Override
    protected void configure() {
        bind(Algorithm.class).toInstance(chooseAlgorithm());
    }
}