package fr.eql.libreplan.selenium.gestionDesRessources;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipantsCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GRE03_CreerParticipantNouvelUtilisateur_CNP extends AbstractTestSelenium {
    // Chargement JDD
    protected String className = getClass().getSimpleName();
    protected String classPackage = this.getClass().getPackage().getName();
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDDD Participant 1
    protected String codeParticipant = listJdd.get(1).get("Code");
    protected boolean checkboxCodeParticipant = Boolean.parseBoolean(listJdd.get(1).get("Générer le code"));
    protected String nomParticipant = listJdd.get(1).get("Nom");
    protected String prenomParticipant = listJdd.get(1).get("Prénom");
    protected String idParticipant = listJdd.get(1).get("ID");
    protected String typeParticipant = listJdd.get(1).get("Type");
    protected String utilisateurLie = listJdd.get(1).get("Utilisateur lié");

    // JDDD Participant NouvelUtilisateur
    protected String utilisateurLie2 = listJdd.get(2).get("Utilisateur lié");
    protected String nomUtilisateur = listJdd.get(2).get("Nom d'utilisateur");
    protected String mdpUtilisateur = listJdd.get(2).get("Mot de passe");
    protected String emailUtilisateur = listJdd.get(2).get("Email");

    // JDD Participant Pre requis
    protected boolean checkboxParticipantPreRequis = Boolean.parseBoolean(listJdd.get(3).get("Code"));
    protected String nomParticipantPreRequis = listJdd.get(3).get("Nom");
    protected String prenomParticipantPreRequis = listJdd.get(3).get("Prénom");
    protected String idParticipantPreRequis = listJdd.get(3).get("ID");
    protected String utilisateurLiePreRequis = listJdd.get(3).get("Utilisateur lié");
    protected String nomUtilisateurPreRequis = listJdd.get(3).get("Nom d'utilisateur");
    protected String mdpUtilisateurPreRequis = listJdd.get(3).get("Mot de passe");
    protected String emailUtilisateurPreRequis = listJdd.get(3).get("Email");

    public GRE03_CreerParticipantNouvelUtilisateur_CNP() throws IOException {
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
        LOGGER.info("Vérification que le JDD pré-requis présent");
        Thread.sleep(500);
        Map<String, Map<String, String>> mapValeurTableau = pageRessourcesParticipants.recuperationValeurTableauParticipant(wait, idCommune);
        if (!mapValeurTableau.containsKey(nomParticipantPreRequis)){
            PageRessourcesParticipantsCreer pageRessourcesParticipantsCreer = pageRessourcesParticipants.cliquerBoutonCreer(wait, idCommune);
            pageRessourcesParticipantsCreer.remplirFormulaire(wait, idCommune,
                    checkboxParticipantPreRequis, nomParticipantPreRequis, prenomParticipantPreRequis, idParticipantPreRequis, typeParticipant);
            pageRessourcesParticipantsCreer.remplirUtilisateurLie(wait, idCommune, utilisateurLiePreRequis);
            pageRessourcesParticipantsCreer.remplirNouvelUtilisateur(wait, idCommune,
                    nomUtilisateurPreRequis, mdpUtilisateurPreRequis, mdpUtilisateurPreRequis, emailUtilisateurPreRequis);
            pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
            pageRessourcesParticipantsCreer.titreDeLaPage(wait, idCommune);
            idCommune = outilsProjet.retournerIdCommune(wait);
        }


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


        LOGGER.info("Pas de test 5 -- Créer un participant - Aucune valeur renseignée");
        pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        LOGGER.info("Vérification de la présence du message de données obligatoires");
        assertion.verifyEquals("Champ vide non autorisé.\nVous devez spécifier une valeur", pageRessourcesParticipantsCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message d'erreur des données obligatoires n'est pas celui attendu");

        LOGGER.info("Pas de test 6 -- Actions sur le message d'erreur - déplacement");
        LOGGER.info("Déplacement de la fenêtre sur le libellé nom");
        seleniumTools.dragAndDrop(wait, pageRessourcesParticipantsCreer.messageAlerteDonneeObligatoire(wait),
                pageRessourcesParticipantsCreer.inputNom(wait, idCommune));
        Thread.sleep(500);
        LOGGER.info("Vérification de la flèche");
        assertion.verifyEquals("z-arrow-u", pageRessourcesParticipantsCreer.orientationFlecheMessageErreur(wait),
                "L'orientation de la flèche du message d'erreur n'est pas celle attendu");

        LOGGER.info("Pas de test 7 -- Actions sur le message d'erreur - Infobulle");
        assertion.verifyEquals("Allez sur le mauvais champ", pageRessourcesParticipantsCreer.flecheMessageAlerteDonneeObligatoire(wait).getAttribute("title"),
                "L'infobulle du message d'erreur n'est pas celle attendu");

        LOGGER.info("Pas de test 8 -- Fermeture du message d'erreur");
        LOGGER.info("Fermeture de l'alerte");
        seleniumTools.clickOnElement(wait, pageRessourcesParticipantsCreer.dismissMessageAlerteDonneeObligatoire(wait));
        LOGGER.info("Press de la key a");
//        Robot robot = new Robot();
//        robot.keyPress(KeyEvent.VK_A);
//        LOGGER.info("Vérification du champ prénom");
//        Thread.sleep(500);
//        assertion.verifyEquals("a", pageRessourcesParticipantsCreer.inputPrenom(wait,idCommune).getAttribute("value"));

        LOGGER.info("Pas de test 9 -- Créer un participant - Utilisateur lié existant non renseigné");
        LOGGER.info("Remplissage du formulaire - Données de base");
        pageRessourcesParticipantsCreer.remplirFormulaire(wait, idCommune,
                checkboxCodeParticipant, nomParticipant, prenomParticipant, idParticipant, typeParticipant);
        LOGGER.info("Coche de la case : " + utilisateurLie);
        pageRessourcesParticipantsCreer.remplirUtilisateurLie(wait, idCommune, utilisateurLie);
        LOGGER.info("Enregistrement");
        pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        LOGGER.info("Vérification du message d'erreur");
        assertion.verifyEquals("merci de choisir à utilisateur à lier", pageRessourcesParticipantsCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message d'erreur n'est pas celui attendu");

        LOGGER.info("Pas de test 10 -- Créer un participant - Utilisateur lié nouvel utilisateur non renseigné");
        LOGGER.info("Remplissage du formulaire - Données de base");
        pageRessourcesParticipantsCreer.remplirFormulaire(wait, idCommune,
                checkboxCodeParticipant, nomParticipant, prenomParticipant, idParticipant, typeParticipant);
        LOGGER.info("Coche de la case : " + utilisateurLie);
        pageRessourcesParticipantsCreer.remplirUtilisateurLie(wait, idCommune, utilisateurLie2);
        LOGGER.info("Enregistrement");
        pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        LOGGER.info("Vérification du message d'erreur");
        assertion.verifyEquals("ne peut pas être vide", pageRessourcesParticipantsCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message d'erreur n'est pas celui attendu");

        LOGGER.info("Pas de test 11 -- Créer un participant - Utilisateur lié nouvel utilisateur : confirmation mot de passe non conforme");
        LOGGER.info("Remplissage du formulaire - Données de base");
        pageRessourcesParticipantsCreer.remplirFormulaire(wait, idCommune,
                checkboxCodeParticipant, nomParticipant, prenomParticipant, idParticipant, typeParticipant);
        LOGGER.info("Coche de la case : " + utilisateurLie);
        pageRessourcesParticipantsCreer.remplirUtilisateurLie(wait, idCommune, utilisateurLie2);
        pageRessourcesParticipantsCreer.remplirNouvelUtilisateur(wait, idCommune, nomUtilisateur, mdpUtilisateur, "", emailUtilisateur);
        LOGGER.info("Enregistrement");
        pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        LOGGER.info("Vérification du message d'erreur");
        assertion.verifyEquals("les mots de passe ne correspondent pas", pageRessourcesParticipantsCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message d'erreur n'est pas celui attendu");

        LOGGER.info("Pas de test 12 -- Créer un participant - Utilisateur lié nouvel utilisateur : ID et Nom d'utilisateur non conformes");
        LOGGER.info("Remplissage du formulaire - Données de base");
        pageRessourcesParticipantsCreer.remplirFormulaire(wait, idCommune,
                checkboxCodeParticipant, nomParticipant, prenomParticipant, idParticipantPreRequis, typeParticipant);
        LOGGER.info("Coche de la case : " + utilisateurLie);
        pageRessourcesParticipantsCreer.remplirUtilisateurLie(wait, idCommune, utilisateurLie2);
        pageRessourcesParticipantsCreer.remplirNouvelUtilisateur(wait, idCommune, nomUtilisateurPreRequis, mdpUtilisateur, mdpUtilisateur, emailUtilisateur);
        pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        assertion.verifyEquals("ID déjà utilisé. Il doit être unique",
                pageRessourcesParticipantsCreer.messageWarning(wait).get(0).getText(),
                "Le premier message warning ne correspond pas à celui attendu");
        assertion.verifyEquals("ce nom d'utilisateur est déjà utilisé par un autre utilisateur",
                pageRessourcesParticipantsCreer.messageWarning(wait).get(1).getText(),
                "Le deuxième message warning ne correspond pas à celui attendu");
        LOGGER.info("Enregistrement");

        LOGGER.info("Pas de test 13 -- Créer un participant - Utilisateur lié nouvel utilisateur : email non conforme");
        String codeParticipantRecup = pageRessourcesParticipantsCreer.inputCode(wait,idCommune).getAttribute("value");
        pageRessourcesParticipantsCreer.remplirFormulaire(wait, idCommune,
                checkboxCodeParticipant, nomParticipant, prenomParticipant, idParticipant, typeParticipant);
        pageRessourcesParticipantsCreer.remplirNouvelUtilisateur(wait, idCommune,
                nomUtilisateur, mdpUtilisateur, mdpUtilisateur, emailUtilisateur.replace("@", ""));
        LOGGER.info("Enregistrement");
        pageRessourcesParticipants = pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du message de création");
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



    }
}
