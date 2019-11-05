package app.util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Mp3Player {

    private File file;
    private FileInputStream fileInputStream;
    private Player player;
    private AtomicBoolean repeated = new AtomicBoolean(false);

    public Mp3Player(File file) {
        this.file = file;
        try {
            this.fileInputStream = new FileInputStream(this.file);
            player = new Player(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    private void repeatPlay() {
        repeated.set(true);
        while (repeated.get()) {
            play();
        }
    }

    public void close() {
        repeated.set(false);
        player.close();
    }

    public void playInNewThread() {
        new Thread(this::play).start();
    }

    private void play() {
        try {
            this.fileInputStream = new FileInputStream(this.file);
            player = new Player(fileInputStream);
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void repeatPlayInNewThread() {
        new Thread(this::repeatPlay).start();
    }
}
