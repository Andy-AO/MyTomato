package app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {
    private  File musicFile;
    private  MediaPlayer mediaPlayer;
    private  Media media;

    public File getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    public void playInNewThread() {
        mediaPlayer  = new MediaPlayer(media);
        new Thread(() -> getMediaPlayer().play()).start();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Music(File musicFile) {
        this.musicFile = musicFile;
        media = new Media(musicFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public Music(File musicFile,int cycleCount) {
        this(musicFile);
        mediaPlayer.setCycleCount(cycleCount);
    }

    public void stop() {
        mediaPlayer.stop();
    }
}
