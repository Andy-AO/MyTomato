package app.control;

import app.TextWrapCell;
import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.time.LocalDate;

public class TitledPane extends javafx.scene.control.TitledPane {
    private final LocalDate LOCAL_DATE;
    private ObservableList<TomatoTask> list = null;
    private AnchorPane anchorPane;
    public static final double TABLE_VIEW_MARGIN = 0;
    public static final int TABLE_VIEW_PADDING = 0;
    private TableView<TomatoTask> tableView;
    private TableColumn<TomatoTask, String> nameColumn;
    private TableColumn<TomatoTask, String> endColumn;
    private TableColumn<TomatoTask, String> startColumn;

    public TitledPane(LocalDate localDate) {
        this.LOCAL_DATE = localDate;
        setTitle();
    }

    private void setTitle() {
        setText(LOCAL_DATE.toString());

    }

    public void setItems(ObservableList<TomatoTask> list) {
        this.list = list;
        createAnchorPane();
        this.setContent(anchorPane);
    }

    private void createAnchorPane() {
        anchorPane = new AnchorPane();
        createTableView();
        anchorPane.getChildren().add(tableView);
        AnchorPane.setBottomAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setLeftAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setRightAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setTopAnchor(tableView, TABLE_VIEW_MARGIN);
        anchorPane.setPadding(new Insets(TABLE_VIEW_PADDING));

    }

    private void createTableView() {
        tableView = new TableView();
        tableView.setItems(list);
        createTableColumn();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setTableViewCell();
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
        tableView.getColumns().setAll(startColumn, endColumn, nameColumn);
    }
//--------------------------------------- Field

//--------------------------------------- GS

//--------------------------------------- Method


}
