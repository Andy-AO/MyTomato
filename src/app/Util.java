package app;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Util {

    static String timeFormatterPattern = "HH:mm";
    static DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern(timeFormatterPattern);

    static String dateFormatterPattern = "MM/dd";
    static DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern(dateFormatterPattern);

    public  static String localTimeToString(LocalTime localTime) {
        return localTime.format(timeFormatter);
    }
    public  static String localDateToString(LocalDate localDate) {
        return localDate.format(dateFormatter);
    }


    public static Stage getAlertStage(Alert alert) {
        return (Stage) alert.getDialogPane().getScene().getWindow();
    }

    public static void setAlertAlwaysOnTop(Alert alert) {
        Util.getAlertStage(alert).setAlwaysOnTop(true);
        Util.getAlertStage(alert).toFront();
    }

}
