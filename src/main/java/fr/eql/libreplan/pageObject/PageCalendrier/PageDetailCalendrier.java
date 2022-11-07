package fr.eql.libreplan.pageObject.PageCalendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class PageDetailCalendrier extends AbstractFullPage {

    public PageDetailCalendrier(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    public List<WebElement> listOngletProjet(WebDriverWait wait, String idCommune){
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//tr[@id='"+idCommune+"r3-chdex']//span[not(@style='display:none;')])[1]")));
        return driver.findElements(By.xpath(
                "//tr[@id='"+idCommune+"r3-chdex']//span[not(@class=\"perspective hidden z-button\")]   "));
    }

    public List<WebElement> listOngletDetailProjet(WebDriverWait wait){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//div[@class=\"z-window-embedded\" and not(contains(@style, 'display:none'))]//li//span)[1]")));
        return driver.findElements(By.xpath("//div[@class=\"z-window-embedded\" and not(contains(@style, 'display:none'))]//li//span"));
    }

    public WebElement boutonEnregistrerProjet(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//table[contains(@class,\"toolbar-box\")]//span[contains(@title,'Enregistrer')]")));
    }

    public WebElement imageBoutonEnregistrerProjet(WebDriverWait wait){
        return boutonEnregistrerProjet(wait).findElement(By.xpath(".//img"));
    }

    public WebElement boutonAnnulerEdition(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//table[contains(@class,\"toolbar-box\")]//span[contains(@title,'Annuler')]")));
    }

    public WebElement imageBoutonboutonAnnulerEdition(WebDriverWait wait){
        return boutonAnnulerEdition(wait).findElement(By.xpath(".//img"));
    }

    // Popup Annulation
    public WebElement textPopupAnnulationEdition(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class=\"z-messagebox\"]/span")));
    }

    public WebElement boutonOKPopupAnnulationEdition(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//span[@class=\"z-messagebox-btn z-button\"])[1]")));

    }

    public WebElement boutonAnnulerPopupAnnulationEdition(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//span[@class=\"z-messagebox-btn z-button\"])[2]")));    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    public void cliquerBoutonAnnulerEdition(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnulerEdition(wait));
    }

    public void cliquerBoutonAnnulerPopup(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnulerPopupAnnulationEdition(wait));
    }

    public void cliquerBoutonOkPopup(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonOKPopupAnnulationEdition(wait));
    }


    public List<String> recuperationListeOngletProjet(WebDriverWait wait, String idCommune){
        List<String> listOngletProjetString = new ArrayList<>();
        for(WebElement onglet : listOngletProjet(wait, idCommune)){
            listOngletProjetString.add(onglet.getText());
        }
        return listOngletProjetString;
    }

    public List<String> recuperationListeOngletDetailProjet(WebDriverWait wait){
        List<String> listOngletDetailProjetString = new ArrayList<>();
        for(WebElement onglet : listOngletDetailProjet(wait)){
            listOngletDetailProjetString.add(onglet.getText());
        }
        return listOngletDetailProjetString;
    }
}
