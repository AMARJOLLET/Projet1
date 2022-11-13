package fr.eql.libreplan.pageObject.pageRessources.avancement;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class PageRessourcesAvancement extends AbstractFullPage {
    public PageRessourcesAvancement(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    // Titre
    public WebElement titreDeLaPage(WebDriverWait wait, String idCommune) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap")));
    }

    // Message Création
    public WebElement messageCreation(WebDriverWait wait, String nom) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '\"" + nom + "\" enregistré')]")));
    }

    // Bouton
    public WebElement boutonCreer(WebDriverWait wait, String idCommune) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id = '" + idCommune + "r4-box']//td[text() = 'Créer']")));
    }


    // Tableau
    public List<WebElement> listLibelleTableauAvancement(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='clickable-rows z-grid']//tr[@class='z-columns']/th")));
    }

    public List<WebElement> listRowTableauAvancement(){
        return driver.findElements(By.xpath("//div[@class='clickable-rows z-grid']//tbody[@class='z-rows']/tr"));
    }

    public WebElement boutonModifierTableau(WebDriverWait wait, String nomAvancement){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='"+nomAvancement+"']/ancestor::tr//span[@title='Modifier']")));
    }

    public WebElement boutonSupprimerTableau(WebDriverWait wait, String nomAvancement){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='"+nomAvancement+"']/ancestor::tr//span[@title='Supprimer']")));
    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    // Clique Bouton
    public PageRessourcesAvancementCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
        outilsProjet.cliquerBoutonCreer(wait, boutonCreer(wait, idCommune));
        return new PageRessourcesAvancementCreer(driver);
    }

    // Libelle du tableau
    public List<String> recuperationLibelleTableau(WebDriverWait wait) {
        return outilsProjet.recuperationLibelleTableau(listLibelleTableauAvancement(wait));
    }

    public Map<String, Map<String, WebElement>> recuperationValeurTableauAvancement(WebDriverWait wait) {
        Map<String, Map<String, WebElement>> mapTableauAvancement = new HashMap<>();
        List<WebElement> listLibelle = listLibelleTableauAvancement(wait);
        List<WebElement> listRow = listRowTableauAvancement();

        LOGGER.info("Récupération de " + listLibelle.size() + " rows");
        for(WebElement row : listRow){
            Map<String, WebElement> mapRowTableau = new HashMap<>();

            for(int i = 0; i < listLibelle.size(); i++){
                List<WebElement> listValeurTableau = row.findElements(By.xpath("./td"));
                String nomLibelle = listLibelle.get(i).getText();
                WebElement rowValeur;

                if(Objects.equals(nomLibelle, "Nom") || Objects.equals(nomLibelle, "Opérations")){
                    rowValeur = listValeurTableau.get(i).findElement(By.xpath(".//span"));
                } else {
                    rowValeur = listValeurTableau.get(i).findElement(By.xpath(".//input"));
                }
                mapRowTableau.put(nomLibelle, rowValeur);
            }
            mapTableauAvancement.put(mapRowTableau.get("Nom").getText(), mapRowTableau);
        }
        return mapTableauAvancement;
    }
}

