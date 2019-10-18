package app;

import app.view.FinishDialogController;
import app.view.RootLayoutController;
import app.view.SettingDialogController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import app.view.MainLayoutController;
import app.model.TomatoTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main extends Application {

    public static final int PRIMARY_STAGE_MIN_WIDTH = 360;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane mainLayout;

    private Stage finishDialogStage = null;
    private AnchorPane finishDialog;
    private FinishDialogController finishDialogController;
    private TomatoTaskDataJson tomatoTaskDataJson;
    private MainLayoutController mainLayoutController;
    private RootLayoutController rootLayoutController;
    private TabPane settingDialog;
    private SettingDialogController settingDialogController;
    private Stage startSettingStage = null;

    private ObservableList<TomatoTask> TOMATO_TASKS = FXCollections.observableArrayList();
    private ObservableList<TomatoTask> REDO_TOMATO_TASKS = FXCollections.observableArrayList();
    private final String  JSON_PATH = "res\\json\\tomatoTaskData.json";

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

    public void setTOMATO_TASKS(ObservableList<TomatoTask> TOMATO_TASKS) {
        this.TOMATO_TASKS = TOMATO_TASKS;
    }

    public AnchorPane getFinishDialog() {
        return finishDialog;
    }

    public void setFinishDialog(AnchorPane finishDialog) {
        this.finishDialog = finishDialog;
    }

    public TomatoTaskDataJson getTomatoTaskDataJson() {
        return tomatoTaskDataJson;
    }

    public void setTomatoTaskDataJson(TomatoTaskDataJson tomatoTaskDataJson) {
        this.tomatoTaskDataJson = tomatoTaskDataJson;
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
        setMainAndInitLayout();
        initPrimaryStage();
        this.primaryStage.show();
    }

    private void setMainAndInitLayout() {
        mainLayoutController.setMainAndInit(this);
        finishDialogController.setMainAndInit(this);
        rootLayoutController.setMainAndInit(this);
        settingDialogController.setMainAndInit(this);
    }

    private void loadLayout() throws IOException {
        loadRootLayout();
        loadMainLayout();
        loadFinishDialog();
        loadSettingDialog();
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
            e.printStackTrace();
        }
    }

    private void initPrimaryStage() {
        primaryStage.setTitle("MyTomato");
        primaryStage.setScene(new Scene(getRootLayout()));
        primaryStage.setMinWidth(PRIMARY_STAGE_MIN_WIDTH);
        setPrimaryStageListener();
    }

    private void setPrimaryStageListener() {
        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> isFocused, Boolean onHidden, Boolean onShown){
                if(isFocused.getValue()){
                    mainLayoutController.initHeadText();
                }
            }
        });

        primaryStage.setOnCloseRequest(evt -> {
            // prevent window from closing
            evt.consume();
            // execute own shutdown procedure
            boolean isClosed = onStageShutdown(primaryStage);
            if(isClosed)
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
            e.printStackTrace();
        }
    }

    public Stage getFinishDialogStage() {
        return finishDialogStage;
    }

    public void setFinishDialogStage(Stage finishDialogStage) {
        this.finishDialogStage = finishDialogStage;
    }

    public void startFinishDialogAndWait() {
        if (finishDialogStage == null) {
            finishDialogStage = new Stage();
            finishDialogStage.setAlwaysOnTop(true);
            finishDialogStage.initOwner(primaryStage);
            finishDialogStage.setTitle("请输入刚刚完成的任务");
            finishDialogStage.setScene(new Scene(finishDialog));
            finishDialogStage.setResizable(false);
            finishDialogStage.setOnCloseRequest(evt -> {
                evt.consume();
                onStageShutdown(finishDialogStage);
            });
        }
        finishDialogStage.showAndWait();
    }

    public void startSettingDialogAndWait() {
        if (startSettingStage == null) {
            startSettingStage = new Stage();
            startSettingStage.setAlwaysOnTop(true);
            startSettingStage.setTitle("SettingDialog");
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
        }
        else
            return false;
    }


    public Main() {
        intiTomatoTaskData();
    }

    private void intiTomatoTaskData() {
        tomatoTaskDataJson = new TomatoTaskDataJson(TOMATO_TASKS,JSON_PATH);
        tomatoTaskDataJson.read();
        setTomatoTaskDataListener();
        setRedoTomatoTaskDataListener();
    }

    private void setRedoTomatoTaskDataListener() {
        REDO_TOMATO_TASKS.addListener((ListChangeListener.Change<? extends TomatoTask> change) -> {
            if (change.next()) {
                if (REDO_TOMATO_TASKS.isEmpty()) {
                    mainLayoutController.closeRedoBar();
                }
                else {
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
        TOMATO_TASKS.addListener((ListChangeListener.Change<? extends TomatoTask> change) -> {
            System.out.println("setTomatoTaskDataListener！");
            tomatoTaskDataJson.write();
            if(change.next()){
                List removedItems =  change.getRemoved();
                List addedSubList =  change.getAddedSubList();
                if(!removedItems.isEmpty()){
                    REDO_TOMATO_TASKS.clear();
                    REDO_TOMATO_TASKS.addAll(removedItems);
                    System.out.println("removedItems:" + removedItems);
                }
                boolean sortAble = (!addedSubList.isEmpty()) | (!removedItems.isEmpty());
                System.out.println("sortAble:"+ sortAble);
                if(sortAble){
                    System.out.println("resort！");
                    mainLayoutController.sort();
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
        launch(args);
    }

    public ObservableList<TomatoTask> getTOMATO_TASKS() {
        return TOMATO_TASKS;
    }

}
