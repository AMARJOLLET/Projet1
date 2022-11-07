package fr.eql.libreplan.pageObject;

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

public class HeaderPage extends AbstractFullPage {

    public HeaderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                    WebElement
######################################################################################################################*/
    // Calendrier et ses sous-menus
    public WebElement calendrierButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "7-b")));
    }

    public WebElement calendrierProjetsButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "9-a")));
    }

    public WebElement calendrierChargementDesRessourcesButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "b-a")));
    }

    public WebElement calendrierRessourcesEnFileButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "c-a")));
    }

    public WebElement calendrierCavenasButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "d-a")));
    }

    public WebElement calendrierImporterUnProjetButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "e-a")));
    }

    // Resources et ses sous-menus
    public WebElement ressourcesButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "r-b")));
    }

    public WebElement ressourcesCritereButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "y-a")));
    }

    public WebElement ressourcesCalendrierButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "w-a")));
    }

    public WebElement ressourcesJoursExceptionnelsButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "x-a")));
    }

    public WebElement ressourcesParticipantsButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "t-a")));
    }

    public WebElement ressourcesAvancementButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "z-a")));
    }

    public WebElement ressourcesMachinesButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "u-a")));
    }


/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/

    // Deconnexion
    public PageLogin seDeconnecter(WebDriverWait wait) throws Throwable {
        WebElement deconnexion = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class=\"user-area\"]//*[contains(@href,'logout')]")));
        seleniumTools.clickOnElement(wait, deconnexion);
        return new PageLogin(driver);
    }



    public PageRessourcesCriteres cliquerRessourcesCriteres(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton critère");
        seleniumTools.mouseOver(wait, ressourcesCritereButton(wait,idCommune));
        LOGGER.info("Click sur le bouton critère");
        seleniumTools.clickOnElement(wait, ressourcesCritereButton(wait,idCommune));
        return new PageRessourcesCriteres(driver);
    }



    public PageRessourcesCalendrier cliquerRessourcesCalendrier(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Calendrier");
        seleniumTools.mouseOver(wait, ressourcesCalendrierButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Calendrier");
        seleniumTools.clickOnElement(wait, ressourcesCalendrierButton(wait,idCommune));
        return new PageRessourcesCalendrier(driver);
    }



    public PageRessourcesJoursExceptionnels cliquerRessourcesJoursExceptionnels(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Jours Exceptionnels");
        seleniumTools.mouseOver(wait, ressourcesJoursExceptionnelsButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Jours Exceptionnels");
        seleniumTools.clickOnElement(wait, ressourcesJoursExceptionnelsButton(wait,idCommune));
        return new PageRessourcesJoursExceptionnels(driver);
    }

    public PageRessourcesParticipants cliquerRessourcesParticipants(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Participant");
        seleniumTools.mouseOver(wait, ressourcesParticipantsButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Participant");
        seleniumTools.clickOnElement(wait, ressourcesParticipantsButton(wait,idCommune));
        return new PageRessourcesParticipants(driver);
    }

    public PageRessourcesAvancement cliquerRessourcesAvancement(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Avancement");
        seleniumTools.mouseOver(wait, ressourcesAvancementButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Avancement");
        seleniumTools.clickOnElement(wait, ressourcesAvancementButton(wait,idCommune));
        return new PageRessourcesAvancement(driver);
    }

    public PageRessourcesMachines cliquerRessourcesMachines(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Machines");
        seleniumTools.mouseOver(wait, ressourcesMachinesButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Machines");
        seleniumTools.clickOnElement(wait, ressourcesMachinesButton(wait,idCommune));
        return new PageRessourcesMachines(driver);
    }


}
