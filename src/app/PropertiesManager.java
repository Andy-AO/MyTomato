package app;

import java.io.*;
import java.util.Properties;

public class PropertiesManager {

//--------------------------------------- Field

    Properties settings = new Properties();
    private static PropertiesManager propertiesManager = null;
    private File propertiesFile;

//--------------------------------------- Setter And Getter

    public static void setPropertiesManager(PropertiesManager propertiesManager) {
        PropertiesManager.propertiesManager = propertiesManager;
    }

    public File getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

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
        String propertiesPath = "res\\properties";
        File propertiesDir = new File(propertiesPath);
        if (!propertiesDir.exists())
            propertiesDir.mkdir();
        propertiesFile = new File(propertiesDir, "Settings.properties");
        createFileIfNotExist();
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
        try (InputStream in = new FileInputStream(propertiesFile)) {
            settings.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return settings.getProperty(key,defaultValue);
    }
}
