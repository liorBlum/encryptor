package Structure;

import java.io.*;
import java.util.ResourceBundle;

/**
 * An abstract class of file modifiers (encryptors and decryptors)
 */
public abstract class FileModifier {
    protected final ResourceBundle strings = ResourceBundle.getBundle("strings");

    /**
     * read bytes from a file to a byte array
     * @param bytes byte array
     * @param file a file
     */
    protected void readBytesFromFile(byte[] bytes, File file) {
        BufferedInputStream inputStream = null;
        try {
            inputStream =
                    new BufferedInputStream(new FileInputStream(file));
            inputStream.read(bytes);
        } catch (FileNotFoundException e) {
            System.out.println(strings.getString("inputErrorMsg"));
        } catch (IOException e) {
            System.out.println(strings.getString("fileReadErrorMsg"));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println(strings.getString("unexpectedErrorMsg"));
                }
            }
        }
    }
    /**
     * write bytes to a new file
     * @param bytes bytes array
     * @param file destination file
     */
    protected void writeBytesToFile(byte[] bytes, File file) {
        BufferedOutputStream outputStream = null;
        try {
            outputStream =
                    new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(bytes);
        } catch (FileNotFoundException e) {
            System.out.println(strings.getString("inputErrorMsg"));
        } catch (IOException e) {
            System.out.println(strings.getString("fileWriteErrorMsg"));
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println(strings.getString("unexpectedErrorMsg"));
                }
            }
        }
    }
}
