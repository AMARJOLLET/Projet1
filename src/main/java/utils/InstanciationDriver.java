package utils;


import org.openqa.selenium.WebDriver;

public class InstanciationDriver extends Logging{
    // Variable
    protected final static String snapshotsDirectory = "target/snapshots/";
    protected WebDriver driver;

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public InstanciationDriver (WebDriver driver){
        this.driver = driver;
    }
}
