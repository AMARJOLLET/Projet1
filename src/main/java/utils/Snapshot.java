package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Snapshot extends InstanciationDriver {

    public Snapshot(WebDriver driver) {
        super(driver);
    }

    // DateFormat
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd_HH.mm.ss");
    private static final Date date = new Date();
    private final String dateExecution = sdf.format(date.getTime());

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public void snapshot(String className, Throwable error) throws Throwable {
        String snapFile = ".//target//snapshots//" + className + "//" + dateExecution + "_" + className + ".png";
        takeSnapShot(driver, snapFile);
        throw error;
    }

    public void snapshotTst(String className) throws Throwable {
        String snapFile = ".//target//snapshots//" + className + "//" + dateExecution + "_" + className + ".png";
        takeSnapShot(driver, snapFile);
    }
}
