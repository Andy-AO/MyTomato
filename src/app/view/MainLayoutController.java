package app.view;

import app.*;
import app.control.OnTopAlert;
import app.util.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import app.model.TomatoTask;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MainLayoutController extends Controller {


//--------------------------------------- Field

    public static final String START = "Start";
    public static final String STOP = "Stop";
    public static final int FIRST_INDEX = 0;

    private final int REDO_DELETE_BAR_SHOW_MILLIS = 5000;

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
    private Button redoDeleteButton;

    @FXML
    private Text progressText;

    @FXML
    private Text headText;

    @FXML
    private ProgressBar progressBar;

    TaskBarProgressbar taskProgressbar = null;

    @FXML
    private FlowPane buttonFlowPane;
    @FXML
    private FlowPane buttonFlowPaneBackground;


    private int todayTaskAmount = 0;
    private Thread showRedoBarAndSleepThread;


    private static final Double STACKED_PANE_MARGIN = 5.0;
    private static final Double STACKED_PANE_MARGIN_TOP = 80.0;
    private ReentrantLock startOrStopLock = new ReentrantLock();

//--------------------------------------- Getter Setter


    public Button getStartOrStopButton() {
        return startOrStopButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getPlusButton() {
        return plusButton;
    }

    public TableColumn<TomatoTask, String> getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(TableColumn<TomatoTask, String> nameColumn) {
        this.nameColumn = nameColumn;
    }


//---------------------------------------handle

    @FXML
    private void handleRedoDelete() {
        List removedItems = main.getREDO_TOMATO_TASKS();
        if (!removedItems.isEmpty()) {
            main.getStackedPanes().addItems(removedItems);
            removedItems.clear();
        } else {
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
        startOrStopLock.lock();
        try {
            if (Main.WORK_COUNT_DOWN.isStarted()) {
                Main.WORK_COUNT_DOWN.cancel();
                Main.WORK_DURATION_MP3_PLAYER.close();
            }

            if (Main.RESPITE_COUNT_DOWN.isStarted()) {
                Main.RESPITE_COUNT_DOWN.cancel();
                Main.RESPITE_DURATION_MP3_PLAYER.close();
            }
        } finally {
            startOrStopLock.unlock();
        }
    }

    private void handleStartButton() {
        startOrStopLock.lock();
        try {
            Main.WORK_COUNT_DOWN.start();
            Main.WORK_DURATION_MP3_PLAYER.repeatPlayInNewThread();
        } finally {
            startOrStopLock.unlock();
        }
    }


    private void handleWorkFinished() {
        handleWorkFinished(true);
    }

    private void handleWorkFinished(boolean ableMusic) {
        Main.WORK_DURATION_MP3_PLAYER.close();
        if (ableMusic)
            Main.WORK_FINISHED_MP3_PLAYER.playInNewThread();
        Platform.runLater(() -> {
            getStartOrStopButton().setDisable(true);
            main.startFinishDialogAndWait();
            getStartOrStopButton().setDisable(false);
        });
    }


//--------------------------------------- Bind setSetting




    private void sizeBind() {
        anchorSizeBindAndInit();
    }

    ChangeListener<? super Number> selectedIndexListener = (observable, oldSelectedIndex, newSelectedIndex) -> {
        int selectedIndex = (Integer) newSelectedIndex;
        boolean disable = selectedIndex < 0;
    };


    private void setSettingListenerAndSetDuration() {
        boolean checked = main.getSettingDialogController().getThreeSecondModeBox().isSelected();
        developmentMode(checked);

        main.getSettingDialogController().getThreeSecondModeBox().selectedProperty().addListener((observable,
                                                                                                   oldValue,
                                                                                                   newValue) -> {
            developmentMode(newValue);

        });
    }


    private void anchorSizeBindAndInit() {
        anchorWidthBindAndInit();
        anchorHeightBindAndInit();
    }

    private void anchorHeightBindAndInit() {
        String heightString = Main.PROPERTIES_MANAGER.getProperty("anchorHeight", Double.toString(anchorPane.getPrefHeight()));
        double height = Double.parseDouble(heightString);
        anchorPane.setPrefHeight(height);
        anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double anchorHeight = (Double) newValue;
            Main.PROPERTIES_MANAGER.setProperty("anchorHeight", Double.toString(anchorHeight));
        });
    }

    private void anchorWidthBindAndInit() {
        String widthString = Main.PROPERTIES_MANAGER.getProperty("anchorWidth", Double.toString(anchorPane.getPrefWidth()));
        double width = Double.parseDouble(widthString);
        anchorPane.setPrefWidth(width);
        anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double anchorWidth = (Double) newValue;
            Main.PROPERTIES_MANAGER.setProperty("anchorWidth", Double.toString(anchorWidth));
        });
    }

    private void setRespiteCountDownListener() {
        Main.RESPITE_COUNT_DOWN.startedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                boolean isStarted = newValue;
                if (isStarted){
                    startOrStopButton.setText(STOP);
                    progressBar.setStyle("-fx-accent: #ffc800");
                }
                else
                    startOrStopButton.setText(START);
            });

        });
        Main.RESPITE_COUNT_DOWN.barProgressProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                progressBar.setProgress((Double) newValue);
                taskProgressbar.showOtherProgress((Double) newValue, TaskBarProgressbar.TaskBarProgressbarType.PAUSED);
            });
        });

        Main.RESPITE_COUNT_DOWN.textProgressProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                progressText.setText(newValue);
            });
        });
        Main.RESPITE_COUNT_DOWN.finishedProperty().addListener((observable, oldValue, newValue) -> {
            boolean finished = newValue;
            if (finished) {
                Main.RESPITE_DURATION_MP3_PLAYER.close();
                Main.RESPITE_FINISHED_MP3_PLAYER.playInNewThread();
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
        Main.WORK_COUNT_DOWN.startedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                boolean isStarted = newValue;
                if (isStarted) {
                    startOrStopButton.setText(STOP);
                    getPlusButton().setDisable(false);
                    progressBar.setStyle("-fx-accent: #328e2e");
                } else {
                    startOrStopButton.setText(START);
                    getPlusButton().setDisable(true);
                }
            });

        });

        Main.WORK_COUNT_DOWN.barProgressProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                progressBar.setProgress((Double) newValue);
                taskProgressbar.showOtherProgress((Double) newValue, TaskBarProgressbar.TaskBarProgressbarType.NORMAL);
            });

        });

        Main.WORK_COUNT_DOWN.textProgressProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                progressText.setText(newValue);
            });
        });
        Main.WORK_COUNT_DOWN.finishedProperty().addListener((observable, oldValue, newValue) -> {
            boolean finished = newValue;
            if (finished) {
                handleWorkFinished();
            }
        });
    }


//--------------------------------------- Method

    private void developmentMode(Boolean newValue) {
        if (newValue) {
            Main.WORK_COUNT_DOWN.setDuration(Main.DEVELOPMENT_DURATION);
            Main.RESPITE_COUNT_DOWN.setDuration(Main.DEVELOPMENT_DURATION);
        } else {
            Main.WORK_COUNT_DOWN.setDuration(Main.DEFAULT_WORK_DURATION);
            Main.RESPITE_COUNT_DOWN.setDuration(Main.DEFAULT_RESPITE_DURATION);
        }
    }


    private void addTaskNameAfterFinished(String taskName) {

        if ((taskName == null)||(taskName.isEmpty())) {
            Alert alert = new OnTopAlert(Alert.AlertType.WARNING, "确定要提交一个空任务吗？", ButtonType.NO, ButtonType.YES);
            ButtonType selectButton = alert.showAndWait().get();
            if (selectButton.equals(ButtonType.NO)) {
                //Platform.runLater 在这里是必不可少的
                Platform.runLater(() -> {
                    main.startFinishDialogAndWait();
                });
                return;
            } else {
            }
        }

        TomatoTask tomatoTask = new TomatoTask(taskName,
                Main.WORK_COUNT_DOWN);
        main.getStackedPanes().addItems(tomatoTask);
        Main.RESPITE_COUNT_DOWN.start();
        Main.RESPITE_DURATION_MP3_PLAYER.repeatPlayInNewThread();

    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        initHeadText();
        headTextBind();
        setWorkCountDownListener();
        setRespiteCountDownListener();
        sizeBind();
        initCountDownText();
        setSettingListenerAndSetDuration();
        setStackedPanes();
        taskProgressbar = new TaskBarProgressbar(main.getPrimaryStage());

    }


    private void setStackedPanes() {

        main.getStackedPanes().setPrefHeight(240.0);

        AnchorPane.setTopAnchor(main.getStackedPanes(), STACKED_PANE_MARGIN_TOP);

        AnchorPane.setBottomAnchor(main.getStackedPanes(), STACKED_PANE_MARGIN);
        AnchorPane.setLeftAnchor(main.getStackedPanes(), STACKED_PANE_MARGIN);
        AnchorPane.setRightAnchor(main.getStackedPanes(), STACKED_PANE_MARGIN);


        Node temp = anchorPane.getChildren().get(FIRST_INDEX);
        anchorPane.getChildren().set(FIRST_INDEX, main.getStackedPanes());
        anchorPane.getChildren().add(temp);
    }


    private void updateHeadText() {
        headText.setText("今日已完成 " + todayTaskAmount + " 个番茄");
    }

    public void initHeadText() {
        todayTaskAmount = getCertainDayTaskAmount(Main.getTomatoTasksMap(), LocalDate.now());
        updateHeadText();
    }

    private void headTextBind() {
        main.getStackedPanes().titledPaneItemsChangeProperty().addListener((observable, oldValue, newValue) -> {
            initHeadText();
        });
    }

    private int getCertainDayTaskAmount(ObservableMap<LocalDate, ObservableList<TomatoTask>> map, LocalDate date) {
        ObservableList<TomatoTask> CertainDayTaskList = map.get(date);
        if (CertainDayTaskList == null)
            return 0;
        else
            return CertainDayTaskList.size();
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
            } finally {
                hideRedoBar();
            }
        },"showRedoBar");
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
