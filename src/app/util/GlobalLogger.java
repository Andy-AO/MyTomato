package app.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class GlobalLogger {

    static public Logger logger = Logger.getLogger(GlobalLogger.class);

    static {
        String resFileAbsolutePath = ResGetter.getResFile().getAbsolutePath();
        PropertyConfigurator.configure(resFileAbsolutePath + "\\log4j.properties");
        GlobalLogger.logger.info("ResFile:" + resFileAbsolutePath);
    }

}
