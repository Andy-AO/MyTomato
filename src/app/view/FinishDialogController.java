package app.view;

import app.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FinishDialogController {

    @FXML
    private Label label;
    @FXML
    private Button okButton;
    @FXML
    private Label deleteButton;

    @FXML
    private void handleOkButton() {

    }

    @FXML
    private void handleDeleteButton() {

    }

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

    private SimpleStringProperty inputString = new SimpleStringProperty(null);
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
        //按下确定就是窗口关闭的时候
        main.getFinishDialogStage().close();
        //设置inputString便于MainLayout读取
        inputString.set(textField.getCharacters().toString());
        //关闭后,将用户在控件中的输入的内容置空
        getTextField().setText("");
        inputString.set(null);
    }

    public void setMainAndInit(Main main) {
        this.main = main;
    }
}
