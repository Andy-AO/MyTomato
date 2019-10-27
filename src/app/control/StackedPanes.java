package app.control;

import app.model.TomatoTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Bounds;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public abstract class StackedPanes extends  javafx.scene.control.ScrollPane{

//--------------------------------------- Field
    protected VBox stackedTitledPanes = new VBox();
//--------------------------------------- GS

    public StackedPanes() {
        this.setContent(stackedTitledPanes);
        this.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                stackedTitledPanes.setPrefWidth(bounds.getWidth());
            }
        });
    }

//--------------------------------------- Method


}
