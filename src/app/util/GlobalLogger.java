package app.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class GlobalLogger {

    static public Logger logger = Logger.getLogger(GlobalLogger.class);

    //log
    static {
        PropertyConfigurator.configure(ResGetter.getResFile().getAbsolutePath() + "\\log4j.properties");
        logger.debug("Util Static initialize !");
    }


}
