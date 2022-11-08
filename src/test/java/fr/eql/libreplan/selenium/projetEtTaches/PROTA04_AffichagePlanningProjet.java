package fr.eql.libreplan.selenium.projetEtTaches;

import fr.eql.libreplan.pageObject.PageCalendrier.projet.PageDetailProjet;
import fr.eql.libreplan.pageObject.PageCalendrier.PageListeDesProjets;
import fr.eql.libreplan.pageObject.PageCalendrier.projet.PagePlanificationProjet;
import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class PROTA04_AffichagePlanningProjet extends AbstractTestSelenium {
    // Chargement JDD
    protected String className = getClass().getSimpleName();
    protected String classPackage = this.getClass().getPackage().getName();
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
    private static final SimpleDateFormat sdfWBS = new SimpleDateFormat("dd/MM/yyyy");


    // JDD Projet
    protected String nomProjet = listJdd.get(1).get("Nom");
    protected String modeleProjet = listJdd.get(1).get("Modèle");
    protected String codeProjet = listJdd.get(1).get("Code");
    protected boolean checkboxCodeProjet = Boolean.parseBoolean(listJdd.get(1).get("checkboxCode"));
    protected String nombreJourDateDebut = listJdd.get(1).get("Date de début");
    protected String nombreJourDateEcheance = listJdd.get(1).get("Echeance");
    protected String clientProjet = listJdd.get(1).get("Client");
    protected String calendrierProjet = listJdd.get(1).get("Calendrier");

    // JDD WBS
    protected String nouvelleTacheWBS = listJdd.get(2).get("Nouvelle tâche");
    protected String heuresWBS = listJdd.get(2).get("Heures");
    protected String codeWBS = listJdd.get(2).get("Code");
    protected String jourDateDebutWBS = listJdd.get(2).get("Date de début");


    public PROTA04_AffichagePlanningProjet() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la liste des projets");
        PageListeDesProjets pageListeDesProjets = pageCalendrierPlanification.cliquerCalendrierProjet(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la liste des projets");
        assertion.verifyEquals("Liste des projets", pageListeDesProjets.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        Map<String, WebElement> mapValeurTableau = pageListeDesProjets.recuperationValeurTableau(wait).get(nomProjet);
        if(mapValeurTableau == null){
            LOGGER.info("Création des dates");
            Calendar calendarDateDebut = Calendar.getInstance();
            Calendar calendarDateEcheance = Calendar.getInstance();
            calendarDateDebut.add(Calendar.DAY_OF_MONTH, Integer.parseInt(nombreJourDateDebut));
            calendarDateEcheance.add(Calendar.DAY_OF_MONTH, Integer.parseInt(nombreJourDateEcheance));
            String dateDebutProjet = sdf.format(calendarDateDebut.getTime());
            String dateEcheance = sdf.format(calendarDateEcheance.getTime());
            LOGGER.info("Generation date de debut: " + dateDebutProjet + " et date échéance " + dateEcheance);
            LOGGER.info("Remplissage du Formulaire du projet");
            pageCalendrierPlanification.cliquerCreerUnProjet(wait, idCommune);
            pageCalendrierPlanification.remplirFormulaireCreationProjet(wait, idCommune,
                    nomProjet, modeleProjet, checkboxCodeProjet, codeProjet, dateDebutProjet, dateEcheance, clientProjet, calendrierProjet);
            LOGGER.info("Formulaire rempli");
            PageDetailProjet pageDetailProjet = pageCalendrierPlanification.cliquerAccepterBouton(wait);
            LOGGER.info("Formulaire enregistrer");
            pageListeDesProjets = pageDetailProjet.cliquerCalendrierProjet(wait, idCommune);
            idCommune = outilsProjet.retournerIdCommune(wait);
        }

        LOGGER.info("Pas de test 3 -- Accéder à la page d'édition du projet ");
        PageDetailProjet pageDetailProjet = pageListeDesProjets.cliquerNomProjet(wait, nomProjet);
        LOGGER.info("Vérification des onglets");
        List<String> listOngletDetailProjet = pageDetailProjet.recuperationListeOngletDetailProjet(wait);
        assertion.verifyEquals("WBS (tâches)", listOngletDetailProjet.get(0),
                "L'onglet WBS (tâches) n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Accéder à la page de planification du projet");
        LOGGER.info("Récupération de la liste des taches");
        List<String> listTacheProjet = pageDetailProjet.recuperationNomTache(wait);
        if(listTacheProjet.size() == 0){
            LOGGER.info("Pas de tache disponible -- Creation d'une nouvelle tache");
            pageDetailProjet.remplirFormulaireWBS(wait, nouvelleTacheWBS, heuresWBS);
            pageDetailProjet.cliquerBoutonAjouterWBS(wait);
            pageDetailProjet.cliquerBoutonEnregisrerProjet(wait);
            pageDetailProjet.cliquerBoutonOkPopup(wait);
        }

        PagePlanificationProjet pagePlanificationProjet = pageDetailProjet.cliquerOngletPlanificationDeProjet(wait, idCommune);
        wait.until(ExpectedConditions.elementToBeClickable(pagePlanificationProjet.selectDate(wait)));
        Map<String, Map<String, WebElement>> recuperationValeurTaskDetail = pagePlanificationProjet.recuperationValeurTaskDetail(wait);
        assertion.verifyTrue(recuperationValeurTaskDetail.size() > 0, "La liste des taskDetails est vide");
        for(String nomTache : listTacheProjet){
            assertion.verifyTrue(recuperationValeurTaskDetail.containsKey(nomTache),
                    "La tache " + nomTache + " n'est pas présent dans la list des taskDetails");
        }

        LOGGER.info("Pas de test 5 -- Zoom - Année");
        pagePlanificationProjet.selectionnerTypeDate(wait, "Année");
        Thread.sleep(1000);
        List<List<String>> listTimeTracker = pagePlanificationProjet.recuperationTimeTracker(wait);
        assertion.verifyEquals("2022", listTimeTracker.get(0).get(0),
                "L'affichage des années du timeTracker n'est pas celui attendu ");
        assertion.verifyEquals("H1", listTimeTracker.get(1).get(0),
                "L'affichage sous les années du timeTracker n'est pas celui attendu ");
        assertion.verifyEquals("H2", listTimeTracker.get(1).get(1),
                "L'affichage sous les années du timeTracker n'est pas celui attendu ");

        LOGGER.info("Pas de test 6 -- Zoom - Trimestre");
        pagePlanificationProjet.selectionnerTypeDate(wait, "Trimestre");
        Thread.sleep(1000);
        listTimeTracker = pagePlanificationProjet.recuperationTimeTracker(wait);
        assertion.verifyEquals("2022", listTimeTracker.get(0).get(0),
                "L'affichage des trimestres du timeTracker n'est pas celui attendu ");
        assertion.verifyEquals("Q1", listTimeTracker.get(1).get(0),
                "L'affichage sous les années du timeTracker n'est pas celui attendu ");
        assertion.verifyEquals("Q2", listTimeTracker.get(1).get(1),
                "L'affichage sous les trimestres du timeTracker n'est pas celui attendu ");
        assertion.verifyEquals("Q3", listTimeTracker.get(1).get(2),
                "L'affichage sous les trimestres du timeTracker n'est pas celui attendu ");
        assertion.verifyEquals("Q4", listTimeTracker.get(1).get(3),
                "L'affichage sous les trimestres du timeTracker n'est pas celui attendu ");

        LOGGER.info("Pas de test 7 -- Zoom - Mois");
        pagePlanificationProjet.selectionnerTypeDate(wait, "Mois");
        Thread.sleep(1000);
        listTimeTracker = pagePlanificationProjet.recuperationTimeTracker(wait);
        assertion.verifyEquals("2022,H2", listTimeTracker.get(0).get(0),
                "L'affichage des années du timeTracker n'est pas celui attendu ");
        assertion.verifyTrue(listTimeTracker.get(1).contains("nov."),
                "L'affichage sous les années du timeTracker n'est pas celui attendu ");

        LOGGER.info("FIN DU TEST");

    }
}
