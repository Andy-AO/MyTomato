package app.control.mytomato;

import app.control.GirdColumn;
import app.control.GirdColumnFactory;
import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GridPane extends app.control.GridPane<TomatoTask,Text> {

    private ArrayList<Text> startTextList = new ArrayList<>();
    private ArrayList<Text> endTextList = new ArrayList<>();
    private GirdColumn<TomatoTask, Text> nameColumn;

    public void setItems(ObservableList<TomatoTask> items) {

        nameColumn = new GirdColumn<>("name");
        nameColumn.setNodeFactory(data -> new Text(data.getName()));
        this.getColumns().add(nameColumn);

        super.setItems(items);

        createText();
    }


    private void createText() {
        createStartText();
        createEndText();
    }

    private void createStartText() {
        createStartTextList();
        int rowIndex = 0;
        for (Text text : startTextList) {
            this.add(text,1,rowIndex++);
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

    public ObservableList<TomatoTask> getItems() {
        return items;
    }


}
