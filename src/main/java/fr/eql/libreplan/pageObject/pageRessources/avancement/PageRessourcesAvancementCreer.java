package fr.eql.libreplan.pageObject.pageRessources.avancement;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageRessourcesAvancementCreer extends AbstractFullPage {
    public PageRessourcesAvancementCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "t4-cnt"))).getText();
    }

    public String titreFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "w4-hm"))).getText();
    }

    public String messageCreation(WebDriverWait wait, String nom) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '" + nom + "')]"))).getText();
    }

    // WebElement Bouton
    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "l5-box")));
    }

    public WebElement boutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "m5-box")));
    }

    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "n5-box")));
    }

    // Methode clique sur Bouton
    public PageRessourcesAvancement cliquerBoutonAnnuler(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnuler(wait, idCommune));
        return new PageRessourcesAvancement(driver);
    }

    public PageRessourcesAvancement cliquerBoutonEnregistrer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait, idCommune));
        return new PageRessourcesAvancement(driver);
    }

    public void cliquerBoutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrerEtContinuer(wait, idCommune));
    }

    // Formulaire
    public String libelleNomUnite(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "45"))).getText();
    }
    public String libelleActif(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "75"))).getText();
    }
    public String libelleValeurMax(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "a5"))).getText();
    }
    public String libellePrecision(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "d5"))).getText();
    }
    public String libelleType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "g5"))).getText();
    }
    public String libellePourcentage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j5"))).getText();
    }

    public WebElement inputNomUnite(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "55")));
    }
    public WebElement checkboxActif(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "85-real")));
    }
    public WebElement inputValeurMax(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune + "b5")));
    }
    public WebElement inputPrecision(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "e5")));
    }
    public WebElement inputType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "h5")));
    }
    public WebElement checkboxPourcentage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "k5-real")));
    }


    // Formulaire
    public void remplirFormulaire(WebDriverWait wait, String idCommune,
                                  String nomUnite, boolean actif, String valeurMax, String precision, boolean pourcentage) throws Throwable {
        LOGGER.info("Renseigne le champ nom unite : " + nomUnite);
        seleniumTools.sendKey(wait, inputNomUnite(wait, idCommune), nomUnite);
        LOGGER.info("Vérification que la checkbox Actif est coché : " + actif);
        seleniumTools.checkBoxCheck(wait, checkboxActif(wait, idCommune), actif);
        LOGGER.info("Renseigne le champ valeurMax: " + valeurMax);
        seleniumTools.sendKey(wait, inputValeurMax(wait, idCommune), valeurMax);
        LOGGER.info("Renseigne le champ precision : " + precision);
        seleniumTools.sendKey(wait, inputPrecision(wait, idCommune), precision);
        LOGGER.info("Vérification que la checkbox pourcentage est coché : " + pourcentage);
        seleniumTools.checkBoxCheck(wait, checkboxPourcentage(wait, idCommune), pourcentage);
    }

}
