package app.control.mytomato;

import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GridPane extends javafx.scene.layout.GridPane {
    private ObservableList<TomatoTask> items;
    private ArrayList<Text> nameTextList = new ArrayList<>();

    public void setItems(ObservableList<TomatoTask> items) {
        this.items = items;
        createText();
    }

    private void createText() {
        createNameText();
    }

    private void createNameText() {
        createNameTextList();   
        int rowIndex = 0;
        for (Text text : nameTextList) {
            this.add(text,0,rowIndex++);
        }
    }

    private void createNameTextList() {
        for (TomatoTask tomatoTask : items) {
            nameTextList.add(new Text(tomatoTask.getName()));
        }
    }

    public ObservableList<TomatoTask> getItems() {
        return items;
    }

    public GridPane(ObservableList<TomatoTask> items) {
        setItems(items);
    }

    public GridPane() {
    }
}
