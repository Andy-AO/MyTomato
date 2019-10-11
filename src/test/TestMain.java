package test;

import app.Mp3Player;

public class TestMain {
    public static void main(String[] args) {
        Mp3Player player = new Mp3Player("res/sound/work_finished.mp3");
        player.repeatPlayInNewThread();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.close();
    }

}
