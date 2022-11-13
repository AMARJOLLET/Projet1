package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class PageRessourcesCalendrier extends AbstractFullPage {
    public PageRessourcesCalendrier(WebDriver driver) {
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

    // Message Création
    public WebElement messageCreation(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class='message_INFO']/span[contains(text(), '"+ nom +"')]")));
    }


    // Bouton
    public WebElement boutonCreer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//table[@id = '" + idCommune + "q4-box']//td[text() = 'Créer']")));
    }


    // TABLEAU
    public List<WebElement> listLibelleTableau(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class='z-dottree']//tr[@class='z-treecols']/th")));
    }

    public List<WebElement> listRowTableau(){
        return driver.findElements(By.xpath(
                "//div[@class='z-dottree-body']//tr[@class='z-treerow']"));
    }



    public WebElement boutonCreerUneDerive(WebDriverWait wait, String nomCalendrier){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nomCalendrier + "']/ancestor::tr//span[@title='Créer une dérive']")));
    }

    public WebElement boutonReplierDerive(WebDriverWait wait, String nomCalendrier){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nomCalendrier + "']/preceding-sibling::span")));
    }

    public String verificationDeriveReplier(WebDriverWait wait, String nomCalendrier){
        WebElement we = driver.findElement(By.xpath(
                "//span[text() = '" + nomCalendrier + "']/ancestor::tr/following-sibling::tr"));
        return we.getCssValue("display");
    }

    public WebElement boutonCopierCalendrier(WebDriverWait wait, String nomCalendrier){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nomCalendrier + "']/ancestor::tr//span[@title='Créer une copie']")));
    }

    public WebElement boutonModifierCalendrier(WebDriverWait wait, String nomCalendrier){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nomCalendrier + "']/ancestor::tr//span[@title='Modifier']")));
    }




/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    // Accès à la page Participant
    public PageRessourcesParticipants cliquerRessourcesParticipants(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesParticipants(wait, idCommune);
    }


    // Clique Bouton
    public PageRessourcesCalendrierCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
        outilsProjet.cliquerBoutonCreer(wait, boutonCreer(wait, idCommune));
        return new PageRessourcesCalendrierCreer(driver);
    }

    public PageRessourcesCalendrierCreer cliquerBoutonCreerUneDerive(WebDriverWait wait, String nom) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonCreerUneDerive(wait, nom));
        return new PageRessourcesCalendrierCreer(driver);
    }

    public void cliquerBoutonRepliereDerive(WebDriverWait wait, String nomCalendrier) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonReplierDerive(wait, nomCalendrier));
    }

    public PageRessourcesCalendrierCreer cliquerBoutonCopierCalendrier(WebDriverWait wait, String nomCalendrier) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonCopierCalendrier(wait, nomCalendrier));
        return new PageRessourcesCalendrierCreer(driver);
    }

    public PageRessourcesCalendrierCreer cliquerBoutonModifierCalendrier(WebDriverWait wait, String nomCalendrier) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonModifierCalendrier(wait, nomCalendrier));
        return new PageRessourcesCalendrierCreer(driver);
    }

    // Libelle du tableau
    public List<String> recuperationLibelleTableau(WebDriverWait wait){
        List<String> listLibelleTableauString = new ArrayList<>();
        for (WebElement we : listLibelleTableau(wait)) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }

    // Tableau
    public Map<String, Map<String, WebElement>> recuperationValeurTableauCalendrier(WebDriverWait wait) {
        Map<String, Map<String, WebElement>> listMapCalendrierTableau = new HashMap<>();
        List<WebElement> listLibelle = listLibelleTableau(wait);
        List<WebElement> listRow = listRowTableau();

        LOGGER.info("Récupération de " + listLibelleTableau(wait).size() + " rows");
        for (WebElement we : listRow) {
            Map<String, WebElement> mapValeurTableau = new HashMap<>();
            List<WebElement> listCritereValeur = we.findElements(By.xpath(".//span[@class='z-label']"));
            for (int j = 0; j < listCritereValeur.size(); j++) {
                mapValeurTableau.put(listLibelle.get(j).getText(), listCritereValeur.get(j));
            }
            listMapCalendrierTableau.put(mapValeurTableau.get("Nom").getText(),mapValeurTableau);
        }
        LOGGER.info("Récupération terminé");

        return listMapCalendrierTableau;
    }
}
