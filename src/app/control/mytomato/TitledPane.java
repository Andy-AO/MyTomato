package app.control.mytomato;

import app.model.TomatoTask;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

public class TitledPane extends javafx.scene.control.TitledPane {


    //--------------------------------------- Field

    private final LocalDate LOCAL_DATE;

    public static final double TABLE_VIEW_MARGIN = 0;
    public static final int TABLE_VIEW_PADDING = 0;

    private AnchorPane anchorPane = new AnchorPane();
    //TODO:在初始化的时候,直接建立tableView,gridPane应该在此替换TableView
    private TableView tableView = new TableView();
    private GridPane gridPane = new GridPane();

    private ObservableList<TomatoTask> items = null;
    //--------------------------------------- GS

    public void setItems(ObservableList<TomatoTask> list) {
        this.items = list;
        //在这里tableView布置了内容,并且显示了
        //GridPane也可以用类似的方法布置吗?
        tableView.setItems(list);
        gridPane.setItems(list);
        setAnchorPane();
        this.setContent(anchorPane);
        tableView.setVisible(false);
    }

    public TableView getTableView() {
        return tableView;
    }

    public LocalDate getLOCAL_DATE() {
        return LOCAL_DATE;
    }

    //--------------------------------------- Method

    public TitledPane(LocalDate localDate) {
        this.LOCAL_DATE = localDate;
        setTitle();
    }

    private void setTitle() {
        setText(LOCAL_DATE.toString());

    }



    private void setAnchorPane() {
        setTableView();
        setGridView();
        anchorPane.getChildren().add(tableView);
        anchorPane.getChildren().add(gridPane);
        anchorPane.setPadding(new Insets(TABLE_VIEW_PADDING));
    }

    private void setGridView() {
        AnchorPane.setBottomAnchor(gridPane, TABLE_VIEW_MARGIN);
        AnchorPane.setLeftAnchor(gridPane, TABLE_VIEW_MARGIN);
        AnchorPane.setRightAnchor(gridPane, TABLE_VIEW_MARGIN);
        AnchorPane.setTopAnchor(gridPane, TABLE_VIEW_MARGIN);
    }

    private void setTableView() {
        AnchorPane.setBottomAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setLeftAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setRightAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setTopAnchor(tableView, TABLE_VIEW_MARGIN);
    }


}
