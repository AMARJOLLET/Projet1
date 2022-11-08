package fr.eql.libreplan.pageObject.PageCalendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageListeDesProjets extends AbstractFullPage {
    public PageListeDesProjets(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    // Bouton Tableau
    public WebElement boutonModifier(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nom + "']/ancestor::tr//span[@title='Modifier']")));
    }

    public WebElement boutonpPrevision(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nom + "']/ancestor::tr//span[@title='Voir la prévision']")));
    }

    public WebElement boutonModele(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nom + "']/ancestor::tr//span[@title='Créer un modèle']")));
    }

    public WebElement boutonSupprimer(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nom + "']/ancestor::tr//span[@title='Supprimer']")));
    }

    // Confirmation Popup Suppression
    public WebElement boutonSupprimerOK(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
    }


/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    // TITRE
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune + "h6"))).getText();
    }


    public PageCalendrierPlanification cliquerOngletPlanificationDesProjets(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, getHeaderCalendrier().mapOngletProjet(wait, idCommune).get("Planification des projets"));
        return new PageCalendrierPlanification(driver);
    }


    // ONGLET
    public List<String> recuperationListeOngletProjet(WebDriverWait wait, String idCommune){
        return getHeaderCalendrier().recuperationListeOngletProjet(wait, idCommune);
    }


    // Tableau
    public Map<String, Map<String, String>> recuperationValeurTableau(WebDriverWait wait, String idCommune){
        Map<String, Map<String, String>> mapRecuperationValeurTableau = new HashMap<>();
        List<WebElement> listLibelle = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class=\"z-window-embedded\"]//tr[@class='z-columns']/th")));
        List<WebElement> listRow = driver.findElements(By.xpath(
                "//div[@class=\"z-window-embedded\"]//tbody[@class='z-rows']/tr"));

        LOGGER.info("Recupération de " + listRow.size() + " rows");
        for(WebElement row : listRow){
            Map<String, String> mapValeurRow = new HashMap<>();
            List<WebElement> listValeurRow = row.findElements(By.xpath(
                    "./td"));
            for(int i = 0; i < listLibelle.size(); i++){

                mapValeurRow.put(listLibelle.get(i).getText(), listValeurRow.get(i).getText());
            }
            LOGGER.info(listLibelle.get(0).getText());
            mapRecuperationValeurTableau.put(listValeurRow.get(0).getText(), mapValeurRow);
        }

        return mapRecuperationValeurTableau;
    }



    // Nettoyage
    public void nettoyageJDD(WebDriverWait wait, String idCommune, String nom) throws Throwable {
        Map<String, Map<String, String>> mapRecuperationValeurTableau = recuperationValeurTableau(wait, idCommune);
        LOGGER.info("Vérification de la présence de " + nom);
        if(mapRecuperationValeurTableau.containsKey(nom)){
            LOGGER.info(nom + " detecté ...");
            seleniumTools.clickOnElement(wait, boutonSupprimer(wait, nom));
            seleniumTools.clickOnElement(wait, boutonSupprimerOK(wait));
            LOGGER.info("Nettoyage terminé");
        }
    }

}
