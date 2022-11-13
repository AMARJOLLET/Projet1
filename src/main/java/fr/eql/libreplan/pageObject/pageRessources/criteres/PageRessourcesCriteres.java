package fr.eql.libreplan.pageObject.pageRessources.criteres;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class PageRessourcesCriteres extends AbstractFullPage {
    public PageRessourcesCriteres(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    // Titre
    public WebElement titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap")));
    }

    // Message Suppression
    public WebElement messageSuppression(WebDriverWait wait, String nom) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '\"" + nom + "\" supprimé')]")));
    }


    // BOUTON
    public WebElement boutonCreer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//table[@id = '" + idCommune + "_5-box']//td[text() = 'Créer']")));
    }


    // TABLEAU
    public List<WebElement> listLibelleTableau(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class='clickable-rows z-grid']//tr[@class='z-columns']/th")));
    }

    public List<WebElement> listRowTableau(){
        return driver.findElements(By.xpath(
                "//div[@class='clickable-rows z-grid']//tbody[@class='z-rows']/tr"));
    }

    public WebElement boutonModifier(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nom + "']/ancestor::tr//span[@title='Modifier']")));
    }

    public WebElement boutonSupprimer(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nom + "']/ancestor::tr//span[@title='Supprimer']")));
    }


    // POPUP SUPPRESSION
    public WebElement textPopupSuppression(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class='z-window-modal-cl']//span[@class='z-label']")));
    }

    public WebElement boutonOKPopupSuppression(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class='z-window-modal-cl']//*[contains(text(), 'OK')]")));
    }

    public WebElement boutonAnnulerPopupSuppression(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class='z-window-modal-cl']//*[contains(text(), 'Annuler')]")));
    }




/*######################################################################################################################
                                                  METHODES
######################################################################################################################*/
    // Bouton
    public PageRessourcesCriteresCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
        outilsProjet.cliquerBoutonCreer(wait, boutonCreer(wait, idCommune));
        return new PageRessourcesCriteresCreer(driver);
    }


    // TABLEAU
    public PageRessourcesCriteresCreer cliquerBoutonModifier(WebDriverWait wait, String nom) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonModifier(wait, nom));
        return new PageRessourcesCriteresCreer(driver);
    }

    public PageRessourcesCriteresCreer cliquerNomTableau(WebDriverWait wait, String nom) throws Throwable {
        Map<String, WebElement> mapValeurRow = recuperationValeurTableauCriteres(wait).get(nom);
        seleniumTools.clickOnElement(wait, mapValeurRow.get("Nom"));
        return new PageRessourcesCriteresCreer(driver);
    }

    // Tableau
    public List<String> recuperationListLibelleTableau(WebDriverWait wait){
        List<String> listValeurEnTeteTableau = new ArrayList<>();
        for(WebElement libelle : listLibelleTableau(wait)){
            listValeurEnTeteTableau.add(libelle.getText());
        }
        listValeurEnTeteTableau.add("Opérations2");
        return listValeurEnTeteTableau;
    }


    public Map<String, Map<String, WebElement>> recuperationValeurTableauCriteres(WebDriverWait wait) {
        Map<String, Map<String, WebElement>> mapTableauAvancement = new HashMap<>();
        LOGGER.info("Récupération de " + listLibelleTableau(wait).size() + " rows");
        for(WebElement row : listRowTableau()){
            Map<String, WebElement> mapRowTableau = new HashMap<>();

            for(int i = 0; i < listLibelleTableau(wait).size(); i++){
                List<WebElement> listValeurTableau = row.findElements(By.xpath("./td"));
                String nomLibelle = listLibelleTableau(wait).get(i).getText();
                WebElement rowValeur;

                if(Objects.equals(nomLibelle, "Activé")){
                    rowValeur = listValeurTableau.get(i).findElement(By.xpath(".//input"));
                } else {
                    rowValeur = listValeurTableau.get(i).findElement(By.xpath(".//span"));
                }
                mapRowTableau.put(nomLibelle, rowValeur);
            }
            mapTableauAvancement.put(mapRowTableau.get("Nom").getText(), mapRowTableau);
        }
        return mapTableauAvancement;
    }

}
