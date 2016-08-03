package Algorithms;

import Structure.Menu;
import Utilities.UserInputUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Abstract class for dependent algorithms. These algorithms have
 * at least one independent algorithm as a member.
 */
public abstract class DependentAlgorithm extends Algorithm {
    protected final static ResourceBundle algoClasses =
            ResourceBundle.getBundle("algorithms_classes");
    protected final static ResourceBundle algoNames =
            ResourceBundle.getBundle("algorithms_names");

    /**
     * /**
     * Algorithm protected constructor that is used to set the Algorithm's name
     * @param name Algorithm's name
     */
    protected DependentAlgorithm(String name) {
        super(name);
    }

    /**
     * Update Algorithm member(s) of this dependent algorithm
     * @param reader user input reader
     * @throws IOException when properties file(s) is corrupted
     */
    protected abstract void updateAlgorithmMembers(Scanner reader)
            throws IOException;

    /**
     * Display only independent algorithms found in properties
     * (algorithms that extend Algorithm directly and not DependentAlgorithm)
     * @throws IOException when a class from properties is not found
     * in Algorithms package
     */
    protected void showIndepAlgorithmsSelection() throws IOException{
        Enumeration<String> algoCodes = algoNames.getKeys();
        try {
            // go over all independent algorithms and print them as choices
            while (algoCodes.hasMoreElements()) {
                String algoCode = algoCodes.nextElement();
                Class algoClass = Class.forName(strings.getString("algoPack")
                        + algoClasses.getString(algoCode));
                if (algoClass.getSuperclass() == (Algorithm.class)) {
                    System.out.println("For " + algoNames.getString(algoCode)
                            + " Enter: " + algoCode);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new IOException(strings.getString("badPropFile"));
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
                        Collections.list(algoClasses.getKeys()), reader);
                Class algoClass = Class.forName(strings.getString("algoPack")
                        + algoClasses.getString(algoCode));
                if (algoClass.getSuperclass() == (Algorithm.class)) {
                    // get the algorithm object from the given algoCode
                    String algoClassName = strings.getString("algoPack")
                            + algoClasses.getString(algoCode);
                    return (Algorithm)(
                            Class.forName(algoClassName).newInstance());
                } else {
                    // if input algorithm is dependent, throw an exception
                    // and continue the loop
                    throw new IllegalArgumentException(
                            strings.getString("depAlgoError"));
                }

            } catch (ClassNotFoundException e) {
                System.out.println(strings.getString("algoNotFound"));
            } catch (Exception e) {
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
        try {
            updateAlgorithmMembers(reader);
            return super.encrypt(inputFile, reader);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n"
                    + strings.getString("generalErrorMsg"));
            return 0;
        }
    }

    @Override
    public long decrypt(File inputFile, Scanner reader) {
        try {
            updateAlgorithmMembers(reader);
            return super.decrypt(inputFile, reader);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n"
                    + strings.getString("generalErrorMsg"));
            return 0;
        }
    }
}
