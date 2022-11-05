package utils;

import fr.eql.libreplan.pageObject.PageCalendrier;
import fr.eql.libreplan.pageObject.PageLogin;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrierCreer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MethodesProjet extends InstanciationDriver {
    OutilsProjet outilsProjet = new OutilsProjet();
    Assertion assertion = new Assertion(driver);

    public MethodesProjet(WebDriver driver) {
        super(driver);
    }

    public PageCalendrier seConnecter(WebDriverWait wait, String username, String password) throws Throwable {
        LOGGER.info("Pas de test 1 -- Connexion à l'application - Profil Admin");

        PageLogin pageLogin = new PageLogin(driver);
        PageCalendrier pageCalendrier = pageLogin.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Planification des projets", pageCalendrier.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        return new PageCalendrier(driver);
    }



    public void AccéderPageAdministrationCalendriers(WebDriverWait wait) throws Throwable {
        LOGGER.info("Pas de test 2 -- Accéder à la page d'administration des calendriers");
        PageCalendrier pageCalendrier = new PageCalendrier(driver);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        PageRessourcesCalendrier pageRessourcesCalendrier = pageCalendrier.cliquerRessourcesCalendrier(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page d'administration des calendriers");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du tableau");
        List<String> listLibelleTableau = pageRessourcesCalendrier.recuperationLibelleTableau(idCommune);
        LOGGER.info("Vérification des libellés du tableau des calendriers");
        assertion.verifyEquals("Nom", listLibelleTableau.get(0),
                "Le libellé est incorrect");
        assertion.verifyEquals("Hérité de la date", listLibelleTableau.get(1),
                "Le libellé est incorrect");
        assertion.verifyEquals("Héritages à jour", listLibelleTableau.get(2),
                "Le libellé est incorrect");
        assertion.verifyEquals("Opérations", listLibelleTableau.get(3),
                "Le libellé est incorrect");
    }
}
