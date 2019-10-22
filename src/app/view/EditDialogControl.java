package app.view;

import app.Main;
import app.OnTopAlert;
import app.model.TomatoTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class EditDialogControl extends Controller {


    private static final String EMPTY_TASK_NAME = "";
    public static final int MINUTES_TO_ADD = -25;
    //--------------------------------------- Field
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextArea taskName;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;
    private final SimpleObjectProperty<TomatoTask> CURRENT_TOMATO_TASK = new SimpleObjectProperty();

    public TomatoTask getCURRENT_TOMATO_TASK() {
        return CURRENT_TOMATO_TASK.get();
    }

    public SimpleObjectProperty<TomatoTask> CURRENT_TOMATO_TASKProperty() {
        return CURRENT_TOMATO_TASK;
    }

    public void setCURRENT_TOMATO_TASK(TomatoTask CURRENT_TOMATO_TASK) {
        this.CURRENT_TOMATO_TASK.set(CURRENT_TOMATO_TASK);
    }

    //--------------------------------------- Method
    @FXML
    private void handleOkButton() {

        getCURRENT_TOMATO_TASK().setDate(datePicker.getValue());
        try {
            getCURRENT_TOMATO_TASK().setStartTime(LocalTime.parse(startTime.getText()));
            getCURRENT_TOMATO_TASK().setEndTime(LocalTime.parse(endTime.getText()));
        } catch (DateTimeParseException ex) {
            formatErrorAlert(ex);
            return;
        }

        String taskName = this.taskName.getText();
        if (taskName.isEmpty()) {
            Alert alert = new OnTopAlert(Alert.AlertType.WARNING, "确定要提交一个空任务吗？", ButtonType.NO, ButtonType.YES);
            ButtonType selectButton = alert.showAndWait().get();
            if (selectButton.equals(ButtonType.NO)) {
                return;
            }
        }
        getCURRENT_TOMATO_TASK().setName(taskName);

        Platform.runLater(() -> {
            main.getMainLayoutController().getTableView().getItems().add(getCURRENT_TOMATO_TASK());
        });
        main.getEditDialogStage().close();
    }

    private void formatErrorAlert(DateTimeParseException ex) {
        Alert alert = new OnTopAlert(Alert.AlertType.WARNING, ex.toString());
        alert.initOwner(main.getEditDialogStage());
        alert.showAndWait();
    }

    @FXML
    private void handleCancelButton() {
        main.getEditDialogStage().close();
    }

    public void loadNewTask() {
        LocalTime endTime = LocalTime.now();
        setCURRENT_TOMATO_TASK(new TomatoTask(EMPTY_TASK_NAME, getStartTime(endTime), endTime));
    }

    private LocalTime getStartTime(LocalTime endTime) {
        LocalTime startTime = LocalTime.from(endTime);
        Duration duration = Duration.ofMinutes(MINUTES_TO_ADD);
        startTime = startTime.plus(duration);
        return startTime;
    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        currentTomatoTaskBind();
    }

    private void currentTomatoTaskBind() {
        CURRENT_TOMATO_TASKProperty().addListener((observable, oldTomatoTask, newTomatoTask) -> {
            if (newTomatoTask != null) {
                System.out.println("newTomatoTask:" + newTomatoTask);
                datePicker.setValue(newTomatoTask.getDate());
                startTime.setText(newTomatoTask.getStartTimeString());
                endTime.setText(newTomatoTask.getEndTimeString());
                taskName.setWrapText(true);
                taskName.setText(newTomatoTask.getName());
            }
        });
    }
}
