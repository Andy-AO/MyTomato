package app.model;

import app.util.CountDown;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.LocalTime;

import static app.util.DateAndTime.localDateToString;
import static app.util.DateAndTime.localTimeToString;

public class TomatoTask {


    private StringProperty name;
    private ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private StringProperty startTimeString;
    private StringProperty endTimeString;
    private StringProperty dateString;


    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getDateString() {
        return dateString.get();
    }

    public StringProperty dateStringProperty() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString.set(dateString);
    }

    public LocalTime getEndTime() {
        return endTime.get();
    }

    public ObjectProperty<LocalTime> endTimeProperty() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime.set(endTime);
    }


    public TomatoTask(String name, CountDown countDown) {
        initTomatoTask(name, countDown.getStartTime(), countDown.getEndTime());
    }

    public TomatoTask(String name, LocalTime startTime, LocalTime endTime) {
        initTomatoTask(name, startTime, endTime);
    }
    public TomatoTask(String name, LocalTime startTime, LocalTime endTime,LocalDate date) {
        initTomatoTask(name, startTime, endTime, date);
    }

    public void initTomatoTask(String name, LocalTime startTime, LocalTime endTime) {
        this.name = new SimpleStringProperty(name);

        addLockTimeListener();
        setEndTime(endTime);
        setStartTime(startTime);
        setDate(LocalDate.now());
    }
    
    public void initTomatoTask(String name, LocalTime startTime, LocalTime endTime,LocalDate date) {
        this.name = new SimpleStringProperty(name);

        addLockTimeListener();
        setEndTime(endTime);
        setStartTime(startTime);
        setDate(date);
    }

    private void addLockTimeListener() {
        startTime.addListener((observable, oldValue, newValue) -> {
            LocalTime startTime = newValue;
            String startTimeString = localTimeToString(startTime);
            this.startTimeString = new SimpleStringProperty(startTimeString);
        });
        endTime.addListener((observable, oldValue, newValue) -> {
            LocalTime endTime = newValue;
            String endTimeString = localTimeToString(endTime);
            this.endTimeString = new SimpleStringProperty(endTimeString);
        });        
        
        date.addListener((observable, oldValue, newValue) -> {
            LocalDate date = newValue;
            String dateString = localDateToString(date);
            this.dateString = new SimpleStringProperty(dateString);
        });
    }

    @Override
    public String toString() {
        return "TomatoTask{" +
                "name=" + name +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                '}';
    }

    public TomatoTask(String name, LocalTime endTime) {
        LocalTime startTime = LocalTime.now();
        initTomatoTask(name, startTime, endTime);
    }

    public String getEndTimeString() {
        return endTimeString.get();
    }

    public StringProperty endTimeStringProperty() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString.set(endTimeString);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalTime getStartTime() {
        return startTime.get();
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime.set(startTime);
    }

    public String getStartTimeString() {
        return startTimeString.get();
    }

    public StringProperty startTimeStringProperty() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString.set(startTimeString);
    }

}
