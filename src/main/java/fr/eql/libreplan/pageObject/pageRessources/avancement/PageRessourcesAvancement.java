package fr.eql.libreplan.pageObject.pageRessources.avancement;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrierCreer;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRessourcesAvancement extends AbstractFullPage {
    public PageRessourcesAvancement(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap"))).getText();
    }

    // Message Création
    public String messageCreation(WebDriverWait wait, String nom) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '" + nom + "')]"))).getText();
    }

    // WebElement Bouton
    public WebElement boutonCreer(WebDriverWait wait, String idCommune) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id = '" + idCommune + "r4-box']//td[text() = 'Créer']")));
    }

    // Clique Bouton
    public PageRessourcesAvancementCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
        WebElement we = boutonCreer(wait, idCommune);
        for (int i = 0; i < 3; i++) {
            try {
                seleniumTools.clickOnElement(wait, we);
                LOGGER.info("Click bouton créer OK");
                break;
            } catch (ElementClickInterceptedException e) {
                LOGGER.info("Element intercepté -- retry");
            }
        }
        return new PageRessourcesAvancementCreer(driver);
    }

    // WebElement tableau
    public WebElement nomTableau(WebDriverWait wait, String nomAvancement){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+nomAvancement+"']")));
    }

    public WebElement activeTableau(WebDriverWait wait, String nomAvancement){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//span[text()='"+nomAvancement+"']/ancestor::tr//input[@type=\"checkbox\"])[1]")));
    }

    public WebElement predefiniTableau(WebDriverWait wait, String nomAvancement){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//span[text()='"+nomAvancement+"']/ancestor::tr//input[@type=\"checkbox\"])[2]")));
    }

    public WebElement modifierTableau(WebDriverWait wait, String nomAvancement){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='"+nomAvancement+"']/ancestor::tr//span[@title='Modifier']")));
    }

    public WebElement supprimerTableau(WebDriverWait wait, String nomAvancement){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='"+nomAvancement+"']/ancestor::tr//span[@title='Supprimer']")));
    }

    // Libelle du tableau
    public List<String> recuperationLibelleTableau(String idCommune) {
        List<String> listLibelleTableauString = new ArrayList<>();
        List<WebElement> listLibelleTableau = driver.findElements(By.xpath("//tr[@id = '" + idCommune + "m4']/th"));
        for (WebElement we : listLibelleTableau) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }

    public Map<String, Map<String, String>> recuperationValeurTableauAvancement(String idCommune) {
        // List WebElement
        List<String> listValeurEnTeteTableau = recuperationLibelleTableau(idCommune);
        List<WebElement> listAvancement = driver.findElements(By.xpath("//tbody[@id='" + idCommune + "o5']/tr"));

        // Map Contenant la map
        Map<String, Map<String, String>> listMapAvancementTableau = new HashMap<>();

        LOGGER.info("Début de la récupération - " + listAvancement.size() + " avancement detectés ");
        for (WebElement we : listAvancement) {
            Map<String, String> listValeurCalendrier = new HashMap<>();
            List<WebElement> listCritereValeur = we.findElements(By.xpath(".//span[@class='z-label']"));
            for (int j = 0; j < listCritereValeur.size(); j++) {
                listValeurCalendrier.put(listValeurEnTeteTableau.get(j), listCritereValeur.get(j).getText());
            }
            listMapAvancementTableau.put(listValeurCalendrier.get("Nom"),listValeurCalendrier);
        }
        LOGGER.info("Récupération terminé");
        return listMapAvancementTableau;
    }


    // Nettoyage
    public void supressionJdd(WebDriverWait wait,String nom) throws Throwable {
        WebElement boutonSupprimer = driver.findElement(By.xpath("//span[text() = '" + nom + "']/ancestor::tr//span[@title='Supprimer']"));
        seleniumTools.clickOnElement(wait, boutonSupprimer);
        WebElement acceptSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
        seleniumTools.clickOnElement(wait, acceptSuppression);
    }

    public void verificationNettoyageTableau(WebDriverWait wait, String idCommune,
                                                 String nomJDD1, String nomJDD2) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        Thread.sleep(500);
        Map<String, Map<String, String>> mapValeurTableau = recuperationValeurTableauAvancement(idCommune);

        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        if(mapValeurTableau.containsKey(nomJDD1)){
            LOGGER.info("Présence du JDD " + nomJDD1);
            supressionJdd(wait, nomJDD1);
            LOGGER.info("Suppression effectué");
        }
        Thread.sleep(500);
        if(mapValeurTableau.containsKey(nomJDD2)){
            LOGGER.info("Présence du JDD " + nomJDD2);
            supressionJdd(wait, nomJDD2);
            LOGGER.info("Suppression effectué");
        }
    }
}

