package fr.eql.libreplan.pageObject.pageRessources.criteres;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PageRessourcesCriteresCreer extends AbstractFullPage {
    public PageRessourcesCriteresCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }



/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    // Titre
    public WebElement titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "15-cnt")));
    }

    public boolean titreDeLaPageApresModification(WebDriverWait wait, String nouveau){
        Function<WebDriver, Boolean> titrePageModifier = driver -> driver.findElement(By.xpath("//*[contains(text(),'"+nouveau+"')]")).isDisplayed();
        return wait.until(titrePageModifier);
    }

    public WebElement titreFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "45-hm")));
    }


    // Message Création
    public WebElement messageCreation(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '"+ nom +"')]")));
    }


    // WebElement Bouton
    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "h6-box")));
    }


    public WebElement boutonSauverEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "i6-box")));
    }


    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j6-box")));
    }


    // Formulaire
    public List<WebElement> listLibelleFormulaire(WebDriverWait wait){
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(
                "//div[@class='z-tabbox']//tbody[@class='z-rows']//span[@class='z-label']")));
    }

    public WebElement inputNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "e5")));
    }

    public WebElement inputType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "h5-real")));
    }

    public WebElement checkBoxValeursMultiplesParRessource(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "k5-real")));
    }

    public WebElement checkBoxHierarchie(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "n5-real")));
    }

    public WebElement checkBoxActive(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q5-real")));
    }

    public WebElement inputDescription(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "t5")));
    }

/*######################################################################################################################
                                                  METHODES
######################################################################################################################*/
    // Methode clique sur Bouton
    public PageRessourcesCriteres cliquerBoutonAnnuler(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnuler(wait, idCommune));
        return new PageRessourcesCriteres(driver);
    }

    public PageRessourcesCriteres cliquerBoutonEnregistrer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait, idCommune));
        return new PageRessourcesCriteres(driver);
    }

    public void cliquerBoutonSauverEtContinuer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonSauverEtContinuer(wait, idCommune));
    }

    // Formulaire WebElement
    public List<String> recuperationLibelleFormulaire(WebDriverWait wait){
        List<String> strListLibelle = new ArrayList<>();
        for (WebElement webElement : listLibelleFormulaire(wait)) {
            strListLibelle.add(webElement.getText());
        }
        return strListLibelle;
    }

    // Formulaire Methodes
    public void remplirNomFormulaire(WebDriverWait wait, String idCommune, String nom) throws Throwable {
        LOGGER.info("Renseigne le nom par : " + nom);
        seleniumTools.sendKey(wait, inputNom(wait, idCommune),nom);
    }

    public void remplirFormulaire(WebDriverWait wait, String idCommune,
                                  String nom,
                                  String type,
                                  Boolean valeursMultiplesParRessource,
                                  Boolean hierarchie,
                                  Boolean active,
                                  String description) throws Throwable {
        LOGGER.info("Début du formulaire");
        LOGGER.info("Renseigne le nom par : " + nom);
        seleniumTools.sendKey(wait, inputNom(wait, idCommune),nom);
        LOGGER.info("Renseigne le type par : " + type);
        seleniumTools.sendKey(wait, inputType(wait, idCommune),nom);
        LOGGER.info("Vérifie la checkbox de la valeurs Multiples Par Ressource avec : " + valeursMultiplesParRessource);
        seleniumTools.checkBoxCheck(wait, checkBoxValeursMultiplesParRessource(wait, idCommune), valeursMultiplesParRessource);
        LOGGER.info("Vérifie la checkbox de la hiérarchie par : " + hierarchie);
        seleniumTools.checkBoxCheck(wait, checkBoxHierarchie(wait, idCommune), hierarchie);
        LOGGER.info("Vérifie la checkbox de l'activé par : " + active);
        seleniumTools.checkBoxCheck(wait, checkBoxActive(wait, idCommune), active);
        LOGGER.info("Renseigne la description par : " + description);
        seleniumTools.sendKey(wait, inputDescription(wait, idCommune),nom);
        LOGGER.info("Fin du formulaire");

    }
}
