package app.view;

import app.*;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import app.model.TomatoTask;
import javafx.util.Callback;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainLayoutController {
    private static final double TOP_ANCHOR = 60.0;
    private static final double SIDE_ANCHOR = 10.0;
    public static final String START = "Start";
    public static final String STOP = "Stop";
    public static final int CELL_TEXT_PAD = 20;

    private Main main;
    @FXML
    private TableView<TomatoTask> tableView;
    @FXML
    private AnchorPane anchorPane;
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
    private Text headText;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private FlowPane buttonFlowPane;
    @FXML
    private FlowPane buttonFlowPaneBackground;


    private static final Duration DEFAULT_WORK_DURATION = Duration.ofMinutes(25);
    public static final CountDown WORK_COUNT_DOWN = new CountDown(DEFAULT_WORK_DURATION);

    private static final Duration DEFAULT_RESPITE_DURATION = Duration.ofMinutes(5);
    public static final CountDown RESPITE_COUNT_DOWN = new CountDown(DEFAULT_RESPITE_DURATION);

    private static final Duration DEVELOPMENT_DURATION = Duration.ofSeconds(3);


    private Mp3Player workDurationMp3Player = new Mp3Player(new File("res/sound/bgm_Ticking.mp3"));
    private Mp3Player respiteDurationMp3Player = new Mp3Player(new File("res/sound/bgm_WindThroughTrees.mp3"));

    private Mp3Player workFinishedMp3Player = new Mp3Player(new File("res/sound/work_finished.mp3"));
    private Mp3Player respiteFinishedMp3Player = new Mp3Player(new File("res/sound/respite_finished.mp3"));


    private static final PropertiesManager PROPERTIES_MANAGER = PropertiesManager.getPropertiesManager();
    private int todayTaskAmount = 0;

    public ArrayList<Text> getCellTexts() {
        return cellTexts;
    }

    public void setCellTexts(ArrayList<Text> cellTexts) {
        this.cellTexts = cellTexts;
    }

    private ArrayList<Text> cellTexts = new ArrayList<>();

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
        ObservableList<TomatoTask> selectedIndices = tableView.getSelectionModel().getSelectedItems();
        if (selectedIndices.isEmpty()) {
            Alert alert = new OnTopAlert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table.");
            alert.showAndWait();
        } else {
            ArrayList<TomatoTask> itemList = new ArrayList<>(selectedIndices);
            for (TomatoTask item : itemList) {
                tableView.getItems().remove(item);
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

    public Mp3Player getWorkDurationMp3Player() {
        return workDurationMp3Player;
    }

    public void setWorkDurationMp3Player(Mp3Player workDurationMp3Player) {
        this.workDurationMp3Player = workDurationMp3Player;
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
                respiteDurationMp3Player.close();
                respiteFinishedMp3Player.playInNewThread();
                Platform.runLater(
                        () -> {
                            Alert respiteFinishedAlert = new OnTopAlert(Alert.AlertType.INFORMATION
                                    , "休息已结束，是否开启下一个番茄？"
                                    , ButtonType.YES, ButtonType.NO);
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
        handleWorkFinished(true);
    }

    private void handleWorkFinished(boolean ableMusic) {
        workDurationMp3Player.close();
        if (ableMusic)
            workFinishedMp3Player.playInNewThread();
        Platform.runLater(() -> {
            getStartOrStopButton().setDisable(true);
            main.startFinishDialogAndWait();
            getStartOrStopButton().setDisable(false);
        });
    }

    private void addTaskNameAfterFinished(String taskName) {
        if ((taskName == null)) {
            System.out.println("taskName is null,in addTaskNameAfterFinished()");
        }

        if ((taskName.equals(""))) {
            Alert alert = new OnTopAlert(Alert.AlertType.WARNING, "确定要提交一个空任务吗？", ButtonType.NO, ButtonType.YES);
            ButtonType selectButton = alert.showAndWait().get();
            if (selectButton.equals(ButtonType.NO)) {
                //!!!这里如果不加 Platform.runLater 那么运行的结果会完全不一样!!! 原因目前不明，待会儿想办法搞懂一下
                Platform.runLater(() -> {
                    main.startFinishDialogAndWait();
                });
                return;
            } else {
            }
        }

        TomatoTask tomatoTask = new TomatoTask(taskName,
                WORK_COUNT_DOWN);
        tableView.getItems().add(tomatoTask);
        RESPITE_COUNT_DOWN.start();
        respiteDurationMp3Player.repeatPlayInNewThread();

    }


    private void nameColumnSizeBind() {

        tableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double otherColumnWidth = dateColumn.getWidth()
                    + endColumn.getWidth()
                    + startColumn.getWidth();
            double nameColumnWidth = (double) newValue - otherColumnWidth;
            nameColumn.setPrefWidth(nameColumnWidth);
        });
    }


    private void cellTextsSizeBind() {
        nameColumn.prefWidthProperty().addListener((observable, oldValue, nameColumnNewWidth) -> {
            cellTexts.forEach((text) -> {
                text.setWrappingWidth((Double) nameColumnNewWidth - CELL_TEXT_PAD);
            });
        });
    }

    private void anchorSizeBindAndInit() {
        anchorWidthBindAndInit();
        anchorHeightBindAndInit();
    }

    private void anchorHeightBindAndInit() {
        String heightString = PROPERTIES_MANAGER.getProperty("anchorHeight", Double.toString(anchorPane.getPrefHeight()));
        double height = Double.parseDouble(heightString);
        anchorPane.setPrefHeight(height);

        anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double anchorHeight = (Double) newValue;
            PROPERTIES_MANAGER.setProperty("anchorHeight", Double.toString(anchorHeight));
        });
    }

    private void anchorWidthBindAndInit() {
        String widthString = PROPERTIES_MANAGER.getProperty("anchorWidth", Double.toString(anchorPane.getPrefWidth()));
        double width = Double.parseDouble(widthString);
        anchorPane.setPrefWidth(width);

        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double anchorWidth = (Double) newValue;
            PROPERTIES_MANAGER.setProperty("anchorWidth", Double.toString(anchorWidth));
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
            workDurationMp3Player.close();
        }

        if (!(RESPITE_COUNT_DOWN.getFinished())) {
            RESPITE_COUNT_DOWN.cancel();
            respiteDurationMp3Player.close();
        }

    }

    private void handleStartButton() {
        new Thread(() -> WORK_COUNT_DOWN.start()).start();
        workDurationMp3Player.repeatPlayInNewThread();
    }


    public MainLayoutController() {
    }

    public void setMainAndInit(Main main) {
        this.main = main;
        initHeadText();
        headTextBind();
        initTable();
        setWorkCountDownListener();
        setRespiteCountDownListener();
        scrollToEnd();
        sizeBind();
        deleteButtonBind();
        initCountDownText();
        setSettingListenerAndSetDuration();
        setFinishDialogListener();
    }

    private void scrollToEnd() {
        int index = main.getTOMATO_TASKS().size() - 1;
        tableView.scrollTo(index);
    }

    private void updateHeadText() {
        headText.setText("今日已完成 " + todayTaskAmount + " 个任务");
    }
    private void initHeadText(){
        todayTaskAmount = getCertainDayTaskAmount(main.getTOMATO_TASKS(), LocalDate.now());
        updateHeadText();
    }
    private void headTextBind() {
        main.getTOMATO_TASKS().addListener(new ListChangeListener<TomatoTask>() {
            @Override
            public void onChanged(Change<? extends TomatoTask> change) {
                change.next();
                List addedSubList = change.getAddedSubList();
                System.out.println(addedSubList);
                todayTaskAmount += getCertainDayTaskAmount(new ArrayList<>(addedSubList), LocalDate.now());
                Platform.runLater(()->updateHeadText());
            }
        });

    }

    private int getCertainDayTaskAmount(List<TomatoTask> tomatoTasks, LocalDate date) {
        int taskAmount = 0;
        for (TomatoTask tomatoTask : tomatoTasks) {
            if (tomatoTask.getDate().equals(date)) {
                ++taskAmount;
            }
        }
        return taskAmount;
    }

    private void sizeBind() {
        cellTextsSizeBind();
        nameColumnSizeBind();
        anchorSizeBindAndInit();//have to be last Init
    }

    private void initTable() {
        tableView.setItems(main.getTOMATO_TASKS());
        //setCellValueFactory
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeStringProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeStringProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateStringProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        //setCellValueFactory
        nameColumn.setCellFactory(new Callback<TableColumn<TomatoTask, String>, TableCell<TomatoTask, String>>() {
            @Override
            // return a table cell , cell can setGraphic
            public TableCell<TomatoTask, String> call(TableColumn<TomatoTask, String> param) {
                return new TableCell<TomatoTask, String>() {
                    private Text text;

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item);
                            cellTexts.add(text);
                            text.setWrappingWidth(nameColumn.getWidth() - CELL_TEXT_PAD);
                            this.setWrapText(true);
                            //可以在cell中set不同的node
                            setGraphic(text);
                        }
                    }
                };
            }
        });

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void setFinishDialogListener() {
        main.getFinishDialogController().inputStringProperty().addListener((observable, oldValue, newValue) -> {
            String taskName = newValue;
            if ((taskName == null)) {
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
