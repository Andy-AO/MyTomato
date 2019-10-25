package app.view;

import app.Main;
import app.model.TomatoTask;
import app.control.ScrollPane;
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
    private ScrollPane scrollPane;

//--------------------------------------- Getter Setter

    public VBox getStackedTitledPanes() {
        return stackedTitledPanes;
    }

    public void setStackedTitledPanes(VBox stackedTitledPanes) {
        this.stackedTitledPanes = stackedTitledPanes;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
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

    public ScrollPane createScrollPane() {
        scrollPane = new ScrollPane();
        return scrollPane;
    }


}