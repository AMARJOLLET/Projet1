package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class PageRessourcesJoursExceptionnelsCreer extends AbstractFullPage {
    public PageRessourcesJoursExceptionnelsCreer(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap"))).getText();
    }

    public String titreFormulaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "y4-hm"))).getText();
    }

    // WebElement
    public WebElement inputCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idCommune+"85")));
    }

    public WebElement checkboxCode(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"95-real")));
    }

    public WebElement inputNom(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"c5")));
    }

    public WebElement selectCouleur(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune+"g5")));
    }

    public List<String> couleurException(WebDriverWait wait, String idCommune){
        List<WebElement> listCouleurException = driver.findElements(By.xpath("//td[@id='"+idCommune+"f5-chdextr']//span"));
        List<String> listCouleurExceptionString = new ArrayList<>();
        for (WebElement we : listCouleurException){
            listCouleurExceptionString.add(we.getText());
        }
        return listCouleurExceptionString;
    }

    public WebElement inputEffortStandart(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id("o5-real")));
    }

    public WebElement inputEffortSupplementaire(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id("u5-real")));
    }




    // Tableau
    public List<String> recuperationLibelleTableau(String idCommune){
        List<String> listLibelleTableauString = new ArrayList<>();
        List<WebElement> listLibelleTableau = driver.findElements(By.xpath("//tbody[@id='"+idCommune+"45']//div/span"));
        for (WebElement we : listLibelleTableau) {
            listLibelleTableauString.add(we.getText());
        }
        return listLibelleTableauString;
    }





}
