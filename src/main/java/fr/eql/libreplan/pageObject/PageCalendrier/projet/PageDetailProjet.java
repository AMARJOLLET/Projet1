package fr.eql.libreplan.pageObject.PageCalendrier.projet;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import fr.eql.libreplan.pageObject.PageCalendrier.PageListeDesProjets;
import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageDetailProjet extends AbstractFullPage {

    public PageDetailProjet(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/

    // Onglet
    public List<WebElement> listOngletDetailProjet(WebDriverWait wait){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//div[@class=\"z-window-embedded\" and not(contains(@style, 'display:none'))]//li//span)[1]")));
        return driver.findElements(By.xpath("//div[@class=\"z-window-embedded\" and not(contains(@style, 'display:none'))]//li//span"));
    }

    //WBS
    public WebElement inputNouvelleTacheWBS(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text()='Nouvelle tâche']/ancestor::tr[1]//input[@class='z-textbox']")));
    }

    public WebElement inputHeuresWBS(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text()='Nouvelle tâche']/ancestor::tr[1]//input[@class='z-intbox']")));
    }

    public WebElement boutonAjouterWBS(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text()='Nouvelle tâche']/ancestor::tr[1]//td[text()='Ajouter']")));
    }


    public List<WebElement> listLibelleTableauWBS(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class=\"orderTree z-dottree\"]//tr[@class=\"z-treecols\"]/th")));
    }

    public List<WebElement> listRowTableauWBS(WebDriverWait wait){
        return driver.findElements(By.xpath(
                "//div[@class=\"orderTree z-dottree\"]//tbody[@class=\"z-treechildren\"]/tr"));
    }

    // Bouton du tableau WBS
    public WebElement boutonModifierWBS(WebDriverWait wait, String nom){
        return outilsProjet.boutonTableauValue(wait, nom, "Modifier");
    }

    public WebElement boutonSupprimerWBS(WebDriverWait wait, String nom){
        return outilsProjet.boutonTableauValue(wait, nom, "Supprimer");
    }

    public WebElement boutonFiniWBS(WebDriverWait wait, String nom){
        return outilsProjet.boutonTableauValue(wait, nom, "Fully scheduled");
    }

    public WebElement boutonDeprogrammeWBS(WebDriverWait wait, String nom){
        return outilsProjet.boutonTableauValue(wait, nom, "Déprogrammé");
    }

    public WebElement boutonDescendreWBS(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[@title=\"Descendre la tâche sélectionnée\"]")));
    }

    public WebElement boutonRemonterWBS(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[@title=\"Remonter la tâche sélectionnée\"]")));
    }

    // Bouton Enregistrer et Annuler
    public WebElement boutonEnregistrerProjet(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//table[contains(@class,\"toolbar-box\")]//span[contains(@title,'Enregistrer')]")));
    }

    public WebElement imageBoutonEnregistrerProjet(WebDriverWait wait){
        return boutonEnregistrerProjet(wait).findElement(By.xpath(".//img"));
    }

    public WebElement boutonAnnulerEdition(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//table[contains(@class,\"toolbar-box\")]//span[contains(@title,'Annuler')]")));
    }

    public WebElement imageBoutonboutonAnnulerEdition(WebDriverWait wait){
        return boutonAnnulerEdition(wait).findElement(By.xpath(".//img"));
    }

    // POPUP ENREGISTRER
    public WebElement boutonOKPopupEnregistrerProjet(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[@class='z-messagebox-btn z-button']")));
    }

    public WebElement textPopupEnregistrerProjet(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class=\"z-messagebox\"]/span")));
    }

    // POPUP ANNULATION
    public WebElement textPopupAnnulationEdition(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class=\"z-messagebox\"]/span")));
    }

    public WebElement boutonOKPopupAnnulationEdition(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//span[@class='z-messagebox-btn z-button'])[1]")));
    }

    public WebElement boutonAnnulerPopupAnnulationEdition(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//span[@class=\"z-messagebox-btn z-button\"])[2]")));
    }


/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    // Acces à la page liste des projets
    public PageListeDesProjets cliquerCalendrierProjet(WebDriverWait wait, String idCommune) throws Throwable {
        return getHeader().cliquerCalendrierProjet(wait, idCommune);
    }

    // Acces à la page de Planification Projet
    public PagePlanificationProjet cliquerOngletPlanificationDeProjet(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, getHeaderCalendrier().mapOngletProjet(wait, idCommune).get("Planification de projet"));
        return new PagePlanificationProjet(driver);
    }

    // Onglet WBS
    public void remplirFormulaireWBS(WebDriverWait wait, String nouvelleTache, String heures) throws Throwable {
        LOGGER.info("Renseigne le nom de la nouvelle tache avec : " + nouvelleTache);
        seleniumTools.sendKey(wait, inputNouvelleTacheWBS(wait), nouvelleTache);
        LOGGER.info("Renseigne le nombre d'heures avec : " + nouvelleTache);
        seleniumTools.sendKey(wait, inputHeuresWBS(wait), heures);
    }

    public void competerFormulaireWBS(WebDriverWait wait, String nomTache, String code, String dateDebut, String DateEcheance) throws Throwable {
        Map<String, WebElement> mapValeurRow = recuperationValeurTableauWBS(wait).get(nomTache);
        LOGGER.info("Renseigne le code de la tache " + nomTache + " avec : " + code);
        seleniumTools.sendKey(wait, mapValeurRow.get("Code").findElement(By.xpath(".//input")), code);
        LOGGER.info("Renseigne la date de debut de la tache " + nomTache + " avec : " + dateDebut);
        seleniumTools.sendKey(wait, mapValeurRow.get("Doit débuter après").findElement(By.xpath(".//input")), dateDebut);
        LOGGER.info("Renseigne la date échéance de la tache " + nomTache + " avec : " + DateEcheance);
        seleniumTools.sendKey(wait, mapValeurRow.get("Echéance").findElement(By.xpath(".//input")), DateEcheance);
    }

    public void cliquerBoutonAjouterWBS(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAjouterWBS(wait));
    }

    public void cliquerBoutonDescendreWBS(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonDescendreWBS(wait));
    }

    public void cliquerBoutonRemonterWBS(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonRemonterWBS(wait));
    }

    public Map<String, Map<String, WebElement>> recuperationValeurTableauWBS(WebDriverWait wait){
        return outilsProjet.recuperationValeurTableauInput(listLibelleTableauWBS(wait), listRowTableauWBS(wait));
    }

    public List<String> recuperationNomTache(WebDriverWait wait){
        List<String> listNomTache = new ArrayList<>();
        for(Map.Entry mapNomTache : recuperationValeurTableauWBS(wait).entrySet()){
            listNomTache.add((String) mapNomTache.getKey());
        }
        return listNomTache;
    }

    public List<Map<String, WebElement>> ordreValeurTableau(WebDriverWait wait){
        return outilsProjet.ordreValeurTableauInput(listLibelleTableauWBS(wait), listRowTableauWBS(wait));
    }

    // POPUP ENREGISTRER
    public void cliquerBoutonEnregisrerProjet(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrerProjet(wait));
    }

    public void cliquerBoutonOKPopupEnregistrerProjet(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonOKPopupEnregistrerProjet(wait));
    }

    // POPUP ANNULATION
    public void cliquerBoutonAnnulerEdition(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnulerEdition(wait));
    }

    public void cliquerBoutonAnnulerPopup(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAnnulerPopupAnnulationEdition(wait));
    }

    public PageCalendrierPlanification cliquerBoutonOkPopup(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonOKPopupAnnulationEdition(wait));
        return new PageCalendrierPlanification(driver);
    }


    // ONGLET PROJET
    public List<String> recuperationListeOngletProjet(WebDriverWait wait, String idCommune){
        return getHeaderCalendrier().recuperationListeOngletProjet(wait, idCommune);
    }



    public List<String> recuperationListeOngletDetailProjet(WebDriverWait wait){
        List<String> listOngletDetailProjetString = new ArrayList<>();
        for(WebElement onglet : listOngletDetailProjet(wait)){
            listOngletDetailProjetString.add(onglet.getText());
        }
        return listOngletDetailProjetString;
    }
}
