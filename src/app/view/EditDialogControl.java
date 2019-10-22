package app.view;

import app.Main;
import app.model.TomatoTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalTime;

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
        getCURRENT_TOMATO_TASK().setName(taskName.getText());
        getCURRENT_TOMATO_TASK().setStartTime(LocalTime.parse(startTime.getText()));
        getCURRENT_TOMATO_TASK().setEndTime(LocalTime.parse(endTime.getText()));

        Platform.runLater(()->{
            main.getMainLayoutController().getTableView().getItems().add(getCURRENT_TOMATO_TASK());
        });
        main.getEditDialogStage().close();
    }

    @FXML
    private void handleCancelButton() {
        main.getEditDialogStage().close();
    }

    public void loadNewTask() {
        LocalTime startTime = LocalTime.now();
        setCURRENT_TOMATO_TASK(new TomatoTask(EMPTY_TASK_NAME, startTime, getEndTime(startTime)));
    }

    private LocalTime getEndTime(LocalTime startTime) {
        LocalTime endTime = LocalTime.from(startTime);
        System.out.println("startTime:" + startTime);
        Duration duration = Duration.ofMinutes(MINUTES_TO_ADD);
        System.out.println("duration:" + duration);
        endTime = endTime.plus(duration);
        System.out.println("endTime:" + endTime);
        return endTime;
    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        currentTomatoTaskBind();
    }

    private void currentTomatoTaskBind() {
        CURRENT_TOMATO_TASKProperty().addListener((observable, oldTomatoTask, newTomatoTask) -> {
            if (newTomatoTask != null) {
                datePicker.setValue(newTomatoTask.getDate());
                startTime.setText(newTomatoTask.getStartTimeString());
                endTime.setText(newTomatoTask.getEndTimeString());
                taskName.setWrapText(true);
                taskName.setText(newTomatoTask.getName());
            }
        });
    }
}
