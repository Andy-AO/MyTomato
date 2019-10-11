package test;

import app.Mp3ZoomPlayer;

public class TestMain {
    public static void main(String[] args) {
        Mp3ZoomPlayer player = new Mp3ZoomPlayer("res/sound/work_finished.mp3");
        player.repeatPlayInNewThread();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.close();
    }

}
