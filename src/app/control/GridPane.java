package app.control;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class GridPane<DataType,NodeType extends Node> extends javafx.scene.layout.GridPane {
    protected ObservableList<DataType> items;

    private ObservableList<GirdColumn<DataType,NodeType>> columns;

    public void setItems(ObservableList<DataType> items) {
        this.items = items;
    }

    public GridPane() {
    }

    public ObservableList<GirdColumn<DataType, NodeType>> getColumns() {
        return columns;
    }

}
