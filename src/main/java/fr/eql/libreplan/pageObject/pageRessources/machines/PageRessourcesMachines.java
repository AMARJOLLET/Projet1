package fr.eql.libreplan.pageObject.pageRessources.machines;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipantsCreer;
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

public class PageRessourcesMachines extends AbstractFullPage {
    public PageRessourcesMachines(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    // WebElement Bouton
    public WebElement boutonCreer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id = '" + idCommune + "x5-box']")));
    }

    // FILTRER
    public String libelleFiltrerPar(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "n4"))).getText();
    }

    public WebElement inputFitrerPar(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q4-real")));
    }

    public WebElement inputDetailPersonnels(WebDriverWait wait, String idCommune){
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



/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/

    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap"))).getText();
    }

    // Clique Bouton
    public PageRessourcesMachinesCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
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
        return new PageRessourcesMachinesCreer(driver);
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
    public Map<String, Map<String, String>> recuperationValeurTableauMachine(String idCommune) {
        // List WebElement
        List<String> listValeurEnTeteTableau = recuperationLibelleTableau(idCommune);
        List<WebElement> listTableau = driver.findElements(By.xpath("//tbody[@id='" + idCommune + "ud']/tr"));

        // Map Contenant la map
        Map<String, Map<String, String>> listMapMachineTableau = new HashMap<>();

        LOGGER.info("Début de la récupération - " + listTableau.size() + " machines detectées ");
        for (WebElement we : listTableau) {
            Map<String, String> listValeurMachine = new HashMap<>();
            List<WebElement> listCritereValeur = we.findElements(By.xpath(".//span[not(@title='Supprimer')]"));
            for (int j = 0; j < listCritereValeur.size(); j++) {
                listValeurMachine.put(listValeurEnTeteTableau.get(j), listCritereValeur.get(j).getText());
            }
            listMapMachineTableau.put(listValeurMachine.get("Nom"),listValeurMachine);
        }
        LOGGER.info("Récupération terminé");
        return listMapMachineTableau;
    }


    // Nettoyage
    public void supressionJdd(WebDriverWait wait,String nom) throws Throwable {
        WebElement boutonSupprimer = driver.findElement(By.xpath("//span[text() = '" + nom + "']/ancestor::tr//span[@title='Supprimer']"));
        seleniumTools.clickOnElement(wait, boutonSupprimer);
        WebElement acceptSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
        seleniumTools.clickOnElement(wait, acceptSuppression);
    }

    public void verificationNettoyageTableau(WebDriverWait wait, String idCommune,
                                             String nom) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        Map<String, Map<String, String>> listValeurTableauMachines = recuperationValeurTableauMachine(idCommune);
        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        if(listValeurTableauMachines.containsKey(nom)){
            LOGGER.info("Présence du JDD " + nom);
            supressionJdd(wait, nom);
            LOGGER.info("Suppression effectué");
        }
    }


}
