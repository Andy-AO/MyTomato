package test;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class PathGetMain {

    public static File getResFile() throws Exception {
        File resFile = new File("res");
        boolean resDirIsInWorkDir = resFile.exists() & resFile.isDirectory();
        if(resDirIsInWorkDir)
           return resFile;
        else{
            String path = PathGetMain.class.getResource("/").getFile();
            resFile = new File(path);
            boolean resDirIsInJarDir = resFile.exists() & resFile.isDirectory();
            if (resDirIsInJarDir) {
                return resFile;
            } else {
                return null;
//                throw new IOException("res file is not found !");
            }
        }

    }

    public static void main(String[] args) {
    }

}
