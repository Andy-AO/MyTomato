package app.control.mytomato;

import app.control.TextWrapCell;
import app.model.TomatoTask;
import javafx.application.Platform;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TableView extends javafx.scene.control.TableView<TomatoTask> {

    public static final int START_AND_END_COLUMN_MIN_WIDTH = 48;
    public static final int SCROLL_WIDTH = 2;
    private TableColumn<TomatoTask, String> nameColumn;
    private TableColumn<TomatoTask, String> endColumn;
    private TableColumn<TomatoTask, String> startColumn;

    public TableColumn<TomatoTask, String> getNameColumn() {
        return nameColumn;
    }

    public TableColumn<TomatoTask, String> getEndColumn() {
        return endColumn;
    }

    public TableColumn<TomatoTask, String> getStartColumn() {
        return startColumn;
    }

    public TableView() {
        createTableColumn();
        this.getColumns().setAll(startColumn, endColumn, nameColumn);
        setColumn();
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setTableViewCell();
    }


    private void setColumn() {
        startColumn.setMinWidth(START_AND_END_COLUMN_MIN_WIDTH);
        endColumn.setMinWidth(START_AND_END_COLUMN_MIN_WIDTH);
        startColumn.setMaxWidth(START_AND_END_COLUMN_MIN_WIDTH);
        endColumn.setMaxWidth(START_AND_END_COLUMN_MIN_WIDTH);
        bindNameColumnWidthToTableViewColumnWidth();
    }

    private void bindNameColumnWidthToTableViewColumnWidth() {
        this.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            Platform.runLater(()->{
                double otherColumnWidth = startColumn.getWidth() + endColumn.getWidth();
                double nameColumnWidth = (Double) newWidth - otherColumnWidth;
                this.nameColumn.setPrefWidth(nameColumnWidth - SCROLL_WIDTH);
            });
        });
    }

    private void setTableViewCell() {

        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeStringProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeStringProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setCellFactory(new Callback<TableColumn<TomatoTask, String>, TableCell<TomatoTask, String>>() {
            @Override
            public TableCell<TomatoTask, String> call(TableColumn<TomatoTask, String> param) {
                return new TextWrapCell<>();
            }
        });

    }

    private void createTableColumn() {
        startColumn = new TableColumn<>("Start");
        endColumn = new TableColumn<>("End");
        nameColumn = new TableColumn<>("Task Name");
    }

}
