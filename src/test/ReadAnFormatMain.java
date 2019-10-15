package test;

import app.Mp3Player;
import app.TomatoTaskDataJson;
import app.model.TomatoTask;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalDate;
import java.util.HashMap;

public class ReadAnFormatMain {

    private TomatoTaskDataJson tomatoTaskDataJson;

    private ObservableList<TomatoTask> TOMATO_TASKS = FXCollections.observableArrayList();

    private ObservableMap<LocalDate,ObservableList<TomatoTask>> TOMATO_TASKS_MAP =
        FXCollections.observableMap(new HashMap<>());

    private final String  JSON_PATH = "res\\json\\tomatoTaskData.json";

    private void intiTomatoTaskData() {
        tomatoTaskDataJson = new TomatoTaskDataJson(TOMATO_TASKS,JSON_PATH);
        tomatoTaskDataJson.read();
    }

    public static void main(String[] args) {
        ReadAnFormatMain readAnFormatMain = new ReadAnFormatMain();
        readAnFormatMain.intiTomatoTaskData();
        System.out.println(readAnFormatMain.TOMATO_TASKS);
        readAnFormatMain.convertToMap();
        System.out.println(readAnFormatMain.TOMATO_TASKS_MAP);

    }

    private void convertToMap() {
        TOMATO_TASKS.forEach((e)->{
            if(TOMATO_TASKS_MAP.get(e.getDate()) == null){
                TOMATO_TASKS_MAP.put(e.getDate(), FXCollections.observableArrayList());
            }
            TOMATO_TASKS_MAP.get(e.getDate()).add(e);
        });
    }

}
