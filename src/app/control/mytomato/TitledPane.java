package app.control.mytomato;

import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class TitledPane extends javafx.scene.control.TitledPane {


    //--------------------------------------- Field

    private final LocalDate LOCAL_DATE;

    public static final double TABLE_VIEW_MARGIN = 0;
    public static final int TABLE_VIEW_PADDING = 0;

    private AnchorPane anchorPane = new AnchorPane();;
    private TableView tableView = new TableView();


    private ObservableList<TomatoTask> list = null;
    //--------------------------------------- GS

    //--------------------------------------- Method

    public TitledPane(LocalDate localDate) {
        this.LOCAL_DATE = localDate;
        setTitle();
    }

    private void setTitle() {
        setText(LOCAL_DATE.toString());

    }

    public void setItems(ObservableList<TomatoTask> list) {
        this.list = list;
        tableView.setItems(list);
        setAnchorPane();
        this.setContent(anchorPane);
    }

    private void setAnchorPane() {
        setTableView();
        anchorPane.getChildren().add(tableView);
        anchorPane.setPadding(new Insets(TABLE_VIEW_PADDING));
    }

    private void setTableView() {
        AnchorPane.setBottomAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setLeftAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setRightAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setTopAnchor(tableView, TABLE_VIEW_MARGIN);
    }


}
