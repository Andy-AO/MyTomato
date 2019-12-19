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
    private GridPane gridPane = new GridPane();

    private ObservableList<TomatoTask> items = null;
    //--------------------------------------- GS

    public void setItems(ObservableList<TomatoTask> list) {
        this.items = list;
        setAnchorPane();
        gridPane.setItems(list);
        gridPane.getStyleClass().add("grid-pane");
        this.setContent(anchorPane);
    }

    public LocalDate getLOCAL_DATE() {
        return LOCAL_DATE;
    }

    //--------------------------------------- Method

    public TitledPane(LocalDate localDate) {
        this.getStyleClass().add("title-pane");
        this.LOCAL_DATE = localDate;
        setTitle();
    }

    private void setTitle() {
        setText(LOCAL_DATE.toString());

    }


    private void setAnchorPane() {
        setGridView();
        anchorPane.getChildren().add(gridPane);
        anchorPane.setPadding(new Insets(TABLE_VIEW_PADDING));
    }

    private void setGridView() {
        AnchorPane.setBottomAnchor(gridPane, TABLE_VIEW_MARGIN);
        AnchorPane.setLeftAnchor(gridPane, TABLE_VIEW_MARGIN);
        AnchorPane.setRightAnchor(gridPane, TABLE_VIEW_MARGIN);
        AnchorPane.setTopAnchor(gridPane, TABLE_VIEW_MARGIN);
    }


}
