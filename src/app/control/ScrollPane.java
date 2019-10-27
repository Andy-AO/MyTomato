package app.control;

import app.model.TomatoTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Bounds;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class ScrollPane extends  javafx.scene.control.ScrollPane{

//--------------------------------------- Field

    private ObservableMap<LocalDate, ObservableList<TomatoTask>> items = null;
    private VBox stackedTitledPanes = new VBox();
//--------------------------------------- GS

    public ScrollPane() {
        this.setContent(stackedTitledPanes);

        this.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                stackedTitledPanes.setPrefWidth(bounds.getWidth());
            }
        });
    }

    public void setItems(ObservableMap<LocalDate, ObservableList<TomatoTask>> items) {
        this.items = items;
        this.items.forEach((localDate,list)->{
            TitledPane titledPane = new TitledPane(localDate);
            titledPane.setItems(list);
            stackedTitledPanes.getChildren().add(titledPane);
        });
    }

//--------------------------------------- Method


}
