package app;

import javafx.application.Platform;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

public class TextWrapCell<T> extends TableCell<T, String> {
    public int instanceCounter = 0;
    public TextWrapCell() {
        instanceCounter++;
    }

    private Text textControl = null;
    public static final int CELL_TEXT_PAD = 20;

    private void wrap() {
        textControl.setWrappingWidth(getTableColumn().getWidth() - CELL_TEXT_PAD);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null){
            setText(null);
            setGraphic(null);
        }
        else {
            if (!isEmpty()) {
                if (textControl == null) {
                    this.setWrapText(true);
                    textControl = new Text(item);
                    setGraphic(textControl);
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
}