package fr.eql.libreplan.pageObject.PageCout;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PageCoutFeuilleDeTemps extends AbstractFullPage {

    public PageCoutFeuilleDeTemps(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                    WEBELEMENT
######################################################################################################################*/
    public WebElement titrePage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap")));
    }

    public WebElement messageCreation(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class='message_INFO']/span")));
    }


    public List<WebElement> listLibelleTableau(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class=\"clickable-rows z-grid\"]//tr[@class=\"z-columns\"]/th")));
    }

    public List<WebElement> listValeurTableau(){
        return driver.findElements(By.xpath(
                "//div[@class=\"clickable-rows z-grid\"]//tbody[@class='z-rows']/tr"));
    }

    public WebElement selectFitre(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "o4")));
    }


    public WebElement inputDateFiltreDe(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "s4-real")));
    }

    public WebElement inputDateFiltreA(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "u4-real")));
    }

    public WebElement inputFiltre(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "v4")));
    }

    public WebElement selectCavenas(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j5")));
    }

    public WebElement nouvelleFeuilleBouton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "m5")));
    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/

    public PageCoutFeuilleDeTempsCreer cliquerNouvelleFeuille(WebDriverWait wait, String idCommune, String cavenas) throws Throwable {
        Select select = new Select(selectCavenas(wait, idCommune));
        for (int i = 0; i < 3; i++) {
            try {
                select.selectByVisibleText(cavenas);
                seleniumTools.clickOnElement(wait, nouvelleFeuilleBouton(wait, idCommune));
                LOGGER.info("Click bouton créer OK");
                break;
            } catch (Exception e) {
                LOGGER.info("Element intercepté -- retry");
                Thread.sleep(200);
            }
        }
        return new PageCoutFeuilleDeTempsCreer(driver);
    }

    // TABLEAU
    public List<String> recuperationLibelleTableau(WebDriverWait wait){
        List<String> listLibelleTableauString = new ArrayList<>();
        LOGGER.info("Recuperation de " + listLibelleTableau(wait).size() + " libellés");
        for(WebElement libelle : listLibelleTableau(wait)){
            listLibelleTableauString.add(libelle.getText());
        }
        return listLibelleTableauString;
    }

    public Map<String, Map<String, WebElement>> recuperationValeurTableau(WebDriverWait wait){
        return outilsProjet.recuperationValeurTableauText("Code", listLibelleTableau(wait), listValeurTableau());
    }

    public List<Map<String, WebElement>> ordreValeurTableauText(WebDriverWait wait){
        return outilsProjet.ordreValeurTableauText(listLibelleTableau(wait), listValeurTableau());
    }




    public void nettoyageJDD(WebDriverWait wait, String dateDebutTemps, String heuresTemps) throws Throwable {
        Map<String, Map<String, WebElement>> mapValeurFeuilleTemps = recuperationValeurTableau(wait);
        for(Map.Entry <String, Map<String, WebElement>> mapParElement : mapValeurFeuilleTemps.entrySet()){
            LOGGER.info(mapParElement.getKey() + " = " + mapParElement.getValue());
            if(Objects.equals(mapParElement.getValue().get("Date de début").getText(), dateDebutTemps)
                    && Objects.equals(mapParElement.getValue().get("Travail total").getText(), heuresTemps)){
                LOGGER.info("Présence de JDD -- Suppression ...");
                WebElement suppression = mapParElement.getValue().get("Code").findElement(By.xpath("./ancestor::tr//span[@title=\"Supprimer\"]"));
                seleniumTools.clickOnElement(wait, suppression);
                WebElement okSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                        "//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
                seleniumTools.clickOnElement(wait, okSuppression);
            }
        }
    }





}
