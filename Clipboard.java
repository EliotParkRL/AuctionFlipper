import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class Clipboard {
    public static void copyToClipboard(String textToCopy) {
        // Create a StringSelection object with the text to copy
        StringSelection selection = new StringSelection(textToCopy);

        // Get the system clipboard
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Set the contents of the clipboard to the StringSelection object
        clipboard.setContents(selection, null);

        System.out.println("Text copied to clipboard: " + textToCopy);
    }

    public static void main(String[] args) {
        // Example usage
        String text = "Hello, world!";
        copyToClipboard(text);
    }
}