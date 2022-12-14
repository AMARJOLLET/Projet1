package fr.eql.libreplan.pageObject.pageRessources.participants;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.PageLogin;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import org.apache.commons.lang3.RandomStringUtils;
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

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    // WebElement Bouton
    public WebElement boutonCreer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id = '" + idCommune + "y5-box']//td[text() = 'Créer']")));
    }

    public WebElement boutonModifierParticipant(WebDriverWait wait, String nomParticipant){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = '" + nomParticipant + "']/ancestor::tr//span[@title='Modifier']")));
    }


    // FILTRER
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

    // OPTION SUPPLEMENTAIRE
    public WebElement inputPeriodeDepuis(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "i5-real")));
    }

    public WebElement inputPeriodeA(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "k5-real")));
    }

    public WebElement selectTypeOptionSupplementaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "m5")));
    }


    public WebElement libelleSelectTypeOptionSupplementaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOf(selectTypeOptionSupplementaire(wait, idCommune).findElement
                (By.xpath("./option[@selected='selected']"))));
    }

    // PAGINATION
    public WebElement paginationSuivante(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q5-next")));
    }

    public WebElement paginationPrecedente(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q5-prev")));
    }

    public WebElement paginationLast(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q5-last")));
    }

    public WebElement paginationFirst(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q5-first")));
    }

    public WebElement inputPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q5-real")));
    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/

    // Se deconnecter
    public PageLogin seDeconnecter(WebDriverWait wait) throws Throwable {
        return getHeader().seDeconnecter(wait);
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
    public String messageCreation(WebDriverWait wait ){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span"))).getText();
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
    public List<String> recuperationLibelleTableau(WebDriverWait wait, String idCommune){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "(//div[@id='" + idCommune + "j4']//tr[@class='z-columns']/th)[1]")));
        List<String> listLibelleTableauString = new ArrayList<>();
        List<WebElement> listLibelleTableau = driver.findElements(
                By.xpath("//div[@id='" + idCommune + "j4']//tr[@class='z-columns']/th"));
        for (WebElement we : listLibelleTableau) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }

    // Tableau
    public Map<String, Map<String, String>> recuperationValeurTableauParticipant(WebDriverWait wait, String idCommune) {
        // List WebElement
        List<String> listValeurEnTeteTableau = recuperationLibelleTableau(wait, idCommune);
        List<WebElement> listTableau = driver.findElements(
                By.xpath("//div[@id='" + idCommune + "j4']//tbody[@class=\"z-rows\"]/tr"));

        // Map Contenant la map
        Map<String, Map<String, String>> listMapCalendrierTableau = new HashMap<>();

        LOGGER.info("Début de la récupération - " + listTableau.size() + " participant detectés ");
        for (WebElement we : listTableau) {
            Map<String, String> listValeurCalendrier = new HashMap<>();
            List<WebElement> listCritereValeur = we.findElements(By.xpath(".//span[not(@title='Supprimer')]"));
            for (int j = 0; j < listCritereValeur.size(); j++) {
                listValeurCalendrier.put(listValeurEnTeteTableau.get(j), listCritereValeur.get(j).getText());
            }
            listMapCalendrierTableau.put(listValeurCalendrier.get("Surnom"),listValeurCalendrier);
        }
        LOGGER.info("Récupération terminé");
        return listMapCalendrierTableau;
    }

    // Filtre Option Supplémentaire
    public List<String> listSelectTypeOptionSupplementaire(WebDriverWait wait, String idCommune){
        List<String> valueSelectTypeOptionSupplementaire = new ArrayList<>();
        List<WebElement> listSelectTypeOptionSupplementaire = selectTypeOptionSupplementaire(wait, idCommune).findElements(By.xpath("./option"));
        for (WebElement we : listSelectTypeOptionSupplementaire){
            valueSelectTypeOptionSupplementaire.add(we.getText());
        }
        return valueSelectTypeOptionSupplementaire;
    }

    public void appliquerFiltre(WebDriverWait wait, String idCommune, String recherche) throws Throwable {
        seleniumTools.sendKey(wait, boutonDetailPersonnels(wait, idCommune), recherche);
        seleniumTools.clickOnElement(wait, boutonFiltre(wait, idCommune));
    }

    // PRE REQUIS DU TEST GRE01
    public void ajoutParticipant(WebDriverWait wait, String idCommune) throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(boutonCreer(wait, idCommune)));
        List<WebElement> listTableau = driver.findElements(By.xpath("//div[@class=\"clickable-rows z-grid\"]//tbody[@class=\"z-rows\"]/tr"));
        LOGGER.info(listTableau.size() + " rows détecté");
        int index = 15 - listTableau.size();
        for (int i = 0; i < index; i++){
            String nom = "ZZ" + RandomStringUtils.randomAlphabetic(5);
            String prenom = "ZZ" + RandomStringUtils.randomAlphabetic(5);
            String id = "ZZ" + RandomStringUtils.randomAlphabetic(5);

            cliquerBoutonCreer(wait, idCommune);
            PageRessourcesParticipantsCreer pageRessourcesParticipantsCreer = new PageRessourcesParticipantsCreer(driver);
            pageRessourcesParticipantsCreer.remplirFormulaireMinimun(wait, idCommune, nom, prenom, id);
            pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        }
    }


    public String nombrePageDisponible(WebDriverWait wait){
        String page = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//div[@class=\"z-paging\" and not(contains(@style,'display:none'))]//span[@class=\"z-paging-text\" and ./text()]"))).getText();
        return page.replace("/","").replace(" ","");
    }
}
