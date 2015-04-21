package card;

import java.io.File;
import javax.sound.sampled.*;

public class PlayClip {

    String ss;
    SourceDataLine soundLine = null;
    String soundName;
    Clip clip;
    public int frame = 0;

    PlayClip(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void stop() {
        if (clip.isRunning()) {
            frame = clip.getFramePosition();
            clip.stop();
        }
    }

    public void resume() {
        if (frame > 0) {
            clip.setFramePosition(frame);
        }
        clip.start();
    }
}
