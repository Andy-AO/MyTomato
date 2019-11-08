package app.view;


import app.Main;
import app.control.OnTopAlert;
import app.model.TomatoTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public  class FinishDialogController extends Controller{

    @FXML
    private Label label;
    @FXML
    private Button okButton;
    @FXML
    private Button deleteButton;

    @FXML
    private TextField textField;


    @FXML
    private void handleDeleteButton(ActionEvent event) {
        main.getFinishDialogStage().close();
    }

    public TextField getTextField() {
        return textField;
    }

    @FXML
    private void initialize() {
        deleteButton.setOnAction(this::handleDeleteButton);
        textField.setOnAction(this::handleTextField);
        okButton.setOnAction(this::handleTextField);
    }

    @FXML
    private void handleTextField(ActionEvent event) {

        String taskName = this.textField.getText();

        if ((taskName==null)||(taskName.isEmpty())) {
            Alert alert = new OnTopAlert(Alert.AlertType.WARNING, "确定要提交一个空任务吗？", ButtonType.NO, ButtonType.YES);
            ButtonType selectButton = alert.showAndWait().get();
            if (selectButton.equals(ButtonType.NO)) {
                return;
            }
        }
        TomatoTask tomatoTask = new TomatoTask(taskName,
                Main.WORK_COUNT_DOWN);
        main.getStackedPanes().addItems(tomatoTask);
        Main.RESPITE_COUNT_DOWN.start();
        Main.RESPITE_DURATION_MP3_PLAYER.repeatPlayInNewThread();


        main.getFinishDialogStage().close();
    }
    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
    }
}
