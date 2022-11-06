package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class PageRessourcesParticipantsCreer extends AbstractFullPage {
    public PageRessourcesParticipantsCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "_6-cnt"))).getText();
    }

    // Message Création
    public String messageCreation(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span"))).getText();
    }

    // WebElement
    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"9f-box")));
    }

    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "7f-box")));
    }

    public WebElement boutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"8f-box")));
    }

    public WebElement inputPrenom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "o6")));
    }

    public WebElement inputNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "u6")));
    }

    public WebElement inputId(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "x6")));
    }


    // Methode
    public PageRessourcesParticipants cliquerBoutonEnregistrer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait, idCommune));
        return new PageRessourcesParticipants(driver);
    }

    public void cliquerEnregistrerEtContinuer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrerEtContinuer(wait, idCommune));
    }

    public void remplirFormulaire(WebDriverWait wait, String idCommune, String nom, String prenom, String id) throws Throwable {
        LOGGER.info("Renseigne le prenom avec : " + prenom);
        seleniumTools.sendKey(wait, inputPrenom(wait, idCommune), prenom);
        LOGGER.info("Renseigne le nom avec : " + nom);
        seleniumTools.sendKey(wait, inputNom(wait, idCommune), nom);
        LOGGER.info("Renseigne le id avec : " + id);
        seleniumTools.sendKey(wait, inputId(wait, idCommune), id);
        LOGGER.info("Fin formulaire");
    }

    // Onglet Calendrier

    // WebElement
    public WebElement boutonOngletCalendrier(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "66-hm")));
    }

    public WebElement inputType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "kb")));
    }

    public WebElement objetCalendrier(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "vb")));
    }

    public WebElement tableauProprieteDesJours(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "yb-cave")));
    }

    public WebElement boutonSupprimerCalendrier(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "ce-box")));
    }

    public WebElement sousOngletExceptions(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "dc-hm")));
    }

    public WebElement sousOngletSemaineDeTravail(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "ec-hm")));
    }

    public WebElement sousOngletPeriodeActivation(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "fc-hm")));
    }

    public WebElement libelleChoisirCalendrierParent(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "_b")));
    }

    public WebElement inputCalendrierParent(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "0b-real")));
    }


    // Methode
    public void cliquerOngletCalendrier(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonOngletCalendrier(wait, idCommune));
    }

    public void cliquerSupprimerCalendrier(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonSupprimerCalendrier(wait, idCommune));
    }

    public void renseignerCalendrierParent(WebDriverWait wait, String idCommune, WebElement weSendKey, String nomCalendrier) throws Throwable {
        seleniumTools.sendKey(wait, weSendKey, nomCalendrier);
        WebElement we = driver.findElement(By.id(idCommune + "_6-cnt"));
        seleniumTools.clickOnElement(wait, we);
    }


    public List<String> recupererListCalendrierParent(WebDriverWait wait, String idCommune) throws Throwable {
        // affichage de la comboList
        WebElement deplierTypeException = driver.findElement(By.id(idCommune+"0b-btn"));
        seleniumTools.clickOnElement(wait, deplierTypeException);

        // Attente que la list ne soit pas null
        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//table[@id='" + idCommune + "0b-cave']//tr[1]")).getText().length() != 0);
        Thread.sleep(500);
        List<WebElement> listWeCalendrierParent = driver.findElements(By.xpath("//table[@id='"+idCommune+"0b-cave']//tr"));
        List<String> listCalendrierParent = new ArrayList<>();
        for (WebElement we : listWeCalendrierParent){
            listCalendrierParent.add(we.getText());
        }
        return listCalendrierParent;
    }



}
