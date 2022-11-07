package fr.eql.libreplan.pageObject.pageRessources.joursExceptionnels;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRessourcesJoursExceptionnelsCreer extends AbstractFullPage {
    public PageRessourcesJoursExceptionnelsCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    // Message d'alerte
    public WebElement messageAlerteDonneeObligatoire(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-errbox-center\"]")));
    }

    // Bouton
    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"z5-box")));
    }

    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"x5-box")));
    }

    public WebElement boutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"y5-box")));
    }


    // FORMULAIRE
    public WebElement inputCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune+"85")));
    }

    public WebElement checkboxCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"95-real")));
    }

    public WebElement inputNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"c5")));
    }

    public WebElement selectCouleur(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"g5")));
    }

    public WebElement couleurFonce(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"h5")));
    }

    public WebElement couleurClair(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"j5")));
    }

    public WebElement inputEffortStandart(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"o5-real")));
    }

    public WebElement inputEffortStandartMinute(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"p5-real")));
    }

    public WebElement inputEffortSupplementaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"u5-real")));
    }

    public WebElement inputEffortSupplementaireMinute(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"v5-real")));
    }

    public WebElement checkboxEffortSupplementaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"w5-real")));

    }



/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/

    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "v4-cnt"))).getText();
    }

    public String titreFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "y4-hm"))).getText();
    }

    // Bouton
    public PageRessourcesJoursExceptionnels cliquerBoutonAnnuler(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnuler(wait, idCommune));
        return new PageRessourcesJoursExceptionnels(driver);
    }

    public PageRessourcesJoursExceptionnels cliquerBoutonEnregistrer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait, idCommune));
        return new PageRessourcesJoursExceptionnels(driver);
    }

    public void cliquerBoutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrerEtContinuer(wait, idCommune));
    }


    public List<String> couleurException(WebDriverWait wait, String idCommune){
        List<WebElement> listCouleurException = driver.findElements(By.xpath("//td[@id='"+idCommune+"f5-chdextr']//span"));
        List<String> listCouleurExceptionString = new ArrayList<>();
        for (WebElement we : listCouleurException){
            listCouleurExceptionString.add(we.getText());
        }
        return listCouleurExceptionString;
    }


    public Map<String, String> correspondanceCouleurCss(){
        Map<String, String> mapCouleurCss = new HashMap<>();

        mapCouleurCss.put("orange foncé","rgba(255, 183, 51, 1)");
        mapCouleurCss.put("orange clair","rgba(255, 219, 153, 1)");
        mapCouleurCss.put("magenta foncé","rgba(255, 51, 255, 1)");
        mapCouleurCss.put("magenta clair","rgba(255, 153, 255, 1)");
        mapCouleurCss.put("bleu foncé","rgba(51, 51, 255, 1)");
        mapCouleurCss.put("bleu clair","rgba(153, 153, 255, 1)");
        mapCouleurCss.put("vert foncé","rgba(46, 230, 46, 1)");
        mapCouleurCss.put("vert clair","rgba(138, 230, 138, 1)");
        mapCouleurCss.put("jaune foncé","rgba(230, 230, 46, 1)");
        mapCouleurCss.put("jaune clair","rgba(230, 230, 161, 1)");
        mapCouleurCss.put("violet foncé","rgba(128, 26, 128, 1)");
        mapCouleurCss.put("violet clair","rgba(179, 142, 179, 1)");
        mapCouleurCss.put("noir foncé","rgba(51, 51, 51, 1)");
        mapCouleurCss.put("noir clair","rgba(153, 153, 153, 1)");
        mapCouleurCss.put("rouge (par défaut) foncé","rgba(255, 51, 51, 1)");
        mapCouleurCss.put("rouge (par défaut) clair","rgba(255, 153, 153, 1)");
        mapCouleurCss.put("cyan foncé","rgba(51, 255, 255, 1)");
        mapCouleurCss.put("cyan clair","rgba(153, 255, 255, 1)");
        return mapCouleurCss;
    }

    // Tableau
    public void renseignerEffort(WebDriverWait wait, String idCommune, WebElement weSendKey, String valeurEffort) throws Throwable {
        seleniumTools.sendKey(wait, weSendKey, valeurEffort);
        seleniumTools.clickOnElement(wait, inputNom(wait, idCommune));
    }

    public Map<String, List<String>> recuperationCouleurDisponible(WebDriverWait wait, String idCommune){
        List<WebElement> couleurWe = selectCouleur(wait,idCommune).findElements(By.xpath("./option"));
        Map<String, String> mapCouleurCss = correspondanceCouleurCss();
        Map<String, List<String>> recuperationCouleurCss = new HashMap<>();

        for(WebElement we : couleurWe){
            List<String> listCouleurCss = new ArrayList<>();
            for(int i = 0; i < 1; i++){
                listCouleurCss.add(mapCouleurCss.get(we.getText() + " foncé"));
                listCouleurCss.add(mapCouleurCss.get(we.getText() + " clair"));
            }
            recuperationCouleurCss.put(we.getText(), listCouleurCss);
        }
        return recuperationCouleurCss;
    }

    public List<String> recuperationLibelleTableau(String idCommune){
        List<String> listLibelleTableauString = new ArrayList<>();
        List<WebElement> listLibelleTableau = driver.findElements(By.xpath("//tbody[@id='"+idCommune+"45']//div/span"));
        for (WebElement we : listLibelleTableau) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }
}
