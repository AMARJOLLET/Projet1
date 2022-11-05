package fr.eql.libreplan.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderPage extends AbstractFullPage {

    public HeaderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/

    // Calendrier et ses sous-menus
    public WebElement calendrierButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "7-b")));
    }

    public WebElement calendrierProjetsButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "9-a")));
    }

    public WebElement calendrierChargementDesRessourcesButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "b-a")));
    }

    public WebElement calendrierRessourcesEnFileButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "c-a")));
    }

    public WebElement calendrierCavenasButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "d-a")));
    }

    public WebElement calendrierImporterUnProjetButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "e-a")));
    }


    // Resources et ses sous-menus
    public WebElement ressourcesButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "r-b")));
    }

    public WebElement ressourcesCritereButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "y-a")));
    }

}
