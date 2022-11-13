package fr.eql.libreplan.selenium.gestionDesCalendriers;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrierCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class CAL02_AjouterUneException extends AbstractTestSelenium {
    // QuerySQL
    protected String queryNettoyage = "select name from base_calendar where name='??';";



    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    // Connexion
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDD Calendrier
    protected String nomCalendrier = listJdd.get(1).get("nom");
    protected Boolean codeCalendrier = Boolean.valueOf(listJdd.get(1).get("code"));

    // JDD Exception
    protected String typeException = listJdd.get(1).get("typeException");
    protected String effortNormal = listJdd.get(1).get("effortNormal");
    protected String effortHeuresSupplementaire = listJdd.get(1).get("effortHeuresSupplementaire");

    // Generation date
    protected String dateDuJour = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


    public CAL02_AjouterUneException() throws IOException {
    }

    @Test
    public void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        LOGGER.info("Pas de test 2 -- Accéder à la page d'administration des calendriers");
        String idCommune = outilsProjet.retournerIdCommune(wait);

        PageRessourcesCalendrier pageRessourcesCalendrier = pageCalendrierPlanification.cliquerRessourcesCalendrier(wait, idCommune);
        methodesProjet.VerificationPageAdministrationCalendriers(wait);
        LOGGER.info("Pas de test 3 -- Créer une exception - Accès au formulaire de modification");
        idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("PRE REQUIS ...");

        LOGGER.info("Nettoyage si nécessaire");
        queryNettoyage = outilsManipulationDonnee.formatageQuery(queryNettoyage, nomCalendrier);
        LOGGER.info("Query setup " + queryNettoyage);
        outilsProjet.verificationNettoyageTableau(wait, connection, queryNettoyage);
        LOGGER.info("Reprise du cas de test");

        LOGGER.info("Etape supplémentaire -- Creation d'un calendrier");
        methodesProjet.creationCalendrier(wait, idCommune, nomCalendrier, codeCalendrier);
        LOGGER.info("PRE REQUIS DONE");

        LOGGER.info("Reprise du cas de test -- Modification calendrier");
        PageRessourcesCalendrierCreer pageRessourcesCalendrierCreer = pageRessourcesCalendrier.cliquerBoutonModifierCalendrier(wait, nomCalendrier);
        LOGGER.info("Acces à la page de modification");
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page de modification du calendrier et du formulaire");
        assertion.verifyEquals("Modifier Calendrier: " + nomCalendrier, pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Données de calendrier", pageRessourcesCalendrierCreer.titreFormulaire(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        LOGGER.info("Vérification du texte des 3 boutons");
        assertion.verifyEquals("Enregistrer", pageRessourcesCalendrierCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer et continuer", pageRessourcesCalendrierCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Annuler", pageRessourcesCalendrierCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Créer une exception - absence de sélection du type d'exception");
        LOGGER.info("Clique directement sur créer exception");
        pageRessourcesCalendrierCreer.cliquerBoutonCreerException(wait, idCommune);
        assertion.verifyEquals("Merci de choisir un type d'exception", pageRessourcesCalendrierCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message d'alerte des données obligatoires n'est pas celui attendu");

        LOGGER.info("Pas de test 5 -- Créer une exception - sélection du type d'exception");
        LOGGER.info("Ajout du type Exception puis création");
        pageRessourcesCalendrierCreer.choisirTypeException(wait, idCommune, typeException);
        pageRessourcesCalendrierCreer.cliquerBoutonCreerException(wait, idCommune);
        assertion.verifyEquals("Merci de choisir une date de fin pour l'exception", pageRessourcesCalendrierCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message d'alerte des données obligatoires n'est pas celui attendu");

        LOGGER.info("Pas de test 6 -- Créer une exception - Créer une exception - sélection de la date de fin de l'exception");
        LOGGER.info("Ajout de la date de fin de l'exception puis création");
        String dateDeDebut = pageRessourcesCalendrierCreer.inputDateDeDebut(wait, idCommune).getAttribute("value");
        pageRessourcesCalendrierCreer.remplirDateDeFin(wait, idCommune, dateDeDebut);
        pageRessourcesCalendrierCreer.cliquerBoutonCreerException(wait, idCommune);
        LOGGER.info("Recuperation de l'exception créé");
        // RECUPERATION 1 VALEUR : DATE(YYYY-MM-DD) - TYPE D'EXCEPTION
        Map<String, String> mapValeurException = pageRessourcesCalendrierCreer.recuperationValeurException(wait, idCommune).get(dateDuJour + " - " + typeException);
        LOGGER.info("Verification de l'exception créé");
        assertion.verifyEquals(dateDuJour, mapValeurException.get("Jour"),
                "La date de creation de l'exception n'est pas celle attendu");
        assertion.verifyEquals(typeException, mapValeurException.get("Type d'exception"),
                "Le type de l'exception n'est pas celui attendu");
        assertion.verifyEquals("0:0", mapValeurException.get("Effort normal"),
                "L'effort normal de l'exception n'est pas celui attendu");
        assertion.verifyEquals("0:0", mapValeurException.get("Effort supplémentaire"),
                "L'effort supplémentaire de l'exception n'est pas celui attendu");
        assertion.verifyEquals("", mapValeurException.get("Code"),
                "Le code d'exception n'est pas vide");
        assertion.verifyEquals("Direct", mapValeurException.get("Origine"),
                "L'origine de l'exception n'est pas vide");
        LOGGER.info("Recupération du tableau propriétés des jours");
        Map<String, String> mapValeurProprieteJour = pageRessourcesCalendrierCreer.recuperationProprieteJours(wait);
        LOGGER.info("Verification du tableau propriétés des jours");
        assertion.verifyEquals(dateDeDebut, mapValeurProprieteJour.get("Jour"),
                "Le jour du tableau des propriétés des jours n'est pas celui attendu");
        assertion.verifyEquals("Exception: " + typeException, mapValeurProprieteJour.get("Type"),
                "Le Type du tableau des propriétés des jours n'est pas celui attendu");
        assertion.verifyEquals("0:0", mapValeurProprieteJour.get("Temps travaillé"),
                "Le temps travaillé du tableau des propriétés des jours n'est pas celui attendu");

        LOGGER.info("Pas de test 7 -- Créer une exception - mise à jour de l'exception");
        pageRessourcesCalendrierCreer.remplirEffort(wait, idCommune, effortNormal, effortHeuresSupplementaire);
        LOGGER.info("MAJ de l'exception");
        pageRessourcesCalendrierCreer.cliquerBoutonMAJException(wait, idCommune);
        LOGGER.info("Recuperation de l'exception MAJ");
        Thread.sleep(500);
        mapValeurException = pageRessourcesCalendrierCreer.recuperationValeurException(wait, idCommune).get(dateDuJour + " - " + typeException);
        mapValeurProprieteJour = pageRessourcesCalendrierCreer.recuperationProprieteJours(wait);
        LOGGER.info("Verification de l'exception MAJ");
        assertion.verifyEquals(effortNormal +":0", mapValeurException.get("Effort normal"),
                "L'effort normal de l'exception n'est pas celui attendu");
        assertion.verifyEquals(effortHeuresSupplementaire+":0", mapValeurException.get("Effort supplémentaire"),
                "L'effort supplémentaire de l'exception n'est pas celui attendu");
        assertion.verifyEquals(effortNormal +":0", mapValeurProprieteJour.get("Temps travaillé"),
                "Le temps travaillé du tableau des propriétés des jours n'est pas celui attendu");

        LOGGER.info("Pas de test 8 -- Enregistrer le projet");
        pageRessourcesCalendrier = pageRessourcesCalendrierCreer.cliquerBoutonEnregistrer(wait, idCommune);
        LOGGER.info("Vérification du titre de la page et du message enregistrement");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Calendrier de base \"" + nomCalendrier + "\" enregistré", pageRessourcesCalendrier.messageCreation(wait, nomCalendrier).getText(),
                "Le message d'enregistrement n'est pas celui attendu");

        LOGGER.info("Pas de test 9 -- Créer une exception - Accès au formulaire de modification");
        pageRessourcesCalendrierCreer = pageRessourcesCalendrier.cliquerBoutonModifierCalendrier(wait, nomCalendrier);
        LOGGER.info("Acces à la page de modification");
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page de modification du calendrier et du formulaire");
        assertion.verifyEquals("Modifier Calendrier: " + nomCalendrier, pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Données de calendrier", pageRessourcesCalendrierCreer.titreFormulaire(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        LOGGER.info("Vérification du texte des 3 boutons");
        assertion.verifyEquals("Enregistrer", pageRessourcesCalendrierCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer et continuer", pageRessourcesCalendrierCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Annuler", pageRessourcesCalendrierCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");

        LOGGER.info("Pas de test 10 -- Vérifier l'alimentation de la colonne Code");
        String codeException = pageRessourcesCalendrierCreer.inputCode(wait, idCommune).getAttribute("value") + "-0002";
        mapValeurException = pageRessourcesCalendrierCreer.recuperationValeurException(wait, idCommune).get(dateDuJour + " - " + typeException);
        assertion.verifyEquals(codeException, mapValeurException.get("Code"),
                "Le code d'exception n'est pas celui attendu");

        LOGGER.info("Pas de test 11 -- Retour page de gestion des calendriers");
        pageRessourcesCalendrierCreer.cliquerBoutonAnnuler(wait, idCommune);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 12 -- Créer un calendrier dérivé");
        pageRessourcesCalendrier.cliquerBoutonCreerUneDerive(wait, nomCalendrier);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer Calendrier", pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("", pageRessourcesCalendrierCreer.inputNom(wait, idCommune).getAttribute("value"),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Dérivé du calendrier " + nomCalendrier, pageRessourcesCalendrierCreer.inputType(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération de l'exception");
        mapValeurException = pageRessourcesCalendrierCreer.recuperationValeurException(wait, idCommune).get(dateDuJour + " - " + typeException);
        LOGGER.info("Verification de l'exception");
        assertion.verifyEquals(dateDuJour, mapValeurException.get("Jour"),
                "La date de creation de l'exception n'est pas celle attendu");
        assertion.verifyEquals(typeException, mapValeurException.get("Type d'exception"),
                "Le type de l'exception n'est pas celui attendu");
        assertion.verifyEquals(effortNormal+":0", mapValeurException.get("Effort normal"),
                "L'effort normal de l'exception n'est pas celui attendu");
        assertion.verifyEquals(effortHeuresSupplementaire+":0", mapValeurException.get("Effort supplémentaire"),
                "L'effort supplémentaire de l'exception n'est pas celui attendu");
        assertion.verifyEquals(codeException, mapValeurException.get("Code"),
                "Le code d'exception n'est pas vide");
        assertion.verifyEquals("Hérité", mapValeurException.get("Origine"),
                "L'origine de l'exception n'est pas vide");

        LOGGER.info("Pas de test 13 -- Retour page de gestion des calendriers");
        pageRessourcesCalendrier = pageRessourcesCalendrierCreer.cliquerBoutonAnnuler(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 14 -- Créer un calendrier par copie");
        pageRessourcesCalendrier.cliquerBoutonCopierCalendrier(wait, nomCalendrier);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer Calendrier: "+nomCalendrier, pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals(nomCalendrier, pageRessourcesCalendrierCreer.inputNom(wait, idCommune).getAttribute("value"),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Calendrier source", pageRessourcesCalendrierCreer.inputType(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération de l'exception");
        mapValeurException = pageRessourcesCalendrierCreer.recuperationValeurException(wait, idCommune).get(dateDuJour + " - " + typeException);
        LOGGER.info("Verification de l'exception");
        assertion.verifyEquals(dateDuJour, mapValeurException.get("Jour"),
                "La date de creation de l'exception n'est pas celle attendu");
        assertion.verifyEquals(typeException, mapValeurException.get("Type d'exception"),
                "Le type de l'exception n'est pas celui attendu");
        assertion.verifyEquals(effortNormal+":0", mapValeurException.get("Effort normal"),
                "L'effort normal de l'exception n'est pas celui attendu");
        assertion.verifyEquals(effortHeuresSupplementaire+":0", mapValeurException.get("Effort supplémentaire"),
                "L'effort supplémentaire de l'exception n'est pas celui attendu");
        assertion.verifyEquals(codeException, mapValeurException.get("Code"),
                "Le code d'exception n'est pas vide");
        assertion.verifyEquals("Direct", mapValeurException.get("Origine"),
                "L'origine de l'exception n'est pas vide");

        LOGGER.info("FIN DU TEST");
    }
}
