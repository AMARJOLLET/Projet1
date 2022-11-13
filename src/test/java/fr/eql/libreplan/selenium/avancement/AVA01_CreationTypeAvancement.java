package fr.eql.libreplan.selenium.avancement;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.avancement.PageRessourcesAvancement;
import fr.eql.libreplan.pageObject.pageRessources.avancement.PageRessourcesAvancementCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AVA01_CreationTypeAvancement extends AbstractTestSelenium {
    // QuerySQL
    protected String queryNettoyage = "select unit_name from advance_type where unit_name in ('??','??');";

    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    // Connexion
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDD Avancement
    protected String nomUnite = listJdd.get(1).get("Nom d'unité");
    protected boolean actif = Boolean.parseBoolean(listJdd.get(1).get("Actif"));
    protected String valeurMax = listJdd.get(1).get("Valeur maximum par défaut").replace(".", ",");
    protected String precision = listJdd.get(1).get("Précision").replace(".", ",");
    protected String type = listJdd.get(1).get("Type");
    protected boolean pourcentage = Boolean.parseBoolean(listJdd.get(1).get("Pourcentage"));

    // JDD Avancement sans pourcentage
    protected String nomUniteSansPourcentage = listJdd.get(2).get("Nom d'unité");
    protected boolean actifSansPourcentage = Boolean.parseBoolean(listJdd.get(2).get("Actif"));
    protected String valeurMaxSansPourcentage = listJdd.get(2).get("Valeur maximum par défaut").replace(".", ",");
    protected String precisionSansPourcentage = listJdd.get(2).get("Précision").replace(".", ",");
    protected String typeSansPourcentage = listJdd.get(2).get("Type");
    protected boolean pourcentageSansPourcentage = Boolean.parseBoolean(listJdd.get(2).get("Pourcentage"));


    public AVA01_CreationTypeAvancement() throws IOException {
    }

    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);


        LOGGER.info("Pas de test 2 -- Accéder à la page de gestion des types d'avancement");
        PageRessourcesAvancement pageRessourcesAvancement = pageCalendrierPlanification.cliquerRessourcesAvancement(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Types d'avancement Liste", pageRessourcesAvancement.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Recuperation des libellés du tableau");
        List<String> listLibelleTableauAvancement = pageRessourcesAvancement.recuperationLibelleTableau(wait);
        assertion.verifyEquals("Nom", listLibelleTableauAvancement.get(0),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Activé", listLibelleTableauAvancement.get(1),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Prédéfini", listLibelleTableauAvancement.get(2),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Opérations", listLibelleTableauAvancement.get(3),
                "Le libellé n'est pas celui attendu");


        LOGGER.info("Pas de test 3 -- Créer un type d'avancement - Accès au formulaire de création");

        LOGGER.info("Nettoyage si nécessaire");
        queryNettoyage = outilsManipulationDonnee.formatageQuery(queryNettoyage, nomUnite, nomUniteSansPourcentage);
        LOGGER.info("Query setup " + queryNettoyage);
        outilsProjet.verificationNettoyageTableau(wait, connection, queryNettoyage);
        LOGGER.info("Reprise du cas de test");

        PageRessourcesAvancementCreer pageRessourcesAvancementCreer = pageRessourcesAvancement.cliquerBoutonCreer(wait, idCommune);
        LOGGER.info("Vérification de la page");
        assertion.verifyEquals("Créer Type d'avancement", pageRessourcesAvancementCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        Map<String, WebElement> mapTableauCreationAvancement = pageRessourcesAvancementCreer.recuperationFormulaireCreationAvancement(wait);
        LOGGER.info("Vérification des libellés du formulaire");
        assertion.verifyTrue(mapTableauCreationAvancement.containsKey("Nom d'unité"),
                "Le libellé Nom d'unité n'est pas retrouvé dans le tableau");
        assertion.verifyTrue(mapTableauCreationAvancement.containsKey("Actif"),
                "Le libellé Actif n'est pas retrouvé dans le tableau");
        assertion.verifyTrue(mapTableauCreationAvancement.containsKey("Valeur maximum par défaut"),
                "Le libellé Valeur maximum par défaut n'est pas retrouvé dans le tableau");
        assertion.verifyTrue(mapTableauCreationAvancement.containsKey("Précision"),
                "Le libellé Précision n'est pas retrouvé dans le tableau");
        assertion.verifyTrue(mapTableauCreationAvancement.containsKey("Type"),
                "Le libellé Type n'est pas retrouvé dans le tableau");
        assertion.verifyTrue(mapTableauCreationAvancement.containsKey("Pourcentage"),
                "Le libellé Pourcentage n'est pas retrouvé dans le tableau");

        LOGGER.info("Vérification des input du formulaire");
        assertion.verifyEquals("", mapTableauCreationAvancement.get("Nom d'unité").getAttribute("value"),
                "Le champ nom d'unité n'est pas vide");
        assertion.verifyTrue(mapTableauCreationAvancement.get("Actif").isSelected(),
                "La checkbox Actif n'est pas selectionné");
        assertion.verifyEquals("100,00", mapTableauCreationAvancement.get("Valeur maximum par défaut").getAttribute("value"),
                "Le champ Valeur maximum par défaut n'est pas celui attendu");
        assertion.verifyEquals("0,1000", mapTableauCreationAvancement.get("Précision").getAttribute("value"),
                "Le champ Precision n'est pas celui attendu");
        assertion.verifyEquals("User", mapTableauCreationAvancement.get("Type").getText(),
                "Le champ type n'est pas égal à User");
        assertion.verifyFalse(mapTableauCreationAvancement.get("Pourcentage").isSelected(),
                "La checkbox pourcentage est selectionné");

        LOGGER.info("Vérification des Boutons");
        assertion.verifyEquals("Annuler",pageRessourcesAvancementCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le bouton Annuler n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer",pageRessourcesAvancementCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le bouton Enregistrer n'est pas celui attendu");
        assertion.verifyEquals("Sauver et continuer",pageRessourcesAvancementCreer.boutonSauverEtContinuer(wait, idCommune).getText(),
                "Le bouton Enregistrer Et Continuer n'est pas celui attendu");


        LOGGER.info("Pas de test 4 -- Créer un type d'avancement - sans pourcentage ");
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesAvancementCreer.remplirFormulaire(wait, idCommune, nomUnite, actif, valeurMax, precision, pourcentage);
        LOGGER.info("Formulaire rempli");
        LOGGER.info("Enregistrement du formulaire");
        pageRessourcesAvancement = pageRessourcesAvancementCreer.cliquerBoutonEnregistrer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du message de création");
        assertion.verifyEquals("Types d'avancement Liste", pageRessourcesAvancement.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Type d'avancement \""+nomUnite+"\" enregistré", pageRessourcesAvancement.messageCreation(wait, nomUnite).getText(),
                "Le message de création n'est pas celui attendu");
        LOGGER.info("Vérification des valeurs du tableau");
        Map<String, WebElement> mapValeurCreerTableauAvancement = pageRessourcesAvancement.recuperationValeurTableauAvancement(wait).get(nomUnite);
        assertion.verifyEquals(nomUnite, mapValeurCreerTableauAvancement.get("Nom").getText(),
                "La valeur du champ nom n'est pas celui attendu");
        assertion.verifyFalse(mapValeurCreerTableauAvancement.get("Activé").isSelected(),
                "La checkbox activé est coché pour : " + nomUnite);
        assertion.verifyFalse(mapValeurCreerTableauAvancement.get("Prédéfini").isSelected(),
                "La checkbox Prédéfini est coché pour : " + nomUnite);
        assertion.verifyTrue(pageRessourcesAvancement.boutonModifierTableau(wait, nomUnite).isDisplayed(),
                "Le bouton modifier n'est pas présent pour : " + nomUnite);
        assertion.verifyTrue(pageRessourcesAvancement.boutonSupprimerTableau(wait, nomUnite).isDisplayed(),
                "Le bouton supprimé n'est pas présent pour : " + nomUnite);

        LOGGER.info("Pas de test 5 -- Créer un type d'avancement - Accès au formulaire de création");
        pageRessourcesAvancementCreer = pageRessourcesAvancement.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer Type d'avancement", pageRessourcesAvancementCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 6 -- Créer un type d'avancement - sans pourcentage (1/2)");
        pageRessourcesAvancementCreer.remplirFormulaire(wait, idCommune,
                nomUniteSansPourcentage, actifSansPourcentage, valeurMaxSansPourcentage, precisionSansPourcentage, pourcentageSansPourcentage);
        Thread.sleep(500);
        assertion.verifyTrue(pageRessourcesAvancementCreer.inputValeurMaxDisabled(wait),
                "Le champ valeur maximun n'est pas désactivé");

        LOGGER.info("Pas de test 7 -- Créer un type d'avancement - sans pourcentage (2/2)");
        pageRessourcesAvancementCreer.cliquerBoutonSauverEtContinuer(wait, idCommune);
        LOGGER.info("Vérification du message de création et du titre de la page");
        assertion.verifyEquals("Type d'avancement \""+nomUniteSansPourcentage+"\" enregistré",
                pageRessourcesAvancementCreer.messageCreation(wait, nomUniteSansPourcentage).getText(),
                "Le message de création n'est pas celui attendu");
        assertion.verifyEquals("Modifier Type d'avancement: "+nomUniteSansPourcentage,
                pageRessourcesAvancementCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 8 -- Retour à la page de gestion des types d'avancement");
        pageRessourcesAvancement = pageRessourcesAvancementCreer.cliquerBoutonAnnuler(wait, idCommune);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Types d'avancement Liste", pageRessourcesAvancement.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 9 -- Conformité du type d'avancement ajouté");
        LOGGER.info("Vérification des valeurs du tableau");
        mapValeurCreerTableauAvancement = pageRessourcesAvancement.recuperationValeurTableauAvancement(wait).get(nomUniteSansPourcentage);
        assertion.verifyEquals(nomUniteSansPourcentage, mapValeurCreerTableauAvancement.get("Nom").getText(),
                "La valeur du champ nom n'est pas celui attendu");
        assertion.verifyTrue(mapValeurCreerTableauAvancement.get("Activé").isSelected(),
                "La checkbox activé n'est pas coché pour : " + nomUniteSansPourcentage);
        assertion.verifyFalse(mapValeurCreerTableauAvancement.get("Prédéfini").isSelected(),
                "La checkbox Prédéfini est coché pour : " + nomUniteSansPourcentage);
        assertion.verifyTrue(pageRessourcesAvancement.boutonModifierTableau(wait, nomUniteSansPourcentage).isDisplayed(),
                "Le bouton modifier n'est pas présent pour : " + nomUniteSansPourcentage);
        assertion.verifyTrue(pageRessourcesAvancement.boutonSupprimerTableau(wait, nomUniteSansPourcentage).isDisplayed(),
                "Le bouton supprimé n'est pas présent pour : " + nomUniteSansPourcentage);
    }
}
