package utils;

import fr.eql.libreplan.pageObject.PageCalendrier;
import fr.eql.libreplan.pageObject.PageLogin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MethodesProjet extends InstanciationDriver{
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
}
