package app.control;

import javafx.scene.Node;

public class GirdColumn<DataType,NodeType extends Node> {
    private final String name;
    private GirdColumnFactory nodeFactory;

    public GirdColumnFactory getNodeFactory() {
        return nodeFactory;
    }

    public void setNodeFactory(GirdColumnFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public GirdColumn(String name) {
        this.name = name;
    }
}

@FunctionalInterface
interface GirdColumnFactory<DataType,NodeType extends Node>{
   public NodeType generateNode(DataType data);
}
