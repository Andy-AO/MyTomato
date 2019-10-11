package app;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Mp3Player {

    private  File file;
    private  FileInputStream fileInputStream;
    private  Player player;

    public Mp3Player(String path) {
        this(new File(path));
     }

    public Mp3Player(File file) {
        this.file = file;
        try {
            this.fileInputStream = new FileInputStream(this.file);
            player = new Player(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        playTheSound();
    }
    private void playTheSound() {
        try {
            this.fileInputStream = new FileInputStream(this.file);
            player = new Player(fileInputStream);
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
