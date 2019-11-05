package app.util;

import javafx.beans.property.*;
import javafx.fxml.FXML;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class CountDown {

    volatile private  Duration duration;

    volatile private LocalTime startTime;
    volatile private LocalTime endTime;
    volatile private LocalTime currentTime;
    volatile private Duration sumDuration;
    volatile private Duration zeroDuration = Duration.ofSeconds(0);
    volatile private Duration currentDuration;

    volatile private SimpleBooleanProperty started = new SimpleBooleanProperty(false);
    volatile private SimpleBooleanProperty finished = new SimpleBooleanProperty(false);


    public static final int PERIOD_MSEC = 100;
    volatile private StringProperty textProgress = new SimpleStringProperty("");
    volatile private DoubleProperty barProgress = new SimpleDoubleProperty(0);
    volatile private  static Timer time;


    public CountDown(Duration duration) {
        this.duration = duration;
    }

    public SimpleBooleanProperty startedProperty() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started.set(started);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void start() {
        setStarted(true);
        setFinished(false);
        initProgressData();
        startTimer();
    }


    public static Timer getTime() {
        return time;
    }

    public static void setTime(Timer time) {
        CountDown.time = time;
    }

    private void initProgressData() {
        currentTime = LocalTime.now();
        startTime = LocalTime.from(currentTime);
        endTime = LocalTime.from(currentTime).plus(duration);
        sumDuration = Duration.between(startTime, endTime);
        System.out.println("sumDuration -> " + sumDuration);
        currentDuration = Duration.between(startTime, currentTime);
        System.out.println("currentDuration -> " + currentDuration);
    }

    public static String formatDuration(Duration duration, Boolean alwaysPositive) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return (seconds < 0) & (!(alwaysPositive)) ? "-" + positive : positive;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    @FXML
    private void startTimer() {
        time = new Timer();
        long delayMsec = 0;
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                updateProgressText();
                updateProgressBar();
                checkProgress();
            }
        }, delayMsec, PERIOD_MSEC);
    }

    private void checkProgress() {
        if (currentDuration.compareTo(zeroDuration) >= 0) {
            cancel(true);
        }
    }

    public void cancel() {
        cancel(false);
    }

    public void cancel(boolean finished) {
        setStarted(false);
        progressReturnToZero();
        time.cancel();
        setFinished(finished);
    }

    public boolean getFinished() {
        return finished.get();
    }

    public SimpleBooleanProperty finishedProperty() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished);
    }


    private void progressReturnToZero() {
        setTextProgress("");
        setBarProgress(0);
    }

    public StringProperty textProgressProperty() {
        return textProgress;
    }

    public void setTextProgress(String textProgress) {
        this.textProgress.set(textProgress);
    }

    private void updateProgressText() {
        currentTime = LocalTime.now();
        currentDuration = Duration.between(endTime, currentTime);
        setTextProgress(CountDown.formatDuration(currentDuration, true));
    }

    public DoubleProperty barProgressProperty() {
        return barProgress;
    }

    public void setBarProgress(double barProgress) {
        this.barProgress.set(barProgress);
    }

    private void updateProgressBar() {
        long currentSec = Math.abs(currentDuration.toMillis());
        long sumSec = Math.abs(sumDuration.toMillis());
        double progress = (double) currentSec / (double) sumSec;
        setBarProgress(progress);
    }
}
