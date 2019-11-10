package app.util;

import app.control.OnTopAlert;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class BriefReport {

     public static void formatErrorAlert(Throwable ex) {
        String contentText = getBriefReport(ex);
        Alert alert = new OnTopAlert(Alert.AlertType.WARNING, contentText);
        alert.showAndWait();
    }

    private static String getBriefReport(Throwable ex) {
        ArrayList<Throwable> causes = getCauses(ex);
        String briefReport = ex.getMessage() + "\r\n";
        for (Throwable throwable : causes) {
            briefReport += "Caused by: " + throwable.toString() + "\r\n";
        }
        return briefReport;
    }

    private static ArrayList<Throwable> getCauses(Throwable ex) {
        ArrayList<Throwable> list = new ArrayList<>();
        Throwable cause = ex;
        while (true) {
            cause = cause.getCause();
            if (cause != null)
                list.add(cause);
            else
                break;
        }
        return list;
    }
}
