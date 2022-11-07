package fr.eql.libreplan.pageObject.pageRessources.machines;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageRessourcesMachinesCreer extends AbstractFullPage {
    public PageRessourcesMachinesCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


/*######################################################################################################################
                                                 WEBELEMENTS
######################################################################################################################*/
    // Bouton
    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"ld-box")));
    }

    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "jd-box")));
    }

    public WebElement boutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"kd-box")));
    }

    // Formulaire
    //////// LIBELLE
    public WebElement libelleCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "e6")));
    }

    public WebElement libelleCheckboxCode(WebDriverWait wait, String idCommune) {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@id='"+ idCommune + "h6-chdex']//label")));
    }

    public WebElement libelleNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j6")));
    }

    public WebElement libelleDescription(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "m6")));
    }

    public WebElement libelleType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q6")));
    }

    // Formulaire
    ////// Input
    public WebElement inputCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune + "g6")));
    }

    public WebElement checkboxCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune + "h6-real")));
    }

    public WebElement inputNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "k6")));
    }

    public WebElement inputDescription(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "n6")));
    }

    public WebElement selectType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "s6")));
    }

    public WebElement libelleSelectType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOf(selectType(wait, idCommune).findElement(
                By.xpath("./option[@selected='selected']"))));
    }


/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "z5-cnt"))).getText();
    }

    // Message Création
    public String messageCreation(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), 'enregistré')]"))).getText();
    }


    // Cliquer Bouton
    public PageRessourcesMachines cliquerBoutonEnregistrer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait, idCommune));
        return new PageRessourcesMachines(driver);
    }

    // Cliquer Bouton
    public void cliquerBoutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrerEtContinuer(wait, idCommune));
    }

    public PageRessourcesMachines cliquerBoutonAnnuler(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnuler(wait, idCommune));
        return new PageRessourcesMachines(driver);
    }

    // Formulaire
    public void remplirFormulaireDonnee(WebDriverWait wait, String idCommune,
                                        String nom, String description, String type, String code, boolean checkBoxCode) throws Throwable {
        LOGGER.info("Verifie que la checkbox code est : " + checkBoxCode);
        seleniumTools.checkBoxCheck(wait, checkboxCode(wait, idCommune), checkBoxCode);
        if (!checkBoxCode) {
            LOGGER.info("Renseigne le code : " + code);
            seleniumTools.sendKey(wait, inputCode(wait, idCommune), code);
        }
        LOGGER.info("Renseigne le nom : " + nom);
        seleniumTools.sendKey(wait, inputNom(wait, idCommune), nom);
        LOGGER.info("Renseigne la description : " + description);
        seleniumTools.sendKey(wait, inputDescription(wait, idCommune), description);
        LOGGER.info("Renseigne le type : " + type);
        Select select = new Select(selectType(wait, idCommune));
        select.selectByVisibleText(type);
    }




}
