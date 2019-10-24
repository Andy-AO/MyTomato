package app.view;

import app.Main;
import app.Mp3Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class PlusDialogController extends Controller{

    @FXML
    private Label label;
    @FXML
    private Button okButton;


    private Mp3Player addTasksMp3Player = new Mp3Player(new File(Main.getResFile(),"sound/add_tasks_ahead_of_time.mp3"));


    @FXML
    private void handleOkButton() {
        handleTextField();
    }


    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Button getOkButton() {
        return okButton;
    }

    public void setOkButton(Button okButton) {
        this.okButton = okButton;
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
        main.getPlusDialogStage().close();
        inputString.set(textField.getCharacters().toString());
        main.getFinishDialogController().getTextField().setText(inputString.getValue());
        if (getTextField().getText() != "") {
            addTasksMp3Player.playInNewThread();
        }
    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
    }
}
