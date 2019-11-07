package app.util;

import app.Main;
import app.control.OnTopAlert;
import javafx.scene.control.Alert;

import java.io.File;

public class ResGetter {
    public static String getResURLString() {
        return getResURIString();
    }

    public static String getResURIString() {
        File file = getResFile();
        String URIString = file.toURI().toString();
        System.out.println("URIString:" + URIString);
        return URIString;
    }

    public static String getJarDirPath() {
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (System.getProperty("os.name").contains("dows")) {
            path = path.substring(1, path.length());
        }
        if (path.contains("jar")) {
            path = path.substring(0, path.lastIndexOf("."));
            return path.substring(0, path.lastIndexOf("/"));
        }
        return path.replace("target/classes/", "");
    }

    public static File getResFile() {
        File resFile = new File("res");
        boolean resDirIsInWorkDir = resFile.exists() && resFile.isDirectory();
        if (resDirIsInWorkDir)
            return resFile;
        else {
            String path = getJarDirPath();
            System.out.println("getJarDirPath():" + path);
            resFile = new File(path, "res");
            boolean resDirIsInJarDir = resFile.exists() && resFile.isDirectory();
            if (resDirIsInJarDir) {
                return resFile;
            } else {
                Alert alert = new OnTopAlert(Alert.AlertType.WARNING, "res files dir is not found !");
                alert.showAndWait();
                System.exit(1);
                return resFile;
            }
        }

    }
}
