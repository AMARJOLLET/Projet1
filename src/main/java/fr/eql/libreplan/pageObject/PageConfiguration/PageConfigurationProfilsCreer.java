package fr.eql.libreplan.pageObject.PageConfiguration;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageConfigurationProfilsCreer extends AbstractFullPage {

    public PageConfigurationProfilsCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                    WEBELEMENT
######################################################################################################################*/
    public WebElement titreDelaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "x4-cnt")));
    }

    public WebElement titreduFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "_5-hm")));
    }

    public WebElement inputNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "a5")));
    }

    public WebElement blocAssociation(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//fieldset[@id='" + idCommune + "b5']")));
    }

    public WebElement titreBlocAssociation(WebDriverWait wait, String idCommune){
        return blocAssociation(wait, idCommune).findElement(By.xpath("./legend"));
    }

    public WebElement inputRole(WebDriverWait wait, String idCommune){
        return blocAssociation(wait, idCommune).findElement(By.xpath(".//input"));
    }

    public WebElement boutonAjouterRole(WebDriverWait wait, String idCommune){
        return blocAssociation(wait, idCommune).findElement(By.xpath(".//span[@class=\"z-button\"]"));
    }

    public List<WebElement> listLibelleTableauRole(WebDriverWait wait, String idCommune){
        return blocAssociation(wait, idCommune).findElements(By.xpath(".//tr[@class=\"z-columns\"]/th"));
    }

    public List<WebElement> listValeurTableauRole(WebDriverWait wait, String idCommune){
        return blocAssociation(wait, idCommune).findElements(By.xpath(".//tbody[@class=\"z-rows\"]/tr"));
    }

    public WebElement boutonEnregistrer(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,\"save-button\")]")));
    }

    public WebElement boutonSauverEtContinuer(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,\"saveandcontinue-button\")]")));
    }

    public WebElement boutonAnnuler(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,\"cancel-button\")]")));
    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    public PageConfigurationProfils cliquerBoutonEnregistrer(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait));
        return new PageConfigurationProfils(driver);
    }


    // FORMULAIRE
    public void remplirNom(WebDriverWait wait, String idCommune, String nomProfil) throws Throwable {
        LOGGER.info("Renseigne le nom avec " + nomProfil);
        seleniumTools.sendKey(wait, inputNom(wait, idCommune), nomProfil);
    }

    public void remplirRoleEtAjouter(WebDriverWait wait, String idCommune, String roleProfil) throws Throwable {
        LOGGER.info("Renseigne le role avec " + roleProfil);
        seleniumTools.sendKey(wait, inputRole(wait, idCommune), roleProfil);
        LOGGER.info("Ajout du role");
        seleniumTools.clickOnElement(wait, boutonAjouterRole(wait,idCommune));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='"+roleProfil+"']")));
    }


    // RECUPERATION DU TABLEAU
    public List<String> recuperationLibelleTableauRole(WebDriverWait wait, String idCommune){
        List<String> listLibelleTableau = new ArrayList<>();
        for(WebElement libelle : listLibelleTableauRole(wait, idCommune)){
            listLibelleTableau.add(libelle.getText());
        }
        return listLibelleTableau;
    }


    public Map<String, WebElement> recuperationValeurTableau(WebDriverWait wait, String idCommune){
        Map<String, WebElement> mapValeurTableau = new HashMap<>();
        for (WebElement valeur : listValeurTableauRole(wait, idCommune)){
            WebElement nomValeur = valeur.findElement(By.xpath(".//span[@class=\"z-label\"]"));
            WebElement boutonSupprimer = valeur.findElement(By.xpath(".//span[@title=\"Supprimer\"]"));
            mapValeurTableau.put(nomValeur.getText(), boutonSupprimer);
        }
        return mapValeurTableau;
    }

}
