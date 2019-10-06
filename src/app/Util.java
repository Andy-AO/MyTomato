package app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Util {

    static String timeFormatterPattern = "kk:mm";
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


}
