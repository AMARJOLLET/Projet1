package fr.eql.libreplan.selenium.gestionUtilisateursProfils;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.PageConfiguration.PageConfigurationProfils;
import fr.eql.libreplan.pageObject.PageConfiguration.PageConfigurationProfilsCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GUP01_GestionProfil extends AbstractTestSelenium {
    // QuerySQL
    protected String queryNettoyage = "select profile_name from profile_table where profile_name in ('??','??');";

    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    // Connexion
    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");


    // JDD Gestion des profils
    protected String nomProfil = listJdd.get(1).get("Nom profil");
    protected String nomProfil2 = listJdd.get(1).get("Nom profil 2");
    protected String roleProfil1 = listJdd.get(1).get("Nom role 1");
    protected String roleProfil2 = listJdd.get(1).get("Nom role 2");
    protected String roleProfil3 = listJdd.get(1).get("Nom role 3");
    protected String roleProfil4 = listJdd.get(1).get("Nom role 4");
    protected String roleProfil5 = listJdd.get(1).get("Nom role 5");


    public GUP01_GestionProfil() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);


        LOGGER.info("Pas de test 2 -- Accéder à la page de gestion des profils");
        PageConfigurationProfils pageConfigurationProfils = pageCalendrierPlanification.cliquerConfigurationProfil(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Profils Liste", pageConfigurationProfils.titreDelaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Vérfication du tableau");
        List<String> listLibelleTableauProfil = pageConfigurationProfils.recuperationLibelleTableauProfil(wait);
        assertion.verifyEquals("Nom de profil", listLibelleTableauProfil.get(0),
                "Le nom de la première colonne n'est pas celle attendu");
        assertion.verifyEquals("Actions", listLibelleTableauProfil.get(1),
                "Le nom de la deuxième colonne n'est pas celle attendu");

        LOGGER.info("PRE REQUIS DU TEST");
        LOGGER.info("Nettoyage si nécessaire");
        queryNettoyage = outilsManipulationDonnee.formatageQuery(queryNettoyage, nomProfil, nomProfil2);
        LOGGER.info("Query setup " + queryNettoyage);
        outilsProjet.verificationNettoyageTableau(wait, connection, queryNettoyage);
        LOGGER.info("Reprise du cas de test");


        LOGGER.info("Pas de test 3 -- Créer un profil - Accès au formulaire de création");
        PageConfigurationProfilsCreer pageConfigurationProfilsCreer = pageConfigurationProfils.cliquerBoutonCreer(wait);
        LOGGER.info("Vérification du titre de la page et du formulaire");
        assertion.verifyEquals("Créer Profil", pageConfigurationProfilsCreer.titreDelaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Données de profil", pageConfigurationProfilsCreer.titreduFormulaire(wait, idCommune).getText(),
                "Le titre du formulaire n'est pas celui attendu");
        assertion.verifyTrue(Objects.equals(pageConfigurationProfilsCreer.inputNom(wait, idCommune).getAttribute("value"), ""),
                "Le champ nom n'est pas vide");
        LOGGER.info("Vérification du bloc Association avce les roles");
        assertion.verifyEquals("Association avec les rôles", pageConfigurationProfilsCreer.titreBlocAssociation(wait, idCommune).getText(),
                "Le titre du bloc association n'est pas celui attendu");
        assertion.verifyTrue(Objects.equals(pageConfigurationProfilsCreer.inputRole(wait, idCommune).getAttribute("value"), ""),
                "Le champ Role n'est pas vide");
        assertion.verifyTrue(pageConfigurationProfilsCreer.boutonAjouterRole(wait, idCommune).isEnabled(),
                "Le bouton Ajouter un role n'est pas présent sur la page");
        List<String> listLibelleTableauRole = pageConfigurationProfilsCreer.recuperationLibelleTableauRole(wait, idCommune);
        assertion.verifyEquals("Nom du rôle", listLibelleTableauRole.get(0),
                "Le premier libellé du tableau Role n'est pas celui attendu");
        assertion.verifyEquals("Actions", listLibelleTableauRole.get(1),
                "Le premier libellé du tableau Role n'est pas celui attendu");
        LOGGER.info("Vériciation des boutons");
        assertion.verifyEquals("Enregistrer", pageConfigurationProfilsCreer.boutonEnregistrer(wait).getText(),
                "Le bouton Enregistrer n'est pas celui atttendu");
        assertion.verifyEquals("Sauver et continuer", pageConfigurationProfilsCreer.boutonSauverEtContinuer(wait).getText(),
                "Le bouton Sauver et continuer n'est pas celui atttendu");
        assertion.verifyEquals("Annuler", pageConfigurationProfilsCreer.boutonAnnuler(wait).getText(),
                "Le bouton Annuler n'est pas celui atttendu");

        LOGGER.info("Pas de test 4 -- Créer un profil - Saisie des informations");
        pageConfigurationProfilsCreer.remplirNom(wait,idCommune, nomProfil);
        pageConfigurationProfilsCreer.remplirRoleEtAjouter(wait, idCommune, roleProfil1);
        LOGGER.info("Vérification de la présence du role ajouter");
        Thread.sleep(1000);
        Map<String, WebElement> mapValeurTableauRole = pageConfigurationProfilsCreer.recuperationValeurTableau(wait, idCommune);
        assertion.verifyTrue(mapValeurTableauRole.containsKey(roleProfil1),
                "Le role n'est pas retrouvé dans le tableau des roles");
        assertion.verifyTrue(mapValeurTableauRole.get(roleProfil1).isEnabled(),
                "Le bouton supprimer n'est pas disponible pour le role créé");

        LOGGER.info("Pas de test 5 -- Créer un profil - Conformité infobulle");
        assertion.verifyEquals("Supprimer", mapValeurTableauRole.get(roleProfil1).getAttribute("title"));

        LOGGER.info("Pas de test 6 -- Créer un profil - Ajout de plusieurs rôles");
        for(int i = 0; i < 4; i++){
            LOGGER.info("Ajout du role " + listJdd.get(1).get("Nom role " + (i+2)));
            pageConfigurationProfilsCreer.remplirRoleEtAjouter(wait, idCommune, listJdd.get(1).get("Nom role " + (i+2)));
            LOGGER.info("Ajout terminé");
        }
        LOGGER.info("Vérification des roles ajoutés");
        mapValeurTableauRole = pageConfigurationProfilsCreer.recuperationValeurTableau(wait, idCommune);
        assertion.verifyTrue(mapValeurTableauRole.containsKey(roleProfil2),
                "Le role " + roleProfil2 + " n'est pas présent dans le tableau");
        assertion.verifyTrue(mapValeurTableauRole.containsKey(roleProfil3),
                "Le role " + roleProfil3 + " n'est pas présent dans le tableau");
        assertion.verifyTrue(mapValeurTableauRole.containsKey(roleProfil4),
                "Le role " + roleProfil4 + " n'est pas présent dans le tableau");
        assertion.verifyTrue(mapValeurTableauRole.containsKey(roleProfil5),
                "Le role " + roleProfil5 + " n'est pas présent dans le tableau");

        LOGGER.info("Pas de test 7 -- Créer un profil - Ajout d'un rôle déjà ajouté ");
        pageConfigurationProfilsCreer.remplirRoleEtAjouter(wait, idCommune, roleProfil1);
        Thread.sleep(500);
        mapValeurTableauRole = pageConfigurationProfilsCreer.recuperationValeurTableau(wait, idCommune);
        assertion.verifyEquals(5, mapValeurTableauRole.size(),
                "Le nombre de ligne du tableau n'est pas celle attendu");

        LOGGER.info("Pas de test 8 -- Créer un profil - Suppression d'un rôle ajouté");
        seleniumTools.clickOnElement(wait, mapValeurTableauRole.get(roleProfil5));
        Thread.sleep(500);
        mapValeurTableauRole = pageConfigurationProfilsCreer.recuperationValeurTableau(wait, idCommune);
        assertion.verifyFalse(mapValeurTableauRole.containsKey(roleProfil5),
                "Le tableau possède toujours le role " + roleProfil5);

        LOGGER.info("Pas de test 9 -- Créer un profil - Suppression de tous les rôles ajoutés");
        int index = mapValeurTableauRole.size();
        for (int i = 0; i<index; i++){
            WebElement we = pageConfigurationProfilsCreer.recuperationValeurTableau(wait, idCommune).get(listJdd.get(1).get("Nom role "+(i+1)));
            LOGGER.info("Suppression du role " + listJdd.get(1).get("Nom role "+(i+1)));
            seleniumTools.clickOnElement(wait, we);
            LOGGER.info("Supression effectué");
            wait.until(ExpectedConditions.invisibilityOf(we));
        }
        mapValeurTableauRole = pageConfigurationProfilsCreer.recuperationValeurTableau(wait, idCommune);
        assertion.verifyEquals(0, mapValeurTableauRole.size(),
                "Il reste encore des roles dans le tableau");

        LOGGER.info("Pas de test 10 -- Créer un profil - enregistrement");
        pageConfigurationProfils = pageConfigurationProfilsCreer.cliquerBoutonEnregistrer(wait);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre et du message de création");
        assertion.verifyEquals("Profils Liste", pageConfigurationProfils.titreDelaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Profil \""+nomProfil+"\" enregistré", pageConfigurationProfils.messageCreation(wait, nomProfil).getText(),
                "Le message d'enregistrement n'est pas celui attendu");
        List<String> listValeurTableauProfil = pageConfigurationProfils.recuperationNomTableauProfil(wait);
        assertion.verifyTrue(listValeurTableauProfil.contains(nomProfil));

        LOGGER.info("Pas de test 11 -- Modifier un profil - accès page de modification");
        pageConfigurationProfilsCreer = pageConfigurationProfils.cliquerBoutonModifier(wait, nomProfil);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Modifier Profil: " + nomProfil, pageConfigurationProfilsCreer.titreDelaPage(wait, idCommune).getText(),
                "La titre de la page n'est pas celui attendu");
        mapValeurTableauRole = pageConfigurationProfilsCreer.recuperationValeurTableau(wait, idCommune);
        assertion.verifyEquals(0, mapValeurTableauRole.size(),
                "Le tableau comporte encore des roles ajoutés");

        LOGGER.info("Pas de test 12 -- Modifier un profil - Ajout de plusieurs rôles");
        for(int i = 0; i < 4; i++){
            LOGGER.info("Ajout du role " + listJdd.get(1).get("Nom role " + (i+2)));
            pageConfigurationProfilsCreer.remplirRoleEtAjouter(wait, idCommune, listJdd.get(1).get("Nom role " + (i+1)));
            LOGGER.info("Ajout terminé");
        }
        mapValeurTableauRole = pageConfigurationProfilsCreer.recuperationValeurTableau(wait, idCommune);
        LOGGER.info("Vérification des valeurs ajoutés du tableau");
        for(int i = 0; i < 4; i++){
        assertion.verifyTrue(mapValeurTableauRole.containsKey(listJdd.get(1).get("Nom role " + (i+1))),
                "Le role " + listJdd.get(1).get("Nom role " + (i+1)) + " n'est pas présent dans le tableau");
        }

        LOGGER.info("Pas de test 13 -- Modifier un profil - enregistrement");
        pageConfigurationProfilsCreer.remplirNom(wait, idCommune, nomProfil2);
        pageConfigurationProfils = pageConfigurationProfilsCreer.cliquerBoutonEnregistrer(wait);
        LOGGER.info("Vérification du titre et du message de création");
        assertion.verifyEquals("Profils Liste", pageConfigurationProfils.titreDelaPage(wait, idCommune).getText(),
                "Le titre de la page n'est pas celui attendu");
        assertion.verifyEquals("Profil \""+nomProfil2+"\" enregistré", pageConfigurationProfils.messageCreation(wait, nomProfil).getText(),
                "Le message d'enregistrement n'est pas celui attendu");
        listValeurTableauProfil = pageConfigurationProfils.recuperationNomTableauProfil(wait);
        assertion.verifyTrue(listValeurTableauProfil.contains(nomProfil2));

        LOGGER.info("FIN DU TEST");

    }

}
