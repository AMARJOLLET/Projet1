package fr.eql.libreplan.selenium.avancement;

import fr.eql.libreplan.pageObject.PageCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.avancement.PageRessourcesAvancement;
import fr.eql.libreplan.pageObject.pageRessources.avancement.PageRessourcesAvancementCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AVA01_CreationTypeAvancement extends AbstractTestSelenium {
    // Chargement JDD
    protected String className = getClass().getSimpleName();
    protected String classPackage = this.getClass().getPackage().getName();
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

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

        PageCalendrier pageCalendrier = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la page de gestion des types d'avancement");
        PageRessourcesAvancement pageRessourcesAvancement = pageCalendrier.cliquerRessourcesAvancement(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Types d'avancement Liste", pageRessourcesAvancement.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Recuperation des libellés du tableau");
        List<String> listLibelleTableauAvancement = pageRessourcesAvancement.recuperationLibelleTableau(idCommune);
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
        pageRessourcesAvancement.verificationNettoyageTableau(wait, idCommune, nomUnite, nomUniteSansPourcentage);
        PageRessourcesAvancementCreer pageRessourcesAvancementCreer = pageRessourcesAvancement.cliquerBoutonCreer(wait, idCommune);
        LOGGER.info("Vérification de la page");
        assertion.verifyEquals("Créer Type d'avancement", pageRessourcesAvancementCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Vérification des libellés du formulaire");
        assertion.verifyEquals("Nom d'unité", pageRessourcesAvancementCreer.libelleNomUnite(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Actif", pageRessourcesAvancementCreer.libelleActif(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Valeur maximum par défaut", pageRessourcesAvancementCreer.libelleValeurMax(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Précision", pageRessourcesAvancementCreer.libellePrecision(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Type", pageRessourcesAvancementCreer.libelleType(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Pourcentage", pageRessourcesAvancementCreer.libellePourcentage(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        LOGGER.info("Vérification des input du formulaire");
        assertion.verifyEquals("", pageRessourcesAvancementCreer.inputNomUnite(wait, idCommune).getAttribute("value"),
                "Le champ nom d'unité n'est pas vide");
        assertion.verifyTrue(pageRessourcesAvancementCreer.checkboxActif(wait, idCommune).isSelected(),
                "La checkbox Actif n'est pas selectionné");
        assertion.verifyEquals("100,00", pageRessourcesAvancementCreer.inputValeurMax(wait, idCommune).getAttribute("value"),
                "Le champ Valeur maximum par défaut n'est pas celui attendu");
        assertion.verifyEquals("0,1000", pageRessourcesAvancementCreer.inputPrecision(wait, idCommune).getAttribute("value"),
                "Le champ Precision n'est pas celui attendu");
        assertion.verifyEquals("User", pageRessourcesAvancementCreer.inputType(wait, idCommune).getText(),
                "Le champ type n'est pas égal à User");
        assertion.verifyFalse(pageRessourcesAvancementCreer.checkboxPourcentage(wait, idCommune).isSelected(),
                "La checkbox pourcentage est selectionné");
        LOGGER.info("Vérification des Boutons");
        assertion.verifyEquals("Annuler",pageRessourcesAvancementCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le bouton Annuler n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer",pageRessourcesAvancementCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le bouton Enregistrer n'est pas celui attendu");
        assertion.verifyEquals("Sauver et continuer",pageRessourcesAvancementCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                "Le bouton Enregistrer Et Continuer n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Créer un type d'avancement - sans pourcentage ");
        valeurMax = valeurMax.replace(".",",");
        precision = precision.replace(".", ",");
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesAvancementCreer.remplirFormulaire(wait, idCommune, nomUnite, actif, valeurMax, precision, pourcentage);
        LOGGER.info("Formulaire rempli");
        LOGGER.info("Enregistrement du formulaire");
        pageRessourcesAvancement = pageRessourcesAvancementCreer.cliquerBoutonEnregistrer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du message de création");
        assertion.verifyEquals("Types d'avancement Liste", pageRessourcesAvancement.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Type d'avancement \""+nomUnite+"\" enregistré", pageRessourcesAvancement.messageCreation(wait, nomUnite),
                "Le message de création n'est pas celui attendu");
        LOGGER.info("Vérification des valeurs du tableau");
        assertion.verifyEquals(nomUnite, pageRessourcesAvancement.nomTableau(wait, nomUnite).getText(),
                "La valeur du champ nom n'est pas celui attendu");
        assertion.verifyFalse(pageRessourcesAvancement.activeTableau(wait, nomUnite).isSelected(),
                "La checkbox activé est coché pour : " + nomUnite);
        assertion.verifyFalse(pageRessourcesAvancement.predefiniTableau(wait, nomUnite).isSelected(),
                "La checkbox Prédéfini est coché pour : " + nomUnite);
        assertion.verifyTrue(pageRessourcesAvancement.modifierTableau(wait, nomUnite).isDisplayed(),
                "Le bouton modifier n'est pas présent pour : " + nomUnite);
        assertion.verifyTrue(pageRessourcesAvancement.supprimerTableau(wait, nomUnite).isDisplayed(),
                "Le bouton supprimé n'est pas présent pour : " + nomUnite);

        LOGGER.info("Pas de test 5 -- Créer un type d'avancement - Accès au formulaire de création");
        pageRessourcesAvancementCreer = pageRessourcesAvancement.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer Type d'avancement", pageRessourcesAvancementCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 6 -- Créer un type d'avancement - sans pourcentage (1/2)");
        pageRessourcesAvancementCreer.remplirFormulaire(wait, idCommune,
                nomUniteSansPourcentage, actifSansPourcentage, valeurMaxSansPourcentage, precisionSansPourcentage, pourcentageSansPourcentage);
        Thread.sleep(500);
        assertion.verifyEquals("true",pageRessourcesAvancementCreer.inputValeurMax(wait, idCommune).getAttribute("disabled"),
                "Le champ valeur maximun n'est pas désactivé");

        LOGGER.info("Pas de test 7 -- Créer un type d'avancement - sans pourcentage (2/2)");
        pageRessourcesAvancementCreer.cliquerBoutonEnregistrerEtContinuer(wait, idCommune);
        LOGGER.info("Vérification du message de création et du titre de la page");
        assertion.verifyEquals("Type d'avancement \""+nomUniteSansPourcentage+"\" enregistré", pageRessourcesAvancementCreer.messageCreation(wait, nomUniteSansPourcentage),
                "Le message de création n'est pas celui attendu");
        assertion.verifyEquals("Modifier Type d'avancement: "+nomUniteSansPourcentage, pageRessourcesAvancementCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 8 -- Retour à la page de gestion des types d'avancement");
        pageRessourcesAvancement = pageRessourcesAvancementCreer.cliquerBoutonAnnuler(wait, idCommune);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Types d'avancement Liste", pageRessourcesAvancement.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 9 -- Conformité du type d'avancement ajouté");
        LOGGER.info("Vérification des valeurs du tableau");
        assertion.verifyEquals(nomUniteSansPourcentage, pageRessourcesAvancement.nomTableau(wait, nomUniteSansPourcentage).getText(),
                "La valeur du champ nom n'est pas celui attendu");
        assertion.verifyTrue(pageRessourcesAvancement.activeTableau(wait, nomUniteSansPourcentage).isSelected(),
                "La checkbox activé n'est pas coché pour : " + nomUniteSansPourcentage);
        assertion.verifyFalse(pageRessourcesAvancement.predefiniTableau(wait, nomUniteSansPourcentage).isSelected(),
                "La checkbox Prédéfini est coché pour : " + nomUniteSansPourcentage);
        assertion.verifyTrue(pageRessourcesAvancement.modifierTableau(wait, nomUniteSansPourcentage).isDisplayed(),
                "Le bouton modifier n'est pas présent pour : " + nomUniteSansPourcentage);
        assertion.verifyTrue(pageRessourcesAvancement.supprimerTableau(wait, nomUniteSansPourcentage).isDisplayed(),
                "Le bouton supprimé n'est pas présent pour : " + nomUniteSansPourcentage);
    }
}
