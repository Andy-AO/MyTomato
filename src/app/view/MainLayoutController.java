package app.view;

import app.CountDown;
import app.Music;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import app.Main;
import app.model.TomatoTask;

import java.io.File;
import java.time.Duration;

public class MainLayoutController {
    private static final double TOP_ANCHOR = 60.0;
    private static final double SIDE_ANCHOR = 10.0;
    public static final String START = "Start";
    public static final String STOP = "Stop";
    public static final int MUSIC_CYCLE_COUNT = Integer.MAX_VALUE;

    private Main main;
    @FXML
    private TableView<TomatoTask> tableView;
    @FXML
    private TableColumn<TomatoTask, String> startColumn;
    @FXML
    private TableColumn<TomatoTask, String> endColumn;
    @FXML
    private TableColumn<TomatoTask, String> nameColumn;
    @FXML
    private TableColumn<TomatoTask, String> dateColumn;

    @FXML
    private Button startOrStopButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    @FXML
    private Text progressText;

    @FXML
    private ProgressBar progressBar;


    private GridPane todoTaskGrid;

    private static final Duration DEFAULT_WORK_DURATION = Duration.ofMinutes(25);
    public static final CountDown WORK_COUNT_DOWN = new CountDown(DEFAULT_WORK_DURATION);

    private static final Duration DEFAULT_RESPITE_DURATION = Duration.ofMinutes(5);
    public static final CountDown RESPITE_COUNT_DOWN = new CountDown(DEFAULT_RESPITE_DURATION);

    private static final Duration DEVELOPMENT_DURATION = Duration.ofSeconds(3);


    private Music workDurationMusic = new Music(new File("res/sound/bgm_Ticking.mp3"), MUSIC_CYCLE_COUNT);
    private Music workFinishedMusic = new Music(new File("res/sound/work_finished.mp3"));
    private Music respiteFinishedMusic = new Music(new File("res/sound/respite_finished.mp3"));


    public Music getRespiteFinishedMusic() {
        return respiteFinishedMusic;
    }

    public void setRespiteFinishedMusic(Music respiteFinishedMusic) {
        this.respiteFinishedMusic = respiteFinishedMusic;
    }

    @FXML
    private void initialize() {
    }


    @FXML
    private void handleTableDeleteKey(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        String keyName = keyCode.getName();
        if ("Delete" == keyName)
            handleDeleteButton();
    }


    private void deleteButtonBind() {
        tableView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = (Integer) newValue;
            boolean disable = selectedIndex < 0;
            deleteButton.setDisable(disable);
        });

    }

    @FXML
    void handleDeleteButton() {
        ObservableList<Integer>  selectedIndices = tableView.getSelectionModel().getSelectedIndices();


        if (selectedIndices.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table.");
            alert.showAndWait();
        } else {
            for (int i : selectedIndices) {
                tableView.getItems().remove(i);
            }

        }

    }

    @FXML
    private void handleAddButton() {
    }

    private void setSettingListenerAndSetDuration() {
        boolean checked = main.getSettingDialogController().getDevelopmentCheckBox().isSelected();
        developmentMode(checked);

        main.getSettingDialogController().getDevelopmentCheckBox().selectedProperty().addListener((observable,
                                                                                                   oldValue,
                                                                                                   newValue) -> {
            developmentMode(newValue);

        });
    }

    private void developmentMode(Boolean newValue) {
        if (newValue) {
            WORK_COUNT_DOWN.setDuration(DEVELOPMENT_DURATION);
            RESPITE_COUNT_DOWN.setDuration(DEVELOPMENT_DURATION);
        } else {
            WORK_COUNT_DOWN.setDuration(DEFAULT_WORK_DURATION);
            RESPITE_COUNT_DOWN.setDuration(DEFAULT_RESPITE_DURATION);
        }
    }

    public Music getWorkDurationMusic() {
        return workDurationMusic;
    }

    public void setWorkDurationMusic(Music workDurationMusic) {
        this.workDurationMusic = workDurationMusic;
    }

    public Music getWorkFinishedMusic() {
        return workFinishedMusic;
    }

    public void setWorkFinishedMusic(Music workFinishedMusic) {
        this.workFinishedMusic = workFinishedMusic;
    }

    private void setRespiteCountDownListener() {
        RESPITE_COUNT_DOWN.startedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                boolean isStarted = newValue;
                if (isStarted)
                    startOrStopButton.setText(STOP);
                else
                    startOrStopButton.setText(START);
            });

        });
        RESPITE_COUNT_DOWN.barProgressProperty().addListener((observable, oldValue, newValue) -> {
            progressBar.setProgress((Double) newValue);
        });

        RESPITE_COUNT_DOWN.textProgressProperty().addListener((observable, oldValue, newValue) -> {
            progressText.setText(newValue);
        });
        RESPITE_COUNT_DOWN.finishedProperty().addListener((observable, oldValue, newValue) -> {
            boolean finished = newValue;
            if (finished) {
                Platform.runLater(() -> {
                    getRespiteFinishedMusic().playInNewThread();
                    Alert respiteFinishedAlert = new Alert(Alert.AlertType.INFORMATION
                            , "休息已结束，是否开启下一个番茄？"
                            , ButtonType.YES, ButtonType.NO);
//                    respiteFinishedAlert.initOwner(main.getPrimaryStage());
                    respiteFinishedAlert.setTitle("休息已结束");
                    respiteFinishedAlert.setHeaderText("休息已结束");
                    ButtonType buttonType = respiteFinishedAlert.showAndWait().orElse(ButtonType.YES);
                    if (buttonType.equals(ButtonType.YES))
                        handleStartButton();
                });
            }
        });

    }

    public Button getStartOrStopButton() {
        return startOrStopButton;
    }

    public void setStartOrStopButton(Button startOrStopButton) {
        this.startOrStopButton = startOrStopButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    private void setWorkCountDownListener() {
        WORK_COUNT_DOWN.startedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                boolean isStarted = newValue;
                if (isStarted)
                    startOrStopButton.setText(STOP);
                else
                    startOrStopButton.setText(START);
            });

        });

        WORK_COUNT_DOWN.barProgressProperty().addListener((observable, oldValue, newValue) -> {
            progressBar.setProgress((Double) newValue);
        });

        WORK_COUNT_DOWN.textProgressProperty().addListener((observable, oldValue, newValue) -> {
            progressText.setText(newValue);
        });
        WORK_COUNT_DOWN.finishedProperty().addListener((observable, oldValue, newValue) -> {
            boolean finished = newValue;
            if (finished) {
                handleWorkFinished();
            }
        });
    }

    private void handleWorkFinished() {
        workDurationMusic.stop();
        new Thread(getWorkFinishedMusic()::playInNewThread).start();
        Platform.runLater(() -> {
            getStartOrStopButton().setDisable(true);
            main.startFinishDialogAndWait();
            getStartOrStopButton().setDisable(false);

        });
    }

    private void addTaskNameAfterFinished(String taskName) {
        if((taskName == null)){
            System.out.println("taskName is null,in askForEmptyTask()");
        }
        askForEmptyTask(taskName);
        TomatoTask tomatoTask = new TomatoTask(taskName,
                WORK_COUNT_DOWN);
        tableView.getItems().add(tomatoTask);
        RESPITE_COUNT_DOWN.start();
    }

    private void askForEmptyTask(String taskName) {
        if((taskName == null)){
            System.out.println("taskName is null,in askForEmptyTask()");
            return;
        }
        while ((taskName.equals("")) ) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "确定要提交一个空任务吗？", ButtonType.NO, ButtonType.YES);
            ButtonType selectButton = alert.showAndWait().get();
            if (selectButton.equals(ButtonType.NO))
                main.startFinishDialogAndWait();
            else
                return;
        }
        return;

    }

    private void sizeBind() {
        tableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double otherColumnWidth = dateColumn.getWidth()
                    + endColumn.getWidth()
                    + startColumn.getWidth();
            nameColumn.setPrefWidth((Double) newValue - otherColumnWidth);
        });
    }


    @FXML
    private void handleStartOrStopButton() {
        if (startOrStopButton.getText().equals(START)) {
            handleStartButton();
        } else if (startOrStopButton.getText().equals(STOP)) {
            handleStopButton();
        }
    }

    private void handleStopButton() {
        if (!(WORK_COUNT_DOWN.getFinished())) {
            WORK_COUNT_DOWN.cancel();
            workDurationMusic.stop();
        }

        if (!(RESPITE_COUNT_DOWN.getFinished()))
            RESPITE_COUNT_DOWN.cancel();
    }

    private void handleStartButton() {
        new Thread(() -> WORK_COUNT_DOWN.start()).start();
        workDurationMusic.playInNewThread();
    }

    private void initTodoTaskGrid() {
        initTaskGridSize();
        todoTaskGrid.setGridLinesVisible(true);
        todoTaskGrid.getColumnConstraints().add(new ColumnConstraints(235));
    }

    private void initTaskGridSize() {
        todoTaskGrid = new GridPane();
        AnchorPane.setLeftAnchor(todoTaskGrid, SIDE_ANCHOR);
        AnchorPane.setRightAnchor(todoTaskGrid, SIDE_ANCHOR);
        AnchorPane.setTopAnchor(todoTaskGrid, TOP_ANCHOR);
        AnchorPane.setBottomAnchor(todoTaskGrid, SIDE_ANCHOR);
    }


    public MainLayoutController() {
    }

    public void setMainAndInit(Main main) {
        this.main = main;
        initTable();
        setWorkCountDownListener();
        setRespiteCountDownListener();
        sizeBind();
        deleteButtonBind();
        initTodoTaskGrid();
        initCountDownText();
        setSettingListenerAndSetDuration();
        setFinishDialogListener();
    }

    private void initTable() {
        tableView.setItems(main.getTOMATO_TASKS());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeStringProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeStringProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateStringProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void setFinishDialogListener() {
        main.getFinishDialogController().inputStringProperty().addListener((observable, oldValue, newValue) -> {
            String taskName = newValue;
            if((taskName == null)){
                System.out.println("taskName is null,in FinishDialogListener");
                return;
            }
            addTaskNameAfterFinished(taskName);
        });
    }

    private void initCountDownText() {
        progressText.setText("");
    }

}
