/**
 * Created by Lior on 27/05/2016.
 */
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TestMenu {
    private final PrintStream defOutStream = System.out;
    private final InputStream defInStream = System.in;
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final PrintStream psOut = new PrintStream(baos, true);
    private final ResourceBundle strings = ResourceBundle.getBundle("strings");
    @Test
    public void testInitialCommand() {
        /*
        the first call will create the object,
         the second is supposed to return
          that same object and not create a new one
          */
        Menu menu1 = Menu.getInstance();
        Menu menu2 = Menu.getInstance();

        //menu.showMenu();
        //System.out.print(init_cmd);

        assertEquals(menu1, menu2);
    }
    @Test
    public void testMenuShouldAppear() {
        // the correct menu example
        String init_cmd = strings.getString("menuText")
                + System.getProperty("line.separator");
        String exit_cmd = "x";
        // check if menu appears correctly
        InputStream isIn = new ByteArrayInputStream(exit_cmd.getBytes());
        // sent prints to psOut instead of console and input to isIn
        System.setOut(psOut);
        System.setIn(isIn);
        Menu menu = Menu.getInstance();
        menu.showMenu();
        // get back to original output stream
        System.setOut(defOutStream);
        System.setIn(defInStream);
        assertEquals(init_cmd, baos.toString());
    }
}
