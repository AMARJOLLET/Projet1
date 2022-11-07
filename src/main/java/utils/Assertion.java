package utils;

import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

public class Assertion extends Snapshot {

    public Assertion(WebDriver driver) {
        super(driver);
    }

    public void verifyEquals(String expected, String actual) throws Throwable {
        try{
            assertEquals(expected, actual);
        } catch (Throwable e){
            snapshot(className, e);
        }
    }

    public void verifyEquals(String expected, String actual, String message) throws Throwable {
        try{
            assertEquals(expected, actual, message);
        } catch (Throwable e){
            snapshot(className, e);
        }
    }

    public void verifyEquals(double expected, double actual, double delta, String message) throws Throwable {
        try{
            assertEquals(expected, actual, delta, message);
        } catch (Throwable e){
            snapshot(className, e);
        }
    }

    public void verifyTrue(boolean condition) throws Throwable {
        try{
            assertTrue(condition);
        } catch (Throwable e){
            snapshot(className, e);
        }
    }

    public void verifyTrue(boolean condition, String message) throws Throwable {
        try{
            assertTrue(condition, message);
        } catch (Throwable e){
            snapshot(className, e);
        }
    }

    public void verifyFalse(boolean condition, String message) throws Throwable {
        try{
            assertFalse(condition, message);
        } catch (Throwable e){
            snapshot(className, e);
        }
    }
}
