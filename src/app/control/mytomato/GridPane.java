package app.control.mytomato;

import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GridPane extends app.control.GridPane<TomatoTask,Text> {
    private ObservableList<TomatoTask> items;
    private ArrayList<Text> nameTextList = new ArrayList<>();
    private ArrayList<Text> startTextList = new ArrayList<>();
    private ArrayList<Text> endTextList = new ArrayList<>();

    public void setItems(ObservableList<TomatoTask> items) {
        this.items = items;
        createText();
    }

    private void createText() {
        createStartText();
        createEndText();
        createNameText();
    }

    private void createNameText() {
        createNameTextList();
        int rowIndex = 0;
        for (Text text : nameTextList) {
            this.add(text,3,rowIndex++);
        }
    }

    private void createStartText() {
        createStartTextList();
        int rowIndex = 0;
        for (Text text : startTextList) {
            this.add(text,0,rowIndex++);
        }
    }

    private void createEndText() {
        createEndTextList();
        int rowIndex = 0;
        for (Text text : endTextList) {
            this.add(text,2,rowIndex++);
        }
    }

    private void createStartTextList() {
        for (TomatoTask tomatoTask : items) {
            startTextList.add(new Text(tomatoTask.getStartTimeString()));
        }
    }

    private void createEndTextList() {
        for (TomatoTask tomatoTask : items) {
            endTextList.add(new Text(tomatoTask.getEndTimeString()));
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
