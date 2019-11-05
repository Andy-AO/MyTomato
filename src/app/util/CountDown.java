package app.util;

import javafx.beans.property.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class CountDown {

    volatile private Duration duration;
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
    volatile private static Timer time;

    public static final long TIMER_DELAY = 0;

    public boolean getFinished() {
        return finished.get();
    }

    public SimpleBooleanProperty finishedProperty() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished);
    }

    public StringProperty textProgressProperty() {
        return textProgress;
    }

    public void setTextProgress(String textProgress) {
        this.textProgress.set(textProgress);
    }

    public DoubleProperty barProgressProperty() {
        return barProgress;
    }

    public void setBarProgress(double barProgress) {
        this.barProgress.set(barProgress);
    }

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

    public synchronized void start() {
        if(started.get()){
            throw new RuntimeException("Already started. Cannot be started again.");
        }
        setStarted(true);
        initProgressData();
        startTimer();
        setFinished(false);
    }

    private void startTimer() {
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                updateProgressText();
                updateProgressBar();
                checkProgress();
            }
        }, TIMER_DELAY, PERIOD_MSEC);
    }

    public void cancel() {
        stop(false);
    }


    public void finish() {
        stop(true);
    }

    public synchronized void stop(boolean finished) {
        if(!started.get()){
            throw new RuntimeException("Already stopped. Cannot be stopped again.");
        }
        setStarted(false);
        progressReturnToZero();
        time.cancel();
        setFinished(finished);
    }

    private void checkProgress() {
        if (currentDuration.compareTo(zeroDuration) >= 0) {
            finish();
        }
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
        currentDuration = Duration.between(startTime, currentTime);
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


    private void progressReturnToZero() {
        setTextProgress("");
        setBarProgress(0);
    }

    private void updateProgressText() {
        currentTime = LocalTime.now();
        currentDuration = Duration.between(endTime, currentTime);
        setTextProgress(CountDown.formatDuration(currentDuration, true));
    }


    private void updateProgressBar() {
        long currentSec = Math.abs(currentDuration.toMillis());
        long sumSec = Math.abs(sumDuration.toMillis());
        double progress = (double) currentSec / (double) sumSec;
        setBarProgress(progress);
    }


}
