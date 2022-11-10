package fr.eql.libreplan.pageObject.PageCout;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class PageCoutFeuilleDeTempsCreer extends AbstractFullPage {

    public PageCoutFeuilleDeTempsCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                    WEBELEMENT
######################################################################################################################*/
    public WebElement titrePage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "19-cap")));
    }

    public List<WebElement> listBloc(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//fieldset[@class=\"z-fieldset\"]")));
    }

    // Donnee General
    public WebElement libelleType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "g9")));
    }

    public WebElement valeurType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "h9")));
    }

    public WebElement libelleCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "j9")));
    }

    public WebElement valeurCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "l9")));
    }

    public WebElement checkboxCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@id='"+idCommune+"m9-chdex']//input")));
    }

    public WebElement libelleCheckboxCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@id='"+idCommune+"m9-chdex']//label")));
    }

    // Champ Rubrique
    public WebElement tableauChampRubrique(WebDriverWait wait){
        return listBloc(wait).get(1).findElement(By.xpath(".//div[@class=\"z-fieldset-cnt\"]"));
    }

    // Lignes de feuille de temps
    public WebElement boutonAjouterUneLigne(WebDriverWait wait, String idCommune){
        return listBloc(wait).get(2).findElement(By.xpath(".//table[@class=\"z-hbox\"]//span"));
    }

    public List<WebElement> listLibelleTableauLigne(WebDriverWait wait){
        return listBloc(wait).get(2).findElements(By.xpath(".//tr[@class=\"z-columns\"]/th"));
    }

    public WebElement absenceValeurTableauLigne(WebDriverWait wait){
        return listBloc(wait).get(2).findElement(By.xpath(".//tbody[@class=\"z-rows\"]"));
    }

    public List<WebElement> listValeurTableauLigne(WebDriverWait wait){
        return listBloc(wait).get(2).findElements(By.xpath(".//tbody[@class=\"z-rows\"]/tr/td"));
    }


    public WebElement boutonEnregistrer(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,\"save-button\")]")));
    }

    public WebElement boutonSauverEtContinuer(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[contains(@class,\"saveandcontinue-button\")])[1]")));
    }

    public WebElement boutonSauvegarder(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[contains(@class,\"saveandcontinue-button\")])[2]")));
    }

    public WebElement boutonAnnuler(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class,\"cancel-button\")]")));
    }

/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/

    public PageCoutFeuilleDeTemps cliquerBoutonEnregistrer(WebDriverWait wait) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait));
        return new PageCoutFeuilleDeTemps(driver);
    }

    public List<String> recuperationLibelleBloc(WebDriverWait wait){
        List<String> listLibelleString = new ArrayList<>();
        for (WebElement listBlocWe : listBloc(wait)){
            WebElement listLibelle = listBlocWe.findElement(By.xpath("./legend/span"));
            listLibelleString.add(listLibelle.getText());
        }
        return listLibelleString;
    }

    // FORMULAIRE
    public void cliquerBoutonAjouterUneLigne(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonAjouterUneLigne(wait, idCommune));
    }


    public void remplirFormulaireLigne(WebDriverWait wait, String date, String ressource, String tache,
                                       String heure, String typeheure, boolean realise) throws Throwable {
        Map<String, WebElement> mapTableauLigne = recuperationTableauLigne(wait);
        LOGGER.info("Renseigne la date avec " + date);
        seleniumTools.sendKey(wait, mapTableauLigne.get("Date"), date);

        LOGGER.info("Renseigne la ressource avec " + ressource);
        renseignerLaRessource(wait, ressource);

        LOGGER.info("Renseigne la tache avec " + tache);
        renseignerLaTache(wait, tache);

        LOGGER.info("Renseigne la date avec " + heure);
        seleniumTools.sendKey(wait, mapTableauLigne.get("Heures"), heure);

        LOGGER.info("Renseigne le type d'heure avec " + typeheure);
        Select select = new Select(mapTableauLigne.get("Type d'heures"));
        select.selectByVisibleText(typeheure);

        LOGGER.info("Check si la checkbox Realisé est " + realise);
        seleniumTools.checkBoxCheck(wait, mapTableauLigne.get("Réalisé"), realise);
    }

    public void renseignerLaTache(WebDriverWait wait, String tache) throws Throwable {
        Map<String, WebElement> mapTableauLigne = recuperationTableauLigne(wait);
        WebElement loupeTache = mapTableauLigne.get("Tâche").findElement(By.xpath("./following-sibling::i"));

        LOGGER.info("Clique sur la loupe de la tache");
        seleniumTools.clickOnElement(wait, loupeTache);
        WebElement typeTacheWe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//div[@class=\"z-bandbox-pp z-bandbox-shadow\"]//div[text()='"+tache+"'])[1]")));
        LOGGER.info("Clique sur la tache " + tache);
        seleniumTools.clickOnElement(wait, typeTacheWe);

    }

    public void renseignerLaRessource(WebDriverWait wait, String ressource) throws Throwable {
        Map<String, WebElement> mapTableauLigne = recuperationTableauLigne(wait);
        WebElement flecheRessource = mapTableauLigne.get("Ressource").findElement(By.xpath("./following-sibling::i"));

        LOGGER.info("Clique sur la loupe de la tache");
        seleniumTools.clickOnElement(wait, flecheRessource);
        WebElement typeTacheWe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class=\"z-combobox-pp   z-combobox-shadow\"]//td[contains(text(),'"+ressource+"')]")));
        LOGGER.info("Clique sur la tache " + ressource);
        seleniumTools.clickOnElement(wait, typeTacheWe);

    }



    public List<String> recuperationLibelleTableauLigne(WebDriverWait wait) throws InterruptedException {
        List<String> listLibelleString = new ArrayList<>();
        for(WebElement libelleLigne : listLibelleTableauLigne(wait)){
            listLibelleString.add(libelleLigne.getText());
        }
        return listLibelleString;
    }

    public Map<String, WebElement> recuperationTableauLigne(WebDriverWait wait){
        Map<String, WebElement> mapTableauLigne = new HashMap<>();
        List<WebElement> listLibelle = listLibelleTableauLigne(wait);
        List<WebElement> listValeur = listValeurTableauLigne(wait);

        LOGGER.info("Recuperation de " + listLibelle.size() + " libellés");
        for(int i = 0; i < listLibelle.size(); i++){
            WebElement we;
            if(Objects.equals(listLibelle.get(i).getText(), "Type d'heures")){
                we = listValeur.get(i).findElement(By.xpath(".//select"));
            } else if (Objects.equals(listLibelle.get(i).getText(), "Op.")) {
                we = listValeur.get(i).findElement(By.xpath(".//span[@title=\"Supprimer\"]"));
            } else{
                we = listValeur.get(i).findElement(By.xpath(".//input[not(@class=\"z-timebox-inp\")]"));
            }
            mapTableauLigne.put(listLibelle.get(i).getText(), we);
        }
        return mapTableauLigne;
    }




}
