package fr.eql.libreplan.selenium.gestionDesRessources;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.PageLogin;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipantsCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GRE01_CreerParticipant_NouvelUtilisateur extends AbstractTestSelenium {
    // Chargement JDD
    protected String className = getClass().getSimpleName();
    protected String classPackage = this.getClass().getPackage().getName();
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDDD Participant
    protected String codeParticipant = listJdd.get(1).get("Code");
    protected boolean checkboxCodeParticiapnt = Boolean.parseBoolean(listJdd.get(1).get("Générer le code"));
    protected String nomParticipant = listJdd.get(1).get("Nom");
    protected String prenomParticipant = listJdd.get(1).get("Prénom");
    protected String idParticipant = listJdd.get(1).get("ID");
    protected String typeParticipant = listJdd.get(1).get("Type");
    protected String utilisateurLie = listJdd.get(1).get("Utilisateur lié");
    protected String nomUtilisateur = listJdd.get(1).get("Nom d'utilisateur");
    protected String mdpUtilisateur = listJdd.get(1).get("Mot de passe");
    protected String emailUtilisateur = listJdd.get(1).get("Email");

    public GRE01_CreerParticipant_NouvelUtilisateur() throws IOException {
    }

    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la page de gestion des participants");
        PageRessourcesParticipants pageRessourcesParticipants = pageCalendrierPlanification.cliquerRessourcesParticipants(wait, idCommune);
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

        LOGGER.info("PRE REQUIS DU TEST");
        LOGGER.info("Vérification que le JDD n'est pas présent");
        pageRessourcesParticipants.verificationNettoyageTableauAvecUtilisateur(wait, idCommune, nomParticipant);
        pageRessourcesParticipants.ajoutParticipant(wait, idCommune);

        LOGGER.info("Pas de test 3 -- Créer un participant - Accès au formulaire de création");
        PageRessourcesParticipantsCreer pageRessourcesParticipantsCreer = pageRessourcesParticipants.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du titre du formulaire");
        assertion.verifyEquals("Créer un participant", pageRessourcesParticipantsCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Données de base", pageRessourcesParticipantsCreer.titreDuFormulaire(wait, idCommune),
                "Le titre du formulaire n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Créer un participant - Conformité de l'onglet Données personnelles");
        LOGGER.info("Vérification des libellés");
        assertion.verifyEquals("Code", pageRessourcesParticipantsCreer.libelleCode(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Générer le code", pageRessourcesParticipantsCreer.libelleCheckboxCode(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Prénom", pageRessourcesParticipantsCreer.libellePrenom(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Nom", pageRessourcesParticipantsCreer.libelleNom(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("ID", pageRessourcesParticipantsCreer.libelleId(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Type", pageRessourcesParticipantsCreer.libelleType(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        LOGGER.info("Vérification des input");
        assertion.verifyEquals("true", pageRessourcesParticipantsCreer.inputCode(wait, idCommune).getAttribute("disabled"),
                "le champ code est modifable");
        assertion.verifyTrue(!Objects.equals(pageRessourcesParticipantsCreer.inputCode(wait, idCommune).getAttribute("value"), ""),
                "le champ code est vide");
        assertion.verifyTrue(Objects.equals(pageRessourcesParticipantsCreer.inputPrenom(wait, idCommune).getAttribute("value"), ""),
                "le champ prenom n'est pas vide");
        assertion.verifyTrue(Objects.equals(pageRessourcesParticipantsCreer.inputNom(wait, idCommune).getAttribute("value"), ""),
                "le champ nom n'est pas vide");
        assertion.verifyTrue(Objects.equals(pageRessourcesParticipantsCreer.inputId(wait, idCommune).getAttribute("value"), ""),
                "le champ ID n'est pas vide");
        assertion.verifyEquals("Ressource normale", pageRessourcesParticipantsCreer.libelleSelectType(wait, idCommune).getText(),
                "La valeur par défaut du champ type n'est pas celle attendu");
        LOGGER.info("Récupération des champ utilisateur lié");
        Map<String, WebElement> mapUtilisateurLie = pageRessourcesParticipantsCreer.mapUtilisateurLie(wait, idCommune);
        LOGGER.info("Vérification des champ utilisateur lié");
        assertion.verifyTrue(mapUtilisateurLie.containsKey("Non lié"),
                "Le libellé Non lié n'est pas retrouvé");
        assertion.verifyTrue(mapUtilisateurLie.containsKey("Utilisateur existant"),
                "Le libellé Utilisateur existant n'est pas retrouvé");
        assertion.verifyTrue(mapUtilisateurLie.containsKey("Créer un nouvel utilisateur"),
                "Le libellé Créer un nouvel utilisateur n'est pas retrouvé");
        for(Map.Entry <String, WebElement> mapentry : mapUtilisateurLie.entrySet()){
            assertion.verifyEquals("radio", mapentry.getValue().getAttribute("type"));
        }
        LOGGER.info("Vérification des boutons");
        assertion.verifyEquals("Annuler", pageRessourcesParticipantsCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le libellé du bouton n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer", pageRessourcesParticipantsCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le libellé du bouton n'est pas celui attendu");
        assertion.verifyEquals("Sauver et continuer", pageRessourcesParticipantsCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                "Le libellé du bouton n'est pas celui attendu");


        LOGGER.info("Pas de test 5 -- Créer un participant - Bouton [Enregistrer]");
        String codeParticipantRecup = pageRessourcesParticipantsCreer.inputCode(wait, idCommune).getAttribute("value");
        pageRessourcesParticipantsCreer.remplirFormulaire(wait,idCommune,
                checkboxCodeParticiapnt, nomParticipant, prenomParticipant, idParticipant, typeParticipant);
        pageRessourcesParticipantsCreer.remplirUtilisateurLie(wait, idCommune, utilisateurLie);
        pageRessourcesParticipantsCreer.remplirNouvelUtilisateur(wait, idCommune,
                nomUtilisateur, mdpUtilisateur, mdpUtilisateur, emailUtilisateur);
        pageRessourcesParticipants = pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Verification du titre de la page et du message de création");
        assertion.verifyEquals("Liste des participants", pageRessourcesParticipants.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Participant enregistré", pageRessourcesParticipants.messageCreation(wait),
                "Le message de création n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du tableau");
        Map<String, String> mapValeurTableauParticipant = pageRessourcesParticipants.recuperationValeurTableauParticipant(wait, idCommune).get(nomParticipant);
        LOGGER.info("Vérification du participant créé");
        assertion.verifyEquals(nomParticipant, mapValeurTableauParticipant.get("Surnom"),
                "La valeur du participé créé n'est pas celle attendu");
        assertion.verifyEquals(prenomParticipant, mapValeurTableauParticipant.get("Prénom"),
                "La valeur du participé créé n'est pas celle attendu");
        assertion.verifyEquals(idParticipant, mapValeurTableauParticipant.get("ID"),
                "La valeur du participé créé n'est pas celle attendu");
        assertion.verifyEquals(codeParticipantRecup, mapValeurTableauParticipant.get("Code"),
                "La valeur du participé créé n'est pas celle attendu");

        LOGGER.info("Pas de test 6 -- Utilisation du filtre Détails personnels");

        pageRessourcesParticipants.appliquerFiltre(wait, idCommune, prenomParticipant);
        Thread.sleep(500);
        LOGGER.info("Récupération des valeurs du tableau filtré");
        mapValeurTableauParticipant = pageRessourcesParticipants.recuperationValeurTableauParticipant(wait, idCommune).get(nomParticipant);
        LOGGER.info("Vérification de la recherche");
        assertion.verifyEquals(prenomParticipant, mapValeurTableauParticipant.get("Prénom"),
                "La valeur n'est pas présent dans le tableau");
        LOGGER.info("Reset du filtre");
        pageRessourcesParticipants.appliquerFiltre(wait, idCommune, "");

        LOGGER.info("Pas de test 7 -- Filtre Plus d'options - conformité des options");
        seleniumTools.clickOnElement(wait, pageRessourcesParticipants.boutonPlusOptions(wait, idCommune));
        assertion.verifyEquals("", pageRessourcesParticipants.inputPeriodeDepuis(wait, idCommune).getAttribute("value"),
                "Le champ date de la période active depuis n'est pas vide");
        assertion.verifyEquals("", pageRessourcesParticipants.inputPeriodeA(wait, idCommune).getAttribute("value"),
                "Le champ date de la période active à n'est pas vide");
        LOGGER.info("Récupération de la liste des type de l'option supplémentaire");
        List<String> listValueSelectOptionSupplementaire = pageRessourcesParticipants.listSelectTypeOptionSupplementaire(wait, idCommune);
        assertion.verifyEquals("Tous", listValueSelectOptionSupplementaire.get(0),
                "La liste du type de l'option supplémentaire n'est pas celle attendu");
        assertion.verifyEquals("Ressource en file", listValueSelectOptionSupplementaire.get(1),
                "La liste du type de l'option supplémentaire n'est pas celle attendu");
        assertion.verifyEquals("Ressource normale", listValueSelectOptionSupplementaire.get(2),
                "La liste du type de l'option supplémentaire n'est pas celle attendu");
        assertion.verifyEquals("Tous", pageRessourcesParticipants.libelleSelectTypeOptionSupplementaire(wait, idCommune).getText(),
                "Le texte par défaut du type d'option supplémentaire n'est pas celui attendu");


        LOGGER.info("Pas de test 8 -- Navigation pages de participants (1/4)");
        LOGGER.info("Vérification du numéro de la page");
        assertion.verifyEquals("1", pageRessourcesParticipants.inputPage(wait, idCommune).getAttribute("value"),
                "Le numéro de la page n'est pas celui attendu");
        LOGGER.info("Navigation à la page suivante");
        seleniumTools.clickOnElement(wait, pageRessourcesParticipants.paginationSuivante(wait, idCommune));
        LOGGER.info("Vérification du numéro de la page");
        Thread.sleep(500);
        assertion.verifyEquals("2", pageRessourcesParticipants.inputPage(wait, idCommune).getAttribute("value"),
                "Le numéro de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 9 -- Navigation pages de participants (2/4)");
        LOGGER.info("Navigation à la page précédente");
        seleniumTools.clickOnElement(wait, pageRessourcesParticipants.paginationPrecedente(wait, idCommune));
        LOGGER.info("Vérification du numéro de la page");
        Thread.sleep(500);
        assertion.verifyEquals("1", pageRessourcesParticipants.inputPage(wait, idCommune).getAttribute("value"),
                "Le numéro de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 10 -- Navigation pages de participants (3/4)");
        seleniumTools.clickOnElement(wait, pageRessourcesParticipants.paginationLast(wait, idCommune));
        Thread.sleep(500);
        assertion.verifyEquals("2", pageRessourcesParticipants.inputPage(wait, idCommune).getAttribute("value"),
                "Le numéro de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 11 -- Navigation pages de participants (4/4)");
        seleniumTools.clickOnElement(wait, pageRessourcesParticipants.paginationFirst(wait, idCommune));
        Thread.sleep(500);
        assertion.verifyEquals("1", pageRessourcesParticipants.inputPage(wait, idCommune).getAttribute("value"),
                "Le numéro de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 12 -- Connexion à l'application - Utilisateur créé");
        LOGGER.info("Deconnection");
        PageLogin pageLogin = pageRessourcesParticipants.seDeconnecter(wait);
        LOGGER.info("Connection avec le nouvel utilisateur");
        pageCalendrierPlanification = pageLogin.seConnecter(wait, nomUtilisateur, mdpUtilisateur);
        LOGGER.info("Vérification de la page");
        idCommune = outilsProjet.retournerIdCommune(wait);
        pageCalendrierPlanification.titreDeLaPageUtilisateur(wait, idCommune);
    }
}
