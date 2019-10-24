package app_4;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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
    scene.getStylesheets().add(getClass().getResource("fishy-fishy.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
  }

  private VBox createStackedTitledPanes() {
    final VBox stackedTitledPanes = new VBox();
    stackedTitledPanes.getChildren().setAll(
      createTitledPane("One Fish",  GREEN_FISH),
      createTitledPane("Two Fish",  YELLOW_FISH, GREEN_FISH),
      createTitledPane("Red Fish",  RED_FISH),
      createTitledPane("Blue Fish", BLUE_FISH)
    );
    ((TitledPane) stackedTitledPanes.getChildren().get(0)).setExpanded(true);
    stackedTitledPanes.getStyleClass().add("stacked-titled-panes");

    return stackedTitledPanes;
  }

  public TitledPane createTitledPane(String title, Image... images) {
    FlowPane content = new FlowPane();
    for (Image image: images) {
      ImageView imageView = new ImageView(image);
      content.getChildren().add(imageView);

      FlowPane.setMargin(imageView, new Insets(10));
    }
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