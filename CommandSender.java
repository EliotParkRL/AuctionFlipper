import java.awt.*;
import java.awt.event.KeyEvent;

public class CommandSender {

    public static void executeCommand(String command){
        System.out.println(command);
        automateKeyPresses(command);
    }

    public static void main(String[] args) {
        String text = "Hello, World!";
        automateKeyPresses(text);
    }

    public static void automateKeyPresses(String text) {
        try {
            Robot robot = new Robot();
            char[] chars = text.toCharArray();
            int[] keyCodes = new int[chars.length];
            for (int i = 0; i < chars.length; i++) {
                char character = chars[i];
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);
                keyCodes[i] = keyCode;
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
