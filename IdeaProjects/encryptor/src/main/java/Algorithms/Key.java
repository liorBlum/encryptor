package Algorithms;

import java.io.Serializable;

/**
 * Created by Lior on 16/07/2016.
 */
public class Key implements Serializable {
    public Key(byte key) {
        this.key = key;
    }
    public final byte key;
}
