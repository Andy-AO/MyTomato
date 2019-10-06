package app.view;

import app.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FinishDialogController {
    @FXML
    private Label label;

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    @FXML
    private TextField textField;

    private SimpleStringProperty inputString = new SimpleStringProperty();
    private Main main;

    public String getInputString() {
        return inputString.get();
    }

    public SimpleStringProperty inputStringProperty() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString.set(inputString);
    }

    @FXML

    private void handleTextField() {
        inputString = new SimpleStringProperty(textField.getCharacters().toString());
        main.getFinishDialogStage().close();
    }

    public void setMainAndInit(Main main) {
        this.main = main;
    }
}
