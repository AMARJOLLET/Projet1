package fr.eql.libreplan.pageObject.pageRessources.avancement;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class PageRessourcesAvancementCreer extends AbstractFullPage {
    public PageRessourcesAvancementCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


/*######################################################################################################################
                                                    WEBELEMENTS
######################################################################################################################*/
    // Titre
    public WebElement titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "t4-cnt")));
    }

    public WebElement titreFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "w4-hm")));
    }

    public WebElement messageCreation(WebDriverWait wait, String nom) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '" + nom + "')]")));
    }

    // WebElement Bouton
    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "l5-box")));
    }

    public WebElement boutonSauverEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "m5-box")));
    }

    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "n5-box")));
    }

    // Formulaire
    public List<WebElement> listRowFormulaire(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class='z-tabbox']//tbody[@class='z-rows']/tr")));
    }

    public boolean inputValeurMaxDisabled(WebDriverWait wait){
        Function<WebDriver, Boolean> inputValeurMaxDisabled = driver -> !driver.findElement(By.xpath(
                "//span[text()='Valeur maximum par défaut']/ancestor::tr//input")).isEnabled();
        return wait.until(inputValeurMaxDisabled);
    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    // Methode clique sur Bouton
    public PageRessourcesAvancement cliquerBoutonAnnuler(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnuler(wait, idCommune));
        return new PageRessourcesAvancement(driver);
    }

    public PageRessourcesAvancement cliquerBoutonEnregistrer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait, idCommune));
        return new PageRessourcesAvancement(driver);
    }

    public void cliquerBoutonSauverEtContinuer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonSauverEtContinuer(wait, idCommune));
    }

    // Formulaire
        // MAP TABLEAU
    public Map<String, WebElement> recuperationFormulaireCreationAvancement(WebDriverWait wait){
        Map<String, WebElement> mapTableauCreationAvancement = new HashMap<>();
        LOGGER.info("Récupération de " + listRowFormulaire(wait).size() + " rows");
        for(WebElement rowWe : listRowFormulaire(wait)){
            WebElement libelle = rowWe.findElement(By.xpath("./td[1]//span"));
            WebElement valeur;
            // PRISE EN COMPTE DU CAS TYPE (double SPAN)
            if(Objects.equals(libelle.getText(), "Type")){
                valeur = rowWe.findElement(By.xpath("./td[2]//span"));
            } else {
                valeur = rowWe.findElement(By.xpath("./td[2]//input"));
            }
            mapTableauCreationAvancement.put(libelle.getText(), valeur);
        }
        LOGGER.info("Récupération réussi avec " + mapTableauCreationAvancement.size() + " rows");
        return mapTableauCreationAvancement;
    }


        // REMPLIR
    public void remplirFormulaire(WebDriverWait wait, String idCommune,
                                  String nomUnite, boolean actif, String valeurMax, String precision, boolean pourcentage) throws Throwable {
        Map<String, WebElement> mapTableau = recuperationFormulaireCreationAvancement(wait);
        LOGGER.info("Renseigne le champ nom unite : " + nomUnite);
        seleniumTools.sendKey(wait, mapTableau.get("Nom d'unité"), nomUnite);
        LOGGER.info("Vérification que la checkbox Actif est coché : " + actif);
        seleniumTools.checkBoxCheck(wait, mapTableau.get("Actif"), actif);
        LOGGER.info("Renseigne le champ valeurMax: " + valeurMax);
        seleniumTools.sendKey(wait, mapTableau.get("Valeur maximum par défaut"), valeurMax);
        LOGGER.info("Renseigne le champ precision : " + precision);
        seleniumTools.sendKey(wait, mapTableau.get("Précision"), precision);
        LOGGER.info("Vérification que la checkbox pourcentage est coché : " + pourcentage);
        seleniumTools.checkBoxCheck(wait, mapTableau.get("Pourcentage"), pourcentage);
    }

}
