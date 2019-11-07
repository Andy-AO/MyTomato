package app.control;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class OnTopAlert extends Alert {
    public OnTopAlert(AlertType alertType) {
        super(alertType);
        setAlertAlwaysOnTop(this);
    }

    public OnTopAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        setAlertAlwaysOnTop(this);
    }

    public static Stage getAlertStage(Alert alert) {
        return (Stage) alert.getDialogPane().getScene().getWindow();
    }

    public static void setAlertAlwaysOnTop(Alert alert) {
        getAlertStage(alert).setAlwaysOnTop(true);
        getAlertStage(alert).toFront();
    }
}
