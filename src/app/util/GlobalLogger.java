package app.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class GlobalLogger {

    static public Logger logger = Logger.getLogger(GlobalLogger.class);

    //log
    static {
        String resFileAbsolutePath = ResGetter.getResFile().getAbsolutePath();
        PropertyConfigurator.configure(resFileAbsolutePath + "\\log4j.properties");
        logger.info("Util Static initialize !");
        GlobalLogger.logger.info("ResFile:" + resFileAbsolutePath);
    }


}
