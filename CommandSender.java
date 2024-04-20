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

    private static void automateKeyPresses(String text) {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < text.length(); i++) {
                char character = text.charAt(i);
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);
                if (Character.isUpperCase(character)) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                if (Character.isUpperCase(character)) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
                Thread.sleep(10); // Adjust delay between keypresses if needed
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
