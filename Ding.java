import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Ding {

    public static void main(String[] args) {
        playSound("example.wav"); // Replace "example.wav" with the path to your sound file
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