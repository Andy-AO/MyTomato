package app.view;

import app.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import app.model.TomatoTask;
import javafx.util.Callback;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainLayoutController extends Controller {


//--------------------------------------- Field

    public static final String START = "Start";
    public static final String STOP = "Stop";
    public static final int FIRST_INDEX = 0;

    private final int REDO_DELETE_BAR_SHOW_MILLIS = 5000;

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
    private Button plusButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button redoDeleteButton;
    @FXML
    private Button editButton;

    @FXML
    private Text progressText;

    @FXML
    private Text headText;

    @FXML
    private ProgressBar progressBar;

    TaskbarProgressbar taskProgressbar = null;

    @FXML
    private FlowPane buttonFlowPane;
    @FXML
    private FlowPane buttonFlowPaneBackground;


    private static final Duration DEFAULT_WORK_DURATION = Duration.ofMinutes(25);
    private static final CountDown WORK_COUNT_DOWN = new CountDown(DEFAULT_WORK_DURATION);

    private static final Duration DEFAULT_RESPITE_DURATION = Duration.ofMinutes(5);
    private static final CountDown RESPITE_COUNT_DOWN = new CountDown(DEFAULT_RESPITE_DURATION);

    private static final Duration DEVELOPMENT_DURATION = Duration.ofSeconds(3);


    private Mp3Player workDurationMp3Player = new Mp3Player(new File(Main.getResFile(), "sound/bgm_Ticking.mp3"));
    private Mp3Player respiteDurationMp3Player = new Mp3Player(new File(Main.getResFile(), "sound/bgm_WindThroughTrees.mp3"));

    private Mp3Player workFinishedMp3Player = new Mp3Player(new File(Main.getResFile(), "sound/work_finished.mp3"));
    private Mp3Player respiteFinishedMp3Player = new Mp3Player(new File(Main.getResFile(), "sound/respite_finished.mp3"));


    private static final PropertiesManager PROPERTIES_MANAGER = PropertiesManager.getPropertiesManager();
    private int todayTaskAmount = 0;
    private Thread showRedoBarAndSleepThread;


    private static final Double STACKED_PANE_MARGIN = 5.0;
    private static final Double STACKED_PANE_MARGIN_TOP = 80.0;

//--------------------------------------- Getter Setter


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

    public Button getPlusButton() {
        return plusButton;
    }

    public void setPlusButton(Button plusButton) {
        this.plusButton = plusButton;
    }


    public TableColumn<TomatoTask, String> getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(TableColumn<TomatoTask, String> startColumn) {
        this.startColumn = startColumn;
    }

    public TableColumn<TomatoTask, String> getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(TableColumn<TomatoTask, String> endColumn) {
        this.endColumn = endColumn;
    }

    public TableColumn<TomatoTask, String> getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(TableColumn<TomatoTask, String> nameColumn) {
        this.nameColumn = nameColumn;
    }

    public TableColumn<TomatoTask, String> getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(TableColumn<TomatoTask, String> dateColumn) {
        this.dateColumn = dateColumn;
    }

    public Mp3Player getWorkDurationMp3Player() {
        return workDurationMp3Player;
    }

    public void setWorkDurationMp3Player(Mp3Player workDurationMp3Player) {
        this.workDurationMp3Player = workDurationMp3Player;
    }


    public TableView<TomatoTask> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<TomatoTask> tableView) {
        this.tableView = tableView;
    }

//---------------------------------------handle

    @FXML
    private void handleEditButton() {
        TomatoTask specifiedTask = main.getStackedPanes().getFocusedTableView().getSelectionModel().getSelectedItem();
        main.getEditDialogController().loadSpecifiedTaskAndFocus(specifiedTask);
        main.startEditDialogAndWait("修改任务");
    }

    @FXML
    private void handleRedoDelete() {
        List removedItems = main.getREDO_TOMATO_TASKS();
        if (!removedItems.isEmpty()) {
            main.getStackedPanes().addItems(removedItems);
            System.out.println("Redo|removedItems -> " + removedItems);
            removedItems.clear();
        } else {
            System.err.println("REDO_TOMATO_TASKS is empty !");
        }
    }


    @FXML
    void handleDeleteButton() {

        TableView<TomatoTask> tableView = main.getStackedPanes().getFocusedTableView();
        ObservableList<TomatoTask> selectedIndices = null;
        if(tableView != null)
            selectedIndices = tableView.getSelectionModel().getSelectedItems();

        if ((tableView == null)|| (selectedIndices ==null) || (selectedIndices.isEmpty())) {
            Alert alert = new OnTopAlert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table.");
            alert.showAndWait();
        } else {
            ArrayList<TomatoTask> itemList = new ArrayList<>(selectedIndices);
            tableView.getItems().removeAll(itemList);
            tableView.refresh();
        }

    }

    @FXML
    private void handlePlusButton() {
        main.startPlusDialogAndWait();
    }

    @FXML
    private void handleAddButton() {
        main.getEditDialogController().loadNewTask();
        main.startEditDialogAndWait("添加新任务");
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


//--------------------------------------- Bind setSetting


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

    private void sizeBind() {
        nameColumnSizeBind();
        anchorSizeBindAndInit();
    }

    ChangeListener<? super Number> selectedIndexListener = (observable, oldSelectedIndex, newSelectedIndex) -> {
        int selectedIndex = (Integer) newSelectedIndex;
        boolean disable = selectedIndex < 0;
        deleteButton.setDisable(disable);
    };

    private void deleteButtonBind() {

        main.getStackedPanes().focusedTableViewProperty().addListener((observable, oldFocusedTable, newFocusedTable) -> {

            if (oldFocusedTable != null) {
                oldFocusedTable.getSelectionModel().selectedIndexProperty().removeListener(selectedIndexListener);
            }

            if (newFocusedTable == null) {
                Platform.runLater(() -> {
                    deleteButton.setDisable(true);
                });

            } else {
                int selectedIndex = newFocusedTable.getSelectionModel().getSelectedIndex();
                boolean disable = selectedIndex < 0;
                deleteButton.setDisable(disable);
                newFocusedTable.getSelectionModel().selectedIndexProperty().addListener(selectedIndexListener);
            }

        });
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


    private void nameColumnSizeBind() {

        tableView.widthProperty().addListener((observable, oldValue, newValue) -> {
            double otherColumnWidth = dateColumn.getWidth()
                    + endColumn.getWidth()
                    + startColumn.getWidth();
            double nameColumnWidth = (double) newValue - otherColumnWidth;
            nameColumn.setPrefWidth(nameColumnWidth);
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
            Platform.runLater(() -> {
                taskProgressbar.showOtherProgress((Double) newValue, TaskbarProgressbar.TaskbarProgressbarType.PAUSED);
            });
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


    private void setWorkCountDownListener() {
        WORK_COUNT_DOWN.startedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                boolean isStarted = newValue;
                if (isStarted) {
                    startOrStopButton.setText(STOP);
                    getPlusButton().setDisable(false);
                } else {
                    startOrStopButton.setText(START);
                    getPlusButton().setDisable(true);
                }
            });

        });

        WORK_COUNT_DOWN.barProgressProperty().addListener((observable, oldValue, newValue) -> {
            progressBar.setProgress((Double) newValue);
            Platform.runLater(() -> {
                taskProgressbar.showOtherProgress((Double) newValue, TaskbarProgressbar.TaskbarProgressbarType.NORMAL);
            });

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


//--------------------------------------- Method

    private void developmentMode(Boolean newValue) {
        if (newValue) {
            WORK_COUNT_DOWN.setDuration(DEVELOPMENT_DURATION);
            RESPITE_COUNT_DOWN.setDuration(DEVELOPMENT_DURATION);
        } else {
            WORK_COUNT_DOWN.setDuration(DEFAULT_WORK_DURATION);
            RESPITE_COUNT_DOWN.setDuration(DEFAULT_RESPITE_DURATION);
        }
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
        main.getStackedPanes().addItems(tomatoTask);
        RESPITE_COUNT_DOWN.start();
        respiteDurationMp3Player.repeatPlayInNewThread();

    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        initHeadText();
        headTextBind();
        initTable();
        setWorkCountDownListener();
        setRespiteCountDownListener();
        scrollToEnd();
        sizeBind();
        deleteButtonBind();
        deleteButtonAndEditButtonDisableBind();
        initCountDownText();
        setSettingListenerAndSetDuration();
        setFinishDialogListener();
        addToolTipForButton();
        setStackedPanes();
        taskProgressbar = new TaskbarProgressbar(main.getPrimaryStage());

    }

    private void deleteButtonAndEditButtonDisableBind() {
        editButton.disableProperty().bind(deleteButton.disableProperty());

    }

    private void setStackedPanes() {

        main.getStackedPanes().setPrefHeight(240.0);

        AnchorPane.setTopAnchor(main.getStackedPanes(), STACKED_PANE_MARGIN_TOP);

        AnchorPane.setBottomAnchor(main.getStackedPanes(), STACKED_PANE_MARGIN);
        AnchorPane.setLeftAnchor(main.getStackedPanes(), STACKED_PANE_MARGIN);
        AnchorPane.setRightAnchor(main.getStackedPanes(), STACKED_PANE_MARGIN);


        Node temp = anchorPane.getChildren().get(FIRST_INDEX);
        anchorPane.getChildren().set(FIRST_INDEX,main.getStackedPanes());
        anchorPane.getChildren().add(temp);
    }


    private void addToolTipForButton() {
        editButton.setTooltip(new Tooltip("双击"));
        deleteButton.setTooltip(new Tooltip("Delete键"));
    }

    public void sort() {
        tableView.sort();
    }

    private void scrollToEnd() {
        int index = main.getTOMATO_TASKS().size() - 1;
        tableView.scrollTo(index);
    }

    private void updateHeadText() {
        headText.setText("今日已完成 " + todayTaskAmount + " 个番茄");
    }

    public void initHeadText() {
        todayTaskAmount = getCertainDayTaskAmount(main.getTomatoTasksMap(), LocalDate.now());
        updateHeadText();
    }

    private void headTextBind() {
        main.getStackedPanes().titledPaneItemsChangeProperty().addListener((observable, oldValue, newValue) -> {
            initHeadText();
        });
    }

    private int getCertainDayTaskAmount(ObservableMap<LocalDate, ObservableList<TomatoTask>> map, LocalDate date) {
        ObservableList<TomatoTask> CertainDayTaskList = map.get(date);
        if(CertainDayTaskList == null)
            return 0;
        else
            return CertainDayTaskList.size();
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


    private void initTable() {

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //检测双击事件
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    TomatoTask specifiedTask = tableView.getSelectionModel().getSelectedItem();
                    main.getEditDialogController().loadSpecifiedTaskAndFocus(specifiedTask);
                    main.startEditDialogAndWait("修改任务");
                }
            }
        });


        tableView.setItems(main.getTOMATO_TASKS());

        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeStringProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeStringProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateStringProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        nameColumn.setCellFactory(new Callback<TableColumn<TomatoTask, String>, TableCell<TomatoTask, String>>() {
            @Override
            public TableCell<TomatoTask, String> call(TableColumn<TomatoTask, String> param) {
                return new TextWrapCell<>();
            }
        });

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    private void initCountDownText() {
        progressText.setText("");
    }

    public void showRedoBarAndSleep() {
        showRedoBarAndSleepThread = new Thread(() -> {
            showRedoBar();
            try {
                Thread.sleep(REDO_DELETE_BAR_SHOW_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                hideRedoBar();
            }
        });
        showRedoBarAndSleepThread.start();
    }

    private void hideRedoBar() {
        Platform.runLater(() -> {
            buttonFlowPaneBackground.setVisible(false);
            buttonFlowPane.setVisible(false);
        });
    }

    public void closeRedoBar() {
        if (showRedoBarAndSleepThread != null) {
            showRedoBarAndSleepThread.interrupt();
        }

    }

    private void showRedoBar() {
        Platform.runLater(() -> {
            buttonFlowPaneBackground.setVisible(true);
            buttonFlowPane.setVisible(true);
        });

    }


}
