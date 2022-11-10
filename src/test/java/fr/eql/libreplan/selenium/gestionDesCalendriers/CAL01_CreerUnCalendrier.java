package fr.eql.libreplan.selenium.gestionDesCalendriers;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrier;
import fr.eql.libreplan.pageObject.pageRessources.calendrier.PageRessourcesCalendrierCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CAL01_CreerUnCalendrier extends AbstractTestSelenium {
    // Chargement JDD
    protected String className = getClass().getSimpleName();
    protected String classPackage = this.getClass().getPackage().getName();
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDD Calendrier
    protected String nomCalendrier = listJdd.get(1).get("nom");
    protected Boolean codeCalendrier = Boolean.valueOf(listJdd.get(1).get("code"));

    // JDD CalendrierDérivé
    protected String nomCalendrierDerive = listJdd.get(2).get("nom");
    protected Boolean codeCalendrierDerive = Boolean.valueOf(listJdd.get(2).get("code"));

    // JDD CalendrierCopier
    protected String nomCalendrierCopier = listJdd.get(3).get("nom");
    protected Boolean codeCalendrierCopier = Boolean.valueOf(listJdd.get(3).get("code"));


    public CAL01_CreerUnCalendrier() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        LOGGER.info("Pas de test 2 -- Accéder à la page d'administration des calendriers");
        String idCommune = outilsProjet.retournerIdCommune(wait);

        PageRessourcesCalendrier pageRessourcesCalendrier = pageCalendrierPlanification.cliquerRessourcesCalendrier(wait, idCommune);
        methodesProjet.VerificationPageAdministrationCalendriers(wait);

        LOGGER.info("Pas de test 3 -- Créer un calendrier - Accès au formulaire de création");
        idCommune = outilsProjet.retournerIdCommune(wait);
        pageRessourcesCalendrier.verificationNettoyageTableauCAL1(wait, idCommune,
                nomCalendrierDerive,nomCalendrier,nomCalendrierCopier);
        PageRessourcesCalendrierCreer pageRessourcesCalendrierCreer = pageRessourcesCalendrier.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du formulaire");
        assertion.verifyEquals("Créer Calendrier", pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Données de calendrier", pageRessourcesCalendrierCreer.titreFormulaire(wait, idCommune),
                "Le titre du formulaire n'est pas celui attendu");
        LOGGER.info("Vérification du texte des 3 boutons");
        assertion.verifyEquals("Enregistrer", pageRessourcesCalendrierCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer et continuer", pageRessourcesCalendrierCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Annuler", pageRessourcesCalendrierCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");


        LOGGER.info("Pas de test 4 -- Créer un calendrier - bouton [Enregistrer]");
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCalendrierCreer.remplirFormulaire(wait, idCommune, nomCalendrier, codeCalendrier);
        LOGGER.info("Formulaire rempli");
        LOGGER.info("Enregistrement du formulaire");
        pageRessourcesCalendrier = pageRessourcesCalendrierCreer.cliquerBoutonEnregistrer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du formulaire");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Recupération des valeurs du tableau");
        Map<String, Map<String, String>> mapValeurTableauCalendrier = pageRessourcesCalendrier.recuperationValeurTableauCalendrier(wait, idCommune);
        LOGGER.info("Vérification");
        assertion.verifyTrue(mapValeurTableauCalendrier.containsKey(nomCalendrier),
                "Le tableau ne contient pas " + nomCalendrier);

        LOGGER.info("Pas de test 5 -- Créer un calendrier dérivé ");
        pageRessourcesCalendrierCreer = pageRessourcesCalendrier.cliquerBoutonCreerUneDerive(wait, nomCalendrier);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer Calendrier", pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Dérivé du calendrier " + nomCalendrier, pageRessourcesCalendrierCreer.inputType(wait, idCommune).getText(),
                "Le Type dans le formulaire n'est pas celui attendu");

        LOGGER.info("Pas de test 6 --  Créer un calendrier dérivé - nom non conforme");
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCalendrierCreer.remplirFormulaire(wait, idCommune, nomCalendrier, codeCalendrier);
        LOGGER.info("Formulaire rempli");
        pageRessourcesCalendrierCreer.cliquerBoutonEnregistrer(wait, idCommune);
        Thread.sleep(1000);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer Calendrier: " + nomCalendrier, pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Vérification du message d'erreur");
        assertion.verifyEquals(nomCalendrier + " existe déjà", pageRessourcesCalendrierCreer.messageAlerte(wait).getText(),
                "Le titre de la page n'est pas celui attendu");
        if(navigateur == "Chrome"){
            assertion.verifyEquals("rgba(253, 202, 135, 1)", pageRessourcesCalendrierCreer.messageAlerteColor(wait),
                    "La couleur du message d'erreur n'est pas celui attendu");
        }

        LOGGER.info("Pas de test 7 --  Créer un calendrier dérivé - bouton [Enregistrer et continuer]");
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCalendrierCreer.remplirFormulaire(wait, idCommune, nomCalendrierDerive, codeCalendrierDerive);
        pageRessourcesCalendrierCreer.cliquerBoutonEnregistrerEtContinuer(wait, idCommune);
        LOGGER.info("Vérification du message de création");
        assertion.verifyEquals("Calendrier de base \"" + nomCalendrierDerive + "\" enregistré", pageRessourcesCalendrierCreer.messageCreation(wait, nomCalendrierDerive),
                "Le message de création n'est pas celui attendu");
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer Calendrier: " + nomCalendrierDerive, pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 8 -- Retour page de gestion des calendriers");
        pageRessourcesCalendrier = pageRessourcesCalendrierCreer.cliquerBoutonAnnuler(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        mapValeurTableauCalendrier = pageRessourcesCalendrier.recuperationValeurTableauCalendrier(wait, idCommune);
        assertion.verifyTrue(mapValeurTableauCalendrier.containsKey(nomCalendrierDerive),
                "Le tableau ne contient pas " + nomCalendrierDerive);

        LOGGER.info("Pas de test 9 -- Affichage du calendrier dérivé");
        pageRessourcesCalendrier.cliquerBoutonRepliereDerive(wait, nomCalendrier);
        LOGGER.info("Vérification que le calendrier dérivé n'est plus affiché");
        assertion.verifyEquals("none", pageRessourcesCalendrier.verificationDeriveReplier(wait, nomCalendrier),
                "Le calendrier derivé est toujours affiché");

        LOGGER.info("Pas de test 10 -- Créer un calendrier par copie");
        pageRessourcesCalendrierCreer = pageRessourcesCalendrier.cliquerBoutonCopierCalendrier(wait, nomCalendrier);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Créer Calendrier: " + nomCalendrier, pageRessourcesCalendrierCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals(nomCalendrier, pageRessourcesCalendrierCreer.inputNom(wait, idCommune).getAttribute("value"),
                "Le nom du calendrier du formulaire n'est pas celui attendu");
        assertion.verifyEquals("Calendrier source", pageRessourcesCalendrierCreer.inputType(wait, idCommune).getText(),
                "Le type du calendrier du formulaire n'est pas celui attendu");

        LOGGER.info("Pas de test 11 -- Créer un calendrier par copie - Nom du calendrier non conforme");
        pageRessourcesCalendrierCreer.cliquerBoutonEnregistrerEtContinuer(wait, idCommune);
        assertion.verifyEquals(nomCalendrier + " existe déjà", pageRessourcesCalendrierCreer.messageAlerte(wait).getText(),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 12 -- Créer un calendrier par copie - bouton [Enregistrer]");
        LOGGER.info("Remplissage du formulaire");
        pageRessourcesCalendrierCreer.remplirFormulaire(wait, idCommune, nomCalendrierCopier, codeCalendrierCopier);
        LOGGER.info("Formulaire rempli");
        pageRessourcesCalendrier = pageRessourcesCalendrierCreer.cliquerBoutonEnregistrer(wait, idCommune);
        LOGGER.info("Enregistrement effectué");
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Liste de calendriers", pageRessourcesCalendrier.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Calendrier de base \"" + nomCalendrierCopier + "\" enregistré", pageRessourcesCalendrier.messageCreation(wait, nomCalendrierCopier),
                "Le message d'enregistrement n'est pas celui attendu");
        LOGGER.info("Récupération du tableau des calendriers");
        mapValeurTableauCalendrier = pageRessourcesCalendrier.recuperationValeurTableauCalendrier(wait, idCommune);
        LOGGER.info("Vérification de la copie du calendrier dans le tableau");
        assertion.verifyTrue(mapValeurTableauCalendrier.containsKey(nomCalendrierCopier));

        LOGGER.info("FIN DU TEST");
    }
}
