package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
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

public class PageRessourcesParticipants extends AbstractFullPage {
    public PageRessourcesParticipants(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Accès à la page Ressource Calendrier
    public PageRessourcesCalendrier cliquerRessourcesCalendrier(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesCalendrier(wait, idCommune);
    }

    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap"))).getText();
    }

    // Message d'enregistrement
    public String messageCreation(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '"+ nom +"')]"))).getText();
    }


    // WebElement Bouton
    public WebElement boutonCreer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id = '" + idCommune + "y5-box']//td[text() = 'Créer']")));
    }

    public WebElement boutonFiltrerPar(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q4-real")));
    }

    public String libelleFiltrerPar(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "n4"))).getText();
    }

    public WebElement boutonDetailPersonnels(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "d5")));
    }

    public String libelleDetailPersonnels(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "c5"))).getText();
    }

    public WebElement boutonPlusOptions(WebDriverWait wait, String idCommune){
        //return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id = '" + idCommune + "n5-chdex]//tbody")));
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "f5-cnt")));

    }

    public WebElement boutonFiltre(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "n5-box")));
    }

    public WebElement boutonModifierParticipant(WebDriverWait wait, String nomParticipant){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = '" + nomParticipant + "']/ancestor::tr//span[@title='Modifier']")));
    }

    // Clique Bouton
    public PageRessourcesParticipantsCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
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
        return new PageRessourcesParticipantsCreer(driver);
    }

    public PageRessourcesParticipantsCreer cliquerModifierParticipant(WebDriverWait wait, String nomParticipant) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonModifierParticipant(wait, nomParticipant));
        return new PageRessourcesParticipantsCreer(driver);
    }

    // Tableau
    public List<String> recuperationLibelleTableau(String idCommune){
        List<String> listLibelleTableauString = new ArrayList<>();
        List<WebElement> listLibelleTableau = driver.findElements(By.xpath("//tr[@id = '" + idCommune + "r5']/th"));
        for (WebElement we : listLibelleTableau) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }

    // Tableau
    public Map<String, Map<String, String>> recuperationValeurTableauParticipant(String idCommune) {
        // List WebElement
        List<String> listValeurEnTeteTableau = recuperationLibelleTableau(idCommune);
        List<WebElement> listTableau = driver.findElements(By.xpath("//tbody[@id='" + idCommune + "lf']/tr"));

        // Map Contenant la map
        Map<String, Map<String, String>> listMapCalendrierTableau = new HashMap<>();

        LOGGER.info("Début de la récupération - " + listTableau.size() + " calendriers detectés ");
        for (WebElement we : listTableau) {
            Map<String, String> listValeurCalendrier = new HashMap<>();
            List<WebElement> listCritereValeur = we.findElements(By.xpath(".//span[not(@title='Supprimer')]"));
            for (int j = 0; j < listCritereValeur.size(); j++) {
                listValeurCalendrier.put(listValeurEnTeteTableau.get(j), listCritereValeur.get(j).getText());
                LOGGER.info("Ajout de " + listValeurEnTeteTableau.get(j) + " = " + listCritereValeur.get(j).getText());
            }
            LOGGER.info("Récupération terminé");
            listMapCalendrierTableau.put(listValeurCalendrier.get("Surnom"),listValeurCalendrier);
        }
        return listMapCalendrierTableau;
    }


    // Nettoyage
    public void supressionJdd(WebDriverWait wait,String nom) throws Throwable {
        WebElement boutonSupprimer = driver.findElement(By.xpath("//span[text() = '" + nom + "']/ancestor::tr[1]//span[@title='Supprimer']"));
        seleniumTools.clickOnElement(wait, boutonSupprimer);
        WebElement acceptSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
        seleniumTools.clickOnElement(wait, acceptSuppression);
    }

    public void verificationNettoyageTableau(WebDriverWait wait, String idCommune,
                                             String nom) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        Map<String, Map<String, String>> listValeurTableauCalendrier = recuperationValeurTableauParticipant(idCommune);
        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        if(listValeurTableauCalendrier.containsKey(nom)){
            LOGGER.info("Présence du JDD " + nom);
            supressionJdd(wait, nom);
            LOGGER.info("Suppression effectué");
        }
    }


}
