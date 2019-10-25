package app;

import app.model.TomatoTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalDate;

public class MapJson implements Json{

    private final ObservableList<TomatoTask> listData = FXCollections.observableArrayList();
    private final ObservableMap<LocalDate, ObservableList<TomatoTask>> mapData;

    private final String path;

    private final ListJson listJson;

    public MapJson(ObservableMap<LocalDate, ObservableList<TomatoTask>> mapData, String path) {
        this.mapData = mapData;
        this.path = path;
        this.listJson = new ListJson(listData, this.path);
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
