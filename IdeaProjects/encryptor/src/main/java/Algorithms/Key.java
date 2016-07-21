package Algorithms;


import java.io.Serializable;

/**
 * Serializable class that holds a key/keys
 */
public class Key implements Serializable {
    /**
     * Construct a Key object with only one valid key
     * @param key only key
     */
    public Key(byte key) {
        this.key = key;
        this.secondKey = null;
    }

    /**
     * Construct a Key object with two valid keys
     * @param key1 first key
     * @param key2 second key
     */
    public Key(byte key1, byte key2) {
        this.key = key1;
        this.secondKey = key2;
    }
    public final Byte key;
    public final Byte secondKey;
}
