
package TestStructure;

/**
 * Tests for the menu
 */


import Structure.Menu;
import org.junit.Test;
import java.io.*;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


public class TestMenu {
    private final PrintStream defOutStream = System.out;
    private final InputStream defInStream = System.in;
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final PrintStream psOut = new PrintStream(baos, true);
    private final ResourceBundle strings = ResourceBundle.getBundle("strings");
    private final String ls = System.getProperty("line.separator");
    @Test
    public void testInstantiation() {

/*
        the first call will create the object,
         the second is supposed to return
          that same object and not create a new one
          */

        Menu menu1 = Menu.getInstance();
        Menu menu2 = Menu.getInstance();
        assertEquals(menu1, menu2);
    }

    @Test
    public void testInvalidInputs() throws IOException{
        String menu_txt = strings.getString("menuActionText") + ls;
        String invalid_txt = strings.getString("inputErrorMsg") + ls;
        String invalidInput1 = "a";
        String invalidInput2 = "aaa";
        String invalidInput3 = "3";
        String exit_cmd = "x";
        InputStream isIn = new ByteArrayInputStream((invalidInput1 + ls
                + invalidInput2 + ls
                + invalidInput3 + ls
                + exit_cmd).getBytes());
        String expectedOutput = menu_txt + invalid_txt + menu_txt + invalid_txt
                + menu_txt + invalid_txt + menu_txt;
        System.setOut(psOut);
        System.setIn(isIn);
        Menu menu = Menu.getInstance();
        menu.showMenu();
        System.setOut(defOutStream);
        System.setIn(defInStream);
        assertEquals(expectedOutput, baos.toString());
        psOut.close();
    }
}
