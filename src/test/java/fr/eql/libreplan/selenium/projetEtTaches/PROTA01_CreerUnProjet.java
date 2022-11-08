package fr.eql.libreplan.selenium.projetEtTaches;

import fr.eql.libreplan.pageObject.PageCalendrier.projet.PageDetailProjet;
import fr.eql.libreplan.pageObject.PageCalendrier.PageListeDesProjets;
import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PROTA01_CreerUnProjet extends AbstractTestSelenium {
    // Chargement JDD
    protected String className = getClass().getSimpleName();
    protected String classPackage = this.getClass().getPackage().getName();
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
    private static final Date date = new Date();
    private final String dateExecution = sdf.format(date.getTime());

    // JDD Projet
    protected String nomProjet = listJdd.get(1).get("Nom");
    protected String modeleProjet = listJdd.get(1).get("Modèle");
    protected String codeProjet = listJdd.get(1).get("Code");
    protected boolean checkboxCodeProjet = Boolean.parseBoolean(listJdd.get(1).get("checkboxCode"));
    protected String nombreJourDateDebut = listJdd.get(1).get("Date de début");
    protected String nombreJourDateEcheance = listJdd.get(1).get("Echeance");
    protected String clientProjet = listJdd.get(1).get("Client");
    protected String calendrierProjet = listJdd.get(1).get("Calendrier");



    public PROTA01_CreerUnProjet() throws IOException {
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

        LOGGER.info("Pas de test 2 -- Accéder au formulaire de création d'un projet");
        pageCalendrierPlanification.cliquerCreerUnProjet(wait, idCommune);
        LOGGER.info("Récupération des valeurs du tableau");
        Map<String, WebElement> mapValeurTableauCreation = pageCalendrierPlanification.recuperationTableau(wait, idCommune);
        LOGGER.info("Vérification des libellés du tableau");
        assertion.verifyTrue(mapValeurTableauCreation.containsKey("Nom"),
                "Le libellé Nom n'est pas retrouvé dans le tableau" );
        assertion.verifyTrue(mapValeurTableauCreation.containsKey("Modèle"),
                "Le libellé Modèle n'est pas retrouvé dans le tableau" );
        assertion.verifyTrue(mapValeurTableauCreation.containsKey("Code"),
                "Le libellé Code n'est pas retrouvé dans le tableau" );
        assertion.verifyTrue(mapValeurTableauCreation.containsKey("Générer le code"),
                "Le libellé Générer le code n'est pas retrouvé dans le tableau" );
        assertion.verifyTrue(mapValeurTableauCreation.containsKey("Date de début"),
                "Le libellé Date de début n'est pas retrouvé dans le tableau" );
        assertion.verifyTrue(mapValeurTableauCreation.containsKey("Echéance"),
                "Le libellé Echéance n'est pas retrouvé dans le tableau" );
        assertion.verifyTrue(mapValeurTableauCreation.containsKey("Client"),
                "Le libellé Client n'est pas retrouvé dans le tableau" );
        assertion.verifyTrue(mapValeurTableauCreation.containsKey("Calendrier"),
                "Le libellé Calendrier n'est pas retrouvé dans le tableau" );
        LOGGER.info("Vérification des input du tableau");
        assertion.verifyEquals("", mapValeurTableauCreation.get("Nom").getAttribute("value"),
                "L'input Nom n'est pas vide" );
        assertion.verifyEquals("", mapValeurTableauCreation.get("Modèle").getAttribute("value"),
                "L'input Modèle n'est pas vide" );
        assertion.verifyFalse(Objects.equals(mapValeurTableauCreation.get("Code").getAttribute("value"), ""),
                "Le libellé Code est vide dans le tableau" );
        assertion.verifyEquals("true", mapValeurTableauCreation.get("Générer le code").getAttribute("checked"),
                "Le libellé Générer le code n'est pas retrouvé dans le tableau" );
        assertion.verifyEquals(dateExecution, mapValeurTableauCreation.get("Date de début").getAttribute("value"),
                "L'input Date de début n'est pas celui attendu" );
        assertion.verifyEquals("",mapValeurTableauCreation.get("Echéance").getAttribute("value"),
                "L'input Echéance n'est pas vide dans le tableau" );
        assertion.verifyEquals("", mapValeurTableauCreation.get("Client").getAttribute("value"),
                "L'input Client n'est pas vide dans le tableau" );
        assertion.verifyEquals("Default", mapValeurTableauCreation.get("Calendrier").getAttribute("value"),
                "L'input Calendrier n'est pas celui attendu" );
        LOGGER.info("Vérification des bouons");
        assertion.verifyEquals("Accepter", pageCalendrierPlanification.boutonAccepter(wait).getText(),
                "Le bouton Accepter n'est pas celui attendu");
        assertion.verifyEquals("Annuler", pageCalendrierPlanification.boutonAnnuler(wait).getText(),
                "Le bouton Annuler n'est pas celui attendu");

        LOGGER.info("Pas de test 3 -- Créer un projet - Bouton [Accepter]");
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

        LOGGER.info("Pas de test 4 -- Vérifier les onglets - menu vertical");
        LOGGER.info("Recuperation de la liste des onglets");
        List<String> listOngletDetailProjet = pageDetailProjet.recuperationListeOngletDetailProjet(wait);
        List<String> listOngletProjet = pageDetailProjet.recuperationListeOngletProjet(wait, idCommune);
        LOGGER.info("Vérification des onglets");
        assertion.verifyEquals("Planification de projet", listOngletProjet.get(0),
                "L'onglet planification DE projet n'est pas celui attendu");
        assertion.verifyEquals("Détail du projet", listOngletProjet.get(1),
                "L'onglet Détail du projet des projets n'est pas celui attendu");
        assertion.verifyEquals("Chargement des ressources", listOngletProjet.get(2),
                "L'onglet Chargement des ressources n'est pas celui attendu");
        assertion.verifyEquals("Allocation avancée", listOngletProjet.get(3),
                "L'onglet Allocation avancée n'est pas celui attendu");
        assertion.verifyEquals("Tableau de bord", listOngletProjet.get(4),
                "L'onglet Tableau de bord n'est pas celui attendu");

        LOGGER.info("Pas de test 5 -- Vérifier les onglets - menu horizontal");
        LOGGER.info("Vérification des onglets");
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

        LOGGER.info("Pas de test 6 -- Bouton d'enregistrement et d'annulation de l'édition du projet ");
        assertion.verifyEquals("Enregistrer le projet", pageDetailProjet.boutonEnregistrerProjet(wait).getAttribute("title"),
                "Le bouton enregistrer le projet n'est pas celui attendu");
        assertion.verifyTrue(pageDetailProjet.imageBoutonEnregistrerProjet(wait).getAttribute("src").contains("ico_save.png"),
                "L'image du bouton Annuler n'est pas celui ");
        assertion.verifyEquals("Annuler l'édition", pageDetailProjet.boutonAnnulerEdition(wait).getAttribute("title"),
                "Le bouton Annuler l'édition n'est pas celui attendu");
        assertion.verifyTrue(pageDetailProjet.imageBoutonboutonAnnulerEdition(wait).getAttribute("src").contains("ico_back.png"),
                "L'image du bouton Annuler n'est pas celui attendu");

        LOGGER.info("Pas de test 7 -- Utilisation du bouton d'annulation de l'édition du projet (1/4)");
        pageDetailProjet.cliquerBoutonAnnulerEdition(wait);
        LOGGER.info("Vérification de la popup Annulation");
        assertion.verifyEquals("Les modifications non enregistrées seront perdues. Êtes-vous sûr ?",
                pageDetailProjet.textPopupAnnulationEdition(wait).getText(),
                "Le texte de la popup annulation de l'édition n'est pas celui attendu");
        assertion.verifyEquals("OK", pageDetailProjet.boutonOKPopupAnnulationEdition(wait).getText(),
                "Le bouton OK de la popup d'annulation de l'édition n'est pas celui attendu");
        assertion.verifyEquals("Annuler", pageDetailProjet.boutonAnnulerPopupAnnulationEdition(wait).getText(),
                "Le bouton Annuler de la popup d'annulation de l'édition n'est pas celui attendu");

        LOGGER.info("Pas de test 8 -- Utilisation du bouton d'annulation de l'édition du projet (2/4)");
        pageDetailProjet.cliquerBoutonAnnulerPopup(wait);
        LOGGER.info("Recuperation de la liste des onglets");
        listOngletProjet = pageDetailProjet.recuperationListeOngletProjet(wait, idCommune);
        listOngletDetailProjet = pageDetailProjet.recuperationListeOngletDetailProjet(wait);
        LOGGER.info("Vérification des onglet");
        assertion.verifyEquals("WBS (tâches)", listOngletDetailProjet.get(0),
                "L'onglet WBS (tâches) n'est pas celui attendu");
        assertion.verifyEquals("Détail du projet", listOngletProjet.get(1),
                "L'onglet Détail du projet des projets n'est pas celui attendu");

        LOGGER.info("Pas de test 9 -- Utilisation du bouton d'annulation de l'édition du projet (3/4)");
        pageDetailProjet.cliquerBoutonAnnulerEdition(wait);
        LOGGER.info("Vérification de la popup Annulation");
        assertion.verifyEquals("Les modifications non enregistrées seront perdues. Êtes-vous sûr ?",
                pageDetailProjet.textPopupAnnulationEdition(wait).getText(),
                "Le texte de la popup annulation de l'édition n'est pas celui attendu");
        assertion.verifyEquals("OK", pageDetailProjet.boutonOKPopupAnnulationEdition(wait).getText(),
                "Le bouton OK de la popup d'annulation de l'édition n'est pas celui attendu");
        assertion.verifyEquals("Annuler", pageDetailProjet.boutonAnnulerPopupAnnulationEdition(wait).getText(),
                "Le bouton Annuler de la popup d'annulation de l'édition n'est pas celui attendu");

        LOGGER.info("Pas de test 10 -- Utilisation du bouton d'annulation de l'édition du projet (4/4)");
        pageCalendrierPlanification = pageDetailProjet.cliquerBoutonOkPopup(wait);
        Thread.sleep(500);
        idCommune = outilsProjet.retournerIdCommune(wait);
        listOngletProjet = pageCalendrierPlanification.recuperationListeOngletProjet(wait, idCommune);
        LOGGER.info("Vérification des onglets");
        assertion.verifyEquals("Planification des projets", listOngletProjet.get(0),
                "L'onglet planification des projets n'est pas celui attendu");
        assertion.verifyTrue(pageCalendrierPlanification.displayOngletWBS(wait),
                "L'onglet WBS est toujours affiché");

        LOGGER.info("Pas de test 11 -- Vérifier la création du projet");
        pageListeDesProjets = pageCalendrierPlanification.cliquerCalendrierProjet(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        Map<String, WebElement> mapValeurTableauProjet = pageListeDesProjets.recuperationValeurTableau(wait).get(nomProjet);
        listOngletProjet = pageListeDesProjets.recuperationListeOngletProjet(wait, idCommune);
        assertion.verifyEquals("Liste des projets", pageListeDesProjets.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Planification des projets", listOngletProjet.get(0),
                "L'onglet planification DES projet n'est pas celui attendu");
        assertion.verifyEquals("Liste des projets", listOngletProjet.get(1),
                "L'onglet Liste des projets des projets n'est pas celui attendu");
        assertion.verifyEquals("Chargement des ressources", listOngletProjet.get(2),
                "L'onglet Chargement des ressources n'est pas celui attendu");
        assertion.verifyEquals("Calendrier des ressources en file", listOngletProjet.get(3),
                "L'onglet Calendrier des ressources en file n'est pas celui attendu");

        LOGGER.info("Pas de test 12 -- Vérifier les informations affichées pour le projet");
        LOGGER.info("Vérification des données du tableau");
        assertion.verifyEquals(nomProjet, mapValeurTableauProjet.get("Nom").getText(),
                "Le nom n'est pas celui attendu");
        assertion.verifyEquals(codeProjet, mapValeurTableauProjet.get("Code").getText(),
                "Le code n'est pas celui attendu");
        assertion.verifyEquals(dateDebutProjet, mapValeurTableauProjet.get("Date de début").getText(),
                "La date de début n'est pas celle attendu");
        assertion.verifyEquals(dateEcheance, mapValeurTableauProjet.get("Echéance").getText(),
                "La date d'échéance n'est pas celle attendu");
        assertion.verifyEquals("", mapValeurTableauProjet.get("Client").getText(),
                "Le client n'est pas celui attendu");
        assertion.verifyEquals("0 €", mapValeurTableauProjet.get("Budget total").getText(),
                "Le budjet total n'est pas celui attendu");
        assertion.verifyEquals("0", mapValeurTableauProjet.get("Heures").getText(),
                "Le nombre d'heure n'est pas celui attendu");
        assertion.verifyEquals("PRE-VENTES", mapValeurTableauProjet.get("Etat").getText(),
                "L'état n'est pas celui attendu");

        LOGGER.info("Vérification des boutons associés au projet");
        assertion.verifyTrue(pageListeDesProjets.boutonModifier(wait, nomProjet).isDisplayed(),
                "Le bouton modifier n'est pas présent pour : " + nomProjet);
        assertion.verifyTrue(pageListeDesProjets.boutonSupprimer(wait, nomProjet).isDisplayed(),
                "Le bouton supprimer n'est pas présent pour : " + nomProjet);
        assertion.verifyTrue(pageListeDesProjets.boutonpPrevision(wait, nomProjet).isDisplayed(),
                "Le bouton prévision n'est pas présent pour : " + nomProjet);
        assertion.verifyTrue(pageListeDesProjets.boutonModele(wait, nomProjet).isDisplayed(),
                "Le bouton créer un modèle n'est pas présent pour : " + nomProjet);

        LOGGER.info("FIN DU TEST");
    }
}
