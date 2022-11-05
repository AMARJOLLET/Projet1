package fr.eql.libreplan.pageObject;

import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesJoursExceptionnels;
import fr.eql.libreplan.pageObject.pageRessources.criteres.PageRessourcesCriteres;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageCalendrier extends AbstractFullPage{

    public PageCalendrier(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENTS
######################################################################################################################*/


/*######################################################################################################################
													METHODES
######################################################################################################################*/

    public String titreDeLaPage(WebDriverWait wait, String idGenerique){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idGenerique + "f8"))).getText();
    }

    /**
     * La méthode permet de cliquer sur le sous-menu Critère dans le menu Ressources
     * @param wait  Le WebdriverWait utilisé
     * @param idCommune la partie constante de l'Id généré dynamiquement sur la page
     * @return  La page Ressources des Critères
     */
    public PageRessourcesCriteres cliquerRessourcesCriteres(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, getHeader().ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton critère");
        seleniumTools.mouseOver(wait, getHeader().ressourcesCritereButton(wait,idCommune));
        LOGGER.info("Click sur le bouton critère");
        seleniumTools.clickOnElement(wait, getHeader().ressourcesCritereButton(wait,idCommune));
        return new PageRessourcesCriteres(driver);
    }


    public PageRessourcesCalendrier cliquerRessourcesCalendrier(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, getHeader().ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Calendrier");
        seleniumTools.mouseOver(wait, getHeader().ressourcesCalendrierButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Calendrier");
        seleniumTools.clickOnElement(wait, getHeader().ressourcesCalendrierButton(wait,idCommune));
        return new PageRessourcesCalendrier(driver);
    }

    public PageRessourcesJoursExceptionnels cliquerRessourcesJoursExceptionnels(WebDriverWait wait, String idCommune) throws Throwable {
        LOGGER.info("Mouseover sur le bouton ressources");
        seleniumTools.mouseOver(wait, getHeader().ressourcesButton(wait,idCommune));
        LOGGER.info("Mouseover sur le bouton Jours Exceptionnels");
        seleniumTools.mouseOver(wait, getHeader().ressourcesJoursExceptionnelsButton(wait,idCommune));
        LOGGER.info("Click sur le bouton Jours Exceptionnels");
        seleniumTools.clickOnElement(wait, getHeader().ressourcesJoursExceptionnelsButton(wait,idCommune));
        return new PageRessourcesJoursExceptionnels(driver);
    }




}
