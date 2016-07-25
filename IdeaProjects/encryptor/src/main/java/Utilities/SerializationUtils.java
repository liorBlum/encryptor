package Utilities;

import java.io.*;
import java.util.ResourceBundle;

/**
 * A utilities class of file modifying methods
 */
public final class SerializationUtils {
    private final static ResourceBundle strings =
            ResourceBundle.getBundle("strings");
    /**
     * A private constructor to avoid instantiation
     */
    private SerializationUtils() {}

    /**
     * Serialize an object to an input file.
     * @param file input file
     * @param object serializable object
     * @throws IOException when I/O error occurs
     */
    public static void serializeObject(File file, Serializable object)
            throws IOException {
        // if the file exists, delete it and serialize again.
        file.delete();
        BufferedOutputStream fileOut = null;
        ObjectOutputStream objectOut = null;
        try {
            fileOut = new BufferedOutputStream(new FileOutputStream(file));
            objectOut = new ObjectOutputStream(fileOut);
            // write the object to the file and close streams
            objectOut.writeObject(object);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(
                    strings.getString("inputErrorMsg"));
        } catch (IOException e) {
            throw new IOException(
                    strings.getString("fileWriteErrorMsg"));
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
                if (objectOut != null) {
                    objectOut.close();
                }
            } catch (IOException e) {
                throw new IOException(
                        strings.getString("unexpectedErrorMsg"));
            }
        }
    }

    /**
     * Deserialize an object from an input file.
     * @param file input file
     * @return object
     * @throws IOException when I/O error occurs
     */
    public static Object deserializeObject(File file)
            throws IOException {
        BufferedInputStream fileIn = null;
        ObjectInputStream objectIn = null;
        try {
            fileIn = new BufferedInputStream(new FileInputStream(file));
            objectIn = new ObjectInputStream(fileIn);
            // read the object from the file and close streams
            return objectIn.readObject();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(strings.getString("inputErrorMsg"));
        } catch (Exception e) {
            throw new IOException(strings.getString("fileReadErrorMsg"));
        } finally {
            try {
                if (fileIn != null) {
                    fileIn.close();
                }
                if (objectIn != null) {
                    objectIn.close();
                }
            } catch (IOException e) {
                throw new IOException(
                        strings.getString("unexpectedErrorMsg"));
            }
        }
    }
}
