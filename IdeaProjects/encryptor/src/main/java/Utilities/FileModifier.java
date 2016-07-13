package Utilities;

import java.io.*;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * An abstract class of file modifiers (encryptors and decryptors)
 */
public abstract class FileModifier extends Observable {
    protected final ResourceBundle strings =
            ResourceBundle.getBundle("strings");

    /**
     * Create file in input path. This method MUST create a new file
     * or an exception will be thrown.
     * @param path new file's path
     * @return new file
     * @throws IOException if file exists or other IO error occurred
     */
    protected File createFileInPath(String path) throws IOException {
        File newFile = new File(path);
        if (newFile.createNewFile()) {
            return newFile;
        } else {
            throw new IOException(strings.getString("fileWriteErrorMsg"));
        }
    }
    /**
     * read bytes from a file to a byte array
     * @param bytes byte array
     * @param file a file
     */
    protected void readBytesFromFile(byte[] bytes, File file) throws
            IOException{
        BufferedInputStream inputStream = null;
        try {
            inputStream =
                    new BufferedInputStream(new FileInputStream(file));
            inputStream.read(bytes);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(strings.getString("inputErrorMsg"));
        } catch (IOException e) {
            throw new IOException(strings.getString("fileReadErrorMsg"));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new IOException(
                            strings.getString("unexpectedErrorMsg"));
                }
            }
        }
    }
    /**
     * write bytes to a new file
     * @param bytes bytes array
     * @param file destination file
     * @throws IOException when file is not found or when write error occurs
     */
    protected void writeBytesToFile(byte[] bytes, File file) throws
            IOException {
        BufferedOutputStream outputStream = null;
        try {
            outputStream =
                    new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(bytes);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(strings.getString("inputErrorMsg"));
        } catch (IOException e) {
            throw new IOException(strings.getString("fileWriteErrorMsg"));
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new IOException(
                            strings.getString("unexpectedErrorMsg"));
                }
            }
        }
    }
}
