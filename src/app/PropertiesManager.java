package app;

import java.io.*;

public class PropertiesManager {

//--------------------------------------- Field


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


}
