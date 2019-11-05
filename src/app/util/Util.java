package app.util;

import app.control.OnTopAlert;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Util {

    static String timeFormatterPattern = "HH:mm";
    static DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern(timeFormatterPattern);

    static String dateFormatterPattern = "MM/dd";
    static DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern(dateFormatterPattern);

    public static String localTimeToString(LocalTime localTime) {
        return localTime.format(timeFormatter);
    }

    public static String localDateToString(LocalDate localDate) {
        return localDate.format(dateFormatter);
    }


    public static Stage getAlertStage(Alert alert) {
        return (Stage) alert.getDialogPane().getScene().getWindow();
    }

    public static void setAlertAlwaysOnTop(Alert alert) {
        Util.getAlertStage(alert).setAlwaysOnTop(true);
        Util.getAlertStage(alert).toFront();
    }

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
