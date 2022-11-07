package fr.eql.libreplan.pageObject;

import fr.eql.libreplan.pageObject.pageRessources.avancement.PageRessourcesAvancement;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.joursExceptionnels.PageRessourcesJoursExceptionnels;
import fr.eql.libreplan.pageObject.pageRessources.machines.PageRessourcesMachines;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
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
                                                    METHODES
######################################################################################################################*/

    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "f8"))).getText();
    }

    public String titreDeLaPageUtilisateur(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(idCommune + "f0-cap"))).getText();
    }

    // Accès Ressources

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

}
