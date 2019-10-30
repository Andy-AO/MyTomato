package app.util;

import app.model.TomatoTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.File;
import java.time.LocalDate;

public class MapJson implements DataManager {

    private final ObservableList<TomatoTask> listData = FXCollections.observableArrayList();
    private final ObservableMap<LocalDate, ObservableList<TomatoTask>> mapData;

    private final File jsonFile;

    private final ListJson listJson;

    public MapJson(ObservableMap<LocalDate, ObservableList<TomatoTask>> mapData, File jsonFile) {
        this.mapData = mapData;
        this.jsonFile = jsonFile;
        this.listJson = new ListJson(listData, jsonFile);
    }

    @Override
    public String write() {
        convertToList();
        return listJson.write();
    }

    @Override
    public void read() {
        listJson.read();
        convertToMap();
    }

    private void convertToMap() {
        listData.forEach((e) -> {
            if (mapData.get(e.getDate()) == null) {
                mapData.put(e.getDate(), FXCollections.observableArrayList());
            }
            mapData.get(e.getDate()).add(e);
        });
    }

    private void convertToList() {
        listData.clear();
        mapData.forEach((k, v) -> {
            listData.addAll(v);
        });
    }
}
