package app_4;

import app.model.TomatoTask;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** 
 * Demonstrates placing multiple TitledPanes in a VBox so that each pane
 * can open and close independently of every other pane (unlike an Accordion,
 * which only allows a single pane to be open at a time).
 */
public class StackedPanes extends Application {
  // image license: linkware - backlink to http://www.fasticon.com
  private static final Image BLUE_FISH   = new Image("app_2/Blue-Fish-icon.png");
  private static final Image RED_FISH    = new Image("app_2/Red-Fish-icon.png");
  private static final Image YELLOW_FISH = new Image("app_2/Yellow-Fish-icon.png");
  private static final Image GREEN_FISH  = new Image("app_2/Green-Fish-icon.png");
  
  @Override public void start(Stage stage) {
    VBox stackedTitledPanes = createStackedTitledPanes();

    ScrollPane scroll = makeScrollable(stackedTitledPanes);
    scroll.getStyleClass().add("stacked-titled-panes-scroll-pane");
    scroll.setPrefSize(410, 480);

    stage.setTitle("Fishy, fishy");
    Scene scene = new Scene(scroll);

    stage.setScene(scene);
    stage.show();
  }

  private VBox createStackedTitledPanes() {
    final VBox stackedTitledPanes = new VBox();
    stackedTitledPanes.getChildren().setAll(
      createTitledPane("One Fish"),
      createTitledPane("Two Fish"),
      createTitledPane("Red Fish"),
      createTitledPane("Blue Fish")
    );
    ((TitledPane) stackedTitledPanes.getChildren().get(0)).setExpanded(true);
    stackedTitledPanes.getStyleClass().add("stacked-titled-panes");

    return stackedTitledPanes;
  }


  private TableView<TomatoTask> createTableView() {
    TableView tableView = new TableView();
    TableColumn<TomatoTask,String> startCol = new TableColumn<TomatoTask,String>("Start");
    TableColumn<TomatoTask,String> endCol = new TableColumn<TomatoTask,String>("End");
    TableColumn<TomatoTask,String> taskNameCol = new TableColumn<TomatoTask,String>("Task Name");
    tableView.getColumns().setAll(startCol,endCol,taskNameCol);
    return tableView;
  }


  public TitledPane createTitledPane(String title) {
    FlowPane content = new FlowPane();

    TableView<TomatoTask> tableView = createTableView();

    content.getChildren().add(tableView);

      FlowPane.setMargin(tableView, new Insets(10));

    content.setAlignment(Pos.TOP_CENTER);
    
    TitledPane pane = new TitledPane(title, content);
    pane.getStyleClass().add("stacked-titled-pane");
    pane.setExpanded(false);

    return pane;
  }
  
  private ScrollPane makeScrollable(final VBox node) {
    final ScrollPane scroll = new ScrollPane();
    scroll.setContent(node);
    scroll.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
      @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
        node.setPrefWidth(bounds.getWidth());
      }
    });
    return scroll;
  }
  
  public static void main(String[] args) { Application.launch(args); }
}