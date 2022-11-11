package fr.eql.libreplan.selenium.relevesHeuresTravail;

import fr.eql.libreplan.pageObject.PageCalendrier.PageListeDesProjets;
import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.PageCout.PageCoutFeuilleDeTemps;
import fr.eql.libreplan.pageObject.PageCout.PageCoutFeuilleDeTempsCreer;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipants;
import fr.eql.libreplan.pageObject.pageRessources.participants.PageRessourcesParticipantsCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RHT01_CreerFeuilleTemps extends AbstractTestSelenium {
    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    // Connexion
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    protected String dateDuJour = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM yyyy"));
    private static final SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");

    // JDD Projet
    protected String nomProjet = listJdd.get(1).get("Nom");
    protected String modeleProjet = listJdd.get(1).get("Modèle");
    protected String codeProjet = listJdd.get(1).get("Code");
    protected boolean checkboxCodeProjet = Boolean.parseBoolean(listJdd.get(1).get("checkboxCode"));
    protected String nombreJourDateDebut = listJdd.get(1).get("Date de début");
    protected String nombreJourDateEcheance = listJdd.get(1).get("Echeance");
    protected String clientProjet = listJdd.get(1).get("Client");
    protected String calendrierProjet = listJdd.get(1).get("Calendrier");

    // JDD Participant Pre requis
    protected boolean checkboxParticipantPreRequis = Boolean.parseBoolean(listJdd.get(2).get("Code"));
    protected String nomParticipantPreRequis = listJdd.get(2).get("Nom");
    protected String prenomParticipantPreRequis = listJdd.get(2).get("Prénom");
    protected String idParticipantPreRequis = listJdd.get(2).get("ID");
    protected String typeParticipant = listJdd.get(2).get("Type");
    protected String utilisateurLiePreRequis = listJdd.get(2).get("Utilisateur lié");
    protected String nomUtilisateurPreRequis = listJdd.get(2).get("Nom d'utilisateur");
    protected String mdpUtilisateurPreRequis = listJdd.get(2).get("Mot de passe");
    protected String emailUtilisateurPreRequis = listJdd.get(2).get("Email");

    // JDD Feuille de temps
    protected String cavenas = "Default";
    protected String nombreDeJourTemps = listJdd.get(3).get("Date");
    protected String ressourceTemps = nomParticipantPreRequis + "," + prenomParticipantPreRequis;
    protected String tacheTemps = nomProjet;
    protected String heuresTemps = listJdd.get(3).get("Heures");
    protected String typeHeureTemps = listJdd.get(3).get("Types d'heures");
    protected boolean realiseTemps = Boolean.parseBoolean(listJdd.get(3).get("Réalisé"));


    public RHT01_CreerFeuilleTemps() throws IOException {
    }


    // JDD Projet


    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        LOGGER.info("Génération de la date");
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.add(Calendar.DAY_OF_MONTH, Integer.parseInt(nombreDeJourTemps));
        String dateDebutTemps = sdf.format(calendarDate.getTime());

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pre requis test");
        LOGGER.info("Vérification du calendrier");
        PageListeDesProjets pageListeDesProjets = pageCalendrierPlanification.cliquerOngletListeDesProjets(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        pageListeDesProjets.titreDeLaPage(wait, idCommune);
        Map<String, Map<String, WebElement>> mapValeurTableau = pageListeDesProjets.recuperationValeurTableau(wait);
        if(!mapValeurTableau.containsKey(nomProjet)){
            pageCalendrierPlanification = pageListeDesProjets.cliquerOngletPlanificationDesProjets(wait, idCommune);
            LOGGER.info("Création du calendrier" + nomProjet);
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
            pageCalendrierPlanification.cliquerAccepterBouton(wait);
            idCommune = outilsProjet.retournerIdCommune(wait);
            LOGGER.info("Formulaire enregistrer");
        } else {
            LOGGER.info("Calendrier " + nomProjet + " trouvé");
        }

        LOGGER.info("Vérification des participant");
        pageListeDesProjets.titreDeLaPage(wait, idCommune);
        PageRessourcesParticipants pageRessourcesParticipants = pageListeDesProjets.cliquerRessourcesParticipants(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        pageRessourcesParticipants.titreDeLaPage(wait, idCommune);
        Map<String, Map<String, String>> mapValeurTableauParticipant = pageRessourcesParticipants.recuperationValeurTableauParticipant(wait, idCommune);
        if (!mapValeurTableauParticipant.containsKey(nomParticipantPreRequis)){
            LOGGER.info("Participant " + nomParticipantPreRequis + " non trouvé");
            PageRessourcesParticipantsCreer pageRessourcesParticipantsCreer = pageRessourcesParticipants.cliquerBoutonCreer(wait, idCommune);
            pageRessourcesParticipantsCreer.remplirFormulaire(wait, idCommune,
                    checkboxParticipantPreRequis, nomParticipantPreRequis, prenomParticipantPreRequis, idParticipantPreRequis, typeParticipant);
            pageRessourcesParticipantsCreer.remplirUtilisateurLie(wait, idCommune, utilisateurLiePreRequis);
            pageRessourcesParticipantsCreer.remplirNouvelUtilisateur(wait, idCommune,
                    nomUtilisateurPreRequis, mdpUtilisateurPreRequis, mdpUtilisateurPreRequis, emailUtilisateurPreRequis);
            pageRessourcesParticipantsCreer.cliquerBoutonEnregistrer(wait, idCommune);
            pageRessourcesParticipantsCreer.titreDeLaPage(wait, idCommune);
            idCommune = outilsProjet.retournerIdCommune(wait);
            LOGGER.info("Participant " + nomParticipantPreRequis + " créé");
        }


        LOGGER.info("Pas de test 2 -- Accéder à la page de gestion des feuilles de temps");
        PageCoutFeuilleDeTemps pageCoutFeuilleDeTemps = pageCalendrierPlanification.cliquerCoutFeuilleDeTemps(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste des feuilles de temps", pageCoutFeuilleDeTemps.titrePage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Vérification du tableau");
        List<String> listLibelleTableauFeuilleTemps = pageCoutFeuilleDeTemps.recuperationLibelleTableau(wait);
        assertion.verifyEquals("Date de début", listLibelleTableauFeuilleTemps.get(0),
                "Le premier libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Date de fin", listLibelleTableauFeuilleTemps.get(1),
                "Le deuxième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Modèle", listLibelleTableauFeuilleTemps.get(2),
                "Le troisième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Travail total", listLibelleTableauFeuilleTemps.get(3),
                "Le quatrième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Code", listLibelleTableauFeuilleTemps.get(4),
                "Le cinquième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Actions", listLibelleTableauFeuilleTemps.get(5),
                "Le sixième libellé du tableau n'est pas celui attendu");
        LOGGER.info("Vérification des boutons filtres");
        assertion.verifyEquals("Montrer tout", outilsProjet.recupererValeurParDefautSelect(pageCoutFeuilleDeTemps.selectFitre(wait, idCommune)),
                "Le champ Modele n'a pas la valeur par défaut attendu");
        assertion.verifyTrue(pageCoutFeuilleDeTemps.inputDateFiltreDe(wait, idCommune).isDisplayed(),
                "Le champ filtre sur la date - DE n'est pas affiché");
        assertion.verifyTrue(pageCoutFeuilleDeTemps.inputDateFiltreA(wait, idCommune).isDisplayed(),
                "Le champ filtre sur la date - A n'est pas affiché");
        assertion.verifyTrue(pageCoutFeuilleDeTemps.inputFiltre(wait, idCommune).isDisplayed(),
                "Le bouton filtre n'est pas affiché");
        LOGGER.info("Vérification des boutons pour créer une nouvelle Feuille de temps");
        assertion.verifyEquals("Default", outilsProjet.recupererValeurParDefautSelect(pageCoutFeuilleDeTemps.selectCavenas(wait, idCommune)),
                "Le champ Choisir un canevas n'a pas la valeur par défaut attendu");
        assertion.verifyTrue(pageCoutFeuilleDeTemps.nouvelleFeuilleBouton(wait, idCommune).isDisplayed(),
                "Le bouton Nouvelle feuille de temps n'est pas affiché");

        LOGGER.info("Vérification de l'absence du JDD");
        pageCoutFeuilleDeTemps.nettoyageJDD(wait, dateDebutTemps, heuresTemps);

        LOGGER.info("Pas de test 3 -- Créer une feuille de temps - Accès au formulaire de création");
        PageCoutFeuilleDeTempsCreer pageCoutFeuilleDeTempsCreer = pageCoutFeuilleDeTemps.cliquerNouvelleFeuille(wait, idCommune, cavenas);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer la feuille de temps", pageCoutFeuilleDeTempsCreer.titrePage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Vérification des blocs");
        List<String> listLibelleBloc = pageCoutFeuilleDeTempsCreer.recuperationLibelleBloc(wait);
        assertion.verifyEquals(3, listLibelleBloc.size(),
                "Le nombre de bloc n'est pas celui attendu");
        assertion.verifyEquals("Données générales", listLibelleBloc.get(0),
                "Le premier bloc n'est pas celui attendu");
        assertion.verifyEquals("Champs Rubriques", listLibelleBloc.get(1),
                "Le dexuième bloc n'est pas celui attendu");
        assertion.verifyEquals("Lignes de feuille de temps", listLibelleBloc.get(2),
                "Le troisième bloc n'est pas celui attendu");

        LOGGER.info("Vérification du bloc Données générales");
        assertion.verifyEquals("Type", pageCoutFeuilleDeTempsCreer.libelleType(wait, idCommune).getText(),
                "Le libelle type n'est pas celui attendu");
        assertion.verifyEquals(cavenas, pageCoutFeuilleDeTempsCreer.valeurType(wait, idCommune).getText(),
                "La valeur de type n'est pas celui attendu");
        assertion.verifyEquals("Code", pageCoutFeuilleDeTempsCreer.libelleCode(wait, idCommune).getText(),
                "Le libelle code n'est pas celui attendu");
        assertion.verifyFalse(Objects.equals(pageCoutFeuilleDeTempsCreer.valeurCode(wait, idCommune).getAttribute("value"),""),
                "La valeur de code est vide");
        assertion.verifyEquals("Générer le code", pageCoutFeuilleDeTempsCreer.libelleCheckboxCode(wait, idCommune).getText(),
                "Le libelle type n'est pas celui attendu");
        assertion.verifyTrue(pageCoutFeuilleDeTempsCreer.checkboxCode(wait, idCommune).isSelected(),
                "La checkbox de Generer le code n'est pas coché");

        LOGGER.info("Vérification du bloc Champs Rubriques");
        assertion.verifyTrue(Objects.equals(pageCoutFeuilleDeTempsCreer.tableauChampRubrique(wait).getText(),""),
                "La tableau du champs rubriques n'est pas vide");

        LOGGER.info("Vérification du bloc Champs Lignes de feuille de temps");
        assertion.verifyTrue(pageCoutFeuilleDeTempsCreer.boutonAjouterUneLigne(wait, idCommune).isDisplayed(),
                "Le bouton Ajouter une ligne n'est pas présent");
        LOGGER.info("Vérification des boutons");
        assertion.verifyEquals("Enregistrer", pageCoutFeuilleDeTempsCreer.boutonEnregistrer(wait).getText(),
                "Le bouton Enregistrer n'est pas celui attendu");
        assertion.verifyEquals("Sauver et continuer", pageCoutFeuilleDeTempsCreer.boutonSauverEtContinuer(wait).getText(),
                "Le bouton Sauver et continuer n'est pas celui attendu");
        assertion.verifyEquals("Sauvegarder & Nouvelle feuille de temps", pageCoutFeuilleDeTempsCreer.boutonSauvegarder(wait).getText(),
                "Le bouton Sauvegarder n'est pas celui attendu");
        assertion.verifyEquals("Annuler", pageCoutFeuilleDeTempsCreer.boutonAnnuler(wait).getText(),
                "Le bouton Annuler n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Ajouter une ligne de feuille de temps");
        pageCoutFeuilleDeTempsCreer.cliquerBoutonAjouterUneLigne(wait, idCommune);
        Thread.sleep(500);
        List<String> listLibelleLigne = pageCoutFeuilleDeTempsCreer.recuperationLibelleTableauLigne(wait);
        assertion.verifyEquals("Date", listLibelleLigne.get(0),
                "Le premier libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Ressource", listLibelleLigne.get(1),
                "Le deuxième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Tâche", listLibelleLigne.get(2),
                "Le troisième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Heures", listLibelleLigne.get(3),
                "Le quatrième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Type d'heures", listLibelleLigne.get(4),
                "Le cinquième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Réalisé", listLibelleLigne.get(5),
                "Le sixième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Code", listLibelleLigne.get(6),
                "Le septième libellé du tableau n'est pas celui attendu");
        assertion.verifyEquals("Op.", listLibelleLigne.get(7),
                "Le huitième libellé du tableau n'est pas celui attendu");

        Map<String, WebElement> mapTableauLigne = pageCoutFeuilleDeTempsCreer.recuperationTableauLigne(wait);
        assertion.verifyEquals(dateDuJour, mapTableauLigne.get("Date").getAttribute("value"),
                "Le champ date n'est pas celui attendu");
        assertion.verifyTrue(Objects.equals(mapTableauLigne.get("Ressource").getAttribute("value"), ""),
                "Le champ Ressource n'est pas vide");
        assertion.verifyTrue(Objects.equals(mapTableauLigne.get("Tâche").getAttribute("value"), ""),
                "Le champ Tâche n'est pas vide");
        assertion.verifyEquals("0", mapTableauLigne.get("Heures").getAttribute("value"),
                "Le champ Heures n'est pas celui attendu");
        assertion.verifyEquals("Default",  outilsProjet.recupererValeurParDefautSelect(mapTableauLigne.get("Type d'heures")),
                "Le champ Heures n'est pas celui attendu");
        assertion.verifyFalse(mapTableauLigne.get("Réalisé").isSelected(),
                "La checkbox Réalisé est coché");
        assertion.verifyTrue(Objects.equals(mapTableauLigne.get("Code").getAttribute("value"), ""),
                "Le champ Code n'est pas vide");
        assertion.verifyTrue(mapTableauLigne.get("Op.").isEnabled(),
                "Le bouton supprimé n'est pas présent");

        LOGGER.info("Pas de test 5 -- Ajouter une feuille de temps - bouton [Enregistrer]");
        String codeLigneCreer = pageCoutFeuilleDeTempsCreer.valeurCode(wait, idCommune).getAttribute("value");
        LOGGER.info("Enregistrement du code : " + codeLigneCreer);
        LOGGER.info("Remplissage du formulaire");
        pageCoutFeuilleDeTempsCreer.remplirFormulaireLigne(wait,
                dateDebutTemps, ressourceTemps, tacheTemps, heuresTemps, typeHeureTemps, realiseTemps);
        pageCoutFeuilleDeTemps = pageCoutFeuilleDeTempsCreer.cliquerBoutonEnregistrer(wait);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du message d'enregistrement");
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste des feuilles de temps", pageCoutFeuilleDeTemps.titrePage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Feuille de temps sauvegardée", pageCoutFeuilleDeTemps.messageCreation(wait).getText(),
                "Le message de création n'est pas celui attendu");
        Map<String, WebElement> mapValeurCreer = pageCoutFeuilleDeTemps.recuperationValeurTableau(wait).get(codeLigneCreer);
        LOGGER.info("Vérification des valeurs de la ligne créer");
        assertion.verifyEquals(dateDebutTemps, mapValeurCreer.get("Date de début").getText(),
                "La valeur de la date de début n'est pas celle attendu");
        assertion.verifyEquals(dateDebutTemps, mapValeurCreer.get("Date de fin").getText(),
                "La valeur de la date de fin n'est pas celle attendu");
        assertion.verifyEquals(cavenas, mapValeurCreer.get("Modèle").getText(),
                "La valeur du Modèle n'est pas celle attendu");
        assertion.verifyEquals(heuresTemps, mapValeurCreer.get("Travail total").getText(),
                "La valeur du Travail total n'est pas celle attendu");
        assertion.verifyEquals(codeLigneCreer, mapValeurCreer.get("Code").getText(),
                "La valeur du Code n'est pas celle attendu");

        Map<String, Map<String, WebElement>> mapToutTableauTemps = pageCoutFeuilleDeTemps.recuperationValeurTableau(wait);
        if(mapToutTableauTemps.size() < 2){
            pageCoutFeuilleDeTempsCreer = pageCoutFeuilleDeTemps.cliquerNouvelleFeuille(wait, idCommune, "JIRA timesheets");
            idCommune = outilsProjet.retournerIdCommune(wait);
            LOGGER.info("Vérification du titre de la page");
            assertion.verifyEquals("Créer la feuille de temps", pageCoutFeuilleDeTempsCreer.titrePage(wait, idCommune).getText(),
                    "Le titre de la page n'est pas celui attendu");
            pageCoutFeuilleDeTempsCreer.cliquerBoutonAjouterUneLigne(wait, idCommune);
            Thread.sleep(500);
            pageCoutFeuilleDeTempsCreer.remplirFormulaireLigne(wait,
                    dateDuJour, ressourceTemps, tacheTemps, "5", typeHeureTemps, false);
            pageCoutFeuilleDeTemps = pageCoutFeuilleDeTempsCreer.cliquerBoutonEnregistrer(wait);
            pageCoutFeuilleDeTemps.listLibelleTableau(wait);
        }


        LOGGER.info("Pas de test 6 -- Tri du tableau des feuilles de temps - tri par défaut");
        List<Map<String, WebElement>> listOrdreTableauTemps = pageCoutFeuilleDeTemps.ordreValeurTableauText(wait);
        assertion.verifyEquals(dateDebutTemps, listOrdreTableauTemps.get(0).get("Date de début").getText(),
                "L'ordre par défaut n'est pas celui attendu");

        LOGGER.info("Pas de test 7 -- Tri du tableau des feuilles de temps - tri par colonne \"Date de fin\" (1/2)");
        seleniumTools.clickOnElement(wait, pageCoutFeuilleDeTemps.listLibelleTableau(wait).get(1));
        Thread.sleep(500);
        listOrdreTableauTemps = pageCoutFeuilleDeTemps.ordreValeurTableauText(wait);
        assertion.verifyEquals(dateDebutTemps, listOrdreTableauTemps.get(1).get("Date de fin").getText(),
                "L'ordre par date de fin croissante n'est pas celui attendu");

        LOGGER.info("Pas de test 8 -- Tri du tableau des feuilles de temps - tri par colonne \"Date de fin\" (2/2)");
        seleniumTools.clickOnElement(wait, pageCoutFeuilleDeTemps.listLibelleTableau(wait).get(1));
        Thread.sleep(500);
        listOrdreTableauTemps = pageCoutFeuilleDeTemps.ordreValeurTableauText(wait);
        assertion.verifyEquals(dateDebutTemps, listOrdreTableauTemps.get(0).get("Date de fin").getText(),
                "L'ordre par date de fin décroissance n'est pas celui attendu");

        LOGGER.info("Pas de test 9 -- Tri du tableau des feuilles de temps - tri par colonne \"Travail total\" (1/2)");
        seleniumTools.clickOnElement(wait, pageCoutFeuilleDeTemps.listLibelleTableau(wait).get(3));
        Thread.sleep(500);
        listOrdreTableauTemps = pageCoutFeuilleDeTemps.ordreValeurTableauText(wait);
        assertion.verifyEquals(heuresTemps, listOrdreTableauTemps.get(1).get("Travail total").getText(),
                "L'ordre par Travail total croissante n'est pas celui attendu");

        LOGGER.info("Pas de test 10 -- Tri du tableau des feuilles de temps - tri par colonne \"Travail total\" (2/2)");
        seleniumTools.clickOnElement(wait, pageCoutFeuilleDeTemps.listLibelleTableau(wait).get(3));
        Thread.sleep(500);
        listOrdreTableauTemps = pageCoutFeuilleDeTemps.ordreValeurTableauText(wait);
        assertion.verifyEquals(heuresTemps, listOrdreTableauTemps.get(0).get("Travail total").getText(),
                "L'ordre par Travail total décroissance n'est pas celui attendu");

        LOGGER.info("Pas de test 11 -- Tri du tableau des feuilles de temps - tri par colonne \"Modèle\" (1/2)");
        seleniumTools.clickOnElement(wait, pageCoutFeuilleDeTemps.listLibelleTableau(wait).get(2));
        Thread.sleep(500);
        listOrdreTableauTemps = pageCoutFeuilleDeTemps.ordreValeurTableauText(wait);
        assertion.verifyEquals(cavenas, listOrdreTableauTemps.get(0).get("Modèle").getText(),
                "L'ordre par Modèle croissant n'est pas celui attendu");

        LOGGER.info("Pas de test 12 -- Tri du tableau des feuilles de temps - tri par colonne \"Modèle\" (2/2)");
        seleniumTools.clickOnElement(wait, pageCoutFeuilleDeTemps.listLibelleTableau(wait).get(2));
        Thread.sleep(500);
        listOrdreTableauTemps = pageCoutFeuilleDeTemps.ordreValeurTableauText(wait);
        assertion.verifyEquals(cavenas, listOrdreTableauTemps.get(1).get("Modèle").getText(),
                "L'ordre par Modèle croissant n'est pas celui attendu");

    }

}
