import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles copying and notifying when a flip has been found
 */
public class Utilities {

    /**
     * copies a string to the clipboard and plays a ding
     * @param textToCopy string to be copied to clipboard
     */
    public static void copyToClipboardWithSound(String textToCopy) {
        // Create a StringSelection object with the text to copy
        StringSelection selection = new StringSelection(textToCopy);

        // Get the system clipboard
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Set the contents of the clipboard to the StringSelection object
        clipboard.setContents(selection, null);

        System.out.println("Text copied to clipboard: " + textToCopy);
        playSound("DingSoundFile.wav");
    }

    /**
     * plays the sound of a wav file
     * @param soundFile wav file to play
     */
    public static void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000); // Sleep until the sound finishes playing
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    /**
     * Converts a comma-separated string into an ArrayList of strings.
     * chat
     * @param input the comma-separated string
     * @return an ArrayList containing the values from the input string
     */
    public static ArrayList<String> convertToArrayList(String input) {
        // Create an ArrayList to hold the values
        ArrayList<String> arrayList = new ArrayList<>();

        // Check if the input is null or empty
        if (input == null || input.isEmpty()) {
            return arrayList;
        }

        // Split the input string by commas
        String[] values = input.split(",");

        // Convert the array to an ArrayList
        arrayList.addAll(Arrays.asList(values));

        // Return the resulting ArrayList
        return arrayList;
    }
}