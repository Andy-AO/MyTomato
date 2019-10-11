package app.view;

import app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.util.Properties;
import java.util.prefs.Preferences;

public class SettingDialogController {
    public static final String PREFERENCES_PATH = "/com/myself/myTomato";
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab genericTab;

    @FXML
    private AnchorPane genericAnchorPane;

    @FXML
    private CheckBox developmentCheckBox;

    private Main main;

    Properties settings = new Properties();

    public static final boolean DEVELOPMENT_MODE_DEFAULT_VALUE = false;
    private File propertiesFile;

    public void setMainAndInit(Main main) {
        this.main = main;
        initProperty();
        loadPropertyFromFile();
        addPropertyListener();
    }

    private void initProperty() {
        String userDir = "res\\properties";
        File propertiesDir = new File(userDir);
        if (!propertiesDir.exists()) propertiesDir.mkdir();
        propertiesFile = new File(propertiesDir, "Settings.properties");
    }

    private void addPropertyListener() {
        developmentCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            settings.setProperty("developmentMode", newValue ? "true" : "false");
            try (OutputStream out = new FileOutputStream(propertiesFile)){
                settings.store(out, "Program Properties");
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
          });
    }

    private void loadPropertyFromFile() {
        boolean developmentMode;
        if (propertiesFile.exists()){
            try (InputStream in = new FileInputStream(propertiesFile))
            {
                settings.load(in);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
             developmentMode = Boolean.parseBoolean(settings.getProperty("developmentMode"));
        }
        else {
            developmentMode = false;
        }
        developmentCheckBox.setSelected(developmentMode);
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public Tab getGenericTab() {
        return genericTab;
    }

    public void setGenericTab(Tab genericTab) {
        this.genericTab = genericTab;
    }

    public AnchorPane getGenericAnchorPane() {
        return genericAnchorPane;
    }

    public void setGenericAnchorPane(AnchorPane genericAnchorPane) {
        this.genericAnchorPane = genericAnchorPane;
    }

    public CheckBox getDevelopmentCheckBox() {
        return developmentCheckBox;
    }

    public void setDevelopmentCheckBox(CheckBox developmentCheckBox) {
        this.developmentCheckBox = developmentCheckBox;
    }

    @FXML
    private void handleDevelopmentCheckBox() {
        System.out.println("handleDevelopmentCheckBox");
        boolean result = developmentCheckBox.isSelected();
        System.out.println(result);
    }


}
