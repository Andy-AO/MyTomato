package app.view;

import app.Main;
import app.util.PropertiesManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class SettingDialogController  extends Controller {

    public static final PropertiesManager PROPERTIES_MANAGER = PropertiesManager.getPropertiesManager();
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab genericTab;

    @FXML
    private AnchorPane genericAnchorPane;

    @FXML
    private CheckBox developmentCheckBox;

    @FXML
    private void throwException() throws Exception {
        throw new RuntimeException("Exception Throw Test！");
    }

    @Override
    public void setMainAndInit(Main main) {
        super.setMainAndInit(main);
        loadPropertyFromFile();
        addPropertyListener();
    }

     private void loadPropertyFromFile() {
        boolean developmentMode;
        developmentMode = Boolean.parseBoolean(PROPERTIES_MANAGER.getProperty("developmentMode", "false"));
        developmentCheckBox.setSelected(developmentMode);
    }

    private void addPropertyListener() {
        developmentCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            PROPERTIES_MANAGER.setProperty("developmentMode", newValue ? "true" : "false");
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
        boolean result = developmentCheckBox.isSelected();
    }


}
