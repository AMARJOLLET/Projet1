package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.pageRessources.criteres.PageRessourcesCriteres;
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

public class PageRessourcesCalendrierCreer extends AbstractFullPage {
    public PageRessourcesCalendrierCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "s4-cnt"))).getText();
    }

    public WebElement titreDeLaPageWe(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "s4-cnt")));
    }


    public String titreFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "v4-hm"))).getText();
    }

    // Message d'alerte
    public WebElement messageAlerte(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"message_WARNING\"]/span")));
    }

    public String messageAlerteColor(WebDriverWait wait){
        WebElement we = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"message_WARNING\"]")));
        return we.getCssValue("background-color");
    }

    public String messageAlerteDonneeObligatoire(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-errbox-center\"]"))).getText();
    }

    // Message Création
    public String messageCreation(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '"+ nom +"')]"))).getText();
    }

    public WebElement messageCreationGenerique(){
        return driver.findElement(By.xpath("//div[@class='message_INFO']"));
    }


    // WebElement Bouton
    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "z7-box")));
    }

    public WebElement boutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "_8-box")));
    }

    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "08-box")));
    }

    // Methode clique sur Bouton
    public PageRessourcesCalendrier cliquerBoutonAnnuler(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnuler(wait, idCommune));
        return new PageRessourcesCalendrier(driver);
    }

    public PageRessourcesCalendrier cliquerBoutonEnregistrer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait, idCommune));
        return new PageRessourcesCalendrier(driver);
    }

    public void cliquerBoutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrerEtContinuer(wait, idCommune));
    }

    // WebElement Formulaire
    public WebElement inputNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "45")));
    }

    public WebElement inputType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "85")));
    }

    public WebElement checkBoxCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "d5-real")));
    }

    public WebElement inputCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune + "c5")));
    }


    // Remplir Formulaire
    public void remplirFormulaire(WebDriverWait wait, String idCommune,
                                  String nom,
                                  Boolean code) throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(inputNom(wait, idCommune)));
        LOGGER.info("Début du formulaire");
        LOGGER.info("Renseigne le nom par : " + nom);
        seleniumTools.sendKey(wait, inputNom(wait, idCommune),nom);
        LOGGER.info("Vérifie la checkbox du code par : " + code);
        seleniumTools.checkBoxCheck(wait, checkBoxCode(wait, idCommune), code);
        LOGGER.info("Fin du formulaire");

    }

    // WebElement Exception
    public WebElement boutonCreerException(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "v6-box")));
    }

    public WebElement boutonMAJException(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "w6-box")));
    }

    public WebElement inputTypeException(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "s6-real")));
    }

    public WebElement inputDateDeDebut(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "g6-real")));
    }

    public WebElement inputDateDeFin(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "i6-real")));
    }

    public WebElement inputEffortNormal(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "l8-real")));
    }

    public WebElement inputEffortHeuresSupplementaires(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "o8-real")));
    }

    // Fonction Exception
    public void cliquerBoutonCreerException(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonCreerException(wait, idCommune));
    }

    public void cliquerBoutonMAJException(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonMAJException(wait, idCommune));
    }

    public void choisirTypeException(WebDriverWait wait, String idCommune, String typeException) throws Throwable {
        LOGGER.info("Choix du type d'exceptions");
        seleniumTools.sendKey(wait, inputTypeException(wait, idCommune), typeException);
    }

    public void remplirDateDeFin(WebDriverWait wait, String idCommune, String date) throws Throwable {
        LOGGER.info("Renseigne date de fin avec " + date);
        seleniumTools.sendKey(wait, inputDateDeFin(wait, idCommune), date);
    }

    public void remplirEffort(WebDriverWait wait, String idCommune, String effortNormal, String effortHeureSupplementaires) throws Throwable {
        LOGGER.info("Ajout de " + effortNormal + " heures dans l'effort normal");
        seleniumTools.sendKey(wait, inputEffortNormal(wait, idCommune), effortNormal);
        LOGGER.info("Ajout de " + effortHeureSupplementaires + " heures dans l'effort heures supplementaires");
        seleniumTools.sendKey(wait, inputEffortHeuresSupplementaires(wait, idCommune), effortHeureSupplementaires);
    }

    // Verification des tableaux Exceptions
    public Map<String, String> recuperationValeurException(WebDriverWait wait, String idCommune){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@id='" + idCommune + "z6-head']//tbody[not(contains(@style, 'visibility:hidden'))]//th[1]")));

        // List We
        List<WebElement> listLibelleException = driver.findElements(By.xpath
                ("//div[@id='" + idCommune + "z6-head']//tbody[not(contains(@style, 'visibility:hidden'))]//th"));
        List<WebElement> listValeurException = driver.findElements(By.xpath
                ("//div[@id='" + idCommune + "z6-body']//tbody[contains(@id,'rows')]//td"));

        // Creation Map
        Map<String, String> mapValeurException = new HashMap<>();
        for(int i = 0; i < listLibelleException.size(); i++){
            if(Objects.equals(listLibelleException.get(i).getText(), "Code")){
                WebElement we = listValeurException.get(i).findElement(By.xpath(".//input"));
                mapValeurException.put(listLibelleException.get(i).getText(), we.getAttribute("value"));
            } else {
                WebElement we = listValeurException.get(i).findElement(By.xpath(".//span"));
                mapValeurException.put(listLibelleException.get(i).getText(), we.getText());
            }
            LOGGER.info("Ajout de " + listLibelleException.get(i).getText() + " = " + listValeurException.get(i).getText());
        }
        return mapValeurException;
    }

    public Map<String, String> recuperationValeurProprieteJours(String idCommune){
        // List We
        WebElement jour = driver.findElement(By.id(idCommune + "t5-real"));
        WebElement type = driver.findElement(By.id(idCommune + "w5"));
        WebElement tempsTravaille = driver.findElement(By.id(idCommune + "z5"));

        // Creation Map
        Map<String, String> mapValeurException = new HashMap<>();
        mapValeurException.put("Jour", jour.getAttribute("value"));
        mapValeurException.put("Type", type.getText());
        mapValeurException.put("Temps travaillé", tempsTravaille.getText());
        return mapValeurException;
    }


}
