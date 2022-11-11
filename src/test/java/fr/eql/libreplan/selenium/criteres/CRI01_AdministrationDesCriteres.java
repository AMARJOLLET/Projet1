package fr.eql.libreplan.selenium.criteres;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.criteres.PageRessourcesCriteres;
import fr.eql.libreplan.pageObject.pageRessources.criteres.PageRessourcesCriteresCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CRI01_AdministrationDesCriteres extends AbstractTestSelenium {
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
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la page d'administration des critères");
        PageRessourcesCriteres pageRessourcesCriteres = pageCalendrierPlanification.cliquerRessourcesCriteres(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page d'administration des critères");
        assertion.verifyEquals("Types de critères Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Vérification des colonnes du tableau");
        assertion.verifyEquals("Nom", pageRessourcesCriteres.recuperationEnTeteTableau(idCommune).get(0),
                "La colonne Nom n'est pas présent");
        assertion.verifyEquals("Code", pageRessourcesCriteres.recuperationEnTeteTableau(idCommune).get(1),
                "La colonne Code n'est pas présent");
        assertion.verifyEquals("Type", pageRessourcesCriteres.recuperationEnTeteTableau(idCommune).get(2),
                "La colonne Type n'est pas présent");
        assertion.verifyEquals("Activé", pageRessourcesCriteres.recuperationEnTeteTableau(idCommune).get(3),
                "La colonne Activité n'est pas présent");
        assertion.verifyEquals("Opérations", pageRessourcesCriteres.recuperationEnTeteTableau(idCommune).get(4),
                "La colonne Opération n'est pas présent");

        LOGGER.info("Pas de test 3 -- Créer un critère - Accès au formulaire de création");
        pageRessourcesCriteres.verificationNettoyageTableau(wait, idCommune,
                nomAnnuler,nomEnregistrer,nomContinuer,nomModifier);
        LOGGER.info("Elements trouvés -- Suppression ok");
        PageRessourcesCriteresCreer pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonCreer(wait, idCommune);
        LOGGER.info("Accès à la page de créatiopn");
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification de la page -- Titre page - Formulaire - Bouton ");
        assertion.verifyEquals("Créer Type de critère", pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Modifier", pageRessourcesCriteresCreer.titreFormulaire(wait, idCommune),
                "Le titre du formulaire n'est pas : Modifier");
        assertion.verifyEquals("Enregistrer", pageRessourcesCriteresCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le texte du bouton Enregistrer n'est pas correct");
        assertion.verifyEquals("Sauver et continuer", pageRessourcesCriteresCreer.boutonSauverEtContinuer(wait, idCommune).getText(),
                "Le texte du bouton Sauver et continuer n'est pas correct");
        assertion.verifyEquals("Annuler", pageRessourcesCriteresCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le texte du bouton Annuler n'est pas correct");

        LOGGER.info("Pas de test 4 -- Créer un critère - bouton [Annuler]");
        LOGGER.info("Récupération des libellés du formulaire");
        List<String> listLibelleFormulaire = pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune);
        LOGGER.info("Vérification des libellés du formulaire");
        assertion.verifyEquals("Nom", listLibelleFormulaire.get(0),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Type", listLibelleFormulaire.get(1),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Valeurs multiples par ressource", listLibelleFormulaire.get(2),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Hiérarchie", listLibelleFormulaire.get(3),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Activé", listLibelleFormulaire.get(4),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Description", listLibelleFormulaire.get(5),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Code", listLibelleFormulaire.get(6),
                "Le libellé du formulaire est incorrect");
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCriteresCreer.remplirFormulaire(wait, idCommune, nomAnnuler, typeAnnuler,
                valeursMultiplesParRessourceAnnuler, hierarchieAnnuler, activeAnnuler, descriptionAnnuler);
        LOGGER.info("Formulaire rempli -- Annulation du formulaire");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Types de critères Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération du tableau");
        Map<String, Map<String, String>> listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(idCommune);
        LOGGER.info("Vérification que le nom n'est pas présent dans le tableau");
        assertion.verifyTrue(!listValeurParCritere.containsKey(nomAnnuler),
                nomAnnuler + " est dans le tableau");

        LOGGER.info("Pas de test 5 -- Créer un critère - bouton [Enregistrer]");
        LOGGER.info("Accès à la page de création");
        pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Accès à la page de création");
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCriteresCreer.remplirFormulaire(wait, idCommune, nomEnregistrer, typeEnregistrer,
                valeursMultiplesParRessourceEnregistrer, hierarchieEnregistrer, activeEnregistrer, descriptionEnregistrer);
        LOGGER.info("Formulaire rempli -- Enregistrement du formulaire");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonEnregistrer(wait, idCommune);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Types de critères Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du talbeau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(idCommune);
        LOGGER.info("Vérification que le nom est présent dans le tableau");
        assertion.verifyTrue(listValeurParCritere.containsKey(nomEnregistrer),
                nomEnregistrer + " n'est pas dans le tableau");
        LOGGER.info("Pas de test 6 -- Créer un critère - Accès au formulaire de création  -- Doublon");
        LOGGER.info("Pas de test 7 -- Créer un critère - bouton [Sauver et continuer]");
        LOGGER.info("Accès à la page de création");
        pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCriteresCreer.remplirFormulaire(wait, idCommune, nomContinuer, typeContinuer,
                valeursMultiplesParRessourceContinuer, hierarchieContinuer, activeContinuer, descriptionContinuer);
        LOGGER.info("Formulaire rempli -- Clique sur Sauver Et Continuer");
        pageRessourcesCriteresCreer.cliquerBoutonSauverEtContinuer(wait, idCommune);
        LOGGER.info("Vérification du titre de la page");
        pageRessourcesCriteresCreer.messageCreation(wait, nomContinuer);
        assertion.verifyEquals("Modifier Type de critère: " + nomContinuer, pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 8 -- Retour page d'administration des critères");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Types de critères Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du talbeau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(idCommune);
        LOGGER.info("Vérification que le nom est présent dans le tableau");
        assertion.verifyTrue(listValeurParCritere.containsKey(nomContinuer),
                nomContinuer + " n'est pas dans le tableau");

        LOGGER.info("Pas de test 9 -- Modifier un critère - accès formulaire de modification - Colonne Opération");
        pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonModifier(wait, nomContinuer);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune);
        assertion.verifyEquals("Modifier Type de critère: " + nomContinuer, pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 10 -- Modifier un critère -  Bouton [Annuler]");
        LOGGER.info("Modification du nom");
        pageRessourcesCriteresCreer.remplirNomFormulaire(wait, idCommune, nomModifier);
        LOGGER.info("Retour à la page des critères en annulant");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Récupération des valeurs du tableau");
        Thread.sleep(500);
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(idCommune);
        LOGGER.info("Vérification de l'absence de la modification dans le nom");
        assertion.verifyTrue(!listValeurParCritere.containsKey(nomModifier),
                nomModifier + " est dans le tableau");

        LOGGER.info("Pas de test 11 -- Modifier un critère - accès formulaire de modification - Colonne Nom");
        pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonModifierNom(wait, nomContinuer);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Modifier Type de critère: " + nomContinuer, pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 12 -- Modifier un critère - modification du nom");
        pageRessourcesCriteresCreer.remplirNomFormulaire(wait, idCommune, nomModifier);
        // Clique sur un element pour actualiser le titre
        seleniumTools.clickOnElement(wait, pageRessourcesCriteresCreer.inputDescription(wait, idCommune));
        Thread.sleep(1000);
        LOGGER.info("Vérification du titre modifié de la page");
        assertion.verifyEquals("Modifier Type de critère: " + nomModifier, pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 13 -- Modifier un critère - bouton [Sauver et continuer]");
        pageRessourcesCriteresCreer.cliquerBoutonSauverEtContinuer(wait, idCommune);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Modifier Type de critère: " + nomModifier, pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Vérification du message de création");
        assertion.verifyEquals("Type de critère \"" + nomModifier + "\" enregistré", pageRessourcesCriteresCreer.messageCreation(wait, nomModifier),
                "Le message de création n'est pas celui attendu");

        LOGGER.info("Pas de test 14 -- Retour page d'administration des critères");
        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Types de critères Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du tableau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(idCommune);
        LOGGER.info("Vérification de l'absence du nom modifié dans le tableau");
        assertion.verifyTrue(listValeurParCritere.containsKey(nomModifier),
                nomModifier + " n'est pas dans le tableau");

        LOGGER.info("Pas de test 15 -- Supprimer un critère - Pop-up de confirmation");
        seleniumTools.clickOnElement(wait, pageRessourcesCriteres.boutonSupprimer(wait, nomModifier));
        LOGGER.info("Récupération du texte de la popup de suppression");
        List<String> listTextExtraitPopupSuppression = pageRessourcesCriteres.listTextExtraitPopupSuppression(wait);
        LOGGER.info("Vérification du texte de la popup de suppression");
        assertion.verifyEquals("Supprimer Type de critère \"" + nomModifier + "\". Êtes-vous sûr ?",
                listTextExtraitPopupSuppression.get(0),
                "Le texte suppresion n'est pas celui attendu");
        assertion.verifyEquals("OK", listTextExtraitPopupSuppression.get(1),
                "Le bouton OK n'est pas celui attendu");
        assertion.verifyEquals("Annuler", listTextExtraitPopupSuppression.get(2),
                "Le bouton Annuler n'est pas celui attendu");

        LOGGER.info("Pas de test 16 -- Supprimer un critère - Bouton [Annuler]");
        seleniumTools.clickOnElement(wait, pageRessourcesCriteres.boutonSupprimerAnnuler(wait));
        LOGGER.info("Récupération des valeurs du tableau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(idCommune);
        LOGGER.info("Vérification que la suppression n'a pas eu lieu");
        assertion.verifyTrue(listValeurParCritere.containsKey(nomModifier),
                nomModifier + " n'est pas dans le tableau");

        LOGGER.info("Pas de test 17 -- Supprimer un critère - Pop-up de confirmation");
        seleniumTools.clickOnElement(wait, pageRessourcesCriteres.boutonSupprimer(wait, nomModifier));
        LOGGER.info("Récupération du texte de la popup de suppression");
        listTextExtraitPopupSuppression = pageRessourcesCriteres.listTextExtraitPopupSuppression(wait);
        LOGGER.info("Vérification du texte de la popup de suppression");
        assertion.verifyEquals("Supprimer Type de critère \"" + nomModifier + "\". Êtes-vous sûr ?",
                listTextExtraitPopupSuppression.get(0),
                "Le texte suppresion n'est pas celui attendu");
        assertion.verifyEquals("OK", listTextExtraitPopupSuppression.get(1),
                "Le bouton OK n'est pas celui attendu");
        assertion.verifyEquals("Annuler", listTextExtraitPopupSuppression.get(2),
                "Le bouton Annuler n'est pas celui attendu");

        LOGGER.info("Pas de test 18 -- Supprimer un critère - Bouton [OK]");
        seleniumTools.clickOnElement(wait, pageRessourcesCriteres.boutonSupprimerOK(wait));
        Thread.sleep(1000);
        LOGGER.info("Récupération des valeurs du tableau");
        listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(idCommune);
        LOGGER.info("Vérification que la suppression a eu lieu");
        assertion.verifyTrue(!listValeurParCritere.containsKey(nomModifier),
                nomModifier + " est dans le tableau");

        LOGGER.info("FIN DU TEST");
    }
}
