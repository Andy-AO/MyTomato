package app_3;

import app.OnTopAlert;
import app.TomatoTaskDataJson;
import app.model.TomatoTask;
import app.view.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main extends Application {

    public static final int PRIMARY_STAGE_MIN_WIDTH = 415 ;

    private Stage primaryStage;

    private Image tomatoImage = new Image(Main.getResURLString() + "image/tomato.png");

    private BorderPane rootLayout;
    private AnchorPane mainLayout;

    private static String getResURLString() {
        return Main.getResURIString();
    }
    private static String getResURIString() {
        File file = Main.getResFile();
        String URIString = file.toURI().toString();
        System.out.println("URIString:" + URIString);
        return URIString;
    }


    private MainLayoutController mainLayoutController;
    private RootLayoutController rootLayoutController;


    public static String getJarDirPath()
    {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if(System.getProperty("os.name").contains("dows"))
        {
            path = path.substring(1,path.length());
        }
        if(path.contains("jar"))
        {
            path = path.substring(0,path.lastIndexOf("."));
            return path.substring(0,path.lastIndexOf("/"));
        }
        return path.replace("target/classes/", "");
    }


    public static File getResFile()  {
        File resFile = new File("res");
        boolean resDirIsInWorkDir = resFile.exists() && resFile.isDirectory();
        if(resDirIsInWorkDir)
            return resFile;
        else{
            String path = getJarDirPath();
            System.out.println("getJarDirPath():" + path);
            resFile = new File(path,"res");
            boolean resDirIsInJarDir = resFile.exists() && resFile.isDirectory();
            if (resDirIsInJarDir) {
                return resFile;
            } else {
                Alert alert = new OnTopAlert(Alert.AlertType.WARNING, "res files dir is not found !");
                alert.showAndWait();
                System.exit(1);
                return resFile;
        }
        }

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
//        mainLayoutController.setMainAndInit(this);
//        rootLayoutController.setMainAndInit(this);
    }

    private void loadLayout() throws IOException {
        loadRootLayout();
        loadMainLayout();

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

    private void initPrimaryStage() {
        primaryStage.setTitle("MyTomato");
        primaryStage.setScene(new Scene(getRootLayout()));
        primaryStage.setMinWidth(PRIMARY_STAGE_MIN_WIDTH);
        primaryStage.getIcons().add(tomatoImage);

    }

    public Main() {
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


}
