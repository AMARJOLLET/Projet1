package fr.eql.libreplan.selenium;

import fr.eql.libreplan.AbstractTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import java.time.Duration;

public abstract class AbstractTestSelenium extends Logging {
    // Driver
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected int implicitWaitingTime = 2;
    protected int explicitWaitingTime = 10;

    // instantiation
    protected OutilsProjet outilsProjet = new OutilsProjet();
    protected InstanciationDriver instanciationDriver;
    protected SeleniumTools seleniumTools;
    protected Assertion assertion;
    protected Snapshot snapshot;
    protected MethodesProjet methodesProjet;

    // Variable
    protected String navigateur = "chrome";
    protected String url = "http://192.168.15.10:8090/libreplan";



    @BeforeEach
    void startup() {
        LOGGER.info("Setup LOGGER ...");
        System.setProperty("logFileName", this.className);
        LOGGER.info("Setup LOGGER effectué");

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
        LOGGER.info("instantiation des classes effectué");

    }



    @AfterEach
    void tearDown() {
        LOGGER.info("Arret du driver ...");
        driver.quit();
        LOGGER.info("Arret du driver effectué");
        //saveAndCleanLogFiles();
    }


}
