package app.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class GridPane<DataType> extends javafx.scene.layout.GridPane {
    protected ObservableList<DataType> items;

    private ObservableList<GirdColumn<DataType>> columns = FXCollections.observableArrayList();

    public void setItems(ObservableList<DataType> items) {
        this.items = items;
        generateColumns();
    }

    protected void generateColumns() {
        clearGrid();
        int columnIndex = 0;
        for (GirdColumn girdColumn : columns) {
            int itemIndex = 0;
            for (DataType item : items) {
                Node node = girdColumn.getNodeFactory().generateNode(item);
                this.add(node,columnIndex,itemIndex);
                itemIndex++;
            }
            columnIndex++;
        }
    }

    private void clearGrid() {
        this.getChildren().clear();
    }

    public GridPane() {
    }

    public ObservableList<GirdColumn<DataType>> getColumns() {
        return columns;
    }

}
