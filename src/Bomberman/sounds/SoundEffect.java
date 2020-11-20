package Bomberman.sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffect {
    public static boolean isOn = true;
    private Clip clip;

    public SoundEffect(String path) {
        try {
            File file = new File(path);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception ev) {
            ev.printStackTrace();
        }
    }

    public void play() {
        if (SoundEffect.isOn) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void playBackground() {
        if (SoundEffect.isOn) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopBackground() {
        clip.stop();
    }

    public void close() {
        clip.close();
    }
}
