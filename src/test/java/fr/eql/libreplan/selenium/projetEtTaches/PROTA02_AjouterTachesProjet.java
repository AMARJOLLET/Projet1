package fr.eql.libreplan.selenium.projetEtTaches;

import fr.eql.libreplan.pageObject.PageCalendrier.projet.PageDetailProjet;
import fr.eql.libreplan.pageObject.PageCalendrier.PageListeDesProjets;
import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class PROTA02_AjouterTachesProjet extends AbstractTestSelenium {
    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    // Connexion
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

    protected String nouvelleTacheWBS2 = listJdd.get(3).get("Nouvelle tâche");
    protected String codeWBS2 = listJdd.get(3).get("Code");
    protected String jourDateDebutWBS2 = listJdd.get(3).get("Date de début");

    protected String nouvelleTacheWBS3 = listJdd.get(4).get("Nouvelle tâche");
    protected String codeWBS3 = listJdd.get(4).get("Code");
    protected String jourDateEcheanceWBS3 = listJdd.get(4).get("Echeance");

    protected String nouvelleTacheWBS4 = listJdd.get(5).get("Nouvelle tâche");
    protected String codeWBS4 = listJdd.get(5).get("Code");
    protected String jourDateEcheanceWBS4 = listJdd.get(5).get("Echeance");

    public PROTA02_AjouterTachesProjet() throws IOException {
    }

    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification de l'absence du JDD");
        PageListeDesProjets pageListeDesProjets = pageCalendrierPlanification.cliquerOngletListeDesProjets(wait, idCommune);
        pageListeDesProjets.nettoyageJDD(wait, nomProjet);
        pageCalendrierPlanification = pageListeDesProjets.cliquerOngletPlanificationDesProjets(wait, idCommune);
        LOGGER.info("Vérification terminé");

        LOGGER.info("Pre requis du test");
        pageCalendrierPlanification.cliquerCreerUnProjet(wait, idCommune);
        LOGGER.info("Création des dates");
        Calendar calendarDateDebut = Calendar.getInstance();
        Calendar calendarDateEcheance = Calendar.getInstance();
        calendarDateDebut.add(Calendar.DAY_OF_MONTH, Integer.parseInt(nombreJourDateDebut));
        calendarDateEcheance.add(Calendar.DAY_OF_MONTH, Integer.parseInt(nombreJourDateEcheance));
        String dateDebutProjet = sdf.format(calendarDateDebut.getTime());
        String dateEcheance = sdf.format(calendarDateEcheance.getTime());
        LOGGER.info("Generation date de debut: " + dateDebutProjet + " et date échéance " + dateEcheance);
        LOGGER.info("Remplissage du Formulaire du projet");
        pageCalendrierPlanification.remplirFormulaireCreationProjet(wait, idCommune,
                nomProjet, modeleProjet, checkboxCodeProjet, codeProjet, dateDebutProjet, dateEcheance, clientProjet, calendrierProjet);
        LOGGER.info("Formulaire rempli");
        PageDetailProjet pageDetailProjet = pageCalendrierPlanification.cliquerAccepterBouton(wait);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Formulaire enregistrer");

        LOGGER.info("Pas de test 2 -- Accéder à la liste des projets");
        pageListeDesProjets = pageDetailProjet.cliquerCalendrierProjet(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 3 -- Accéder à la liste des projets");
        assertion.verifyEquals("Liste des projets", pageListeDesProjets.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        pageDetailProjet = pageListeDesProjets.cliquerNomProjet(wait, nomProjet);
        LOGGER.info("Vérification des onglets");
        List<String> listOngletDetailProjet = pageDetailProjet.recuperationListeOngletDetailProjet(wait);
        assertion.verifyEquals("WBS (tâches)", listOngletDetailProjet.get(0),
                "L'onglet WBS (tâches) n'est pas celui attendu");
        assertion.verifyEquals("Données générales", listOngletDetailProjet.get(1),
                "L'onglet Détail du projet des projets n'est pas celui attendu");
        assertion.verifyEquals("Coût", listOngletDetailProjet.get(2),
                "L'onglet Chargement des ressources n'est pas celui attendu");
        assertion.verifyEquals("Avancement", listOngletDetailProjet.get(3),
                "L'onglet Avancement n'est pas celui attendu");
        assertion.verifyEquals("Libellés", listOngletDetailProjet.get(4),
                "L'onglet Libellés n'est pas celui attendu");
        assertion.verifyEquals("Exigence de critère", listOngletDetailProjet.get(5),
                "L'onglet Exigence de critère n'est pas celui attendu");
        assertion.verifyEquals("Matériels", listOngletDetailProjet.get(6),
                "L'onglet Matériels n'est pas celui attendu");
        assertion.verifyEquals("Formulaires qualité des tâches", listOngletDetailProjet.get(7),
                "L'onglet Formulaires qualité des tâches n'est pas celui attendu");
        assertion.verifyEquals("Autorisation", listOngletDetailProjet.get(8),
                "L'onglet Autorisation n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Vérification affichage de la page d'édition du projet");
        assertion.verifyEquals("DEBUT\nCalendrier Détail du projet " + nomProjet, pageListeDesProjets.recuperationFilAriance(wait),
                "Le fil d'ariane n'est pas celui attendu");

        LOGGER.info("Pas de test 5 -- Création d'une nouvelle tâche");
        LOGGER.info("Remplissage du formulaire");
        pageDetailProjet.remplirFormulaireWBS(wait, nouvelleTacheWBS, heuresWBS);
        LOGGER.info("Formulaire rempli");
        pageDetailProjet.cliquerBoutonAjouterWBS(wait);
        LOGGER.info("Formulaire enregistré");
        LOGGER.info("Vérification des boutons du tableau");
        assertion.verifyTrue(pageDetailProjet.boutonModifierWBS(wait, nouvelleTacheWBS).isDisplayed(),
                "Le bouton modifier n'est pas présent avec : " + nouvelleTacheWBS);
        assertion.verifyTrue(pageDetailProjet.boutonSupprimerWBS(wait, nouvelleTacheWBS).isDisplayed(),
                "Le bouton Supprimer n'est pas présent avec : " + nouvelleTacheWBS);
        assertion.verifyTrue(pageDetailProjet.boutonDeprogrammeWBS(wait, nouvelleTacheWBS).isDisplayed(),
                "Le bouton Deprogrammé n'est pas présent avec : " + nouvelleTacheWBS);
        assertion.verifyTrue(pageDetailProjet.boutonFiniWBS(wait, nouvelleTacheWBS).isDisplayed(),
                "Le bouton Fully scheduled n'est pas présent avec : " + nouvelleTacheWBS);
        LOGGER.info("Récupération des valeurs du tableau");
        Map<String, WebElement> mapListValeurTableauWBS = pageDetailProjet.recuperationValeurTableauWBS(wait).get(nouvelleTacheWBS);
        LOGGER.info("Vérication des valeurs du tableau");
        assertion.verifyEquals(nouvelleTacheWBS, outilsProjet.extraireValueWBS(mapListValeurTableauWBS.get("Nom")),
                "Le champ nom n'est pas celui attendu");
        assertion.verifyEquals("", outilsProjet.extraireValueWBS(mapListValeurTableauWBS.get("Code")),
                "Le champ code n'est pas vide");
        assertion.verifyEquals(heuresWBS, outilsProjet.extraireValueWBS(mapListValeurTableauWBS.get("Heures")),
                "Le champ nom n'est pas celui attendu");
        assertion.verifyEquals("0 €", outilsProjet.extraireValueWBS(mapListValeurTableauWBS.get("Budget")),
                "Le champ Budget n'est pas vide");
        assertion.verifyEquals("", outilsProjet.extraireValueWBS(mapListValeurTableauWBS.get("Doit débuter après")),
                "Le champ Doit débuter après n'est pas vide");
        assertion.verifyEquals("", outilsProjet.extraireValueWBS(mapListValeurTableauWBS.get("Echéance")),
                "Le champ Echéance n'est pas vide");

        LOGGER.info("Pas de test 6 -- Création de plusieurs nouvelles tâches");
        seleniumTools.clickOnElement(wait, pageDetailProjet.inputHeuresWBS(wait));
        for(int i = 0; i < 3; i++){
            LOGGER.info("Remplissage du formulaire");
            pageDetailProjet.remplirFormulaireWBS(wait, listJdd.get(i+3).get("Nouvelle tâche"), listJdd.get(i+3).get("Heures"));
            LOGGER.info("Formulaire rempli");
            pageDetailProjet.cliquerBoutonAjouterWBS(wait);
            LOGGER.info("Formulaire enregistré");

            // Firefox -- random click
            seleniumTools.clickOnElement(wait, driver.findElement(By.xpath("//strong")));
        }
        Thread.sleep(500);
        List<Map<String, WebElement>> ordreValeurTableau = pageDetailProjet.ordreValeurTableau(wait);
        assertion.verifyEquals(nouvelleTacheWBS, outilsProjet.extraireValueWBS(ordreValeurTableau.get(0).get("Nom")),
                "La premiere tache n'est pas " + nouvelleTacheWBS);
        assertion.verifyEquals(nouvelleTacheWBS2, outilsProjet.extraireValueWBS(ordreValeurTableau.get(1).get("Nom")),
                "La deuxième tache n'est pas " + nouvelleTacheWBS2);
        assertion.verifyEquals(nouvelleTacheWBS3, outilsProjet.extraireValueWBS(ordreValeurTableau.get(2).get("Nom")),
                "La troisième tache n'est pas " + nouvelleTacheWBS3);
        assertion.verifyEquals(nouvelleTacheWBS4, outilsProjet.extraireValueWBS(ordreValeurTableau.get(3).get("Nom")),
                "La quatrième tache n'est pas " + nouvelleTacheWBS4);

        LOGGER.info("Pas de test 7 -- Modifier l'ordre d'affichage des tâches (1/2)");
        seleniumTools.clickOnElement(wait, ordreValeurTableau.get(0).get("Nom"));
        pageDetailProjet.cliquerBoutonDescendreWBS(wait);
        Thread.sleep(500);
        ordreValeurTableau = pageDetailProjet.ordreValeurTableau(wait);
        assertion.verifyEquals(nouvelleTacheWBS2, outilsProjet.extraireValueWBS(ordreValeurTableau.get(0).get("Nom")),
                "La premiere tache n'est pas " + nouvelleTacheWBS2);
        assertion.verifyEquals(nouvelleTacheWBS, outilsProjet.extraireValueWBS(ordreValeurTableau.get(1).get("Nom")),
                "La deuxième tache n'est pas " + nouvelleTacheWBS);

        LOGGER.info("Pas de test 8 -- Modifier l'ordre d'affichage des tâches (2/2)");
        seleniumTools.clickOnElement(wait, ordreValeurTableau.get(2).get("Nom"));
        pageDetailProjet.cliquerBoutonRemonterWBS(wait);
        Thread.sleep(500);
        ordreValeurTableau = pageDetailProjet.ordreValeurTableau(wait);
        assertion.verifyEquals(nouvelleTacheWBS2, outilsProjet.extraireValueWBS(ordreValeurTableau.get(0).get("Nom")),
                "La premiere tache n'est pas " + nouvelleTacheWBS2);
        assertion.verifyEquals(nouvelleTacheWBS3, outilsProjet.extraireValueWBS(ordreValeurTableau.get(1).get("Nom")),
                "La deuxième tache n'est pas " + nouvelleTacheWBS3);
        assertion.verifyEquals(nouvelleTacheWBS, outilsProjet.extraireValueWBS(ordreValeurTableau.get(2).get("Nom")),
                "La troisième tache n'est pas " + nouvelleTacheWBS);

        LOGGER.info("Pas de test 9 -- Renseigner les informations des tâches + enregistrement");
        String dateDebutWBSMois = sdfWBS.format(calendarDateDebut.getTime());
        String dateDebutWBS = jourDateDebutWBS + dateDebutWBSMois.substring(2);
        String dateDebutWBS2 = jourDateDebutWBS2 + dateDebutWBSMois.substring(2);
        String dateEcheanceWBS3 = jourDateEcheanceWBS3 + dateDebutWBSMois.substring(2);
        String dateEcheanceWBS4 = jourDateEcheanceWBS4 + dateDebutWBSMois.substring(2);

        pageDetailProjet.competerFormulaireWBS(wait, nouvelleTacheWBS, codeWBS, dateDebutWBS, "");
        pageDetailProjet.competerFormulaireWBS(wait, nouvelleTacheWBS2, codeWBS2, dateDebutWBS2, "");
        pageDetailProjet.competerFormulaireWBS(wait, nouvelleTacheWBS3, codeWBS3, "", dateEcheanceWBS3);
        pageDetailProjet.competerFormulaireWBS(wait, nouvelleTacheWBS4, codeWBS4, "", dateEcheanceWBS4);

        pageDetailProjet.cliquerBoutonEnregisrerProjet(wait);
        assertion.verifyEquals("Projet enregistré", pageDetailProjet.textPopupEnregistrerProjet(wait).getText(),
                "Le message de la popup enregistrement n'est pas celui attendu");
        pageDetailProjet.cliquerBoutonOKPopupEnregistrerProjet(wait);

        LOGGER.info("Pas de test 10 -- Visualisation de la planification du projet");
        pageDetailProjet.cliquerOngletPlanificationDeProjet(wait, idCommune);

        Thread.sleep(5000);
        LOGGER.info("FIN DU TEST");

    }
}
