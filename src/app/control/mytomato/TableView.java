package app.control.mytomato;

import app.control.TextWrapCell;
import app.model.TomatoTask;
import app.util.GL;
import javafx.application.Platform;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TableView extends javafx.scene.control.TableView<TomatoTask> {

    public static final int START_AND_END_COLUMN_MIN_WIDTH = 48;
    public static final int SCROLL_WIDTH = 2;
    public static final int DEFAULT_PREHEIGHT = 400;
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
        getColumns().setAll(startColumn, endColumn, nameColumn);
        setColumn();
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setCellValueFactory();
        setCellFactory();
        setPrefHeight(DEFAULT_PREHEIGHT);
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
            Platform.runLater(() -> {
                double otherColumnWidth = startColumn.getWidth() + endColumn.getWidth();
                double nameColumnWidth = (Double) newWidth - otherColumnWidth;
                this.nameColumn.setPrefWidth(nameColumnWidth - SCROLL_WIDTH);
            });
        });
    }

    private void setCellValueFactory() {
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeStringProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeStringProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    private void setCellFactory() {
        nameColumn.setCellFactory(param -> new TextWrapCell<>());
    }

    private void createTableColumn() {
        startColumn = new TableColumn<>("Start");
        endColumn = new TableColumn<>("End");
        nameColumn = new TableColumn<>("Task Name");
    }

    public void refreshAndResize() {
        this.refresh();
    }
}
