package fr.eql.libreplan.pageObject.PageConfiguration;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageConfigurationProfils extends AbstractFullPage {

    public PageConfigurationProfils(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                    WEBELEMENT
######################################################################################################################*/
    public WebElement titreDelaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap")));
    }

    // Message Création
    public WebElement messageCreation(WebDriverWait wait, String nom) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '" + nom + "')]")));
    }


    public WebElement boutonCreer(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,'create-button')]")));
    }

    public WebElement boutonTableau(WebDriverWait wait, String nomProfil, String action){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text()='"+ nomProfil +"']/ancestor::tr//span[@title='" + action + "']")));
    }

    public List<WebElement> listLibelleTableauProfil(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class=\"clickable-rows z-grid\"]//tr[@class=\"z-columns\"]/th")));
    }

    public List<WebElement> listValeurTableauProfil(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class=\"clickable-rows z-grid\"]//tbody[@class=\"z-rows\"]/tr")));
    }






/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    public PageConfigurationProfilsCreer cliquerBoutonCreer(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonCreer(wait));
        return new PageConfigurationProfilsCreer(driver);
    }

    public PageConfigurationProfilsCreer cliquerBoutonModifier(WebDriverWait wait, String nomProfil) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonTableau(wait, nomProfil, "Modifier"));
        return new PageConfigurationProfilsCreer(driver);
    }

    public void cliquerBoutonSupprimer(WebDriverWait wait, String nomProfil) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonTableau(wait, nomProfil, "Supprimer"));
    }


    public List<String> recuperationLibelleTableauProfil(WebDriverWait wait){
        List<String> valeurTableauLibelle = new ArrayList<>();
        for(WebElement valeurLibelle : listLibelleTableauProfil(wait)){
            valeurTableauLibelle.add(valeurLibelle.getText());
        }
        return valeurTableauLibelle;
    }

    public List<String> recuperationNomTableauProfil(WebDriverWait wait){
        List<String> valeurTableauNom = new ArrayList<>();
        for(WebElement valeurRow : listValeurTableauProfil(wait)){
            valeurTableauNom.add(valeurRow.getText());
        }
        return valeurTableauNom;
    }


    // Nettoyage
    public void supressionJdd(WebDriverWait wait,String nom) throws Throwable {
        WebElement boutonSupprimer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//span[text() = '" + nom + "']/ancestor::tr//span[@title='Supprimer']")));
        seleniumTools.clickOnElement(wait, boutonSupprimer);
        WebElement acceptSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
        seleniumTools.clickOnElement(wait, acceptSuppression);
    }

    public void verificationNettoyageTableau(WebDriverWait wait,
                                             String nomProfil, String nomProfil2) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        List<String> listNomTableau = recuperationNomTableauProfil(wait);
        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        LOGGER.info(listNomTableau.size() + " rows detectées");
        if(listNomTableau.contains(nomProfil)){
            LOGGER.info("Présence du JDD " + nomProfil);
            supressionJdd(wait, nomProfil);
            wait.until(ExpectedConditions.invisibilityOf(boutonTableau(wait, nomProfil, "Modifier")));
            LOGGER.info("Suppression effectué");
        }
        if (listNomTableau.contains(nomProfil2)){
            LOGGER.info("Présence du JDD " + nomProfil2);
            supressionJdd(wait, nomProfil2);
            LOGGER.info("Suppression effectué");
            wait.until(ExpectedConditions.invisibilityOf(boutonTableau(wait, nomProfil2, "Modifier")));
        }
    }
}
