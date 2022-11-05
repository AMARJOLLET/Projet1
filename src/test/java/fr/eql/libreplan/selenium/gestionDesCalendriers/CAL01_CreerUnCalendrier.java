package fr.eql.libreplan.selenium.gestionDesCalendriers;

import fr.eql.libreplan.pageObject.PageCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.criteres.PageRessourcesCriteres;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CAL01_CreerUnCalendrier extends AbstractTestSelenium {
    // Chargement JDD
    protected String className = getClass().getSimpleName();
    protected String classPackage = this.getClass().getPackage().getName();
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    protected String url = "http://192.168.15.10:8090/libreplan";
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    public CAL01_CreerUnCalendrier() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrier pageCalendrier = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la page d'administration des calendriers");
        PageRessourcesCalendrier pageRessourcesCalendrier = pageCalendrier.cliquerRessourcesCalendrier(wait, idCommune);


    }
}
