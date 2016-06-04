/**
 * Created by Lior on 27/05/2016.
 */
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class TestMenu {
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(baos, true);
    PrintStream defOutStream = System.out;
    @Test
    public void testInitialCommand() {
        /*
        the first call will create the object,
         the second is supposed to return it
          */
        Menu menu1 = Menu.getInstance();
        Menu menu2 = Menu.getInstance();
        //String init_cmd = "For Encryption - enter 'e'\nFor Decryption - enter 'd'\n";
        // sent prints to ps instead of console
        //System.setOut(ps);
        //menu.showMenu();
        //System.out.print(init_cmd);
        // get back to original output stream
        //System.setOut(defOutStream);
        assertEquals(menu1, menu2);
    }
}
