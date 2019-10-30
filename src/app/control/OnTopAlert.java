package app.control;

import app.util.Util;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class OnTopAlert extends Alert {
    public OnTopAlert(AlertType alertType) {
        super(alertType);
        Util.setAlertAlwaysOnTop(this);
    }

    public OnTopAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        Util.setAlertAlwaysOnTop(this);
    }
}
