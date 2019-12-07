package app.control;

import javafx.scene.Node;

public class GirdColumn<DataType,NodeType extends Node> {
    private final String name;
    private GirdColumnFactory<DataType,NodeType> nodeFactory;

    public GirdColumnFactory getNodeFactory() {
        return nodeFactory;
    }

    public void setNodeFactory(GirdColumnFactory<DataType,NodeType> nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public GirdColumn(String name) {
        this.name = name;
    }
}


