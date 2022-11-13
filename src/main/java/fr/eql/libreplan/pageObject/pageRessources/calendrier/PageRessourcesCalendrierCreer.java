package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class PageRessourcesCalendrierCreer extends AbstractFullPage {
    public PageRessourcesCalendrierCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    // Titre
    public WebElement titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "s4-cnt")));
    }

    public WebElement titreFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "v4-hm")));
    }


    // Message Création
    public WebElement messageCreation(WebDriverWait wait, String nom){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span[contains(text(), '"+ nom +"')]")));
    }


    // Message d'alerte
    public WebElement messageAlerte(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"message_WARNING\"]/span")));
    }

    public WebElement messageAlerteDonneeObligatoire(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-errbox-center\"]")));
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


    // Exception

    public WebElement boutonCreerException(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "v6-box")));
    }

    public WebElement boutonMAJException(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "w6-box")));
    }

    public WebElement inputTypeException(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class=\"calendar-options-tabbox z-tabbox\"]//i[@class=\"z-combobox\"]/input")));
    }

    public WebElement inputDateDeDebut(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "g6-real")));
    }

    public WebElement inputDateDeFin(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "i6-real")));
    }

    public WebElement inputEffortNormal(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//td[@id='"+idCommune+"n6-chdex']//i[@title='Heures']/input")));
    }

    public WebElement inputEffortNormalMinute(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//td[@id='"+idCommune+"n6-chdex']//i[@title='Minutes']/input")));
    }

    public WebElement inputEffortHeuresSupplementaires(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//td[@id='"+idCommune+"p6-chdex']//i[@title='Heures']/input")));
    }

    public WebElement inputEffortHeuresSupplementairesMinute(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//td[@id='"+idCommune+"p6-chdex']//i[@title='Minutes']/input")));
    }

    // TABLEAU EXCEPTION
    public WebElement titreTableauException(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(
                idCommune + "x6")));
    }

    public List<WebElement> listLibelleTableauException(WebDriverWait wait, String idCommune){
        return titreTableauException(wait, idCommune).findElements(By.xpath(
                ".//tr[@class='z-listhead']/th"));
    }

    public List<WebElement> listRowTableauException(WebDriverWait wait, String idCommune){
        return titreTableauException(wait, idCommune).findElements(By.xpath(
                ".//tr[contains(@class,'z-listitem')]"));
    }

    // TABLEAU PROPRIETE DU JOUR
    public List<WebElement> listRowTableauProprieteJour(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class='day-details z-grid']//tbody[@class='z-rows']/tr")));
    }

/*######################################################################################################################
                                                  METHODES
######################################################################################################################*/
    public String messageAlerteColor(WebDriverWait wait){
        WebElement we = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"message_WARNING\"]")));
        return we.getCssValue("background-color");
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
    public Map<String, Map<String, String>> recuperationValeurException(WebDriverWait wait, String idCommune){
        // List We
        List<WebElement> listLibelleException = listLibelleTableauException(wait, idCommune);
        List<WebElement> listRowException = listRowTableauException(wait, idCommune);
        Map<String, Map<String, String>> mapTableauException = new HashMap<>();

        for(WebElement row : listRowException){
            Map<String, String> mapValeurException = new HashMap<>();
            List<WebElement> listValeurException = row.findElements(By.xpath("./td"));
            for(int i = 0; i < listLibelleException.size(); i++){
                if(Objects.equals(listLibelleException.get(i).getText(), "Code")){
                    WebElement we = listValeurException.get(i).findElement(By.xpath(".//input"));
                    mapValeurException.put(listLibelleException.get(i).getText(), we.getAttribute("value"));
                } else {
                    WebElement we = listValeurException.get(i).findElement(By.xpath(".//span"));
                    mapValeurException.put(listLibelleException.get(i).getText(), we.getText());
                }
            }
            mapTableauException.put(mapValeurException.get("Jour") + " - " + mapValeurException.get("Type d'exception"), mapValeurException);
        }
        return mapTableauException;
    }

    public Map<String, String> recuperationProprieteJours(WebDriverWait wait) throws InterruptedException {
        List<WebElement> listProprieteJour = listRowTableauProprieteJour(wait);
        Map<String, String> mapTableauProprieteJour = new HashMap<>();
        for(WebElement row : listProprieteJour){
            WebElement libelle = row.findElement(By.xpath("./td[1]//span"));
            String input;
            if(Objects.equals(libelle.getText(), "Jour")){
                input = row.findElement(By.xpath("./td[2]//input[contains(@class,'z-datebox-inp')]")).getAttribute("value");
            } else {
                input = row.findElement(By.xpath("./td[2]//span")).getText();
            }
            mapTableauProprieteJour.put(libelle.getText(), input);
        }
        return mapTableauProprieteJour;
    }

    public List<String> recuperationListTypeException(WebDriverWait wait, String idCommune) throws Throwable {
        // affichage de la comboList
        WebElement deplierTypeException = inputTypeException(wait, idCommune).findElement(By.xpath("./following-sibling::i"));
        seleniumTools.clickOnElement(wait, deplierTypeException);

        wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath(
                "//div[contains(@class,'z-combobox-pp')]//tr[contains(@class,'z-comboitem')]")).getText().length() != 0);

        Thread.sleep(500);
        List<WebElement> listWeTypeException = driver.findElements(By.xpath("//div[contains(@class,'z-combobox-pp')]//tr[contains(@class,'z-comboitem')]"));
        List<String> listTypeException = new ArrayList<>();
        for (WebElement we : listWeTypeException){
            listTypeException.add(we.getText());
        }
        return listTypeException;
    }

}
