package utils;

import org.openqa.selenium.By;
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


    public String retournerIdCommune(WebDriverWait wait){
        WebElement elementWithID = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[1]")));
        String idCommune = elementWithID.getAttribute("id").substring(0,4);
        LOGGER.info("Récupération de l'id commune : " + idCommune);
        return idCommune;
    }

}

