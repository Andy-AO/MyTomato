package app.control;

import javafx.scene.Node;

@FunctionalInterface
public interface GirdColumnFactory<DataType>{
   Node generateNode(DataType data);
}
