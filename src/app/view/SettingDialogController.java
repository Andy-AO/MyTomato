package app.view;

import app.Main;
import app.PropertiesManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.util.Properties;
import java.util.prefs.Preferences;

public class SettingDialogController {

    public static final PropertiesManager PROPERTIES_MANAGER = PropertiesManager.getPropertiesManager();
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

    private File propertiesFile;

    public void setMainAndInit(Main main) {
        this.main = main;
        initProperty();
        loadPropertyFromFile();
        addPropertyListener();
    }

    private void initProperty() {
        propertiesFile = PROPERTIES_MANAGER.getPropertiesFile();
    }

    private void loadPropertyFromFile() {
        boolean developmentMode;
        developmentMode = Boolean.parseBoolean(PROPERTIES_MANAGER.getProperty("developmentMode", "false"));
        developmentCheckBox.setSelected(developmentMode);
    }

    private void addPropertyListener() {
        developmentCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            settings.setProperty("developmentMode", newValue ? "true" : "false");
            try (OutputStream out = new FileOutputStream(propertiesFile)) {
                settings.store(out, "Program Properties");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
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
