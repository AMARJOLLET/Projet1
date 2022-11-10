package utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public abstract class Logging {

    protected String className;
    protected String currentClassPath;
    protected String tempTestLogFilePath;
    protected String logsDirectory;
    protected final Logger LOGGER;

    //Local java.util.logging.Logger which will be used only in this Logging.java class :
    private final java.util.logging.Logger LOCALOGGER = java.util.logging.Logger.getLogger("LoggingClassLogger");

    protected Logging() {
        // Echouer la construction en cas d'absence de fichier de configuration
        Properties prop = new Properties();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("log4j2.properties");
        try {
            prop.load(input);
        } catch (Exception e) {
            LOCALOGGER.log(Level.SEVERE, "\nYou must provide a log4j2.properties configuration file.");
            e.printStackTrace();
            System.exit(-1);
        }
        // Affectation des variables dynamiques
        try {
            logsDirectory = getLogsDirectoryName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        className = getClass().getSimpleName();
        currentClassPath = getClass().getName().replace(".", "/");
        tempTestLogFilePath = logsDirectory + "/temp/" + className + ".log";
        System.setProperty("logFilename", className);
        // Initialisation fichiers de logs et logger
        try {
            initializeLogFileAndTree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER = org.slf4j.LoggerFactory.getLogger(className);
        if (!selectTestClassPath().equals("notATestClass")) {
            LOGGER.warn("\nPlease make sure your log4j2 configuration is complete.\n" +
                    "It should include a full rolling logger file configuration in order to save your tests logs history.\n" +
                    "You may use the log4j2.properties example file available in CNAF-QA-TOOLS 'src/test/resources/lo4j2Example.properties'");
            LOGGER.warn("\nPlease make sure your test class is located within a package of src/test/java containing the word 'test'\n" +
                    "- for instance, src/test/java/org.project.test will work - otherwise logs will not be saved.\n");
            LOGGER.warn("\nMake sure to call saveAndCleanLogFiles() method after each test.");
            LOGGER.info("\nLogs will be saved here from project root directory : " + logsDirectory);
        }
    }


    /**
     * Cette méthode récupère le chemin du dossier de destination des logs dans log4j2.properties s'il est fourni.
     *
     * @return le chemin de destination des logs
     */
    public String getLogsDirectoryName() throws IOException {
        Properties prop = new Properties();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("log4j2.properties");
        try {
            prop.load(input);
        } catch (IOException e) {
            LOCALOGGER.log(Level.SEVERE, "Logs directory hasn't been defined in log4j2.properties.");
            throw e;
        }
        return prop.getProperty("property.filename");
    }

    /**
     * Pour chaque classe instanciant le loggeur, cette méthode permet de déterminer si il s'agit d'une classe de test ou non.
     * Si c'est le cas, la méthode renvoie le chemin de la classe de test.
     * REM : toutes les classes de test doivent se situer dans un package contenant le mot 'test' de src/test/java.
     * Par exemple, src/test/java/org.project.test/monTest.java sera reconnu comme une classe de test.
     * REM : Le mot "test" doit être unique dans le path situé après src/test/java.
     *
     * @return String : le path de la classe de test (si c'est une classe test), "notATestClass" sinon.
     */
    protected String selectTestClassPath() {
        String testClassPath = null;
        Path s = Paths.get(currentClassPath);
        int n = s.getNameCount();
        while (n > 1) {
            s = s.getParent();
            n -= 1;
//          LOGGER.info("Boucle en cours. Situation dans l'arbo :" +s.toString());
            if (s.toString().endsWith("test")) {
                testClassPath = Paths.get(currentClassPath).getParent().toString().replace("\\", "/");
                break;
            } else {
                testClassPath = "notATestClass";
            }
        }
        return testClassPath;
    }

    /**
     * Pour chaque classe instanciant le loggeur, cette méthode permet :
     * > D'initier le log temporaire, même si ce n'est pas une classe de test
     * > De créer l'arborescence et le fichier de log d'une classe de test, si inexistant
     * > De copier l'historique du log existant d'une classe de test dans le log temporaire associé
     * REM : L'arborescence / le log n'est pas créé si la classe n'est pas une classe de test
     */
    protected void initializeLogFileAndTree() throws IOException {
        // Initialisation (si inexistant) du log temporaire pour toutes les classes (nécéssaire pour log4j2)
        File tempTestLogFile = new File(tempTestLogFilePath);
        if (!tempTestLogFile.exists()) {
            tempTestLogFile.getParentFile().mkdirs();
            try {
                tempTestLogFile.createNewFile();
            } catch (IOException e) {
                LOCALOGGER.log(Level.SEVERE, "---------- Exception caught ----------", e);
                throw e;
            }
        }
        // Copie de l'historique d'un log de test vers son log temporaire
        if (!selectTestClassPath().equals("notATestClass")) {
            String testLogFilePath = logsDirectory + "/" + selectTestClassPath() + "/" + className + ".log";
            File testLogFile = new File(testLogFilePath);
            // Initialisation du log de test si inexistant
            if (!testLogFile.exists()) {
                testLogFile.getParentFile().mkdirs();
                try {
                    testLogFile.createNewFile();
                } catch (IOException e) {
                    LOCALOGGER.log(Level.SEVERE, "---------- Exception caught ----------", e);
                    throw e;
                }
            }
            // Copie
            try {
                Files.copy(Paths.get(testLogFilePath), Paths.get(tempTestLogFilePath), REPLACE_EXISTING);
            } catch (IOException e) {
                LOCALOGGER.log(Level.SEVERE, "A log history couldn't be recovered.");
            }
        }
    }

    /**
     * Pour chaque classe instanciant le loggeur, cette méthode permet :
     * > De récupérer le log temporaire d'une classe de test et de le copier dans le log de cette classe
     * > De supprimer les logs temporaires
     */
    public void saveAndCleanLogFiles() {
        // Récupération du log temporaire d'un test et sauvegarde
        if (!selectTestClassPath().equals("notATestClass")) {
            String testLogFilePath = logsDirectory + "/" + selectTestClassPath() + "/" + className + ".log";
            try {
                Files.copy(Paths.get(tempTestLogFilePath), Paths.get(testLogFilePath), REPLACE_EXISTING);
            } catch (IOException e) {
                LOGGER.info("A temporary log couldn't be saved.");
            }
        }
        // Suppression des logs temporaires
        try {
            FileUtils.deleteDirectory(new File(Paths.get(tempTestLogFilePath).getParent().toString()));
        } catch (IOException ex) {
//            LOGGER.info("A temporary log couldn't be deleted");
        }
    }

}
