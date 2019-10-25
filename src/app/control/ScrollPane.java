package app.control;

import app.model.TomatoTask;
import javafx.collections.ObservableList;

public class ScrollPane extends  javafx.scene.control.ScrollPane{
    private ObservableList<TomatoTask> items = null;

    public ObservableList<TomatoTask> getItems() {
        return items;
    }

    public void setItems(ObservableList<TomatoTask> observableList) {
        items = observableList;
    }
}
