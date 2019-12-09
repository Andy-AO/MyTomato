package app.control.mytomato;

import app.control.GirdColumn;
import app.control.GirdColumnFactory;
import app.model.TomatoTask;
import app.util.GL;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class GridPane extends app.control.GridPane<TomatoTask> {

    private GirdColumn<TomatoTask> nameColumn;
    private GirdColumn<TomatoTask> startColumn;
    private GirdColumn<TomatoTask> endColumn;
    private GirdColumn<TomatoTask> deleteColumn;

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

        deleteColumn = new GirdColumn<>("name");
        deleteColumn.setNodeFactory(new GirdColumnFactory<TomatoTask>() {
            @Override
            public Node generateNode(TomatoTask data) {
                Button deleteButton = new Button("D");
                deleteButton.setOnAction(event -> {
                    items.remove(data);
                });
                return deleteButton;
            }
        });
        this.getColumns().add(deleteColumn);

        super.setItems(items);

        addListenerToItems();

    }

    private void addListenerToItems() {
        this.items.addListener((ListChangeListener<TomatoTask>) c -> {
            c.next();
            generateColumns();
        });
    }
}
