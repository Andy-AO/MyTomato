package app.control;

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
            if (!isEmpty()) {
                if (textControl == null) {
                    this.setWrapText(true);
                    textControl = new Text(item);
                    setGraphic(textControl);
                    wrap();
                    getTableColumn().widthProperty().addListener((observable, oldValue, newValue) -> wrap());
                } else {
                    textControl.setText(item);
                }
            }


    }
}