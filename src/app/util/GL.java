package app.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class GL {

    static public Logger logger = Logger.getLogger(GL.class);

    static {
        String resFileAbsolutePath = ResGetter.getResFile().getAbsolutePath();
        PropertyConfigurator.configure(resFileAbsolutePath + "\\properties\\log4j.properties");
        GL.logger.info("ResFile:" + resFileAbsolutePath);
    }

}
