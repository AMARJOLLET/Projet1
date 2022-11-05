package fr.eql.libreplan.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class PageRessourcesCriteresCreer extends AbstractFullPage{
    public PageRessourcesCriteresCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "15-cnt"))).getText();
    }

    public String titreFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "45-hm"))).getText();
    }


    //Bouton
    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "h6-box")));
    }

    public WebElement boutonSauverEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "i6-box")));
    }

    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j6-box")));
    }

    public PageRessourcesCriteres cliquerBoutonAnnuler(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnuler(wait, idCommune));
        return new PageRessourcesCriteres(driver);
    }

    // Formulaire
    public List<String> listLibelleFormulaire(WebDriverWait wait, String idCommune){
        List<String> strListLibelle = new ArrayList<>();
        List<WebElement> listLibelle = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody[@id='"+ idCommune + "b5']//span[@class='z-label']")));
        for (WebElement webElement : listLibelle) {
            strListLibelle.add(webElement.getText());
        }
        return strListLibelle;
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
