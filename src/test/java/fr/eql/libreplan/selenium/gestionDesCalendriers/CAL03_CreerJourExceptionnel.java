package fr.eql.libreplan.selenium.gestionDesCalendriers;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrierCreer;
import fr.eql.libreplan.pageObject.pageRessources.joursExceptionnels.PageRessourcesJoursExceptionnels;
import fr.eql.libreplan.pageObject.pageRessources.joursExceptionnels.PageRessourcesJoursExceptionnelsCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CAL03_CreerJourExceptionnel extends AbstractTestSelenium {
    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    // Connexion
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDD Jour exceptionnel
    protected String nomJourExceptionnel = listJdd.get(1).get("nom");
    protected String couleur = listJdd.get(1).get("couleur");
    protected String nombreIncorrect = "-1";
    protected String nombreEffortStandartHeure = listJdd.get(1).get("nombreEffortStandartHeure");
    protected String nombreEffortStandartMinute = listJdd.get(1).get("nombreEffortStandartMinute");
    protected String nombreEffortSupplementaireHeure = listJdd.get(1).get("nombreEffortSupplementaireHeure");
    protected String nombreEffortSupplementaireMinute = listJdd.get(1).get("nombreEffortSupplementaireMinute");

    // JDD Calendrier
    protected String nomCalendrier = listJdd.get(2).get("nomCalendrier");
    protected boolean codeCalendrier = Boolean.parseBoolean(listJdd.get(2).get("codeCalendrier"));
    protected String dateDuJour = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public CAL03_CreerJourExceptionnel() throws IOException {
    }

    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la page d'administration des jours exceptionnels des calendriers");
        PageRessourcesJoursExceptionnels pageRessourcesJoursExceptionnels = pageCalendrierPlanification.cliquerRessourcesJoursExceptionnels(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Jours exceptionnels du calendrier Liste", pageRessourcesJoursExceptionnels.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des libellés du tableau");
        List<String> listLibelleTableau = pageRessourcesJoursExceptionnels.recuperationLibelleTableau(idCommune);
        LOGGER.info("Récupération des libellés du tableau");
        assertion.verifyEquals("Nom", listLibelleTableau.get(0),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Couleur", listLibelleTableau.get(1),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Sur-affecté", listLibelleTableau.get(2),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Effort standard", listLibelleTableau.get(3),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Effort en heures supplémentaires", listLibelleTableau.get(4),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Opérations", listLibelleTableau.get(5),
                "Le libelle n'est pas celui attendu");
        LOGGER.info("Nettoyage si nécessaire");
        pageRessourcesJoursExceptionnels.verificationNettoyageTableau(wait, idCommune, nomJourExceptionnel);

        LOGGER.info("Pas de test 3 -- Accéder au formulaire de création d'un jour exceptionnel du calendrier");
        PageRessourcesJoursExceptionnelsCreer pageRessourcesJoursExceptionnelsCreer = pageRessourcesJoursExceptionnels.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et formulaire");
        assertion.verifyEquals("Créer Jour du calendrier exceptionnel", pageRessourcesJoursExceptionnelsCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Modifier", pageRessourcesJoursExceptionnelsCreer.titreFormulaire(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des titres du formulaire");
        listLibelleTableau = pageRessourcesJoursExceptionnelsCreer.recuperationLibelleTableau(idCommune);
        LOGGER.info("Assertion des libelle du formulaire");
        assertion.verifyEquals("Code:", listLibelleTableau.get(0),
                "Le libelle du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Nom", listLibelleTableau.get(1),
                "Le libelle du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Couleur", listLibelleTableau.get(2),
                "Le libelle du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Effort standard", listLibelleTableau.get(3),
                "Le libelle du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Effort supplémentaire", listLibelleTableau.get(4),
                "Le libelle du formulaire n'est pas celui attendu");
        LOGGER.info("Assertion des input du formulaire");
        assertion.verifyTrue(!Objects.equals(pageRessourcesJoursExceptionnelsCreer.inputCode(wait, idCommune).getAttribute("value"), ""),
                "Le champ code est vide");
        assertion.verifyTrue(pageRessourcesJoursExceptionnelsCreer.checkboxCode(wait, idCommune).isSelected(),
                "La checkbox du code n'est pas coché");
        assertion.verifyEquals("", pageRessourcesJoursExceptionnelsCreer.inputNom(wait, idCommune).getAttribute("value"),
                "l'input Nom n'est pas vide");
        assertion.verifyEquals("Exception particulière", pageRessourcesJoursExceptionnelsCreer.couleurException(wait, idCommune).get(0),
                "Le champ texte associé à la couleur n'est pas celui attendu");
        assertion.verifyEquals("Exception héritée", pageRessourcesJoursExceptionnelsCreer.couleurException(wait, idCommune).get(1),
                "Le champ texte associé à la couleur n'est pas celui attendu");
        assertion.verifyEquals("0", pageRessourcesJoursExceptionnelsCreer.inputEffortStandart(wait, idCommune).getAttribute("value"),
                "Le champ effort standart n'est pas celui attendu");
        assertion.verifyEquals("0", pageRessourcesJoursExceptionnelsCreer.inputEffortSupplementaire(wait, idCommune).getAttribute("value"),
                "Le champ effort standart n'est pas celui attendu");
        assertion.verifyFalse(pageRessourcesJoursExceptionnelsCreer.checkboxEffortSupplementaire(wait, idCommune).isSelected(),
                "La checkbox Infiniment sur-affectable est coché");
        LOGGER.info("Vérification des boutons");
        assertion.verifyEquals("Annuler", pageRessourcesJoursExceptionnelsCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le bouton annuler n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer", pageRessourcesJoursExceptionnelsCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le bouton Enregistrer n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer et continuer", pageRessourcesJoursExceptionnelsCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                "Le bouton Enregistrer et continuer n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Annulation");
        LOGGER.info("Recuperation de la valeur du champ Code");
        String code = pageRessourcesJoursExceptionnelsCreer.inputCode(wait, idCommune).getAttribute("value");
        seleniumTools.sendKey(wait, pageRessourcesJoursExceptionnelsCreer.inputNom(wait, idCommune), nomJourExceptionnel);
        pageRessourcesJoursExceptionnels = pageRessourcesJoursExceptionnelsCreer.cliquerBoutonAnnuler(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Jours exceptionnels du calendrier Liste", pageRessourcesJoursExceptionnels.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Recupération des valeurs du tableau");
        Map<String, Map<String, String>> mapValeurTableauCalendrier = pageRessourcesJoursExceptionnels.recuperationValeurTableauJourExceptionnel(idCommune);
        LOGGER.info("Vérification");
        assertion.verifyFalse(mapValeurTableauCalendrier.containsKey(nomJourExceptionnel),
                "Le tableau contient " + nomJourExceptionnel);

        LOGGER.info("Pas de test 5 -- Accéder au formulaire de création d'un jour exceptionnel du calendrier");
        pageRessourcesJoursExceptionnelsCreer = pageRessourcesJoursExceptionnels.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et formulaire");
        assertion.verifyEquals("Créer Jour du calendrier exceptionnel", pageRessourcesJoursExceptionnelsCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Modifier", pageRessourcesJoursExceptionnelsCreer.titreFormulaire(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des titres du formulaire");
        listLibelleTableau = pageRessourcesJoursExceptionnelsCreer.recuperationLibelleTableau(idCommune);
        LOGGER.info("Assertion des libelle du formulaire");
        assertion.verifyEquals("Code:", listLibelleTableau.get(0),
                "Le libelle du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Nom", listLibelleTableau.get(1),
                "Le libelle du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Couleur", listLibelleTableau.get(2),
                "Le libelle du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Effort standard", listLibelleTableau.get(3),
                "Le libelle du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Effort supplémentaire", listLibelleTableau.get(4),
                "Le libelle du formulaire n'est pas celui attendu");
        LOGGER.info("Assertion des input du formulaire");
        assertion.verifyTrue(!Objects.equals(pageRessourcesJoursExceptionnelsCreer.inputCode(wait, idCommune).getAttribute("value"), ""),
                "Le champ code est vide");
        assertion.verifyTrue(pageRessourcesJoursExceptionnelsCreer.checkboxCode(wait, idCommune).isSelected(),
                "La checkbox du code n'est pas coché");
        assertion.verifyEquals("", pageRessourcesJoursExceptionnelsCreer.inputNom(wait, idCommune).getAttribute("value"),
                "l'input Nom n'est pas vide");
        assertion.verifyEquals("Exception particulière", pageRessourcesJoursExceptionnelsCreer.couleurException(wait, idCommune).get(0),
                "Le champ texte associé à la couleur n'est pas celui attendu");
        assertion.verifyEquals("Exception héritée", pageRessourcesJoursExceptionnelsCreer.couleurException(wait, idCommune).get(1),
                "Le champ texte associé à la couleur n'est pas celui attendu");
        assertion.verifyEquals("0", pageRessourcesJoursExceptionnelsCreer.inputEffortStandart(wait, idCommune).getAttribute("value"),
                "Le champ effort standart n'est pas celui attendu");
        assertion.verifyEquals("0", pageRessourcesJoursExceptionnelsCreer.inputEffortSupplementaire(wait, idCommune).getAttribute("value"),
                "Le champ effort standart n'est pas celui attendu");
        assertion.verifyFalse(pageRessourcesJoursExceptionnelsCreer.checkboxEffortSupplementaire(wait, idCommune).isSelected(),
                "La checkbox Infiniment sur-affectable est coché");
        LOGGER.info("Vérification des boutons");
        assertion.verifyEquals("Annuler", pageRessourcesJoursExceptionnelsCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le bouton annuler n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer", pageRessourcesJoursExceptionnelsCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le bouton Enregistrer n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer et continuer", pageRessourcesJoursExceptionnelsCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                "Le bouton Enregistrer et continuer n'est pas celui attendu");

        LOGGER.info("Pas de test 6 -- Vérifier l'alimentation du champ Code");
        code = outilsProjet.ajouterUnAuCode(wait, code);
        LOGGER.info("Verification du code");
        assertion.verifyEquals(code, pageRessourcesJoursExceptionnelsCreer.inputCode(wait, idCommune).getAttribute("value"));

        LOGGER.info("Pas de test 7 -- Sélectionner de la couleur associée au jour d'exception");
        LOGGER.info("Renseigner le nom avec " + nomJourExceptionnel);
        seleniumTools.sendKey(wait, pageRessourcesJoursExceptionnelsCreer.inputNom(wait, idCommune), nomJourExceptionnel);
        LOGGER.info("Récuperation des couleurs disponible");
        Map<String, List<String>> mapRecuperationCouleurCss = pageRessourcesJoursExceptionnelsCreer.recuperationCouleurDisponible(wait, idCommune);
        Select select = new Select(pageRessourcesJoursExceptionnelsCreer.selectCouleur(wait, idCommune));
        LOGGER.info("Verification des couleurs");
        if (navigateur == "chrome"){
            for(Map.Entry <String, List<String>> map : mapRecuperationCouleurCss.entrySet()){
                select.selectByVisibleText(map.getKey());
                Thread.sleep(100);
                List<String> couleurCssCorrespondance = map.getValue();
                LOGGER.info("Verification de la couleur : " + map.getKey());
                assertion.verifyEquals(couleurCssCorrespondance.get(0), pageRessourcesJoursExceptionnelsCreer.couleurFonce(wait, idCommune).getCssValue("background-color"),
                        "La couleur " + map.getKey() + " foncé ne correspond pas à celle selectionné");
                assertion.verifyEquals(couleurCssCorrespondance.get(1), pageRessourcesJoursExceptionnelsCreer.couleurClair(wait, idCommune).getCssValue("background-color"),
                        "La couleur "+ map.getKey() +" clair ne correspond pas à celle selectionné");
            }
        }
        select.selectByVisibleText(couleur);

        LOGGER.info("Pas de test 8 -- Sélection Effort standard - 1ere valeur non conforme");
        LOGGER.info("Renseigne le champ Effort standart avec " + nombreIncorrect);
        pageRessourcesJoursExceptionnelsCreer.renseignerEffort(wait, idCommune, pageRessourcesJoursExceptionnelsCreer.inputEffortStandart(wait,idCommune),
                nombreIncorrect);
        LOGGER.info("Vérification du message de données obligatoires");
        assertion.verifyEquals("Dépassement du rang (>= 0).", pageRessourcesJoursExceptionnelsCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message des données obligatoires n'est pas celui attendu");

        LOGGER.info("Pas de test 9 -- Sélectionner Effort standard - 2eme valeur non conforme");
        LOGGER.info("Renseigne l'effort standart heure avec " + nombreEffortStandartHeure);
        pageRessourcesJoursExceptionnelsCreer.renseignerEffort(wait, idCommune, pageRessourcesJoursExceptionnelsCreer.inputEffortStandart(wait,idCommune),
                nombreEffortStandartHeure);
        Thread.sleep(500);
        LOGGER.info("Renseigne le champ Effort standart Minute avec " + nombreIncorrect);
        pageRessourcesJoursExceptionnelsCreer.renseignerEffort(wait, idCommune, pageRessourcesJoursExceptionnelsCreer.inputEffortStandartMinute(wait,idCommune),
                nombreIncorrect);
        LOGGER.info("Vérification du message de données obligatoires");
        assertion.verifyEquals("Dépassement du rang (0 ~ 59).", pageRessourcesJoursExceptionnelsCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message des données obligatoires n'est pas celui attendu");


        LOGGER.info("Pas de test 10 -- Sélectionner Effort supplémentaire - 1ere valeur non conforme ");
        LOGGER.info("Renseigne l'effort standart en minute par " + nombreEffortStandartMinute );
        pageRessourcesJoursExceptionnelsCreer.renseignerEffort(wait, idCommune, pageRessourcesJoursExceptionnelsCreer.inputEffortStandartMinute(wait,idCommune),
                nombreEffortStandartMinute);
        Thread.sleep(500);
        LOGGER.info("Renseigne Effort supplémentaire avec "+nombreIncorrect);
        pageRessourcesJoursExceptionnelsCreer.renseignerEffort(wait, idCommune, pageRessourcesJoursExceptionnelsCreer.inputEffortSupplementaire(wait,idCommune),
                nombreIncorrect);
        LOGGER.info("Vérification du message de données obligatoires");
        assertion.verifyEquals("Dépassement du rang (>= 0).", pageRessourcesJoursExceptionnelsCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message des données obligatoires n'est pas celui attendu");

        LOGGER.info("Pas de test 11 -- Sélectionner Effort supplémentaire - 2eme valeur non conforme");
        LOGGER.info("Renseigne l'effort Supplémentaire en heure par " + nombreEffortSupplementaireHeure);
        pageRessourcesJoursExceptionnelsCreer.renseignerEffort(wait, idCommune, pageRessourcesJoursExceptionnelsCreer.inputEffortSupplementaire(wait,idCommune),
                nombreEffortSupplementaireHeure);
        Thread.sleep(500);
        LOGGER.info("Renseigne Effort supplémentaire Minute avec " + nombreIncorrect);
        pageRessourcesJoursExceptionnelsCreer.renseignerEffort(wait, idCommune, pageRessourcesJoursExceptionnelsCreer.inputEffortSupplementaireMinute(wait,idCommune),
                nombreIncorrect);
        LOGGER.info("Vérification du message de données obligatoires");
        assertion.verifyEquals("Dépassement du rang (0 ~ 59).", pageRessourcesJoursExceptionnelsCreer.messageAlerteDonneeObligatoire(wait).getText(),
                "Le message des données obligatoires n'est pas celui attendu");


        LOGGER.info("Pas de test 12 -- Sélectionner Effort supplémentaire conforme + Enregistrement");
        LOGGER.info("Renseigne l'effort Supplémentaire en minute par " + nombreEffortSupplementaireMinute);
        pageRessourcesJoursExceptionnelsCreer.renseignerEffort(wait, idCommune, pageRessourcesJoursExceptionnelsCreer.inputEffortSupplementaireMinute(wait,idCommune),
                nombreEffortSupplementaireMinute);
        LOGGER.info("Enregistrement de la saisie");
        pageRessourcesJoursExceptionnels = pageRessourcesJoursExceptionnelsCreer.cliquerBoutonEnregistrer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Jours exceptionnels du calendrier Liste", pageRessourcesJoursExceptionnels.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Vérification du message d'enregistrement");
        assertion.verifyEquals("Jour du calendrier exceptionnel \"" + nomJourExceptionnel + "\" enregistré", pageRessourcesJoursExceptionnels.messageCreation(wait, nomJourExceptionnel),
                "Le message d'enregistrement n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du tableau");
        Map<String, String> mapValeurTableau = pageRessourcesJoursExceptionnels.recuperationValeurTableauJourExceptionnel(idCommune).get(nomJourExceptionnel);
        LOGGER.info("Vérification des valeurs créées du tableau");
        assertion.verifyEquals(nomJourExceptionnel, mapValeurTableau.get("Nom"));
        assertion.verifyEquals(couleur, mapValeurTableau.get("Couleur"));
        assertion.verifyEquals("No", mapValeurTableau.get("Sur-affecté"));
        assertion.verifyEquals(nombreEffortStandartHeure + ":" + nombreEffortStandartMinute, mapValeurTableau.get("Effort standard"));
        assertion.verifyEquals(nombreEffortSupplementaireHeure + ":" + nombreEffortSupplementaireMinute, mapValeurTableau.get("Effort en heures supplémentaires"));

        LOGGER.info("Pas de test 13 -- Accéder à la page d'administration des calendriers");
        PageRessourcesCalendrier pageRessourcesCalendrier = pageRessourcesJoursExceptionnels.cliquerRessourcesCalendrier(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        methodesProjet.VerificationPageAdministrationCalendriers(wait);

        LOGGER.info("Pas de test 14 -- Accéder à la page de modification d'un calendrier");
        LOGGER.info("Nettoyage si nécessaire avant la création");
        pageRessourcesCalendrier.verificationNettoyageTableauCAL2(wait, idCommune, nomCalendrier);
        LOGGER.info("Etape supplémentaire -- Creation d'un calendrier");
        methodesProjet.creationCalendrier(wait, idCommune, nomCalendrier, codeCalendrier);
        LOGGER.info("Reprise du cas de test -- Modification calendrier");
        pageRessourcesCalendrier.cliquerBoutonModifierCalendrier(wait, nomCalendrier);
        LOGGER.info("Acces à la page de modification");
        PageRessourcesCalendrierCreer pageRessourcesCalendrierCreer = new PageRessourcesCalendrierCreer(driver);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page de modification du calendrier et du formulaire");
        assertion.verifyEquals("Modifier Calendrier: " + nomCalendrier, pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 15 -- Vérifier la disponibilité du jour exceptionnel");
        LOGGER.info("Récupération de la list des exceptions");
        List<String> listTypeException = pageRessourcesCalendrierCreer.recuperationListTypeException(wait, idCommune);
        for (String str : listTypeException){
            LOGGER.info(str);
        }
        LOGGER.info("Vérication si " + nomJourExceptionnel + " est présent dans la liste");
        assertion.verifyTrue(listTypeException.contains(nomJourExceptionnel),
                "");

        LOGGER.info("Pas de test 16 -- Créer une exception avec le jour exceptionnel - sélection du jour exceptionnel");
        String dateDeDebut = pageRessourcesCalendrierCreer.inputDateDeDebut(wait, idCommune).getAttribute("value");
        LOGGER.info("Renseigne la date de fin avec " + dateDeDebut + " et le type exception avec : " + nomJourExceptionnel);
        pageRessourcesCalendrierCreer.remplirDateDeFin(wait, idCommune, dateDeDebut);
        pageRessourcesCalendrierCreer.choisirTypeException(wait,idCommune,nomJourExceptionnel);
        seleniumTools.clickOnElement(wait, pageRessourcesCalendrierCreer.inputNom(wait, idCommune));
        Thread.sleep(500);
        LOGGER.info("Vérification des valeurs renseignées et celle de l'exception créée");
        assertion.verifyEquals(nombreEffortStandartHeure, pageRessourcesCalendrierCreer.inputEffortNormal(wait, idCommune).getAttribute("value"),
                "Le champ effort standart Heure n'a pas la valeur attendu");
        assertion.verifyEquals(nombreEffortStandartMinute, pageRessourcesCalendrierCreer.inputEffortNormalMinute(wait, idCommune).getAttribute("value"),
                "Le champ effort standart Minute n'a pas la valeur attendu");
        assertion.verifyEquals(nombreEffortSupplementaireHeure, pageRessourcesCalendrierCreer.inputEffortHeuresSupplementaires(wait, idCommune).getAttribute("value"),
                "Le champ effort Supplémentaire Heure n'a pas la valeur attendu");
        assertion.verifyEquals(nombreEffortSupplementaireMinute, pageRessourcesCalendrierCreer.inputEffortHeuresSupplementairesMinute(wait, idCommune).getAttribute("value"),
                "Le champ effort Supplémentaire minute n'a pas la valeur attendu");

        LOGGER.info("Pas de test 17 -- ");
        pageRessourcesCalendrierCreer.cliquerBoutonCreerException(wait, idCommune);
        LOGGER.info("Recuperation de l'exception créé");
        Thread.sleep(1000);
        Map<String, String> mapValeurException = pageRessourcesCalendrierCreer.recuperationValeurException(wait, idCommune);
        LOGGER.info("Verification de l'exception créé");
        assertion.verifyEquals(dateDuJour, mapValeurException.get("Jour"),
                "La date de creation de l'exception n'est pas celle attendu");
        assertion.verifyEquals(nomJourExceptionnel, mapValeurException.get("Type d'exception"),
                "Le type de l'exception n'est pas celui attendu");
        assertion.verifyEquals(nombreEffortStandartHeure + ":" + nombreEffortStandartMinute,
                mapValeurException.get("Effort normal"),
                "L'effort normal de l'exception n'est pas celui attendu");
        assertion.verifyEquals(nombreEffortSupplementaireHeure + ":" + nombreEffortSupplementaireMinute,
                mapValeurException.get("Effort supplémentaire"),
                "L'effort supplémentaire de l'exception n'est pas celui attendu");
        assertion.verifyEquals("", mapValeurException.get("Code"),
                "Le code d'exception n'est pas vide");
        assertion.verifyEquals("Direct", mapValeurException.get("Origine"),
                "L'origine de l'exception n'est pas vide");
        LOGGER.info("Recupération du tableau propriétés des jours");
        Map<String, String> mapValeurProprieteJour = pageRessourcesCalendrierCreer.recuperationValeurProprieteJours(idCommune);
        LOGGER.info("Verification du tableau propriétés des jours");
        assertion.verifyEquals(dateDeDebut, mapValeurProprieteJour.get("Jour"),
                "Le jour du tableau des propriétés des jours n'est pas celui attendu");
        assertion.verifyEquals("Exception: " + nomJourExceptionnel, mapValeurProprieteJour.get("Type"),
                "Le Type du tableau des propriétés des jours n'est pas celui attendu");
        assertion.verifyEquals(nombreEffortStandartHeure + ":" + nombreEffortStandartMinute,
                mapValeurProprieteJour.get("Temps travaillé"),
                "Le temps travaillé du tableau des propriétés des jours n'est pas celui attendu");

        LOGGER.info("FIN DU TEST");

    }

}
