package fr.eql.libreplan.pageObject;

import fr.eql.libreplan.pageObject.PageCalendrier.PageDetailCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.avancement.PageRessourcesAvancement;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.joursExceptionnels.PageRessourcesJoursExceptionnels;
import fr.eql.libreplan.pageObject.pageRessources.machines.PageRessourcesMachines;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
import fr.eql.libreplan.pageObject.pageRessources.criteres.PageRessourcesCriteres;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class PageCalendrierPlanification extends AbstractFullPage{

    public PageCalendrierPlanification(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


/*######################################################################################################################
                                               Accès Ressources
######################################################################################################################*/
    public PageRessourcesCriteres cliquerRessourcesCriteres(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesCriteres(wait, idCommune);
    }

    public PageRessourcesCalendrier cliquerRessourcesCalendrier(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesCalendrier(wait, idCommune);
    }

    public PageRessourcesJoursExceptionnels cliquerRessourcesJoursExceptionnels(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesJoursExceptionnels(wait, idCommune);
    }

    public PageRessourcesParticipants cliquerRessourcesParticipants(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesParticipants(wait, idCommune);
    }

    public PageRessourcesAvancement cliquerRessourcesAvancement(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesAvancement(wait, idCommune);
    }

    public PageRessourcesMachines cliquerRessourcesMachine(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerRessourcesMachines(wait, idCommune);
    }


/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    public WebElement creerUnProjet(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "q3-box")));
    }

    public WebElement boutonAccepter(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[contains(@class,\"save-button\")]")));
    }

    public WebElement boutonAnnuler(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[contains(@class,\"cancel-button\")]")));
    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "f8"))).getText();
    }

    public String titreDeLaPageUtilisateur(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "f0-cap"))).getText();
    }

    public void cliquerCreerUnProjet(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, creerUnProjet(wait, idCommune));
    }

    public PageDetailCalendrier cliquerAccepterBouton(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAccepter(wait));
        return new PageDetailCalendrier(driver);
    }


    // Recuperation Creation Projet
    public Map<String, WebElement> recuperationTableau(WebDriverWait wait, String idCommune){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("(//tbody[@id='"+idCommune+"m7']//span[@class='z-label'])[1]")));
        // Recuperation de tous les libelles et input
        Map<String, WebElement> mapValeurTableau = new HashMap<>();
        List<WebElement> listLibelle= driver.findElements(By.xpath(
                "//tbody[@id='"+idCommune+"m7']//span[@class='z-label']"));
        List<WebElement> listinput= driver.findElements(By.xpath(
                "//tbody[@id='"+idCommune+"m7']//input[not(@type=\"checkbox\") and not(@class=\"z-timebox-inp\")]"));
        LOGGER.info(listLibelle.size() + " rows ont été détectées");
        for(int i = 0; i < listLibelle.size(); i++){
            mapValeurTableau.put(listLibelle.get(i).getText(), listinput.get(i));
        }

        // Ajout du générer le code
        WebElement labelCode = driver.findElement(By.xpath("//span[@id='"+idCommune+"48']/label"));
        WebElement inputCode = driver.findElement(By.xpath("//span[@id='"+idCommune+"48']/input"));
        mapValeurTableau.put(labelCode.getText(), inputCode);

        return mapValeurTableau;
    }

    public void remplirFormulaireCreationProjet(WebDriverWait wait, String idCommune,
                                                String nom, String modele, boolean checkboxCode, String code, String dateDebut,
                                                String dateEcheance, String client, String calendrier) throws Throwable {
        LOGGER.info("Récupération des valeurs du tableau");
        Map<String, WebElement> mapValeurTableau = recuperationTableau(wait, idCommune);
        LOGGER.info("Renseigne le nom avec " + nom);
        seleniumTools.sendKey(wait, mapValeurTableau.get("Nom"), nom);
        LOGGER.info("Renseigne le modele avec " + modele);
        seleniumTools.sendKey(wait, mapValeurTableau.get("Modèle"), modele);
        LOGGER.info("Coche la checkbox code " + checkboxCode);
        seleniumTools.checkBoxCheck(wait, mapValeurTableau.get("Générer le code"), checkboxCode);
        LOGGER.info("Renseigne le code avec " + code);
        seleniumTools.sendKey(wait, mapValeurTableau.get("Code"), code);
        LOGGER.info("Renseigne la date de Début avec " + dateDebut);
        seleniumTools.sendKey(wait, mapValeurTableau.get("Date de début"), dateDebut);
        LOGGER.info("Renseigne la date d'Echeance avec " + dateEcheance);
        seleniumTools.sendKey(wait, mapValeurTableau.get("Echéance"), dateEcheance);
        LOGGER.info("Renseigne le client avec " + client);
        seleniumTools.sendKey(wait, mapValeurTableau.get("Client"), client);
        LOGGER.info("Renseigne le calendrier avec " + calendrier);
        seleniumTools.sendKey(wait, mapValeurTableau.get("Calendrier"), calendrier);
    }



}
