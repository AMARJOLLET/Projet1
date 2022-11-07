package fr.eql.libreplan.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageLogin extends AbstractFullPage{

    public PageLogin(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /*######################################################################################################################
                                                      WEBELEMENTS
    ######################################################################################################################*/
    @FindBy(id = "textfield")
    WebElement usernameField;

    @FindBy (xpath = "//input[@type=\"password\"]")
    WebElement passwordField;

    @FindBy (xpath = "//input[@type=\"submit\"]")
    WebElement seConnecter;

/*######################################################################################################################
													METHODES
######################################################################################################################*/

    public PageCalendrierPlanification seConnecter(WebDriverWait wait, String username, String password) throws Throwable {
        LOGGER.info("Renseigne le username + " + username);
        seleniumTools.sendKey(wait, usernameField, username);
        LOGGER.info("Renseigne le username + " + password);
        seleniumTools.sendKey(wait, passwordField, password);
        LOGGER.info("Connection en tant " + username);
        seleniumTools.clickOnElement(wait, seConnecter);
        return new PageCalendrierPlanification(driver);
    }

}

