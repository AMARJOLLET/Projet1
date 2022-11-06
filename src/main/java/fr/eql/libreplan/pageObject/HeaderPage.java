package fr.eql.libreplan.pageObject;

import fr.eql.libreplan.pageObject.pageRessources.avancement.PageRessourcesAvancement;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesJoursExceptionnels;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesParticipants;
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
                                                    METHODES
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

    public PageRessourcesCriteres cliquerRessourcesCriteres(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton critère");
        seleniumTools.mouseOver(wait, ressourcesCritereButton(wait,idCommune));
        LOGGER.info("Click sur le bouton critère");
        seleniumTools.clickOnElement(wait, ressourcesCritereButton(wait,idCommune));
        return new PageRessourcesCriteres(driver);
    }

    public WebElement ressourcesCalendrierButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "w-a")));
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

    public WebElement ressourcesJoursExceptionnelsButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "x-a")));
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

    public WebElement ressourcesParticipantsButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "t-a")));
    }

    public PageRessourcesParticipants cliquerRessourcesParticipants(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Jours Exceptionnels");
        seleniumTools.mouseOver(wait, ressourcesParticipantsButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Jours Exceptionnels");
        seleniumTools.clickOnElement(wait, ressourcesParticipantsButton(wait,idCommune));
        return new PageRessourcesParticipants(driver);
    }

    public WebElement ressourcesAvancementButton(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "z-a")));
    }

    public PageRessourcesAvancement cliquerRessourcesAvancement(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Jours Exceptionnels");
        seleniumTools.mouseOver(wait, ressourcesAvancementButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Jours Exceptionnels");
        seleniumTools.clickOnElement(wait, ressourcesAvancementButton(wait,idCommune));
        return new PageRessourcesAvancement(driver);
    }



}
