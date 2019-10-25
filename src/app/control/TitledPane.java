package app.control;

import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class TitledPane extends javafx.scene.control.TitledPane {
    private final LocalDate LOCAL_DATE;
    private ObservableList<TomatoTask> list = null;
    private AnchorPane anchorPane;
    public static final double TABLE_VIEW_MARGIN = 0;
    public static final int TABLE_VIEW_PADDING = 0;
    private TableView<TomatoTask> tableView;

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
        TableColumn<TomatoTask, String> startCol = new TableColumn<>("Start");
        TableColumn<TomatoTask, String> endCol = new TableColumn<>("End");
        TableColumn<TomatoTask, String> taskNameCol = new TableColumn<>("Task Name");
        tableView.getColumns().setAll(startCol, endCol, taskNameCol);
    }
//--------------------------------------- Field

//--------------------------------------- GS

//--------------------------------------- Method


}
