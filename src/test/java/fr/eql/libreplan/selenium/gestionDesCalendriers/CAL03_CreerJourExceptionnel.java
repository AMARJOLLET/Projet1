package fr.eql.libreplan.selenium.gestionDesCalendriers;

import fr.eql.libreplan.pageObject.PageCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesJoursExceptionnels;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CAL03_CreerJourExceptionnel extends AbstractTestSelenium {
    // Chargement JDD
    protected String className = getClass().getSimpleName();
    protected String classPackage = this.getClass().getPackage().getName();
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    protected String url = "http://192.168.15.10:8090/libreplan";
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    public CAL03_CreerJourExceptionnel() throws IOException {
    }

    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrier pageCalendrier = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la page d'administration des jours exceptionnels des calendriers");
        PageRessourcesJoursExceptionnels pageRessourcesJoursExceptionnels = pageCalendrier.cliquerRessourcesJoursExceptionnels(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Jours exceptionnels du calendrier Liste", pageRessourcesJoursExceptionnels.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des titres du tableau");



    }

}
