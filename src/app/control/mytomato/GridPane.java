package app.control.mytomato;

import app.Main;
import app.control.GirdColumn;
import app.model.TomatoTask;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class GridPane extends app.control.GridPane<TomatoTask> {

    private GirdColumn<TomatoTask> nameColumn;
    private GirdColumn<TomatoTask> startColumn;
    private GirdColumn<TomatoTask> endColumn;
    private GirdColumn<TomatoTask> deleteColumn;
    private GirdColumn<TomatoTask> editColumn;

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

        deleteColumn = new GirdColumn<>("delete");
        deleteColumn.setNodeFactory(data -> {
            Button deleteButton = new Button();
            deleteButton.getStyleClass().add("delete-button");
            deleteButton.setOnAction(event -> items.remove(data));
            return deleteButton;
        });
        this.getColumns().add(deleteColumn);
        
        editColumn = new GirdColumn<>("edit");
        editColumn.setNodeFactory(data -> {
            Button editButton = new Button();
            editButton.getStyleClass().add("edit-button");
            editButton.setOnAction(event -> {
                getStackedPanes().setEditingGridPane(this);
                getStackedPanes().getStackedPanesController().main.getEditDialogController().loadSpecifiedTask(data);
                getStackedPanes().getStackedPanesController().main.startEditDialogAndWait("修改任务");
            });
            return editButton;
        });

        this.getColumns().add(editColumn);

        super.setItems(items);

        addListenerToItems();

    }

    private StackedPanes getStackedPanes() {
        Parent parent = this.getParent();
        for (int i = 0;i<Integer.MAX_VALUE; i++) {
            if(parent instanceof StackedPanes)
                break;
            else
                parent = parent.getParent();
        }
        return (StackedPanes)parent;
    }


    private void addListenerToItems() {
        this.items.addListener((ListChangeListener<TomatoTask>) c -> {
            c.next();
            generateColumns();
        });
    }

    public void refresh() {
        generateColumns();
    }
}
