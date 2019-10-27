package app_4;

import app.Main;
import app.control.StackedPanes;
import app.model.TomatoTask;
import app.view.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Demonstrates placing multiple TitledPanes in a VBox so that each pane
 * can open and close independently of every other pane (unlike an Accordion,
 * which only allows a single pane to be open at a time).
 */
public class StackedPanesController extends Controller {


//--------------------------------------- Field

    public static final double TABLE_VIEW_MARGIN = 0;
    public static final int TABLE_VIEW_PADDING = 0;
    private VBox stackedTitledPanes;
    private StackedPanes scrollPane;

//--------------------------------------- Getter Setter

    public VBox getStackedTitledPanes() {
        return stackedTitledPanes;
    }

    public void setStackedTitledPanes(VBox stackedTitledPanes) {
        this.stackedTitledPanes = stackedTitledPanes;
    }

    public StackedPanes getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(StackedPanes scrollPane) {
        this.scrollPane = scrollPane;
    }

//--------------------------------------- Method

    public StackedPanesController() {
    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        scrollPane.setItems(main.getTomatoTasksMap());
    }

    public StackedPanes createScrollPane() {
        createStackedTitledPanes();
        scrollPane = makeScrollable(stackedTitledPanes);
        scrollPane.setPrefSize(410, 480);
        return scrollPane;
    }

    // VBox类似TitledPane的Stack
    private void createStackedTitledPanes() {
        stackedTitledPanes = new VBox();
        stackedTitledPanes.getChildren().setAll(
                createTitledPane("One"),
                createTitledPane("Two"),
                createTitledPane("Three"),
                createTitledPane("Four")
        );
    }


    private TableView<TomatoTask> createTableView() {
        TableView tableView = new TableView();
        TableColumn<TomatoTask, String> startCol = new TableColumn<TomatoTask, String>("Start");
        TableColumn<TomatoTask, String> endCol = new TableColumn<TomatoTask, String>("End");
        TableColumn<TomatoTask, String> taskNameCol = new TableColumn<TomatoTask, String>("Task Name");
        tableView.getColumns().setAll(startCol, endCol, taskNameCol);
        return tableView;
    }


    public TitledPane createTitledPane(String title) {
        AnchorPane anchorPane = new AnchorPane();

        TableView<TomatoTask> tableView = createTableView();

        anchorPane.getChildren().add(tableView);

        AnchorPane.setBottomAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setLeftAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setRightAnchor(tableView, TABLE_VIEW_MARGIN);
        AnchorPane.setTopAnchor(tableView, TABLE_VIEW_MARGIN);
        anchorPane.setPadding(new Insets(TABLE_VIEW_PADDING));

        TitledPane titledPane = new TitledPane(title, anchorPane);

        //全部展开
        titledPane.setExpanded(true);

        return titledPane;
    }

    private StackedPanes makeScrollable(final VBox node) {
        final StackedPanes scroll = new StackedPanes();
        scroll.setContent(node);
        scroll.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                node.setPrefWidth(bounds.getWidth());
            }
        });
        return scroll;
    }

}