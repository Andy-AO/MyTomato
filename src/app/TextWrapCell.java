package app;

import javafx.application.Platform;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

public class TextWrapCell<T> extends TableCell<T, String> {


    private Text textControl = null;
    public static final int CELL_TEXT_PAD = 20;

    private void wrap() {
        textControl.setWrappingWidth(getTableColumn().getWidth() - CELL_TEXT_PAD);
    }
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        System.out.print("updateItem:item -> ");
        if (!isEmpty()) {
            if (textControl == null) {
                this.setWrapText(true);
                textControl = new Text(item);
                setGraphic(textControl);
                textControl.textProperty().addListener((observable, oldText, newText) -> {
                    Platform.runLater(() -> {
                        getTableView().refresh();
                    });
                });
                wrap();
                getTableColumn().prefWidthProperty().addListener((observable, oldValue, nameColumnNewWidth) -> {
                    wrap();
                });
            } else {
                textControl.setText(item);
            }
        }
    }
}
