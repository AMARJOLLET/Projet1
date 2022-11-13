package fr.eql.libreplan.selenium.gestionDesRessources;

import fr.eql.libreplan.pageObject.PageCalendrierPlanification;
import fr.eql.libreplan.pageObject.pageRessources.machines.PageRessourcesMachines;
import fr.eql.libreplan.pageObject.pageRessources.machines.PageRessourcesMachinesCreer;
import fr.eql.libreplan.selenium.AbstractTestSelenium;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GRE02_CreerUneMachine extends AbstractTestSelenium {
    // QuerySQL
    protected String queryNettoyage = "select name from machine where name='??';";

    // Chargement JDD
    protected List<Map<String, String>> listJdd = outilsProjet.loadCsvSeveralJDD(classPackage, className);

    protected String username = listJdd.get(0).get("username");
    protected String password = listJdd.get(0).get("password");

    // JDD MACHINE
    protected boolean checkboxCodeMachine = Boolean.parseBoolean(listJdd.get(1).get("CheckBoxCode"));
    protected String codeMachine = listJdd.get(1).get("Code");
    protected String nomMachine = listJdd.get(1).get("Nom");
    protected String descriptionMachine = listJdd.get(1).get("Description");
    protected String typeMachine = listJdd.get(1).get("Type");


    public GRE02_CreerUneMachine() throws IOException {
    }


    @Test
    void run() throws Throwable {
        LOGGER.info("Accès à la page : " + url);
        driver.get(url);

        PageCalendrierPlanification pageCalendrierPlanification = methodesProjet.seConnecter(wait, username, password);
        String idCommune = outilsProjet.retournerIdCommune(wait);

        LOGGER.info("Pas de test 2 -- Accéder à la page de gestion des participants");
        PageRessourcesMachines pageRessourcesMachines = pageCalendrierPlanification.cliquerRessourcesMachine(wait, idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page");
        assertion.verifyEquals("Machines Liste", pageRessourcesMachines.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des titres du tableau");
        List<String> listLibelleTableau = pageRessourcesMachines.recuperationLibelleTableau(idCommune);
        LOGGER.info("Vérification des libellés du tableau");
        assertion.verifyEquals("Nom", listLibelleTableau.get(0),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Description", listLibelleTableau.get(1),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Code", listLibelleTableau.get(2),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("En file", listLibelleTableau.get(3),
                "Le libelle n'est pas celui attendu");
        assertion.verifyEquals("Opérations", listLibelleTableau.get(4),
                "Le libelle n'est pas celui attendu");
        LOGGER.info("Vérification des bouton filtre et libellés");
        assertion.verifyEquals("Filtré par", pageRessourcesMachines.libelleFiltrerPar(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Détails personnels", pageRessourcesMachines.libelleDetailPersonnels(wait, idCommune),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Plus d'options", pageRessourcesMachines.boutonPlusOptions(wait, idCommune).getText(),
                "Le bouton plus d'option n'est pas celui attendu");
        assertion.verifyEquals("Filtre", pageRessourcesMachines.boutonFiltre(wait, idCommune).getText(),
                "Le bouton Filtre n'est pas celui attendu");
        assertion.verifyEquals("Créer", pageRessourcesMachines.boutonCreer(wait, idCommune).getText(),
                "Le bouton créer n'est pas celui attendu");

        LOGGER.info("Pas de test 3 -- Créer une machine - Accès au formulaire de création ");

        LOGGER.info("PRE REQUIS DU TEST");
        LOGGER.info("Nettoyage si nécessaire");
        queryNettoyage = outilsManipulationDonnee.formatageQuery(queryNettoyage, nomMachine);
        LOGGER.info("Query setup " + queryNettoyage);
        outilsProjet.verificationNettoyageTableau(wait, connection, queryNettoyage);
        LOGGER.info("Reprise du cas de test");

        PageRessourcesMachinesCreer pageRessourcesMachinesCreer = pageRessourcesMachines.cliquerBoutonCreer(wait,idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du titre du formulaire");
        assertion.verifyEquals("Créer Machine", pageRessourcesMachinesCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 4 -- Créer une machine - Conformité de l'onglet Données de la machine");
        LOGGER.info("Vérification des libellés");
        assertion.verifyEquals("Code", pageRessourcesMachinesCreer.libelleCode(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Générer le code", pageRessourcesMachinesCreer.libelleCheckboxCode(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Nom", pageRessourcesMachinesCreer.libelleNom(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Description", pageRessourcesMachinesCreer.libelleDescription(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        assertion.verifyEquals("Type", pageRessourcesMachinesCreer.libelleType(wait, idCommune).getText(),
                "Le libellé n'est pas celui attendu");
        LOGGER.info("Vérification des input");
        assertion.verifyEquals("true", pageRessourcesMachinesCreer.inputCode(wait, idCommune).getAttribute("disabled"),
                "le champ code est modifable");
        assertion.verifyTrue(!Objects.equals(pageRessourcesMachinesCreer.inputCode(wait, idCommune).getAttribute("value"), ""),
                "le champ code est vide");
        assertion.verifyTrue(Objects.equals(pageRessourcesMachinesCreer.inputNom(wait, idCommune).getAttribute("value"), ""),
                "le champ prenom n'est pas vide");
        assertion.verifyTrue(Objects.equals(pageRessourcesMachinesCreer.inputDescription(wait, idCommune).getAttribute("value"), ""),
                "le champ nom n'est pas vide");
        assertion.verifyEquals("Ressource normale", pageRessourcesMachinesCreer.libelleSelectType(wait, idCommune).getText(),
                "La valeur par défaut du champ type n'est pas celle attendu");
        LOGGER.info("Vérification des boutons");
        assertion.verifyEquals("Annuler", pageRessourcesMachinesCreer.boutonAnnuler(wait, idCommune).getText(),
                "Le libellé du bouton n'est pas celui attendu");
        assertion.verifyEquals("Enregistrer", pageRessourcesMachinesCreer.boutonEnregistrer(wait, idCommune).getText(),
                "Le libellé du bouton n'est pas celui attendu");
        assertion.verifyEquals("Sauver et continuer", pageRessourcesMachinesCreer.boutonEnregistrerEtContinuer(wait, idCommune).getText(),
                "Le libellé du bouton n'est pas celui attendu");

        LOGGER.info("Pas de test 5 -- Créer une machine - Bouton [Sauver et continuer]");
        LOGGER.info("Remplissage du formulaire");
        seleniumTools.clickOnElement(wait, pageRessourcesMachinesCreer.inputNom(wait, idCommune));
        pageRessourcesMachinesCreer.remplirFormulaireDonnee(wait, idCommune, nomMachine, descriptionMachine, typeMachine, codeMachine, checkboxCodeMachine);
        LOGGER.info("Formulaire rempli");
        pageRessourcesMachinesCreer.cliquerBoutonEnregistrerEtContinuer(wait, idCommune);
        LOGGER.info("Vérification du message de création et du titre de la page");
        assertion.verifyEquals("Machine \""+nomMachine+"\" enregistré",pageRessourcesMachinesCreer.messageCreation(wait, nomMachine),
                "Le message de création ne correspond pas à celui attendu");
        assertion.verifyEquals("Modifier Machine: " + nomMachine, pageRessourcesMachinesCreer.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");

        LOGGER.info("Pas de test 6 -- retour page de gestion des machines");
        pageRessourcesMachines = pageRessourcesMachinesCreer.cliquerBoutonAnnuler(wait,idCommune);
        idCommune = outilsProjet.retournerIdCommune(wait);
        LOGGER.info("Vérification du titre de la page et du titre du formulaire");
        assertion.verifyEquals("Machines Liste", pageRessourcesMachines.titreDeLaPage(wait, idCommune),
                "Le titre de la page n'est pas celui attendu");
        LOGGER.info("Récupération des valeurs du tableau");
        Map<String, String> mapValeurTableauMachine = pageRessourcesMachines.recuperationValeurTableauMachine(idCommune).get(nomMachine);
        assertion.verifyEquals(nomMachine, mapValeurTableauMachine.get("Nom"),
                "La valeur nom n'est pas celle attendu");
        assertion.verifyEquals(descriptionMachine, mapValeurTableauMachine.get("Description"),
                "La valeur nom n'est pas celle attendu");
        if(!checkboxCodeMachine){
            assertion.verifyEquals(codeMachine, mapValeurTableauMachine.get("Code"),
                    "La valeur nom n'est pas celle attendu");
        }
        assertion.verifyEquals("non", mapValeurTableauMachine.get("En file"),
                "La valeur nom n'est pas celle attendu");






    }
}
