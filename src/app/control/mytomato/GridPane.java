package app.control.mytomato;

import app.control.GirdColumn;
import app.model.TomatoTask;
import app.util.GL;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GridPane extends app.control.GridPane<TomatoTask,Text> {

    public TitledPane container;

    public GridPane(TitledPane container) {
        this.container = container;
    }

    private GirdColumn<TomatoTask, Text> nameColumn;
    private GirdColumn<TomatoTask, Text> startColumn;
    private GirdColumn<TomatoTask, Text> endColumn;

    public void setItems(ObservableList<TomatoTask> items) {

        startColumn = new GirdColumn<>("start");
        startColumn.setNodeFactory(data -> new Text(data.getStartTimeString()));
        this.getColumns().add(startColumn);

        endColumn = new GirdColumn<>("end");
        endColumn.setNodeFactory(data -> new Text(data.getEndTimeString()));
        this.getColumns().add(endColumn);

        nameColumn = new GirdColumn<>("name");
        nameColumn.setNodeFactory(data -> {
            Text text = new Text(data.getName());
            text.setWrappingWidth(220);
            return text;
        });
        this.getColumns().add(nameColumn);
        
        super.setItems(items);

    }
}
