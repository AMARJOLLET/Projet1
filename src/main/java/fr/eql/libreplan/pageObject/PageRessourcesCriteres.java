package fr.eql.libreplan.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRessourcesCriteres extends AbstractFullPage{
    public PageRessourcesCriteres(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap"))).getText();
    }

    public PageRessourcesCriteresCreer cliquerBoutonCreer(WebDriverWait wait, String idCommune) throws Throwable {
        WebElement boutonCreer = wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "_5-box")));
        seleniumTools.clickOnElement(wait, boutonCreer);
        return new PageRessourcesCriteresCreer(driver);
    }

    public List<String> recuperationEnTeteTableau(String idCommune){
        List<WebElement> listEnTeteTableau = driver.findElements(By.xpath("//tr[@id='" + idCommune + "l4']/th/div"));
        List<String> listValeurEnTeteTableau = new ArrayList<>();
        for(int i = 0; i < listEnTeteTableau.size(); i++){
            listValeurEnTeteTableau.add(listEnTeteTableau.get(i).getText());
        }
        return listValeurEnTeteTableau;
    }

    public List<Map<String, String>> recuperationValeurTableauCriteres(String idCommune){
        // List WebElement
        List<String> listValeurEnTeteTableau = recuperationEnTeteTableau(idCommune);
        List<WebElement> listCritere = driver.findElements(By.xpath("//tbody[@id='" + idCommune + "r4']/tr"));

        // List Contenant la map
        List<Map<String, String>> listMapCritèreTableau = new ArrayList<>();

        LOGGER.info("Début de la récupération - " + listCritere.size() + " critères detectés ");
        for(int i = 0; i < listCritere.size(); i++){
            Map<String, String> listValeurCriteres = new HashMap<>();
            List<WebElement> listCritereValeur = listCritere.get(i).findElements(By.xpath(".//span[not(@title='Supprimer')]"));
            for(int j = 0; j < listValeurEnTeteTableau.size(); j++){
                LOGGER.info("Ajout de " + listValeurEnTeteTableau.get(j) + " = " + listCritereValeur.get(j).getText());
                listValeurCriteres.put(listValeurEnTeteTableau.get(j), listCritereValeur.get(j).getText());
            }
            LOGGER.info("Récupération terminé");
            listMapCritèreTableau.add(listValeurCriteres);
        }
        return listMapCritèreTableau;
    }

}
