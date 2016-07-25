package Algorithms;

import Structure.Menu;
import Utilities.UserInputUtils;

import java.io.File;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Abstract class for dependent algorithms. These algorithms have
 * at least one independent algorithm as a member.
 */
public abstract class DependentAlgorithm extends Algorithm {
    protected final static ResourceBundle independentAlgosCodes =
            ResourceBundle.getBundle("indep_algorithms");
    /**
     * Update Algorithm member(s) of this dependent algorithm
     */
    protected abstract void updateAlgorithmMembers(Scanner reader);

    /**
     * Display all independent algorithms found in properties
     */
    protected void showIndepAlgorithmsSelection() {
        Enumeration<String> indepAlgorithms = independentAlgosCodes.getKeys();
        // go over all algorithms and print them as choices
        while (indepAlgorithms.hasMoreElements()) {
            String algo = indepAlgorithms.nextElement();
            System.out.println("For " + algo + " Enter: "
                    + independentAlgosCodes.getString(algo));
        }
    }

    /**
     * Get valid algorithm from the user input. The algorithm
     * must be 'independent algorithm', an algorithm which does not
     * depend on any other algorithm (in order to avoid infinite loops
     * of algorithms calls
     * @return independent algorithm (object)
     */
    protected Algorithm getIndepAlgorithmFromUser(Scanner reader) {
        while (true) {
            try {
                String algoCode = UserInputUtils.getValidUserInput(
                        Menu.indepAlgosMap.keySet(), reader);
                return Menu.indepAlgosMap.get(algoCode);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    protected abstract Key generateKey();

    @Override
    protected abstract Key getDecryptionKey(Key encryptionKey)
            throws Exception;

    @Override
    public long encrypt(File inputFile, Scanner reader) {
        updateAlgorithmMembers(reader);
        return super.encrypt(inputFile, reader);
    }

    @Override
    public long decrypt(File inputFile, Scanner reader) {
        updateAlgorithmMembers(reader);
        return super.decrypt(inputFile, reader);
    }
}
