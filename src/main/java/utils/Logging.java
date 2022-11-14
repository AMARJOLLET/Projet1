package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Logging {
    public static String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
    public Logger LOGGER;
    public String className = getClass().getSimpleName();
    public String pathLog4j2 = "src/main/resources/log4j2.xml";
    public String tempDir = "target/logs/temp";
    public static String tempName = "temp-" + date;


    public Logging() {
        System.setProperty("tempDir", tempDir);
        System.setProperty("tempName", tempName);
        LOGGER = LoggerFactory.getLogger(className);
    }

    public void setupLogging(){
        File file = new File(pathLog4j2);
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        context.setConfigLocation(file.toURI());
    }

    public void createAndCleanLogFile(String className, String classPackage) {
        createLogFile(className, classPackage);
        cleanTempLogFile();
    }

    public void createLogFile(String className, String classPackage) {
        classPackage = classPackage.replace(".", "/");
        String logPath = "target/logs/" + classPackage + className + "." + date + ".log";
        File logFile = new File(logPath);
        try {
            if (!logFile.exists()) {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            }
        } catch (IOException ex) {
            LOGGER.error("error : " + ex);
        }
        try {
            Files.copy(Paths.get(tempDir + "/" + tempName + ".log"), Paths.get(logPath), REPLACE_EXISTING);
        } catch (IOException ex) {
            LOGGER.error("error : " + ex);
        }

    }

    public void cleanTempLogFile() {
        File dir = new File(tempDir);
        File[] files = dir.listFiles();

        for(File fileSearched : files){
            if(!fileSearched.getName().contains(tempName)){
                fileSearched.delete();
            }
        }
    }



}