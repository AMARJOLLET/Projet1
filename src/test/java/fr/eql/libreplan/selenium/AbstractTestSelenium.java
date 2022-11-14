package fr.eql.libreplan.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;

public abstract class AbstractTestSelenium extends Logging {
    protected String className = getClass().getSimpleName();
    protected String classPackage = getClass().getPackage().getName();
    protected String classPackageLogs = getClass().getName();

    // URL MACHINE HOST
    protected String urlMachineHost = "192.168.15.10";

    // Driver
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected int implicitWaitingTime = 2;
    protected int explicitWaitingTime = 10;

    // instantiation
    protected OutilsManipulationDonnee outilsManipulationDonnee = new OutilsManipulationDonnee();
    protected OutilsProjet outilsProjet = new OutilsProjet(driver);
    protected InstanciationDriver instanciationDriver;
    protected SeleniumTools seleniumTools;
    protected Assertion assertion;
    protected Snapshot snapshot;
    protected MethodesProjet methodesProjet;

    // Variable
    protected String navigateur = "chrome";
    protected String url = "http://"+urlMachineHost+":8090/libreplan";

    // SQL
    protected String databaseURL = "jdbc:postgresql://192.168.15.10:5432/libreplan";
    protected String userDatabase = "libreplan";
    protected String passwordDatabase = "secret";
    protected Connection connection;


    @BeforeEach
    void startup() {
        LOGGER.info("Setup Database connection");
        try {
            connection = outilsManipulationDonnee.connection(databaseURL, userDatabase, passwordDatabase);
        } catch (SQLException e){
            LOGGER.error("Error sql " + e);
        }

        LOGGER.info("Setup Choix driver " + navigateur + " ...");
        switch (navigateur.toLowerCase()) {
            case "firefox" :
                System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "chrome" :
                System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "edge" :
                System.setProperty("webdriver.edge.driver", "src/main/resources/driver/msedgedriver.exe");
                driver = new EdgeDriver();
                break;
        }
        LOGGER.info("Setup Choix driver " + navigateur + " effectué");

        LOGGER.info("Setup wait et driver ...");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitingTime));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitingTime));
        LOGGER.info("Setup wait et driver effectué");

        LOGGER.info("instantiation des classes avec driver");
        instanciationDriver = new InstanciationDriver(driver);
        seleniumTools = new SeleniumTools(driver);
        assertion = new Assertion(driver);
        snapshot = new Snapshot(driver);
        methodesProjet = new MethodesProjet(driver);
        outilsProjet = new OutilsProjet(driver);
        LOGGER.info("instantiation des classes effectué");

    }



    @AfterEach
    void tearDown() {
        LOGGER.info("Arret du driver ...");
        driver.quit();
        LOGGER.info("Arret du driver effectué");
        LOGGER.info("Generate log file");
        createAndCleanLogFile(className, classPackageLogs);
    }


}
