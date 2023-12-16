package main;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

// Handles PLaying Music, Sound Effects
public class Sound {
    MediaPlayer[] soundMedia = new MediaPlayer[30];

    public Sound() {
        // soundMedia[0] = new MediaPlayer(new Media(new File("res\\sounds\\sound_name.mp3").toURI().toString()));
        // soundMedia[0].setVolume(0.05);
    }
    public void play(int num) {
        soundMedia[num].play();
        soundMedia[num].seek(Duration.ZERO);
    }

    public void loop(int num) {
        soundMedia[num].setOnEndOfMedia(() -> {
            soundMedia[num].seek(Duration.ZERO);
            soundMedia[num].play();
        });
    }
    public void stop(int num) {
        soundMedia[num].stop();
    }

}
