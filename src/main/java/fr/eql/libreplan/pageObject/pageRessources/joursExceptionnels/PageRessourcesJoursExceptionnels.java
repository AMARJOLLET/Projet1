package fr.eql.libreplan.pageObject.pageRessources.joursExceptionnels;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRessourcesJoursExceptionnels extends AbstractFullPage {
    public PageRessourcesJoursExceptionnels(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    // Acces à la page Calendrier
    public PageRessourcesCalendrier cliquerRessourcesCalendrier(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesCalendrier(wait, idCommune);
    }


/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    // Titre
    public WebElement titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap")));
    }

    // Message d'enregistrement
    public WebElement messageCreation(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '"+ nom +"')]")));
    }

    // WebElement Bouton
    public WebElement boutonCreer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id = '" + idCommune + "t4-box']//td[text() = 'Créer']")));
    }

    // Tableau
    public List<WebElement> listLibelleTableau(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class='clickable-rows z-grid']//tr[@class='z-columns']/th")));
    }

    public List<WebElement> listRowTableau(){
        return driver.findElements(By.xpath(
                "//div[@class='clickable-rows z-grid']//tbody[@class='z-rows']/tr"));
    }




/*######################################################################################################################
                                                  METHODES
######################################################################################################################*/

    // Clique Bouton
    public PageRessourcesJoursExceptionnelsCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
        outilsProjet.cliquerBoutonCreer(wait, boutonCreer(wait, idCommune));
        return new PageRessourcesJoursExceptionnelsCreer(driver);
    }


    // Tableau
    public List<String> recuperationLibelleTableau(WebDriverWait wait){
        List<String> listLibelleTableauString = new ArrayList<>();
        for (WebElement we : listLibelleTableau(wait)) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }

    // Tableau
    public Map<String, Map<String, WebElement>> recuperationValeurTableauJourExceptionnel(WebDriverWait wait) {
        return outilsProjet.recuperationValeurTableauText("Nom", listLibelleTableau(wait), listRowTableau());
    }
}
