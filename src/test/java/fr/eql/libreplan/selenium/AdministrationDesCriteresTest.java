package fr.eql.libreplan.selenium;

import fr.eql.libreplan.pageObject.PageCalendrier;
import fr.eql.libreplan.pageObject.PageLogin;
import fr.eql.libreplan.pageObject.PageRessourcesCriteres;
import fr.eql.libreplan.pageObject.PageRessourcesCriteresCreer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdministrationDesCriteresTest extends AbstractTestSelenium{
    String url = "http://192.168.15.10:8090/libreplan";
    String username = "admin";
    String password = "admin";

    String nom = "Critère - Test bouton [Annuler]";
    String type = "PARTICIPANT";
    Boolean valeursMultiplesParRessource = true;
    Boolean hierarchie = true;
    Boolean active = true;
    String description = "Critère - Test bouton [Annuler]";


    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        LOGGER.info("Pas de test 1 -- Connexion à l'application - Profil Admin");

        PageLogin pageLogin = new PageLogin(driver);
        PageCalendrier pageCalendrier = pageLogin.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification de l'onglet");
        assertion.verifyEquals("Planification des projets", pageCalendrier.titreDeLaPage(wait, idCommune),
                "L'onglet n'est pas celui attendu");

        LOGGER.info("Pas de test 2 -- Accéder à la page d'administration des critères");
        PageRessourcesCriteres pageRessourcesCriteres = pageCalendrier.cliquerRessourcesCriteres(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification de l'onglet");
        assertion.verifyEquals("Types de critères Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune),
                "L'onglet n'est pas celui attendu");
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
        LOGGER.info("Vérification OK");

        LOGGER.info("Pas de test 3 -- Créer un critère - Accès au formulaire de création");
        PageRessourcesCriteresCreer pageRessourcesCriteresCreer = pageRessourcesCriteres.cliquerBoutonCreer(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification de l'onglet");
        assertion.verifyEquals("Créer Type de critère", pageRessourcesCriteresCreer.titreDeLaPage(wait, idCommune),
                "L'onglet n'est pas celui attendu");
        assertion.verifyEquals("Modifier", pageRessourcesCriteresCreer.titreFormulaire(wait, idCommune),
                "Le titre du formulaire n'est pas : Modifier");
        assertion.verifyEquals("Enregistrer", pageRessourcesCriteresCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le texte du bouton Enregistrer n'est pas correct");
        assertion.verifyEquals("Sauver et continuer", pageRessourcesCriteresCreer.boutonSauverEtContinuer(wait, idCommune).getText(),
                "Le texte du bouton Sauver et continuer n'est pas correct");
        assertion.verifyEquals("Annuler", pageRessourcesCriteresCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le texte du bouton Annuler n'est pas correct");

        LOGGER.info("Pas de test 4 -- Créer un critère - bouton [Annuler]");
        LOGGER.info("Libelle du formulaire");
        List<String> listLibelleFormulaire = pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune);
        assertion.verifyEquals("Nom", pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune).get(0),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Type", pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune).get(1),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Valeurs multiples par ressource", pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune).get(2),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Hiérarchie", pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune).get(3),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Activé", pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune).get(4),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Description", pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune).get(5),
                "Le libellé du formulaire est incorrect");
        assertion.verifyEquals("Code", pageRessourcesCriteresCreer.listLibelleFormulaire(wait, idCommune).get(6),
                "Le libellé du formulaire est incorrect");
        pageRessourcesCriteresCreer.remplirFormulaire(wait, idCommune, nom, type, valeursMultiplesParRessource, hierarchie, active, description);


        pageRessourcesCriteres = pageRessourcesCriteresCreer.cliquerBoutonAnnuler(wait, idCommune);
        assertion.verifyEquals("Types de critères Liste", pageRessourcesCriteres.titreDeLaPage(wait, idCommune),
                "L'onglet n'est pas celui attendu");
        LOGGER.info("Vérification que le nom n'est pas présent dans le tableau");
        List<Map<String, String>> listValeurParCritere = pageRessourcesCriteres.recuperationValeurTableauCriteres(idCommune);
        for (Map<String, String> mapFromList : listValeurParCritere){
            for(Map.Entry<String, String> mapEntry : mapFromList.entrySet()){
                assertion.verifyTrue(!Objects.equals(nom, mapEntry.getValue()));
            }
        }

        LOGGER.info("Pas de test 5 -- Créer un critère - bouton [Enregistrer]");


        LOGGER.info("FIN DU TEST");

    }
}
