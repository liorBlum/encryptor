package Algorithms;

import Logs.DirectoryReport;
import Logs.FileReport;
import Utilities.FileModifierUtils;
import Utilities.JAXBUtils;
import Utilities.SerializationUtils;
import Utilities.UserInputUtils;
import lombok.Getter;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Abstract class for algorithms
 *
 * ALL ALGORITHMS MUST BE IN "XmlSeeAlso" ANNOTATION
 */
@XmlSeeAlso({CaesarAlgo.class, DoubleAlgo.class, XORAlgo.class, MwoAlgo.class,
            ReverseAlgo.class,SplitAlgo.class})
public abstract class Algorithm extends Observable {
    /**
     * Get the Algorithm's name
     * @return algorithm's name
     */
    @Getter protected String name = "";
    private String syncOpt = "s";
    private String asyncOpt = "a";
    protected final static ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    protected Random randomizer = new Random();
    private final static Logger logger = Logger.getLogger(Algorithm.class);
    private final static String ls = System.getProperty("line.separator");

    /**
     * Algorithm protected constructor that is used to set the Algorithm's name
     * @param name Algorithm's name
     */
    protected Algorithm(String name) {
        this.name = name;
    }

    /*default no-arg constructor for algorithms with no specific name*/
    public Algorithm() {}
    /**
     * Get the file path of the encryption key from the user input
     * @param reader System.in scanner
     * @return the decryption key
     * @throws IOException when I/O error occurs
     */
    protected Key getInputKey(Scanner reader) throws
            Exception {
        System.out.println(strings.getString("keyInputMsg"));
        return ((Key)SerializationUtils.deserializeObject(
                UserInputUtils.getInputFile(reader)));
    }

    /**
     * Generate a random encryption key between -128 and 127
     * @return encryption key
     */
     protected Key generateKey() {
        // randomize a key between -128 and 127 (1 byte) and return it
        byte key = (byte)(randomizer.nextInt(2*Byte.MAX_VALUE + 2)
                + Byte.MIN_VALUE);
        return new Key(key);
    }

    /**
     * Compute the decryption key using its respective encryption key.
     * By default, encryption key == decryption key
     * @param encryptionKey encryption key
     * @return decryption key
     * @throws IOException when key is invalid
     */
    protected Key getDecryptionKey(Key encryptionKey) throws Exception {
        return encryptionKey;
    }

    /**
     * Encrypt a byte with any encryption algorithm.
     * @param b input byte
     * @param idx byte's index in file
     *@param key encryption key  @return encrypted byte.
     * @throws IOException when key is invalid
     */
    protected abstract byte encryptByte(byte b, int idx, Key key)
            throws IOException;

    /**
     * Decrypt a byte with any encryption algorithm.
     * @param b input byte
     * @param idx byte's index in file
     * @param key decryption key
     * @return decrypted byte.
     * @throws IOException when key is invalid
     */
    protected abstract byte decryptByte(byte b, int idx, Key key)
            throws IOException;

    /**
     * Encrypt/Decrypt a single normal file (not directory)
     * @param file source file
     * @param key key
     * @param destinationFilePath filepath of the outcome
     * @param actionCode "e" for encryption / "d" for decryption
     * @return how much the process took (in milliseconds)
     * @throws Exception when I/O error occurs/when unexpected error occurs
     */
    protected double execAlgoOnFile(File file, Key key, String destinationFilePath,
                                  String actionCode) throws Exception {
        try {
            // log the start of the operation
            logger.info(strings.getString("operStartMsg")
                    + " " + file.getName());
            long startTime = System.nanoTime();
            // initialize a byte array to contain processed bytes
            int fileLength = (int) file.length();
            byte[] processedBytes = new byte[fileLength];
            // read the file and process it byte-by-byte.
            FileModifierUtils.readBytesFromFile(processedBytes, file);
            if (actionCode.equals(strings.getString("encOption"))) {
                for (int i = 0; i < fileLength; i++) {
                    processedBytes[i] = encryptByte(processedBytes[i], i, key);
                }
            } else if (actionCode.equals(strings.getString("decOption"))) {
                for (int i = 0; i < fileLength; i++) {
                    processedBytes[i] = decryptByte(processedBytes[i], i, key);
                }
            } else {
                throw new IllegalArgumentException();
            }
            // write the processed bytes to a new file
            File processedFile =
                    FileModifierUtils.createNewFileInPath(destinationFilePath);
            FileModifierUtils.writeBytesToFile(processedBytes, processedFile);
            // measure time and convert it from nanoseconds to milliseconds
            double elapsedTime = (double)(System.nanoTime() - startTime) / 1000000;
            // write success info in log file
            logger.info(strings.getString("operEndMsg")
                    + " " + file.getName()
                    + " in " + elapsedTime
                    + "milliseconds");
            return elapsedTime;
        } catch (Exception e) {
            // write error in log file
            logger.error(e.toString());
            throw e;
        }
    }

    /**
     * Encrypt/Decrypt a directory
     * @param directory directory
     * @param key key
     * @param processedDirectory directory that will include
     *                           processed files (encrypted/decrypted)
     * @param actionCode "e" for encryption / "d" for decryption
     * @param syncCode "s" for sync / "a" for async
     * @return how much the process took (in milliseconds)
     * @throws Exception when invalid input is entered/ unexpected error
     */
    protected double execAlgoOnDirectory(File directory, final Key key,
                                       final File processedDirectory,
                                       final String actionCode,
                                       String syncCode) throws Exception {
        /**
         * A runnable local class that executes the algorithm on a given file
         */
        class ExecAlgoOnFileJob implements Runnable {
            File file;
            FileReport fr;

            /**
             * Construct the job with a given file and an empty file report
             * @param file input file
             * @param fr empty file report (to report status)
             */
            private ExecAlgoOnFileJob(File file, FileReport fr) {
                this.file = file;
                this.fr = fr;
            }
            // run the algorithm on the file with outer class' parameters
            public void run() {
                try {
                    fr.setFileName(file.getName());
                    double elapsedTime = execAlgoOnFile(file, key,
                            processedDirectory.getPath() + "/" + file.getName(),
                            actionCode);
                    // write the result in a file report
                    fr.setSucceded(true);
                    fr.setTime(elapsedTime);
                } catch (Exception e) {
                    fr.setSucceded(false);
                    fr.setExceptionName(e.getClass().getCanonicalName());
                    fr.setExceptionMessage(e.getMessage());
                    // the stacktrace will only include the invoking method
                    fr.setExceptionStacktrace(e.getStackTrace()[0].toString());
                }
            }
        }
        //start measuring time
        long startTime = System.nanoTime();
        DirectoryReport dr = new DirectoryReport();
        // source/outcome directories must both be directories
        if (!directory.isDirectory() || !processedDirectory.isDirectory()) {
            throw new IllegalArgumentException(
                    strings.getString("unexpectedErrorMsg"));
        }
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalArgumentException(
                    strings.getString("unexpectedErrorMsg"));
        }
        if (syncCode.equals(syncOpt)) {
            // process the directory file-by-file synchronously
            for (final File file : files) {
                if (file.canRead() && file.isFile()) {
                    FileReport fr = new FileReport();
                    (new ExecAlgoOnFileJob(file, fr)).run();
                    dr.addFileReport(fr);
                }
            }
        } else if(syncCode.equals(asyncOpt)) {
            // process the directory using threads
            // executor uses 1 element queue so that files never wait
            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    0, Integer.MAX_VALUE,
                    100, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(1));
            for (final File file : files) {
                if (file.canRead() && file.isFile()) {
                    FileReport fr = new FileReport();
                    executor.execute(new ExecAlgoOnFileJob(file, fr));
                    dr.addFileReport(fr);
                }
            }
            executor.shutdown();
            executor.awaitTermination(120, TimeUnit.SECONDS);
        } else {
            throw new IllegalArgumentException(
                    strings.getString("unexpectedErrorMsg"));
        }
        // write status report on the encryption/decryption
        JAXBUtils.marshalObject(
                dr, new File(strings.getString("xmlReportFile")),
                new Class[]{FileReport.class, DirectoryReport.class});
        // measure time and convert it from nanoseconds to milliseconds
        return  (double)(System.nanoTime() - startTime) / 1000000;
    }
    /**
     * Encrypt an input file/directory
     * and write the result in a new file/directory.
     * @param inputFile an input file/directory
     * @param reader user input reader
     * @return the exact time the encryption process took (in milliseconds)
     */
    public double encrypt(File inputFile, Scanner reader) {
        try {
            logger.info(strings.getString("logStart"));
            // Generate a random encryption key
            Key key = generateKey();
            File keyFile;
            double elapsedTime;
            // start encrypting file/directory
            if (inputFile.isFile()) {
                // notify observers that encryption started
                setChanged();
                notifyObservers(strings.getString("encStartMsg"));
                elapsedTime = execAlgoOnFile(inputFile, key, inputFile.getPath()
                        + ".encrypted", strings.getString("encOption"));
                // the key is stored in the same directory
                keyFile = new File(inputFile.getParent(),
                        strings.getString("keyFileName"));
            } else if (inputFile.isDirectory()) {
                // get sync/async from the user
                System.out.println(strings.getString("syncText"));
                String syncCode = UserInputUtils.getValidUserInput(
                        Arrays.asList(syncOpt, asyncOpt), reader);
                // notify observers that encryption started and measure time
                setChanged();
                notifyObservers(strings.getString("encStartMsg"));
                // create encrypted sub-directory and encrypt input directory
                File encryptedDirectory = new File(inputFile, "encrypted");
                encryptedDirectory.mkdir();
                elapsedTime = execAlgoOnDirectory(inputFile, key,
                        encryptedDirectory, strings.getString("encOption"),
                        syncCode);
                // the key is stored in 'encrypted' directory
                keyFile = new File(inputFile,
                        strings.getString("keyFileName"));
            } else {
                System.out.println(strings.getString("unexpectedErrorMsg"));
                return 0;
            }
            // serialize the key
            SerializationUtils.serializeObject(keyFile, key);
            setChanged();
            // notify observers that decryption ended and return elapsed time
            notifyObservers(strings.getString("encEndMsg"));
            System.out.println(strings.getString("keyMsg"));
            return elapsedTime;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        } finally {
            // in any case, send log end
            logger.info(strings.getString("logEnd"));
        }
    }

    /**
     * Decrypt an input file/directory
     * and write the result in a new file/directory.
     * @param inputFile an input file/directory.
     * @param reader user input reader
     * @return the exact time the decryption process took (in milliseconds)
     */
    public double decrypt(File inputFile, Scanner reader) {
        Key key;
        // get a decryption key from the user
        while (true) {
            try {
                key = getDecryptionKey(getInputKey(reader));
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            // make the name of the decrypted file
            int fileExtIdx = inputFile.getPath().lastIndexOf('.');
            if (fileExtIdx == -1) {
                fileExtIdx = inputFile.getPath().length();
            }
            String fileName = inputFile.getPath().substring(0, fileExtIdx);
            String fileExtension = inputFile.getPath().substring(fileExtIdx);
            double elapsedTime;
            // start decrypting the file/directory
            if (inputFile.isFile()) {
                // notify observers that decryption started and measure time
                setChanged();
                notifyObservers(strings.getString("decStartMsg"));
                elapsedTime = execAlgoOnFile(inputFile, key,
                        fileName + "_decrypted" + fileExtension,
                        strings.getString("decOption"));
            } else if (inputFile.isDirectory()) {
                // get sync/async from the user
                System.out.println(strings.getString("syncText"));
                String syncCode = UserInputUtils.getValidUserInput(
                        Arrays.asList(syncOpt, asyncOpt), reader);
                // notify observers that decryption started and measure time
                setChanged();
                notifyObservers(strings.getString("decStartMsg"));
                // create decrypted sub-directory and decrypt input directory
                File decryptedDirectory = new File(inputFile, "decrypted");
                decryptedDirectory.mkdir();
                elapsedTime = execAlgoOnDirectory(inputFile, key,
                        decryptedDirectory, strings.getString("decOption"),
                        syncCode);
            } else {
                System.out.println(strings.getString("unexpectedErrorMsg"));
                return 0;
            }
            // notify observers that decryption ended and return elapsed time
            setChanged();
            notifyObservers(strings.getString("decEndMsg"));
            return elapsedTime;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
