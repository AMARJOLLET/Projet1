package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class OutilsProjet extends InstanciationDriver {
    public OutilsProjet(WebDriver driver) {
        super(driver);
    }


    // Instanciation
    private final SeleniumTools seleniumTools = new SeleniumTools(driver);
    private final OutilsManipulationDonnee outilsManipulationDonnee = new OutilsManipulationDonnee();


    //


    // Methode recuperation CSV
    public ArrayList<Map<String, String>> loadCsvSeveralJDD (String classPackage, String className) throws IOException {
        String classPath = classPackage.replace(".", "/");
        String csvFilePath = "src/main/resources/JDD/csv/" + classPath + "/" + className + ".csv";
        ArrayList<Map<String, String>> listJDD = new ArrayList<>();
        LOGGER.info("---------- Fichier JDD chargé : " + csvFilePath + " ----------");
        List<String[]> list =
                Files.lines(Paths.get(csvFilePath))
                        .map(line -> line.split("\\\\r?\\\\n"))
                        .collect(Collectors.toList());
        for (int j = 0; j < list.size(); j += 2) {
            Map<String, String> jdd = new HashMap<>();

            String[] titres = list.get(j)[0].split(",");
            String[] val = list.get(j+1)[0].split((","));
            for (int i = 0; i < titres.length; i++) {
                jdd.put(titres[i], val[i]);
            }
            listJDD.add(jdd);
        }
        return listJDD;
    }


    public void cliquerBoutonCreer(WebDriverWait wait, WebElement we) throws Throwable {
        for (int i = 0; i < 3; i++) {
            try {
                seleniumTools.clickOnElement(wait, we);
                LOGGER.info("Click bouton créer OK");
                break;
            } catch (ElementClickInterceptedException e) {
                LOGGER.info("Element intercepté -- retry");
            }
        }
    }


    // RECUPERATION DE L'ID DYNAMIQUE
    public String retournerIdCommune(WebDriverWait wait){
        String idCommune = null;
        for (int i = 0; i<4; i++){
            try {
                WebElement elementWithID = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div)[1]")));
                idCommune = elementWithID.getAttribute("id").substring(0,4);
                break;
            } catch (StaleElementReferenceException e){
                LOGGER.warn("StaleElementReferenceException, retry -- " + (i+1));
            }
        }
        LOGGER.info("Récupération de l'id commune : " + idCommune);
        return idCommune;
    }

    public String ajouterUnAuCode(String code){
        String codeRemplacer = code.substring(code.length() - 4);
        int codePlusUn = Integer.parseInt(codeRemplacer) + 1;
        String codePlusUnString = String.valueOf(codePlusUn);
        while (codePlusUnString.length() < 4){
            codePlusUnString = "0" + codePlusUnString;
        }
        code = code.replace(codeRemplacer, codePlusUnString);
        return code;
    }


    public String extraireValueWBS(WebElement weValeurWBS){
        try {
            WebElement we = weValeurWBS.findElement(By.xpath(".//input"));
            return we.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public String recupererValeurParDefautSelect(WebElement we){
        WebElement weSelected = we.findElement(By.xpath("./option[@selected=\"selected\"]"));
        return weSelected.getText();
    }

    /*
    * METHODE SUR LES TABLEAUX
    */

    public List<String> recuperationLibelleTableau(List<WebElement> listWe) {
        List<String> listLibelleTableauString = new ArrayList<>();
        for (WebElement we : listWe) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }

    /// TABLEAU AVEC TEXT
    public Map<String, Map<String, WebElement>> recuperationValeurTableauText(String KeyRow,List<WebElement> listLibelle, List<WebElement> listRow){
        Map<String, Map<String, WebElement>> mapRecuperationValeurTableau = new HashMap<>();

        LOGGER.info("Recupération de " + listRow.size() + " rows");
        for(WebElement row : listRow){
            Map<String, WebElement> mapValeurRow = new HashMap<>();
            List<WebElement> listValeurRow = row.findElements(By.xpath("./td"));
            for(int i = 0; i < listLibelle.size(); i++){
                mapValeurRow.put(listLibelle.get(i).getText(), listValeurRow.get(i));
            }
            mapRecuperationValeurTableau.put(mapValeurRow.get(KeyRow).getText(), mapValeurRow);
        }
        LOGGER.info("Recupération terminé");
        return mapRecuperationValeurTableau;
    }

    public List<Map<String, WebElement>> ordreValeurTableauText(List<WebElement> listLibelle, List<WebElement> listRow){
        List<Map<String, WebElement>> mapRecuperationValeurTableau = new ArrayList<>();

        LOGGER.info("Recupération de " + listRow.size() + " rows");
        for(WebElement row : listRow){
            Map<String, WebElement> mapValeurRow = new HashMap<>();
            List<WebElement> listValeurRow = row.findElements(By.xpath("./td"));
            for(int i = 0; i < listLibelle.size(); i++){
                mapValeurRow.put(listLibelle.get(i).getText(), listValeurRow.get(i));
            }
            mapRecuperationValeurTableau.add(mapValeurRow);
        }
        return mapRecuperationValeurTableau;
    }


    /// TABLEAU AVEC INPUT

    public Map<String, Map<String, WebElement>> recuperationValeurTableauInput(List<WebElement> listLibelle, List<WebElement> listRow){
        Map<String, Map<String, WebElement>> mapRecuperationValeurTableau = new HashMap<>();

        LOGGER.info("Recupération de " + listRow.size() + " rows");
        for(WebElement row : listRow){
            Map<String, WebElement> mapValeurRow = new HashMap<>();
            List<WebElement> listValeurRow = row.findElements(By.xpath("./td"));
            for(int i = 0; i < listLibelle.size(); i++){
                mapValeurRow.put(listLibelle.get(i).getText(), listValeurRow.get(i));
            }
            LOGGER.info(extraireValueWBS(mapValeurRow.get("Nom")));
            mapRecuperationValeurTableau.put(extraireValueWBS(mapValeurRow.get("Nom")), mapValeurRow);
        }

        return mapRecuperationValeurTableau;
    }

    public List<Map<String, WebElement>> ordreValeurTableauInput(List<WebElement> listLibelle, List<WebElement> listRow){
        List<Map<String, WebElement>> listRecuperationValeurTableau = new ArrayList<>();

        LOGGER.info("Recupération de " + listRow.size() + " rows");
        for(WebElement row : listRow){
            Map<String, WebElement> mapValeurRow = new HashMap<>();
            List<WebElement> listValeurRow = row.findElements(By.xpath("./td"));
            for(int i = 0; i < listLibelle.size(); i++){
                mapValeurRow.put(listLibelle.get(i).getText(), listValeurRow.get(i));
            }
            LOGGER.info(extraireValueWBS(mapValeurRow.get("Nom")));
            listRecuperationValeurTableau.add(mapValeurRow);
        }

        return listRecuperationValeurTableau;
    }


    // BOUTON DES TABLEAUX
    public WebElement boutonTableauValue(WebDriverWait wait, String nomRow, String titleBouton){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//input[@value = '" + nomRow + "']/ancestor::tr//span[@title='"+titleBouton+"']")));
    }


    // Nettoyage
    public void supressionJdd(WebDriverWait wait,String nom) throws Throwable {
        WebElement boutonSupprimer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath
                ("//span[text() = '" + nom + "']/ancestor::tr//span[@title='Supprimer']")));
        seleniumTools.clickOnElement(wait, boutonSupprimer);
        WebElement acceptSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
        seleniumTools.clickOnElement(wait, acceptSuppression);
    }

    public String uppercaseOnlyFirstLetter(String name){
        name = name.toLowerCase();
        String firstLetter = name.substring(0,1).toUpperCase();
        return firstLetter + name.substring(1);

    }

    public void supressionJddAvecUtilisateur(WebDriverWait wait, Connection connection, String nom) throws Throwable {
        ResultSet rs = outilsManipulationDonnee.select(connection,
                "select user_id from worker where surname='"+nom+"';");

        WebElement boutonSupprimer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nom + "']/ancestor::tr//span[@title='Supprimer']")));
        seleniumTools.clickOnElement(wait, boutonSupprimer);
        WebElement acceptSuppression = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"OK\")]")));
        seleniumTools.clickOnElement(wait, acceptSuppression);
        boolean presenceUtilisateur = rs.next();
        if (presenceUtilisateur){
            WebElement acceptSuppressionUtilisateur = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//div[@class=\"z-window-modal-cl\"]//*[contains(text(), \"Oui\")]")));
            seleniumTools.clickOnElement(wait, acceptSuppressionUtilisateur);
        }
    }


    public void verificationNettoyageTableau(WebDriverWait wait,
                                             Connection connection, String query) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        List<String> listResultSet = outilsManipulationDonnee.resultQueryToString(connection, query);

        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        for (String JDD : listResultSet){
            LOGGER.info("Présence du JDD " + JDD);
            supressionJdd(wait, JDD);
            LOGGER.info("Suppression effectué");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//div[@class='message_INFO']/span[contains(text(), '\"" + JDD + "\"')]")));
        }
    }


    public void verificationNettoyageTableauParticipant(WebDriverWait wait,
                                             Connection connection, String query) throws Throwable {
        LOGGER.info("Récupération valeurs du tableau");
        List<String> listResultSet = outilsManipulationDonnee.resultQueryToString(connection, query);

        LOGGER.info("Vérification de l'absence du JDD dans le tableau");
        for (String JDD : listResultSet){
            LOGGER.info("Présence du JDD " + JDD);
            supressionJddAvecUtilisateur(wait, connection, JDD);
            LOGGER.info("Suppression effectué");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//div[@class='message_INFO']/span")));
        }
    }
}

