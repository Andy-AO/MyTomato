package app.control;

import javafx.scene.Node;

@FunctionalInterface
public interface GirdColumnFactory<DataType,NodeType extends Node>{
   public NodeType generateNode(DataType data);
}
