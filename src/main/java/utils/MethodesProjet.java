package utils;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.PageLogin;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrierCreer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MethodesProjet extends InstanciationDriver {
    protected OutilsProjet outilsProjet = new OutilsProjet(driver);
    protected Assertion assertion = new Assertion(driver);

    public MethodesProjet(WebDriver driver) {
        super(driver);
    }

    public PageCalendrierPlanification seConnecter(WebDriverWait wait, String username, String password) throws Throwable {
        LOGGER.info("Pas de test 1 -- Connexion à l'application - Profil Admin");

        PageLogin pageLogin = new PageLogin(driver);
        PageCalendrierPlanification pageCalendrierPlanification = pageLogin.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Planification des projets", pageCalendrierPlanification.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        return new PageCalendrierPlanification(driver);
    }



    public void VerificationPageAdministrationCalendriers(WebDriverWait wait) throws Throwable {
        PageRessourcesCalendrier pageRessourcesCalendrier = new PageRessourcesCalendrier(driver);
        String idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page d'administration des calendriers");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du tableau");
        List<String> listLibelleTableau = pageRessourcesCalendrier.recuperationLibelleTableau(wait);
        LOGGER.info("Vérification des libellés du tableau des calendriers");
        assertion.verifyEquals("Nom", listLibelleTableau.get(0),
                "Le libellé est incorrect");
        assertion.verifyEquals("Hérité de la date", listLibelleTableau.get(1),
                "Le libellé est incorrect");
        assertion.verifyEquals("Héritages à jour", listLibelleTableau.get(2),
                "Le libellé est incorrect");
        assertion.verifyEquals("Opérations", listLibelleTableau.get(3),
                "Le libellé est incorrect");
        LOGGER.info("Vérification du bouton Créer");
        assertion.verifyEquals("Créer", pageRessourcesCalendrier.boutonCreer(wait, idCommune).getText(),
                "Le bouton créer n'est pas celui attendu");
    }

    public PageRessourcesCalendrier creationCalendrier(WebDriverWait wait, String idCommune, String nomCalendrier, boolean codeCalendrier) throws Throwable {
        PageRessourcesCalendrier pageRessourcesCalendrier = new PageRessourcesCalendrier(driver);
        PageRessourcesCalendrierCreer pageRessourcesCalendrierCreer = pageRessourcesCalendrier.cliquerBoutonCreer(wait, idCommune);
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCalendrierCreer.remplirFormulaire(wait, idCommune, nomCalendrier, codeCalendrier);
        LOGGER.info("Enregistrement du formulaire");
        pageRessourcesCalendrier = pageRessourcesCalendrierCreer.cliquerBoutonEnregistrer(wait, idCommune);
        return new PageRessourcesCalendrier(driver);
    }
}
