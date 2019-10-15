package test;

import app.Mp3Player;
import app.TomatoTaskDataJson;
import app.model.TomatoTask;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class ReadAnFormatMain {

    private TomatoTaskDataJson tomatoTaskDataJson;
    private ObservableList<TomatoTask> TOMATO_TASKS = FXCollections.observableArrayList();
    private final String  JSON_PATH = "res\\json\\tomatoTaskData.json";

    private void intiTomatoTaskData() {
        tomatoTaskDataJson = new TomatoTaskDataJson(TOMATO_TASKS,JSON_PATH);
        tomatoTaskDataJson.read();
    }

    public static void main(String[] args) {


    }

}
