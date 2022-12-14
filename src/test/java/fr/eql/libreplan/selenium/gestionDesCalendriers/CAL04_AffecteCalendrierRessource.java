package fr.eql.libreplan.selenium.gestionDesCalendriers;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipantsCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CAL04_AffecteCalendrierRessource extends AbstractTestSelenium {
    // QuerySQL
    protected String queryNettoyage = "select name from base_calendar where name='??';";
    protected String queryNettoyageParticipant = "select surname from worker where surname='??';";



    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    // Connexion
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDDD Participant
    protected String nomParticipant = listJdd.get(1).get("nomParticipant");
    protected String prenomParticipant = listJdd.get(1).get("prenomParticipant");
    protected String idParticipant = listJdd.get(1).get("idParticipant");

    // JDDD Calendrier
    protected String nomCalendrier = listJdd.get(2).get("nomCalendrier");
    protected boolean codeCalendrier = Boolean.parseBoolean(listJdd.get(2).get("codeCalendrier"));

    public CAL04_AffecteCalendrierRessource() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        // Pré-requis du test
        LOGGER.info("Nettoyage si necessaire");
        PageRessourcesParticipants pageRessourcesParticipants = pageCalendrierPlanification.cliquerRessourcesParticipants(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Nettoyage si nécessaire");
        queryNettoyageParticipant = outilsManipulationDonnee.formatageQuery(queryNettoyageParticipant, nomParticipant);
        LOGGER.info("Query setup " + queryNettoyageParticipant);
        outilsProjet.verificationNettoyageTableauParticipant(wait, connection, queryNettoyageParticipant);
        LOGGER.info("Reprise du cas de test");


        PageRessourcesCalendrier pageRessourcesCalendrier = pageRessourcesParticipants.cliquerRessourcesCalendrier(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Nettoyage Calendrier");
        assertion.verifyEquals("Liste de calendriers",pageRessourcesCalendrier.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Nettoyage si nécessaire");
        queryNettoyage = outilsManipulationDonnee.formatageQuery(queryNettoyage, nomCalendrier);
        LOGGER.info("Query setup " + queryNettoyage);
        outilsProjet.verificationNettoyageTableau(wait, connection, queryNettoyage);
        LOGGER.info("Reprise du cas de test");

        LOGGER.info("Création du calendrier");
        methodesProjet.creationCalendrier(wait, idCommune, nomCalendrier, codeCalendrier);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        Map<String, Map<String, WebElement>> mapValeurTableauCalendrier = pageRessourcesCalendrier.recuperationValeurTableauCalendrier(wait);
        assertion.verifyTrue(mapValeurTableauCalendrier.containsKey(nomCalendrier),
                nomCalendrier + " n'est pas retrouvé dans le tableau des calendriers");

        LOGGER.info("Pas de test 2 -- Accéder à la page de gestion des participants ");
        pageRessourcesParticipants = pageRessourcesCalendrier.cliquerRessourcesParticipants(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste des participants", pageRessourcesParticipants.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des titres du tableau");
        List<String> listLibelleTableau = pageRessourcesParticipants.recuperationLibelleTableau(wait, idCommune);
        LOGGER.info("Vérification des libellés du tableau");
        assertion.verifyEquals("Surnom", listLibelleTableau.get(0),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Prénom", listLibelleTableau.get(1),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("ID", listLibelleTableau.get(2),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Code", listLibelleTableau.get(3),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("En file", listLibelleTableau.get(4),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Opérations", listLibelleTableau.get(5),
                "Le libelle n'est pas celui attendu");
        LOGGER.info("Vérification des bouton filtre et libellés");
        assertion.verifyEquals("Filtré par", pageRessourcesParticipants.libelleFiltrerPar(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Détails personnels", pageRessourcesParticipants.libelleDetailPersonnels(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Plus d'options", pageRessourcesParticipants.boutonPlusOptions(wait, idCommune).getText(),
                "Le bouton plus d'option n'est pas celui attendu");
        assertion.verifyEquals("Filtre", pageRessourcesParticipants.boutonFiltre(wait, idCommune).getText(),
                "Le bouton Filtre n'est pas celui attendu");
        assertion.verifyEquals("Créer", pageRessourcesParticipants.boutonCreer(wait, idCommune).getText(),
                "Le bouton créer n'est pas celui attendu");


        LOGGER.info("Pas de test 3 -- Accéder au formulaire de modification d'un participant");
        LOGGER.info("Creation d'un participant");
        PageRessourcesParticipantsCreer pageRessourcesParticipantsCreer = pageRessourcesParticipants.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer un participant", pageRessourcesParticipantsCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        pageRessourcesParticipantsCreer.remplirFormulaireMinimun(wait, idCommune, nomParticipant, prenomParticipant, idParticipant);
        LOGGER.info("Enregistrement du participant");
        pageRessourcesParticipants = pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait,idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste des participants", pageRessourcesParticipants.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du tableau");
        Map<String, Map<String, String>> mapValeurTableauParticipant = pageRessourcesParticipants.recuperationValeurTableauParticipant(wait, idCommune);
        LOGGER.info("Vérification des valeurs du tableau");
        assertion.verifyTrue(mapValeurTableauParticipant.containsKey(nomParticipant),
                "Le tableau ne possède pas le participant " + nomParticipant);
        LOGGER.info("Récupération des valeurs du participant");
        Map<String, String> mapParticipant = mapValeurTableauParticipant.get(nomParticipant);
        LOGGER.info("Vérification des valeurs du participant");
        assertion.verifyEquals(nomParticipant, mapParticipant.get("Surnom"),
                "Le surnom/nom du participant n'est pas celui attendu");
        assertion.verifyEquals(prenomParticipant, mapParticipant.get("Prénom"),
                "Le prénom du participant n'est pas celui attendu");
        assertion.verifyEquals(idParticipant, mapParticipant.get("ID"),
                "L'ID du participant n'est pas celui attendu");
        LOGGER.info("Modification du participant");
        pageRessourcesParticipantsCreer = pageRessourcesParticipants.cliquerModifierParticipant(wait, nomParticipant);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Modifier le participant: " + prenomParticipant + " " + nomParticipant,
                pageRessourcesParticipantsCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Accéder à l'onglet Calendrier");
        pageRessourcesParticipantsCreer.cliquerOngletCalendrier(wait, idCommune);
        LOGGER.info("Vérification champ Type");
        assertion.verifyEquals("Dérivé du calendrier Default", pageRessourcesParticipantsCreer.inputTypeCalendrier(wait).getText(),
                "Le champ type n'est pas celui attendu");
        LOGGER.info("Vérification Gauche Onglet");
        assertion.verifyTrue(pageRessourcesParticipantsCreer.objetCalendrier(wait).isDisplayed(),
                "Le calendrier n'est pas présent");
        assertion.verifyEquals("Propriétés des jours", pageRessourcesParticipantsCreer.tableauProprieteDesJours(wait, idCommune).getText(),
                "Le tableau propriété des jours n'est pas celui attendu");
        assertion.verifyEquals("Supprimer le calendrier", pageRessourcesParticipantsCreer.boutonSupprimerCalendrier(wait, idCommune).getText(),
                "Le bouton Supprimer le calendrier n'est pas celui attendu");

        LOGGER.info("Vérification sous onglet");
        List<String> listSousOnglet = pageRessourcesParticipantsCreer.recuperationListSousOnglet(wait);
        assertion.verifyEquals("Exceptions", listSousOnglet.get(0),
                "Le sous onglet Exceptions n'est pas celui attendu");
        assertion.verifyEquals("Semaine de travail", listSousOnglet.get(1),
                "Le sous onglet Semaine de travail n'est pas celui attendu");
        assertion.verifyEquals("Périodes d'activation", listSousOnglet.get(2),
                "Le sous onglet Périodes d'activation n'est pas celui attendu");

        LOGGER.info("Vérification Bouton");
        assertion.verifyEquals("Enregistrer", pageRessourcesParticipantsCreer.boutonEnregistrer(wait, idCommune).getText(),
                " n'est pas celui attendu");
        assertion.verifyEquals("Sauver et continuer", pageRessourcesParticipantsCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                " n'est pas celui attendu");
        assertion.verifyEquals("Annuler", pageRessourcesParticipantsCreer.boutonAnnuler(wait, idCommune).getText(),
                " n'est pas celui attendu");

        LOGGER.info("Pas de test 5 -- Supprimer le calendrier affecté par défaut (1/2)");
        pageRessourcesParticipantsCreer.cliquerSupprimerCalendrier(wait, idCommune);
        LOGGER.info("Vérification du champ calendrier Parent");
        assertion.verifyEquals("Choisir un calendrier parent",
                pageRessourcesParticipantsCreer.libelleChoisirCalendrierParent(wait, idCommune).getText(),
                "Le champ calendrier Parent ne possède pas le bon libellé");
        assertion.verifyEquals("Default", pageRessourcesParticipantsCreer.inputCalendrierParent(wait, idCommune).getAttribute("value"),
                "Le champ calendrier Parent ne possède pas la bonne valeur par défaut");
        LOGGER.info("Récuperation de la liste des calendrier parent");
        List<String> listCalendrierParent = pageRessourcesParticipantsCreer.recupererListCalendrierParent(wait, idCommune);
        for (String str : listCalendrierParent) {
            LOGGER.info(str);
        }
        for (Map.Entry<String, Map<String, WebElement>> mapCalendrier : mapValeurTableauCalendrier.entrySet()){
            LOGGER.info("Vérification de " + mapCalendrier.getKey() + " dans la liste des calendriers parents");
            assertion.verifyTrue(listCalendrierParent.contains(mapCalendrier.getKey()),
                    "La liste des calendriers parents ne possède pas le calendrier " + mapCalendrier.getKey());
        }

        LOGGER.info("Pas de test 6 -- Supprimer le calendrier affecté par défaut (2/2)");
        pageRessourcesParticipantsCreer.renseignerCalendrierParent(wait, idCommune,
                pageRessourcesParticipantsCreer.inputCalendrierParent(wait,idCommune), nomCalendrier );
        pageRessourcesParticipantsCreer.cliquerEnregistrerEtContinuer(wait, idCommune);
        assertion.verifyEquals("Participant enregistré", pageRessourcesParticipantsCreer.messageCreation(wait));
        assertion.verifyEquals(prenomParticipant, pageRessourcesParticipantsCreer.inputPrenom(wait, idCommune).getAttribute("value"),
                "Le prenom du participant n'est pas celui attendu. On est peut-être sur l'onglet calendrier ?" );

        LOGGER.info("Pas de test 7 -- Vérifier l'affectation du calendrier");
        pageRessourcesParticipantsCreer.cliquerOngletCalendrier(wait, idCommune);
        assertion.verifyEquals("Dérivé du calendrier " + nomCalendrier, pageRessourcesParticipantsCreer.inputTypeCalendrier(wait).getText());

        LOGGER.info("Fin du test");

    }
}


