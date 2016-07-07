package Structure;
/**
 * Tests for the menu
 */
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
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
    public void testMenuShouldAppear() {
        // the correct menu example
        String menu_txt = strings.getString("menuText") + ls;
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
        // check if te printed menu is equal to the expected one
        assertEquals(menu_txt, baos.toString());

    }
    @Test
    public void testInvalidInputs() {
        String menu_txt = strings.getString("menuText") + ls;
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
    }
}
