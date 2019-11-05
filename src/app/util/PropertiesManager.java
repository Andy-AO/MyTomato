package app.util;

import app.Main;

import java.io.*;
import java.util.Properties;

public class PropertiesManager {

//--------------------------------------- Field

    private Properties settings = new Properties();
    private static PropertiesManager propertiesManager = null;
    private File propertiesFile;
    private static final String PROPERTIES_DIR_PATH = "properties";

//--------------------------------------- Setter And Getter


//--------------------------------------- Constructor

    private PropertiesManager() {
        initFile();
    }

//--------------------------------------- Method

    public static PropertiesManager getPropertiesManager() {
        if (propertiesManager == null) {
            propertiesManager = new PropertiesManager();
        }
        return propertiesManager;
    }


    private void initFile() {
        File propertiesDir = new File(Main.getResFile(),PROPERTIES_DIR_PATH);
        if (!propertiesDir.exists())
            propertiesDir.mkdir();
        propertiesFile = new File(propertiesDir, "Settings.properties");
        createFileIfNotExist();
        loadSettings();
    }

    private void createFileIfNotExist() {
        if (!(propertiesFile.exists())) {
            try (OutputStream out = new FileOutputStream(propertiesFile)) {

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getProperty(String key, String defaultValue) {
        loadSettings();
        return settings.getProperty(key,defaultValue);
    }

    private void loadSettings() {
        try (InputStream in = new FileInputStream(propertiesFile)) {
            settings.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setProperty(String key, String newValue) {
        settings.setProperty(key, newValue);
        storeSettings();
    }

    private void storeSettings() {
        try (OutputStream out = new FileOutputStream(propertiesFile)) {
            settings.store(out, "Program Properties");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}