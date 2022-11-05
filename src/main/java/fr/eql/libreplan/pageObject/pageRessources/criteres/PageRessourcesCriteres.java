package fr.eql.libreplan.pageObject.pageRessources.criteres;

import fr.eql.libreplan.pageObject.AbstractFullPage;
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

    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap"))).getText();
    }

    // Text Popup suppression
    public List<String> listTextExtraitPopupSuppression(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"z-window-modal-cl\"]//span[1]")));

        // Creation des list
        List<WebElement> listWe = driver.findElements(By.xpath("//div[@class=\"z-window-modal-cl\"]//span"));
        List<String> listTextExtrait = new ArrayList<>();
        for (WebElement we : listWe){
            listTextExtrait.add(we.getText());
        }

        return listTextExtrait;
    }


    // WebElement bouton
    public WebElement boutonSupprimer(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text() = '" + nom + "']/ancestor::tr[1]//span[@title='Supprimer']")));
    }

    public WebElement boutonSupprimerOK(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
    }

    public WebElement boutonSupprimerAnnuler(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"Annuler\")]")));
    }

    // Bouton
    public PageRessourcesCriteresCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
        WebElement boutonCreer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//table[@id = '" + idCommune + "_5-box']//td[text() = 'Créer']")));
        for (int i = 0; i < 3; i++){
            try {
                seleniumTools.clickOnElement(wait, boutonCreer);
                LOGGER.info("Click bouton créer OK");
                break;
            } catch (ElementClickInterceptedException e){
                LOGGER.info("Element intercepté -- retry");
            }
        }

        return new PageRessourcesCriteresCreer(driver);
    }

    public PageRessourcesCriteresCreer cliquerBoutonModifier(WebDriverWait wait, String nom) throws Throwable {
        WebElement boutonModifier = driver.findElement(By.xpath("//span[text() = '" + nom + "']/ancestor::tr[1]//span[@title='Modifier']"));
        seleniumTools.clickOnElement(wait, boutonModifier);
        return new PageRessourcesCriteresCreer(driver);
    }

    public PageRessourcesCriteresCreer cliquerBoutonModifierNom(WebDriverWait wait, String nom) throws Throwable {
        WebElement boutonModifier = driver.findElement(By.xpath("//span[text() = '" + nom + "']"));
        seleniumTools.clickOnElement(wait, boutonModifier);
        return new PageRessourcesCriteresCreer(driver);
    }

    // Tableau
    public List<String> recuperationEnTeteTableau(String idCommune){
        List<WebElement> listEnTeteTableau = driver.findElements(By.xpath("//tr[@id='" + idCommune + "l4']/th/div"));
        List<String> listValeurEnTeteTableau = new ArrayList<>();
        for(int i = 0; i < listEnTeteTableau.size(); i++){
            listValeurEnTeteTableau.add(listEnTeteTableau.get(i).getText());
        }
        listValeurEnTeteTableau.add("Opérations2");
        return listValeurEnTeteTableau;
    }


    public Map<String, Map<String, String>> recuperationValeurTableauCriteres(String idCommune) {
        // List WebElement
        List<String> listValeurEnTeteTableau = recuperationEnTeteTableau(idCommune);
        List<WebElement> listCritere = driver.findElements(By.xpath("//tbody[@id='" + idCommune + "r4']/tr"));

        // Map Contenant la map
        Map<String, Map<String, String>> listMapCritereTableau = new HashMap<>();

        LOGGER.info("Début de la récupération - " + listCritere.size() + " critères detectés ");
        for (WebElement we : listCritere) {
            Map<String, String> listValeurCriteres = new HashMap<>();
            List<WebElement> listCritereValeur = we.findElements(By.xpath(".//span[not(@title='Supprimer')]"));
            for (int j = 0; j < listCritereValeur.size(); j++) {
                // Prise en compte de la checkbox Activité
                if (Objects.equals(listValeurEnTeteTableau.get(j), "Activé")) {
                    WebElement activiteState = we.findElement(By.xpath(".//input[@type='checkbox']"));
                    listValeurCriteres.put(listValeurEnTeteTableau.get(j),  activiteState.getAttribute("checked"));
                    LOGGER.info("Ajout de " + listValeurEnTeteTableau.get(j) + " = " + activiteState.getAttribute("checked"));
                }
                // Récupération du texte dans les autres cas
                else {
                    listValeurCriteres.put(listValeurEnTeteTableau.get(j), listCritereValeur.get(j).getText());
                    LOGGER.info("Ajout de " + listValeurEnTeteTableau.get(j) + " = " + listCritereValeur.get(j).getText());
                }
            }
            LOGGER.info("Récupération terminé");
            listMapCritereTableau.put(listValeurCriteres.get("Nom"),listValeurCriteres);
        }
        return listMapCritereTableau;
    }

    // Nettoyage
    public void supressionJdd(WebDriverWait wait,String nom) throws Throwable {
        WebElement boutonSupprimer = driver.findElement(By.xpath("//span[text() = '" + nom + "']/ancestor::tr[1]//span[@title='Supprimer']"));
        seleniumTools.clickOnElement(wait, boutonSupprimer);
        WebElement acceptSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
        seleniumTools.clickOnElement(wait, acceptSuppression);
    }

    public void verificationNettoyageTableau(WebDriverWait wait, String idCommune,
                                             String nomAnnuler, String nomEnregistrer, String nomContinuer, String nomModifier) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        Map<String, Map<String, String>> listValeurParCritere = recuperationValeurTableauCriteres(idCommune);
        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        if(listValeurParCritere.containsKey(nomAnnuler)){
            LOGGER.info("Présence du JDD " + nomAnnuler);
            supressionJdd(wait, nomAnnuler);
            LOGGER.info("Suppression effectué");
        } else if (listValeurParCritere.containsKey(nomEnregistrer)){
            LOGGER.info("Présence du JDD " + nomEnregistrer);
            supressionJdd(wait, nomEnregistrer);
            LOGGER.info("Suppression effectué");
        } else if (listValeurParCritere.containsKey(nomContinuer)){
            LOGGER.info("Présence du JDD " + nomContinuer);
            supressionJdd(wait, nomContinuer);
            LOGGER.info("Suppression effectué");
        } else if (listValeurParCritere.containsKey(nomModifier)){
            LOGGER.info("Présence du JDD " + nomModifier);
            supressionJdd(wait, nomModifier);
            LOGGER.info("Suppression effectué");
        } else {
            LOGGER.info("Absence d'ancien JDD");
        }
    }

}
