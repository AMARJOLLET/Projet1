package fr.eql.libreplan.selenium.criteres;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.criteres.PageRessourcesCriteres;
import fr.eql.libreplan.pageObject.pageRessources.criteres.PageRessourcesCriteresCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CRI01_AdministrationDesCriteres extends AbstractTestSelenium {
    // QuerySQL
    protected String queryNettoyage = "select name from criterion_type where name in('??','??','??','??');";

    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    // Connexion
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDD annuler
    protected String nomAnnuler = listJdd.get(1).get("nom");
    protected String typeAnnuler = listJdd.get(1).get("type");
    protected Boolean valeursMultiplesParRessourceAnnuler = Boolean.valueOf(listJdd.get(1).get("valeursMultiplesParRessource"));
    protected Boolean hierarchieAnnuler = Boolean.valueOf(listJdd.get(1).get("hierarchie"));
    protected Boolean activeAnnuler = Boolean.valueOf(listJdd.get(1).get("active"));
    protected String descriptionAnnuler = listJdd.get(1).get("descriptionAnnuler");

    // JDD Enregistrer
    protected String nomEnregistrer = listJdd.get(2).get("nom");
    protected String typeEnregistrer = listJdd.get(2).get("type");
    protected Boolean valeursMultiplesParRessourceEnregistrer = Boolean.valueOf(listJdd.get(2).get("valeursMultiplesParRessource"));
    protected Boolean hierarchieEnregistrer = Boolean.valueOf(listJdd.get(2).get("hierarchie"));
    protected Boolean activeEnregistrer = Boolean.valueOf(listJdd.get(2).get("active"));
    protected String descriptionEnregistrer = listJdd.get(2).get("descriptionAnnuler");

    // JDD Continuer
    protected String nomContinuer = listJdd.get(3).get("nom");
    protected String typeContinuer = listJdd.get(3).get("type");
    protected Boolean valeursMultiplesParRessourceContinuer = Boolean.valueOf(listJdd.get(3).get("valeursMultiplesParRessource"));
    protected Boolean hierarchieContinuer = Boolean.valueOf(listJdd.get(3).get("hierarchie"));
    protected Boolean activeContinuer = Boolean.valueOf(listJdd.get(3).get("active"));
    protected String descriptionContinuer = listJdd.get(3).get("descriptionAnnuler");

    // JDD Modification
    protected String nomModifier = nomContinuer + " 2";


    public CRI01_AdministrationDesCriteres() throws IOException {
    }

    // JDD enregistrer

    @Test
    void run() throws Throwable {
        LOGGER.info("Acc??s ?? la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);


        LOGGER.info("Pas de test 2 -- Acc??der ?? la page d'administration des crit??res");
        PageRessourcesCriteres pageRessourcesCriteres = pageCalendrierPlanification.cliquerRessourcesCriteres(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("V??rification du titre de la page d'administration des crit??res");
        assertion.verifyEquals("Types de crit??res Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("V??rification des colonnes du tableau");
        List<String> listLibelleTableau = pageRessourcesCriteres.recuperationListLibelleTableau(wait);
        assertion.verifyEquals("Nom", listLibelleTableau.get(0),
                "La colonne Nom n'est pas pr??sent");
        assertion.verifyEquals("Code", listLibelleTableau.get(1),
                "La colonne Code n'est pas pr??sent");
        assertion.verifyEquals("Type", listLibelleTableau.get(2),
                "La colonne Type n'est pas pr??sent");
        assertion.verifyEquals("Activ??", listLibelleTableau.get(3),
                "La colonne Activit?? n'est pas pr??sent");
        assertion.verifyEquals("Op??rations", listLibelleTableau.get(4),
                "La colonne Op??ration n'est pas pr??sent");


        LOGGER.info("Pas de test 3 -- Cr??er un crit??re - Acc??s au formulaire de cr??ation");

        LOGGER.info("Nettoyage si n??cessaire");
        queryNettoyage = outilsManipulationDonnee.formatageQuery(queryNettoyage, nomAnnuler, nomEnregistrer, nomContinuer, nomModifier);
        LOGGER.info("Query setup " + queryNettoyage);
        outilsProjet.verificationNettoyageTableau(wait, connection, queryNettoyage);
        LOGGER.info("Reprise du cas de test");

        PageRessourcesCriteresCreer pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonCreer(wait, idCommune);
        LOGGER.info("Acc??s ?? la page de cr??atiopn");
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("V??rification de la page -- Titre page - Formulaire - Bouton ");
        assertion.verifyEquals("Cr??er Type de crit??re", pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Modifier", pageRessourcesCriteresCreer.titreFormulaire(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas : Modifier");
        assertion.verifyEquals("Enregistrer", pageRessourcesCriteresCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le texte du bouton Enregistrer n'est pas correct");
        assertion.verifyEquals("Sauver et continuer", pageRessourcesCriteresCreer.boutonSauverEtContinuer(wait, idCommune).getText(),
                "Le texte du bouton Sauver et continuer n'est pas correct");
        assertion.verifyEquals("Annuler", pageRessourcesCriteresCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le texte du bouton Annuler n'est pas correct");


        LOGGER.info("Pas de test 4 -- Cr??er un crit??re - bouton [Annuler]");
        LOGGER.info("R??cup??ration des libell??s du formulaire");
        List<String> listLibelleFormulaire = pageRessourcesCriteresCreer.recuperationLibelleFormulaire(wait);

        LOGGER.info("V??rification des libell??s du formulaire");
        assertion.verifyEquals("Nom", listLibelleFormulaire.get(0),
                "Le libell?? du formulaire est incorrect");
        assertion.verifyEquals("Type", listLibelleFormulaire.get(1),
                "Le libell?? du formulaire est incorrect");
        assertion.verifyEquals("Valeurs multiples par ressource", listLibelleFormulaire.get(2),
                "Le libell?? du formulaire est incorrect");
        assertion.verifyEquals("Hi??rarchie", listLibelleFormulaire.get(3),
                "Le libell?? du formulaire est incorrect");
        assertion.verifyEquals("Activ??", listLibelleFormulaire.get(4),
                "Le libell?? du formulaire est incorrect");
        assertion.verifyEquals("Description", listLibelleFormulaire.get(5),
                "Le libell?? du formulaire est incorrect");
        assertion.verifyEquals("Code", listLibelleFormulaire.get(6),
                "Le libell?? du formulaire est incorrect");

        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCriteresCreer.remplirFormulaire(wait, idCommune, nomAnnuler, typeAnnuler,
                valeursMultiplesParRessourceAnnuler, hierarchieAnnuler, activeAnnuler, descriptionAnnuler);
        LOGGER.info("Formulaire rempli -- Annulation du formulaire");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);

        LOGGER.info("V??rification du titre de la page");
        assertion.verifyEquals("Types de crit??res Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("R??cup??ration du tableau");
        Map<String, Map<String, WebElement>> listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(wait);
        LOGGER.info("V??rification que le nom n'est pas pr??sent dans le tableau");
        assertion.verifyTrue(!listValeurParCritere.containsKey(nomAnnuler),
                nomAnnuler + " est dans le tableau");


        LOGGER.info("Pas de test 5 -- Cr??er un crit??re - bouton [Enregistrer]");

        LOGGER.info("Acc??s ?? la page de cr??ation");
        pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Acc??s ?? la page de cr??ation");

        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCriteresCreer.remplirFormulaire(wait, idCommune, nomEnregistrer, typeEnregistrer,
                valeursMultiplesParRessourceEnregistrer, hierarchieEnregistrer, activeEnregistrer, descriptionEnregistrer);
        LOGGER.info("Formulaire rempli -- Enregistrement du formulaire");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonEnregistrer(wait, idCommune);

        LOGGER.info("V??rification du titre de la page");
        assertion.verifyEquals("Types de crit??res Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("R??cup??ration des valeurs du talbeau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(wait);
        LOGGER.info("V??rification que le nom est pr??sent dans le tableau");
        assertion.verifyTrue(listValeurParCritere.containsKey(nomEnregistrer),
                nomEnregistrer + " n'est pas dans le tableau");


        LOGGER.info("Pas de test 6 -- Cr??er un crit??re - Acc??s au formulaire de cr??ation  -- Doublon");


        LOGGER.info("Pas de test 7 -- Cr??er un crit??re - bouton [Sauver et continuer]");
        LOGGER.info("Acc??s ?? la page de cr??ation");
        pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCriteresCreer.remplirFormulaire(wait, idCommune, nomContinuer, typeContinuer,
                valeursMultiplesParRessourceContinuer, hierarchieContinuer, activeContinuer, descriptionContinuer);
        LOGGER.info("Formulaire rempli -- Clique sur Sauver Et Continuer");
        pageRessourcesCriteresCreer.cliquerBoutonSauverEtContinuer(wait, idCommune);

        LOGGER.info("V??rification du titre de la page");
        pageRessourcesCriteresCreer.messageCreation(wait, nomContinuer);
        assertion.verifyEquals("Modifier Type de crit??re: " + nomContinuer,
                pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");


        LOGGER.info("Pas de test 8 -- Retour page d'administration des crit??res");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);
        LOGGER.info("V??rification du titre de la page");
        assertion.verifyEquals("Types de crit??res Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("R??cup??ration des valeurs du talbeau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(wait);
        LOGGER.info("V??rification que le nom est pr??sent dans le tableau");
        assertion.verifyTrue(listValeurParCritere.containsKey(nomContinuer),
                nomContinuer + " n'est pas dans le tableau");


        LOGGER.info("Pas de test 9 -- Modifier un crit??re - acc??s formulaire de modification - Colonne Op??ration");
        pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonModifier(wait, nomContinuer);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("V??rification du titre de la page");
        pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune);
        assertion.verifyEquals("Modifier Type de crit??re: " + nomContinuer,
                pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");


        LOGGER.info("Pas de test 10 -- Modifier un crit??re -  Bouton [Annuler]");
        LOGGER.info("Modification du nom");
        pageRessourcesCriteresCreer.remplirNomFormulaire(wait, idCommune, nomModifier);
        LOGGER.info("Retour ?? la page des crit??res en annulant");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);
        LOGGER.info("R??cup??ration des valeurs du tableau");
        LOGGER.info("V??rification du titre de la page");
        assertion.verifyEquals("Types de crit??res Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(wait);
        LOGGER.info("V??rification de l'absence de la modification dans le nom");
        assertion.verifyTrue(!listValeurParCritere.containsKey(nomModifier),
                nomModifier + " est dans le tableau");


        LOGGER.info("Pas de test 11 -- Modifier un crit??re - acc??s formulaire de modification - Colonne Nom");
        pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerNomTableau(wait, nomContinuer);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("V??rification du titre de la page");
        assertion.verifyEquals("Modifier Type de crit??re: " + nomContinuer,
                pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");


        LOGGER.info("Pas de test 12 -- Modifier un crit??re - modification du nom");
        String titrePageAvantModification = pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune).getText();
        pageRessourcesCriteresCreer.remplirNomFormulaire(wait, idCommune, nomModifier);
        // Clique sur un element pour actualiser le titre
        seleniumTools.clickOnElement(wait, pageRessourcesCriteresCreer.inputDescription(wait, idCommune));
        LOGGER.info("V??rification du titre modifi?? de la page");
        assertion.verifyTrue(pageRessourcesCriteresCreer.titreDeLaPageApresModification(wait, nomModifier),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Modifier Type de crit??re: " + nomModifier,
                pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 13 -- Modifier un crit??re - bouton [Sauver et continuer]");
        pageRessourcesCriteresCreer.cliquerBoutonSauverEtContinuer(wait, idCommune);
        LOGGER.info("V??rification du titre de la page");
        assertion.verifyEquals("Modifier Type de crit??re: " + nomModifier,
                pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("V??rification du message de cr??ation");
        assertion.verifyEquals("Type de crit??re \"" + nomModifier + "\" enregistr??",
                pageRessourcesCriteresCreer.messageCreation(wait, nomModifier).getText(),
                "Le message de cr??ation n'est pas celui attendu");


        LOGGER.info("Pas de test 14 -- Retour page d'administration des crit??res");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("V??rification du titre de la page");
        assertion.verifyEquals("Types de crit??res Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("R??cup??ration des valeurs du tableau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(wait);
        LOGGER.info("V??rification de l'absence du nom modifi?? dans le tableau");
        assertion.verifyTrue(listValeurParCritere.containsKey(nomModifier),
                nomModifier + " n'est pas dans le tableau");


        LOGGER.info("Pas de test 15 -- Supprimer un crit??re - Pop-up de confirmation");
        seleniumTools.clickOnElement(wait, pageRessourcesCriteres.boutonSupprimer(wait, nomModifier));
        LOGGER.info("V??rification du texte de la popup de suppression");
        assertion.verifyEquals("Supprimer Type de crit??re \"" + nomModifier + "\". ??tes-vous s??r ?",
                pageRessourcesCriteres.textPopupSuppression(wait).getText(),
                "Le texte suppresion n'est pas celui attendu");
        assertion.verifyTrue(pageRessourcesCriteres.boutonOKPopupSuppression(wait).isDisplayed(),
                "Le bouton OK n'est pas affich??");
        assertion.verifyTrue(pageRessourcesCriteres.boutonAnnulerPopupSuppression(wait).isDisplayed(),
                "Le bouton Annuler n'est pas affich??");


        LOGGER.info("Pas de test 16 -- Supprimer un crit??re - Bouton [Annuler]");
        seleniumTools.clickOnElement(wait, pageRessourcesCriteres.boutonAnnulerPopupSuppression(wait));
        LOGGER.info("R??cup??ration des valeurs du tableau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(wait);
        LOGGER.info("V??rification que la suppression n'a pas eu lieu");
        assertion.verifyTrue(listValeurParCritere.containsKey(nomModifier),
                nomModifier + " n'est pas dans le tableau");


        LOGGER.info("Pas de test 17 -- Supprimer un crit??re - Pop-up de confirmation");
        seleniumTools.clickOnElement(wait, pageRessourcesCriteres.boutonSupprimer(wait, nomModifier));
        LOGGER.info("V??rification du texte de la popup de suppression");
        assertion.verifyEquals("Supprimer Type de crit??re \"" + nomModifier + "\". ??tes-vous s??r ?",
                pageRessourcesCriteres.textPopupSuppression(wait).getText(),
                "Le texte suppresion n'est pas celui attendu");
        assertion.verifyTrue(pageRessourcesCriteres.boutonOKPopupSuppression(wait).isDisplayed(),
                "Le bouton OK n'est pas affich??");
        assertion.verifyTrue(pageRessourcesCriteres.boutonAnnulerPopupSuppression(wait).isDisplayed(),
                "Le bouton Annuler n'est pas affich??");


        LOGGER.info("Pas de test 18 -- Supprimer un crit??re - Bouton [OK]");
        seleniumTools.clickOnElement(wait, pageRessourcesCriteres.boutonOKPopupSuppression(wait));
        assertion.verifyEquals("Type de crit??re \""+nomModifier+"\" supprim??",
                pageRessourcesCriteres.messageSuppression(wait, nomModifier).getText(),
                "Le message de suppression n'est pas celui attendu");
        LOGGER.info("R??cup??ration des valeurs du tableau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(wait);
        LOGGER.info("V??rification que la suppression a eu lieu");
        assertion.verifyTrue(!listValeurParCritere.containsKey(nomModifier),
                nomModifier + " est dans le tableau");


        LOGGER.info("FIN DU TEST");
    }
}
