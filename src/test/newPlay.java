package test;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
public class newPlay {
    public static void main(String[] args) {
        String musicFile = "D:\\GitRepository\\Java\\MyTomato\\res\\sound\\bgm_Ticking.mp3";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
