package app.control;

import javafx.scene.Node;

@FunctionalInterface
public interface GirdColumnFactory<DataType>{
   public Node generateNode(DataType data);
}
