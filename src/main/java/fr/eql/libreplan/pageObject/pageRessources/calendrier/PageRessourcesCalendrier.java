package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
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

    // Accès à la page Participant
    public PageRessourcesParticipants cliquerRessourcesParticipants(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesParticipants(wait, idCommune);
    }

    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap"))).getText();
    }

    // Message Création
    public String messageCreation(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '"+ nom +"')]"))).getText();
    }


    // WebElement Bouton
    public WebElement boutonCreer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id = '" + idCommune + "q4-box']//td[text() = 'Créer']")));
    }

    public WebElement boutonCreerUneDerive(WebDriverWait wait, String nomCalendrier){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = '" + nomCalendrier + "']/ancestor::tr[1]//span[@title='Créer une dérive']")));
    }

    public WebElement boutonReplierDerive(WebDriverWait wait, String nomCalendrier){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = '" + nomCalendrier + "']/preceding-sibling::span")));
    }

    public String verificationDeriveReplier(WebDriverWait wait, String nomCalendrier){
        WebElement we = driver.findElement(By.xpath("//span[text() = '" + nomCalendrier + "']/ancestor::tr/following-sibling::tr[1]"));
        return we.getCssValue("display");
    }

    public WebElement boutonCopierCalendrier(WebDriverWait wait, String nomCalendrier){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = '" + nomCalendrier + "']/ancestor::tr[1]//span[@title='Créer une copie']")));
    }

    public WebElement boutonModifierCalendrier(WebDriverWait wait, String nomCalendrier){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = '" + nomCalendrier + "']/ancestor::tr[1]//span[@title='Modifier']")));
    }

    // Clique Bouton
    public PageRessourcesCalendrierCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
        WebElement we = boutonCreer(wait, idCommune);
        for (int i = 0; i < 3; i++){
            try {
                seleniumTools.clickOnElement(wait, we);
                LOGGER.info("Click bouton créer OK");
                break;
            } catch (ElementClickInterceptedException e){
                LOGGER.info("Element intercepté -- retry");
            }
        }
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
    public List<String> recuperationLibelleTableau(String idCommune){
        List<String> listLibelleTableauString = new ArrayList<>();
        List<WebElement> listLibelleTableau = driver.findElements(By.xpath("//tr[@id = '" + idCommune + "l4']/th"));
        for (WebElement we : listLibelleTableau) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }

    // Tableau
    public Map<String, Map<String, String>> recuperationValeurTableauCalendrier(WebDriverWait wait, String idCommune) {
        // List WebElement
        List<String> listValeurEnTeteTableau = recuperationLibelleTableau(idCommune);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[@id = '" + idCommune + "l4']/th")));
        List<WebElement> listCalendrier = driver.findElements(By.xpath("//tbody[@id='" + idCommune + "k4-rows']/tr"));

        // Map Contenant la map
        Map<String, Map<String, String>> listMapCalendrierTableau = new HashMap<>();

        LOGGER.info("Début de la récupération - " + listCalendrier.size() + " calendriers detectés ");
        for (WebElement we : listCalendrier) {
            Map<String, String> listValeurCalendrier = new HashMap<>();
            List<WebElement> listCritereValeur = we.findElements(By.xpath(".//span[@class='z-label']"));
            for (int j = 0; j < listCritereValeur.size(); j++) {
                listValeurCalendrier.put(listValeurEnTeteTableau.get(j), listCritereValeur.get(j).getText());
            }
            listMapCalendrierTableau.put(listValeurCalendrier.get("Nom"),listValeurCalendrier);
        }
        LOGGER.info("Récupération terminé");

        return listMapCalendrierTableau;
    }

    // Nettoyage
    public void supressionJdd(WebDriverWait wait,String nom) throws Throwable {
        WebElement boutonSupprimer = driver.findElement(By.xpath("//span[text() = '" + nom + "']/ancestor::tr[1]//span[@title='Supprimer']"));
        seleniumTools.clickOnElement(wait, boutonSupprimer);
        WebElement acceptSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
        seleniumTools.clickOnElement(wait, acceptSuppression);
    }

    public void verificationNettoyageTableauCAL1(WebDriverWait wait, String idCommune,
                                             String nomCalendrierDerive, String nomCalendrier, String nomCalendrierCopier) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        Map<String, Map<String, String>> listValeurTableauCalendrier = recuperationValeurTableauCalendrier(wait, idCommune);
        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        if(listValeurTableauCalendrier.containsKey(nomCalendrierDerive)){
            LOGGER.info("Présence du JDD " + nomCalendrierDerive);
            supressionJdd(wait, nomCalendrierDerive);
            LOGGER.info("Suppression effectué");
        }
        Thread.sleep(1000);
        listValeurTableauCalendrier = recuperationValeurTableauCalendrier(wait, idCommune);
        if (listValeurTableauCalendrier.containsKey(nomCalendrier)){
            LOGGER.info("Présence du JDD " + nomCalendrier);
            supressionJdd(wait, nomCalendrier);
            LOGGER.info("Suppression effectué");
        }
        Thread.sleep(1000);
        listValeurTableauCalendrier = recuperationValeurTableauCalendrier(wait, idCommune);
        if (listValeurTableauCalendrier.containsKey(nomCalendrierCopier)) {
            LOGGER.info("Présence du JDD " + nomCalendrierCopier);
            supressionJdd(wait, nomCalendrierCopier);
            LOGGER.info("Suppression effectué");
        }
    }

    public void verificationNettoyageTableauCAL2(WebDriverWait wait, String idCommune,
                                                 String nomCalendrierDerive) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        Map<String, Map<String, String>> listValeurTableauCalendrier = recuperationValeurTableauCalendrier(wait, idCommune);
        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        if(listValeurTableauCalendrier.containsKey(nomCalendrierDerive)){
            LOGGER.info("Présence du JDD " + nomCalendrierDerive);
            supressionJdd(wait, nomCalendrierDerive);
            LOGGER.info("Suppression effectué");
        }
    }
}
