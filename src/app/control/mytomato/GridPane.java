package app.control.mytomato;

import app.control.GirdColumn;
import app.control.GirdColumnFactory;
import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GridPane extends app.control.GridPane<TomatoTask,Text> {
    private GirdColumn<TomatoTask, Text> nameColumn;

    public void setItems(ObservableList<TomatoTask> items) {

        nameColumn = new GirdColumn<>("name");
        nameColumn.setNodeFactory(data -> new Text(data.getName()));
        this.getColumns().add(nameColumn);

        super.setItems(items);

    }


}
