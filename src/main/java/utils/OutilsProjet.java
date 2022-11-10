package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class OutilsProjet extends Logging {
    Logger LOGGER = LoggerFactory.getLogger(className);


    public Map<String, String> chargementCSVJDD(String classPackage, String className) throws IOException {
        String classPath = classPackage.replace(".", "/");
        String csvFilePath = "src/main/resources/JDD/csv/" + classPath + "/" + className + ".csv";
        Map<String, String> jdd = new HashMap<>();
        LOGGER.info("---------- Fichier JDD chargé : " + csvFilePath + " ----------");
        List<String[]> list =
                Files.lines(Paths.get(csvFilePath))
                        .map(line -> line.split("\\\\r?\\\\n"))
                        .collect(Collectors.toList());
        if (list.size() > 2) {
            LOGGER.error("***** Mauvais format de fichier CSV - trop de lignes (2 lignes attendues : 1 ligne keys et 1 ligne values *****");
        }
        String[] titres = list.get(0)[0].split(",");
        String[] valeurs = list.get(1)[0].split((","));
        for (int i = 0; i < titres.length; i++) {
            jdd.put(titres[i], valeurs[i]);
        }
        jdd.forEach((key, value) -> LOGGER.info(key + " = " + value));
        return jdd;
    }

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

    public static String generateRandomNumber(int numberOfDigit) {
        String result;
        do {
            if (numberOfDigit <= 0) {
                throw new IllegalArgumentException("A random number's length cannot be zero or negative");
            }
            Random random = new Random();
            String bound = "1";
            StringBuilder nombre0 = new StringBuilder();
            for (int i = 0; i < numberOfDigit; i++) {
                nombre0.append("0");
            }
            bound = bound + nombre0;
            int boundInteger = Integer.parseInt(bound);
            result = String.format("%0" + numberOfDigit + "d", random.nextInt(boundInteger));
        } while (Objects.equals(result,"0"));
        return result;
    }

    public String changementDate(String date){
        String MMsans0 = date.substring(8,10).replace("0","");
        return date = date.substring(5,7) + "/" + MMsans0 + "/" + date.substring(0,4);
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

    public String ajouterUnAuCode(WebDriverWait wait, String code){
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
        return mapRecuperationValeurTableau;
    }

    public List<Map<String, WebElement>> ordreValeurTableauText(String KeyRow,List<WebElement> listLibelle, List<WebElement> listRow){
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
    public WebElement boutonTableauText(WebDriverWait wait, String nomRow, String titleBouton){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[text() = '" + nomRow + "']/ancestor::tr//span[@title='"+titleBouton+"']")));
    }

    // BOUTON DES TABLEAUX
    public WebElement boutonTableauValue(WebDriverWait wait, String nomRow, String titleBouton){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//input[@value = '" + nomRow + "']/ancestor::tr//span[@title='"+titleBouton+"']")));
    }
}

