package app.control;

import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalDate;

public class ScrollPane extends  javafx.scene.control.ScrollPane{

    private ObservableMap<LocalDate, ObservableList<TomatoTask>> Items = null;

    public ObservableMap<LocalDate, ObservableList<TomatoTask>> getItems() {
        return Items;
    }

    public void setItems(ObservableMap<LocalDate, ObservableList<TomatoTask>> items) {
        Items = items;
    }
}
