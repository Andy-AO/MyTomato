package app.control;

import javafx.scene.Node;

public class GirdColumn<DataType> {
    private final String name;
    private GirdColumnFactory<DataType> nodeFactory;

    public GirdColumnFactory getNodeFactory() {
        return nodeFactory;
    }

    public void setNodeFactory(GirdColumnFactory<DataType> nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public GirdColumn(String name) {
        this.name = name;
    }
}


