import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Clipboard {
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
}