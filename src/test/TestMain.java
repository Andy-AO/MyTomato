package test;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;

public class TestMain {
    public static void main(String[] args) {
        File file = new File(TestMain.class.getResource("").getFile());
        System.out.println(file);
    }
}
