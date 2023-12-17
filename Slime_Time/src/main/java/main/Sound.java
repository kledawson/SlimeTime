package main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

// Handles PLaying Music, Sound Effects
public class Sound {
    MediaPlayer[] soundMedia = new MediaPlayer[30];

    public Sound() {
        setup(0, "Part_1_-_8bit_vr.mp4", 1.5);
        setup(1, "pickup.mp3", 0.25);
        setup(2, "player_damage.mp3", 0.25);
        setup(3, "scythe_swing.mp3", 0.25);
        setup(4, "slime_damage.mp3", 0.25);
        setup(5, "slingshot_shoot.mp3", 0.25);
        setup(6, "stone_break.mp3", 0.25);
        setup(7, "stone_damage.mp3", 0.25);
        setup(8, "upgrade.mp3", 0.25);
        setup(9, "wood_break.mp3", 0.25);
        setup(10, "wood_damage.mp3", 0.25);
        setup(11, "Part_3_-_8bit_vr.mp4", 1.5);
    }

    public void setup(int i, String soundName, Double volume) {
        soundMedia[i] = new MediaPlayer(new Media(new File("Slime_Time/res/sounds/" + soundName).toURI().toString()));
        soundMedia[i].setVolume(volume);
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
