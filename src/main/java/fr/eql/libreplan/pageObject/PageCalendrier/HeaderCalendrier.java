package fr.eql.libreplan.pageObject.PageCalendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
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

public class HeaderCalendrier extends AbstractFullPage {

    public HeaderCalendrier(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    public List<WebElement> listOngletProjet(WebDriverWait wait, String idCommune){
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//tr[@id='"+idCommune+"r3-chdex']//span[not(@style='display:none;')])[1]")));
        return driver.findElements(By.xpath(
                "//tr[@id='"+idCommune+"r3-chdex']//span[not(@class=\"perspective hidden z-button\")]   "));
    }



/*######################################################################################################################
                                                    METHODES
######################################################################################################################*/
    public List<String> recuperationListeOngletProjet(WebDriverWait wait, String idCommune){
        List<String> listOngletProjetString = new ArrayList<>();
        for(WebElement onglet : listOngletProjet(wait, idCommune)){
            listOngletProjetString.add(onglet.getText());
        }
        return listOngletProjetString;
    }

    public Map<String, WebElement> mapOngletProjet(WebDriverWait wait, String idCommune){
        List<WebElement> listOngletProjet = listOngletProjet(wait, idCommune);
        Map<String, WebElement> mapOngletProjet = new HashMap<>();
        for(WebElement ongletProjet : listOngletProjet){
            mapOngletProjet.put(ongletProjet.getText(), ongletProjet);
        }
        return mapOngletProjet;
    }



}
