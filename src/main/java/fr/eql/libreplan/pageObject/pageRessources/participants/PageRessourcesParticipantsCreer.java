package fr.eql.libreplan.pageObject.pageRessources.participants;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRessourcesParticipantsCreer extends AbstractFullPage {
    public PageRessourcesParticipantsCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/
    // Message Erreur
    public WebElement messageAlerteDonneeObligatoire(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='z-errbox z-popup' and not(contains(@style,'display: none'))]//div[@class='z-errbox-center']")));
    }

    public WebElement flecheMessageAlerteDonneeObligatoire(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOf(messageAlerteDonneeObligatoire(wait).
                findElement(By.xpath("./ancestor::div[@title='Allez sur le mauvais champ']"))));
    }

    public WebElement dismissMessageAlerteDonneeObligatoire(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOf(messageAlerteDonneeObligatoire(wait).
                findElement(By.xpath("./ancestor::div[contains(@class,'z-errbox-close')]"))));
    }

    public List<WebElement> messageWarning(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class=\"message_WARNING\"]//span")));
    }

    // Bouton
    public WebElement boutonEnregistrer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='"+idCommune+"z5-cave']/span[contains(@class,'save-button')]/table")));
    }

    public WebElement boutonEnregistrerEtContinuer(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='"+idCommune+"z5-cave']/span[contains(@class,'saveandcontinue-button')]/table")));
    }

    public WebElement boutonAnnuler(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='"+idCommune+"z5-cave']/span[contains(@class,'cancel-button')]/table")));
    }




    // Formulaire
    //////// LIBELLE
    public WebElement libelleCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "i6")));
    }

    public WebElement libelleCheckboxCode(WebDriverWait wait, String idCommune) {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@id='"+ idCommune + "l6-chdex']//label")));
    }

    public WebElement libellePrenom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "n6")));
    }

    public WebElement libelleNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "t6")));
    }

    public WebElement libelleId(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "w6")));
    }

    public WebElement libelleType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "_7")));
    }

    public WebElement libelleSelectType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//select[@id='"+ idCommune + "17']/option[@selected='selected']")));
    }

    // Formulaire
    ////// Input
    public WebElement inputCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune + "k6")));
    }

    public WebElement checkboxCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune + "l6-real")));
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

    public WebElement selectType(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "17")));
    }

    // Onglet Calendrier
    public WebElement boutonOngletCalendrier(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "66-hm")));
    }

    public WebElement inputTypeCalendrier(WebDriverWait wait, String idCommune){
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



/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "_6-cnt"))).getText();
    }

    public String titreDuFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "b6-cnt"))).getText();
    }


    // Message Création
    public String messageCreation(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//div[@class='message_INFO']/span"))).getText();
    }

    // Récupération de l'orientation de la flèche du message d'erreur
    public String orientationFlecheMessageErreur(WebDriverWait wait){
        String orientationFleche = flecheMessageAlerteDonneeObligatoire(wait).getAttribute("class");
        int indexElementAncrage = orientationFleche.indexOf("z-arrow-");
        return orientationFleche.substring(indexElementAncrage);
    }

    // Cliquer Bouton
    public PageRessourcesParticipants cliquerBoutonEnregistrer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrer(wait, idCommune));
        return new PageRessourcesParticipants(driver);
    }

    public void cliquerEnregistrerEtContinuer(WebDriverWait wait, String idCommune) throws Throwable {
        seleniumTools.clickOnElement(wait, boutonEnregistrerEtContinuer(wait, idCommune));
    }

    // UTILISATEUR LIE
    public Map<String, WebElement> mapUtilisateurLie(WebDriverWait wait, String idCommune){
        List<WebElement> listOptionUtilisateurLie = driver.findElements(By.xpath("//span[@id='" + idCommune +"77']/span"));
        Map<String, WebElement> mapOptionUtilisateurLie = new HashMap<>();

        for(WebElement we : listOptionUtilisateurLie){
               WebElement libelle = we.findElement(By.xpath("./label"));
               WebElement input = we.findElement(By.xpath("./input"));
               mapOptionUtilisateurLie.put(libelle.getText(), input);
        }
        return mapOptionUtilisateurLie;
    }

    // Methode Formulaire
    public void remplirFormulaireMinimun(WebDriverWait wait, String idCommune, String nom, String prenom, String id) throws Throwable {
        LOGGER.info("Renseigne le prenom avec : " + prenom);
        seleniumTools.sendKey(wait, inputPrenom(wait, idCommune), prenom);
        LOGGER.info("Renseigne le nom avec : " + nom);
        seleniumTools.sendKey(wait, inputNom(wait, idCommune), nom);
        LOGGER.info("Renseigne le id avec : " + id);
        seleniumTools.sendKey(wait, inputId(wait, idCommune), id);
        LOGGER.info("Fin formulaire");
    }

    public void remplirFormulaire(WebDriverWait wait, String idCommune,
                                         boolean checkboxCode, String nom, String prenom, String id, String type) throws Throwable {
        LOGGER.info("Checkbox code : " + checkboxCode);
        seleniumTools.checkBoxCheck(wait, checkboxCode(wait, idCommune), checkboxCode);
        LOGGER.info("Renseigne le prenom avec : " + prenom);
        seleniumTools.sendKey(wait, inputPrenom(wait, idCommune), prenom);
        LOGGER.info("Renseigne le nom avec : " + nom);
        seleniumTools.sendKey(wait, inputNom(wait, idCommune), nom);
        LOGGER.info("Renseigne le id avec : " + id);
        seleniumTools.sendKey(wait, inputId(wait, idCommune), id);
        LOGGER.info("Renseigne le type avec : " + type);
        Select select = new Select(selectType(wait, idCommune));
        select.selectByVisibleText(type);
    }

    public void remplirUtilisateurLie(WebDriverWait wait, String idCommune, String typeUtilisateur) throws Throwable {
        Map<String, WebElement> mapUtilisateurLie = mapUtilisateurLie(wait, idCommune);
        LOGGER.info("Coche de la case : " + typeUtilisateur);
        seleniumTools.clickOnElement(wait, mapUtilisateurLie.get(typeUtilisateur));
    }

    public Map<String, WebElement> mapNouvelUtilisateur(WebDriverWait wait) {
        Map<String, WebElement> mapNouvelUtilisateur = new HashMap<>();
        List<WebElement> listNouvelUtilisateur = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[text()='Nouvel utilisateur']/following-sibling::div//div[@class='z-grid-body']//tbody[@class='z-rows']/tr")));
        for(WebElement we : listNouvelUtilisateur) {
            WebElement libelle = we.findElement(By.xpath(".//span"));
            WebElement input = we.findElement(By.xpath(".//input"));
            mapNouvelUtilisateur.put(libelle.getText(), input);
        }
        return mapNouvelUtilisateur;
    }

    public void remplirNouvelUtilisateur(WebDriverWait wait, String idCommune, String nomUtilisateur, String mdpUtilisateur, String mdpConfirmation, String email) throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "i8")));
        Map<String, WebElement> mapNouvelUtilisateur = mapNouvelUtilisateur(wait);
        LOGGER.info("Renseigne le nom utilisateur avec : " + nomUtilisateur);
        seleniumTools.sendKey(wait, mapNouvelUtilisateur.get("Nom d'utilisateur"), nomUtilisateur);
        LOGGER.info("Renseigne le mdp utilisateur avec : " + mdpUtilisateur);
        seleniumTools.sendKey(wait, mapNouvelUtilisateur.get("Mot de passe"), mdpUtilisateur);
        LOGGER.info("Renseigne la confirmation du mdp utilisateur avec : " + mdpConfirmation);
        seleniumTools.sendKey(wait, mapNouvelUtilisateur.get("Confirmation du mot de passe"), mdpConfirmation);
        LOGGER.info("Renseigne l'email utilisateur avec : " + email);
        seleniumTools.sendKey(wait, mapNouvelUtilisateur.get("Email"), email);
    }

    // Onglet Calendrier
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
