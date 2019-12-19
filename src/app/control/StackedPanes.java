package app.control;

import app.util.GL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.layout.VBox;

public abstract class StackedPanes extends  javafx.scene.control.ScrollPane{

//--------------------------------------- Field
    protected VBox vBox = new VBox();
//--------------------------------------- GS

    public StackedPanes() {
        this.setContent(vBox);
        this.viewportBoundsProperty().addListener((ov, oldBounds, bounds) -> vBox.setPrefWidth(bounds.getWidth()));
    }

//--------------------------------------- Method


}
