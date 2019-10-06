package test;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;

public class TestMain {
    public static void main(String[] args){
        Platform.runLater(()->{
            File mediaFile = new File("res/sound/work_finished.mp3");
            Media media = new Media(mediaFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });

    }
}
