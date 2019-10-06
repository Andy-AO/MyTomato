package app.view;

import app.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RootLayoutController {

    private Main main;
    @FXML
    private BorderPane rootLayout;
    @FXML
    private MenuBar MenuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem settingsMenuItem;

    @FXML
    private void handleDeleteButton() {
        main.getMainLayoutController().handleDeleteButton();
    }
    @FXML
    private void handleSettingsMenuItem() {
        main.startSettingDialogAndWait();
    }

    public void setMainAndInit(Main main) {
        this.main = main;
        rootLayout.setCenter(main.getMainLayout());
    }

}
