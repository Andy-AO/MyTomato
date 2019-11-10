package app.view;

import app.Main;
import app.control.OnTopAlert;
import app.util.TimeStringPolisher;
import app.model.TomatoTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditDialogControl extends Controller {


    private static final String EMPTY_TASK_NAME = "";
    public static final int MINUTES_TO_ADD = -25;
    public static final String CHINESE_COLON = "：";
    public static final String ENGLISH_COLON = ":";
    public static final int DIGIT_AMOUNT_OF_TIME_STRING = 4;
    public static final int ENGLISH_COLON_INSERT_OFFSET = 2;
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
    private AtomicBoolean isNewTask = new AtomicBoolean();


    //--------------------------------------- Method

    public TomatoTask getCURRENT_TOMATO_TASK() {
        return CURRENT_TOMATO_TASK.get();
    }

    public SimpleObjectProperty<TomatoTask> CURRENT_TOMATO_TASKProperty() {
        return CURRENT_TOMATO_TASK;
    }

    public void setCURRENT_TOMATO_TASK(TomatoTask CURRENT_TOMATO_TASK) {
        this.CURRENT_TOMATO_TASK.set(CURRENT_TOMATO_TASK);
    }

    @FXML
    private void handleOkButton() {

        getCURRENT_TOMATO_TASK().setDate(datePicker.getValue());

            getCURRENT_TOMATO_TASK().setStartTime(LocalTime.parse(startTime.getText()));
            getCURRENT_TOMATO_TASK().setEndTime(LocalTime.parse(endTime.getText()));

        String taskName = this.taskName.getText();
        if (taskName.isEmpty()) {
            Alert alert = new OnTopAlert(Alert.AlertType.WARNING, "确定要提交一个空任务吗？", ButtonType.NO, ButtonType.YES);
            ButtonType selectButton = alert.showAndWait().get();
            if (selectButton.equals(ButtonType.NO)) {
                return;
            }
        }
        getCURRENT_TOMATO_TASK().setName(taskName);

        if(isNewTask.get()){
            Platform.runLater(() -> {
                main.getStackedPanes().addItems(getCURRENT_TOMATO_TASK());
            });
        }
        else {
            main.getTomatoTaskDataMapJson().write();
            main.getStackedPanes().getFocusedTableView().refresh();
        }

        main.getEditDialogStage().close();

        Platform.runLater(()->{
            main.getMainLayoutController().getAddButton().setDisable(false);
        });
    }

    private void focusControl() {
        TableColumn tableColumn = main.getStackedPanes().getFocusedTableView().getFocusModel().getFocusedCell().getTableColumn();
        String tableColumnText = tableColumn.getText();

        if (tableColumnText.equals("Task Name")) {
            taskName.requestFocus();
            taskName.selectAll();
        } else if (tableColumnText.equals("Start")) {
            startTime.requestFocus();
            startTime.selectAll();
        } else if (tableColumnText.equals("End"))  {
            endTime.requestFocus();
            endTime.selectAll();
        } else {
            datePicker.getEditor().requestFocus();
            datePicker.getEditor().selectAll();
        }
    }

    @FXML
    private void handleCancelButton() {
        main.getEditDialogStage().close();
    }

    public void loadNewTask() {
        isNewTask.set(true);
        LocalTime endTime = LocalTime.now();
        setCURRENT_TOMATO_TASK(new TomatoTask(EMPTY_TASK_NAME, getStartTime(endTime), endTime));
    }
    public void loadSpecifiedTask(TomatoTask tomatoTask) {
        isNewTask.set(false);
        setCURRENT_TOMATO_TASK(tomatoTask);
    }
    public void loadSpecifiedTaskAndFocus(TomatoTask tomatoTask) {
        loadSpecifiedTask(tomatoTask);
        focusControl();
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
        textFieldCheck();
    }

    public class TimeFieldTrueCheck implements ChangeListener<String> {

        private TextField textField;

        public TimeFieldTrueCheck(TextField textField) {
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldText, String newText) {


            if (newText.contains(CHINESE_COLON)) {
                newText = (newText.replace(CHINESE_COLON, ENGLISH_COLON));
            }

            boolean notOnlyContainTimeChar = !onlyContainTimeChar(newText);


            if (notOnlyContainTimeChar) {
                newText = (oldText);
            }

            boolean autoSwap = onlyContainDigit(newText) &&
                    (newText.length() == DIGIT_AMOUNT_OF_TIME_STRING);
            if (autoSwap) {
                StringBuilder sb = new StringBuilder(newText);
                sb.insert(ENGLISH_COLON_INSERT_OFFSET, ENGLISH_COLON);
                newText = (sb.toString());
            }

            this.textField.setText(newText);

        }
    }

    public class TimeFieldCheckWhenLoseFocus implements ChangeListener<Boolean> {

        private TextField textField;
        private String correctFormatString;

        public TimeFieldCheckWhenLoseFocus(TextField textField) {
            this.textField = textField;
            correctFormatString = textField.getText();
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldIsFocused, Boolean newIsFocused) {
            if (newIsFocused){
                correctFormatString = textField.getText();
            }
            else  {
                TimeStringPolisher timeStringPolisher = new TimeStringPolisher(textField.getText());
                String newText = null;
                try {
                    newText = timeStringPolisher.polish();
                } catch (Exception e) {
                    e.printStackTrace();
                    newText = correctFormatString;
                }
                finally {
                    textField.setText(newText);
                }
            }
        }
    }

    private void textFieldCheck() {
        startTime.textProperty().addListener(new TimeFieldTrueCheck(startTime));
        endTime.textProperty().addListener(new TimeFieldTrueCheck(endTime));
        startTime.focusedProperty().addListener(new TimeFieldCheckWhenLoseFocus(startTime));
        endTime.focusedProperty().addListener(new TimeFieldCheckWhenLoseFocus(endTime));
    }

    private boolean onlyContainTimeChar(String newText) {
        char[] charArray = newText.toCharArray();
        for (char c : charArray) {
            if (!isDigitOrEnglishColon(c)) {
                return false;
            }
        }
        return true;
    }
    private boolean onlyContainDigit(String newText) {
        char[] charArray = newText.toCharArray();
        for (char c : charArray) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isDigitOrEnglishColon(char c) {
        String letterOrDigital = "0123456789";
        letterOrDigital += ENGLISH_COLON;
        return -1 == letterOrDigital.indexOf(c) ? false : true;
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
