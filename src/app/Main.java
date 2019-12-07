package app;

import app.control.OnTopAlert;
import app.control.mytomato.NewStackedPanes;
import app.control.mytomato.StackedPanes;
import app.util.*;
import app.view.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import app.model.TomatoTask;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    public static final int PRIMARY_STAGE_MIN_WIDTH = 415;
    private static final ObservableMap<LocalDate, ObservableList<TomatoTask>> TOMATO_TASKS_MAP = FXCollections.observableMap(new HashMap());
    public static final Duration DEFAULT_WORK_DURATION = Duration.ofMinutes(25);
    public static final CountDown WORK_COUNT_DOWN = new CountDown(DEFAULT_WORK_DURATION);
    public static final Duration DEFAULT_RESPITE_DURATION = Duration.ofMinutes(5);
    public static final CountDown RESPITE_COUNT_DOWN = new CountDown(DEFAULT_RESPITE_DURATION);
    public static final Duration DEVELOPMENT_DURATION = Duration.ofSeconds(3);
    public static final Mp3Player WORK_DURATION_MP3_PLAYER = new Mp3Player(new File(ResGetter.getResFile(), "sound/bgm_Ticking.mp3"));
    public static final Mp3Player RESPITE_DURATION_MP3_PLAYER = new Mp3Player(new File(ResGetter.getResFile(), "sound/bgm_WindThroughTrees.mp3"));

    public static final Mp3Player WORK_FINISHED_MP3_PLAYER = new Mp3Player(new File(ResGetter.getResFile(), "sound/work_finished.mp3"));
    public static final Mp3Player RESPITE_FINISHED_MP3_PLAYER = new Mp3Player(new File(ResGetter.getResFile(), "sound/respite_finished.mp3"));

    public static final PropertiesManager PROPERTIES_MANAGER = PropertiesManager.getPropertiesManager();

    private BorderPane rootLayout;
    private AnchorPane mainLayout;

    private Stage primaryStage;
    private Stage finishDialogStage = null;
    private Stage plusDialogStage = null;
    private Stage startSettingStage = null;

    private Image tomatoImage = new Image(ResGetter.getResURLString() + "image/tomato.png");
    private AnchorPane editDialog;
    private EditDialogControl editDialogController;
    private Stage editDialogStage = null;
    private AnchorPane plusDialog;
    private PlusDialogController plusDialogController;
    private StackedPanesController stackedPanesController;
    private DataManager tomatoTaskDataMapJson;

    private AnchorPane finishDialog;
    private StackedPanes stackedPanes;
    private FinishDialogController finishDialogController;

    private MainLayoutController mainLayoutController;
    private RootLayoutController rootLayoutController;
    private TabPane settingDialog;
    private SettingDialogController settingDialogController;

    private ObservableList<TomatoTask> REDO_TOMATO_TASKS = FXCollections.observableArrayList();

    private final File JSON_FILE = new File(ResGetter.getResFile(), "json\\tomatoTaskData.json");
    private NewStackedPanesController newStackedPanesController;
    private NewStackedPanes newStackedPanes;

    public NewStackedPanes getNewStackedPanes() {
        return newStackedPanes;
    }

    public TabPane getSettingDialog() {
        return settingDialog;
    }

    public void setSettingDialog(TabPane settingDialog) {
        this.settingDialog = settingDialog;
    }

    public SettingDialogController getSettingDialogController() {
        return settingDialogController;
    }

    public void setSettingDialogController(SettingDialogController settingDialogController) {
        this.settingDialogController = settingDialogController;
    }


    public FinishDialogController getFinishDialogController() {
        return finishDialogController;
    }

    public void setFinishDialogController(FinishDialogController finishDialogController) {
        this.finishDialogController = finishDialogController;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public AnchorPane getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(AnchorPane mainLayout) {
        this.mainLayout = mainLayout;
    }


    public AnchorPane getFinishDialog() {
        return finishDialog;
    }

    public void setFinishDialog(AnchorPane finishDialog) {
        this.finishDialog = finishDialog;
    }

    public static ObservableMap<LocalDate, ObservableList<TomatoTask>> getTomatoTasksMap() {
        return TOMATO_TASKS_MAP;
    }

    public DataManager getTomatoTaskDataMapJson() {
        return tomatoTaskDataMapJson;
    }

    public MainLayoutController getMainLayoutController() {
        return mainLayoutController;
    }

    public void setMainLayoutController(MainLayoutController mainLayoutController) {
        this.mainLayoutController = mainLayoutController;
    }

    public RootLayoutController getRootLayoutController() {
        return rootLayoutController;
    }

    public void setRootLayoutController(RootLayoutController rootLayoutController) {
        this.rootLayoutController = rootLayoutController;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loadLayout();
        intiTomatoTaskData();
        setMainAndInitLayout();
        initPrimaryStage();
        this.primaryStage.show();
    }

    private void setMainAndInitLayout() {
        mainLayoutController.setMainAndInit(this);
        finishDialogController.setMainAndInit(this);
        plusDialogController.setMainAndInit(this);
        rootLayoutController.setMainAndInit(this);
        settingDialogController.setMainAndInit(this);
        editDialogController.setMainAndInit(this);
        stackedPanesController.setMainAndInit(this);
        newStackedPanesController.setMainAndInit(this);

    }

    private void loadLayout() throws IOException {


        loadRootLayout();
        loadMainLayout();
        loadFinishDialog();
        loadPlusDialog();
        loadSettingDialog();
        loadEditDialog();
        loadStackedPanes();
        loadNewStackedPanes();
    }

    private void loadNewStackedPanes() {
        newStackedPanesController = new NewStackedPanesController();
        newStackedPanes = newStackedPanesController.createScrollPane();
    }

    public Main(TabPane settingDialog) {
        this.settingDialog = settingDialog;
    }

    private void loadSettingDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL name = getClass().getResource("view/SettingDialog.fxml");
            loader.setLocation(name);
            settingDialog = loader.load();
            settingDialogController = loader.getController();
        } catch (IOException e) {
            GL.logger.debug("load SettingDialog fail.",e);
        }
    }

    private void loadEditDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL name = getClass().getResource("view/EditDialog.fxml");
            loader.setLocation(name);
            editDialog = loader.load();
            editDialogController = loader.getController();
        } catch (IOException e) {
            GL.logger.warn(getClass().getSimpleName(),e);
        }
    }

    private void initPrimaryStage() {
        primaryStage.setTitle("MyTomato");
        Scene primaryStageScene = new Scene(getRootLayout());
        primaryStageScene.getStylesheets().add(getClass().getResource("view/stylesheet.css").toExternalForm());
        primaryStage.setScene(primaryStageScene);
        primaryStage.setMinWidth(PRIMARY_STAGE_MIN_WIDTH);
        primaryStage.getIcons().add(tomatoImage);
        setPrimaryStageListener();
    }

    private void setPrimaryStageListener() {
        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> isFocused, Boolean onHidden, Boolean onShown) {
                if (isFocused.getValue()) {
                    mainLayoutController.initHeadText();
                }
            }
        });

        primaryStage.setOnCloseRequest(evt -> {
            // prevent window from closing
            evt.consume();
            // execute own shutdown procedure
            boolean isClosed = onStageShutdown(primaryStage);
            if (isClosed)
                System.exit(0);
        });
    }

    private void loadFinishDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL name = getClass().getResource("view/FinishDialog.fxml");
            loader.setLocation(name);
            finishDialog = loader.load();
            finishDialogController = loader.getController();
        } catch (IOException e) {
            GL.logger.warn(getClass().getSimpleName(),e);
        }
    }

    public StackedPanesController getStackedPanesController() {
        return stackedPanesController;
    }

    public void setStackedPanesController(StackedPanesController stackedPanesController) {
        this.stackedPanesController = stackedPanesController;
    }

    public StackedPanes getStackedPanes() {
        return stackedPanes;
    }

    private void loadStackedPanes() {
        stackedPanesController = new StackedPanesController();
        stackedPanes = stackedPanesController.createScrollPane();
        stackedPanes.setVisible(false);
    }

    public Stage getPlusDialogStage() {
        return plusDialogStage;
    }

    public void setPlusDialogStage(Stage plusDialogStage) {
        this.plusDialogStage = plusDialogStage;
    }

    public AnchorPane getPlusDialog() {
        return plusDialog;
    }

    public void setPlusDialog(AnchorPane plusDialog) {
        this.plusDialog = plusDialog;
    }

    public PlusDialogController getPlusDialogController() {
        return plusDialogController;
    }

    public void setPlusDialogController(PlusDialogController plusDialogController) {
        this.plusDialogController = plusDialogController;
    }

    private void loadPlusDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL name = getClass().getResource("view/PlusDialog.fxml");
            loader.setLocation(name);
            plusDialog = loader.load();
            plusDialogController = loader.getController();
        } catch (IOException e) {
            GL.logger.warn(getClass().getSimpleName(),e);
        }
    }

    public Stage getFinishDialogStage() {
        return finishDialogStage;
    }

    public void setFinishDialogStage(Stage finishDialogStage) {
        this.finishDialogStage = finishDialogStage;
    }

    public AnchorPane getEditDialog() {
        return editDialog;
    }

    public void setEditDialog(AnchorPane editDialog) {
        this.editDialog = editDialog;
    }

    public EditDialogControl getEditDialogController() {
        return editDialogController;
    }

    public void setEditDialogController(EditDialogControl editDialogController) {
        this.editDialogController = editDialogController;
    }

    public Stage getEditDialogStage() {
        return editDialogStage;
    }

    public void setEditDialogStage(Stage editDialogStage) {
        this.editDialogStage = editDialogStage;
    }

    public void startEditDialogAndWait(String Title) {
        if (editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.initOwner(primaryStage);
            editDialogStage.setTitle(Title);
            editDialogStage.getIcons().add(tomatoImage);
            editDialogStage.setScene(new Scene(editDialog));
            editDialogStage.setResizable(false);
            editDialogStage.setOnCloseRequest(evt -> {
                Platform.runLater(() -> {
                    getMainLayoutController().getAddButton().setDisable(false);
                });
            });
        }
        Platform.runLater(() -> {
            getMainLayoutController().getAddButton().setDisable(true);
        });

        editDialogStage.showAndWait();
    }

    public void startFinishDialogAndWait() {
        if (finishDialogStage == null) {
            finishDialogStage = new Stage();
            finishDialogStage.setAlwaysOnTop(true);
            finishDialogStage.initOwner(primaryStage);
            finishDialogStage.setTitle("请输入刚刚完成的任务");
            finishDialogStage.getIcons().add(tomatoImage);
            finishDialogStage.setScene(new Scene(finishDialog));
            finishDialogStage.setResizable(false);

            finishDialogStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            getFinishDialogController().getTextField().setText("");
                            getPlusDialogController().getTextField().setText("");

                        }
                    });
                }
            });

            finishDialogStage.setOnCloseRequest(evt -> {
                evt.consume();
                onStageShutdown(finishDialogStage);
            });
        }
        finishDialogStage.showAndWait();
    }

    public void startPlusDialogAndWait() {
        if (plusDialogStage == null) {
            plusDialogStage = new Stage();
            plusDialogStage.setAlwaysOnTop(true);
            plusDialogStage.initOwner(primaryStage);
            plusDialogStage.setTitle("请输入要提前添加的任务:");
            plusDialogStage.getIcons().add(tomatoImage);
            plusDialogStage.setScene(new Scene(plusDialog));
            plusDialogStage.setResizable(false);
        }
        plusDialogStage.showAndWait();
    }

    public void startSettingDialogAndWait() {
        if (startSettingStage == null) {
            startSettingStage = new Stage();
            startSettingStage.setAlwaysOnTop(true);
            startSettingStage.setTitle("SettingDialog");
            startSettingStage.getIcons().add(tomatoImage);
            startSettingStage.setScene(new Scene(settingDialog));
            startSettingStage.setResizable(false);
        }
        startSettingStage.showAndWait();
    }

    private boolean onStageShutdown(Stage mainWindow) {
        // you could also use your logout window / whatever here instead
        Alert alert = new OnTopAlert(Alert.AlertType.NONE, "Really close the Windows?", ButtonType.YES, ButtonType.NO);
        alert.initOwner(mainWindow);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            // you may need to close other windows or replace this with Platform.exit();
            mainWindow.close();
            return true;
        } else
            return false;
    }


    public Main() {

    }


    private void intiTomatoTaskData() {
        tomatoTaskDataMapJson = new MapJson(TOMATO_TASKS_MAP, JSON_FILE);
        tomatoTaskDataMapJson.read();

        setTomatoTaskDataListener();
        setRedoTomatoTaskDataListener();
    }

    private void setRedoTomatoTaskDataListener() {
        REDO_TOMATO_TASKS.addListener((ListChangeListener.Change<? extends TomatoTask> change) -> {
            if (change.next()) {
                if (REDO_TOMATO_TASKS.isEmpty()) {
                    mainLayoutController.closeRedoBar();
                } else {
                    mainLayoutController.showRedoBarAndSleep();
                }
            }

        });
    }

    public ObservableList<TomatoTask> getREDO_TOMATO_TASKS() {
        return REDO_TOMATO_TASKS;
    }

    public void setREDO_TOMATO_TASKS(ObservableList<TomatoTask> REDO_TOMATO_TASKS) {
        this.REDO_TOMATO_TASKS = REDO_TOMATO_TASKS;
    }

    private void setTomatoTaskDataListener() {
         getStackedPanes().titledPaneItemsChangeProperty().addListener((observable, oldChange, newChange) -> {

            if (newChange != null) {
                if (newChange.next()) {
                    List removedItems = newChange.getRemoved();
                    List addedSubList = newChange.getAddedSubList();
                    if (!removedItems.isEmpty()) {
                        REDO_TOMATO_TASKS.clear();
                        REDO_TOMATO_TASKS.addAll(removedItems);
                    }

                    newChange.reset();
                }
            }

        });

    }


    private void loadMainLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL name = getClass().getResource("view/MainLayout.fxml");
        loader.setLocation(name);
        mainLayout = loader.load();
        mainLayoutController = loader.getController();
    }

    private void loadRootLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
        rootLayout = loader.load();
        rootLayoutController = loader.getController();
    }

    public static void main(String[] args) {
        setDefaultUncaughtExceptionHandler();
        launch(args);
    }

    private static void setDefaultUncaughtExceptionHandler() {
        GL.logger.info("setDefaultUncaughtExceptionHandler!");
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            GL.logger.warn("UncaughtException",e);
            BriefReport.formatErrorAlert(e);
        });
    }


}
