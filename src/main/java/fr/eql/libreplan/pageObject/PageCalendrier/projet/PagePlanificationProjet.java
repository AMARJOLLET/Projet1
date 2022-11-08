package fr.eql.libreplan.pageObject.PageCalendrier.projet;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagePlanificationProjet extends AbstractFullPage {

    public PagePlanificationProjet(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

/*######################################################################################################################
                                                  WEBELEMENT
######################################################################################################################*/
    public WebElement selectDate(WebDriverWait wait){
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//table[@class=\"z-hbox\"]//select")));
    }



    public List<WebElement> listLibelleTaskDetail(WebDriverWait wait){
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[@class=\"z-tree-header\"]//tbody[not(contains(@style,'visibility:hidden'))]//th")));
    }

    public List<WebElement> listRowTaskDetail(WebDriverWait wait){
        return driver.findElements(By.xpath("//tbody[@class=\"z-treechildren\"]/tr[contains(@class,'taskdetail')]"));
    }

    // TIME TRACKER
    public List<WebElement> listLibelleTimetracker(WebDriverWait wait){
        return driver.findElements(By.xpath("//div[@id=\"timetrackerheader\"]//tr[@class=\"z-columns\"]/th"));
    }

    public List<WebElement> listDecoupageTimetracker(WebDriverWait wait){
        return driver.findElements(By.xpath(
                "//div[@id=\"timetrackerheader\"]//tr[@class=\"z-columns\"]/td"));
    }




/*######################################################################################################################
                                                   METHODES
######################################################################################################################*/

    public void selectionnerTypeDate(WebDriverWait wait, String typeDate){
        Select select = new Select(selectDate(wait));
        select.selectByVisibleText(typeDate);
    }




    // RECUPERATION TASK
    public Map<String, Map<String, WebElement>> recuperationValeurTaskDetail(WebDriverWait wait){
        return outilsProjet.recuperationValeurTableauInput(listLibelleTaskDetail(wait), listRowTaskDetail(wait));
    }

    // RECUPERATION TIMETRACKER
    public List<List<String>> recuperationTimeTracker(WebDriverWait wait){
        ArrayList<List<String>> listRecuperationTimeTracker = new ArrayList<>();
        List<String> listLibelleTimetrackerString = new ArrayList<>();
        List<String> listDecoupageTimetrackerString = new ArrayList<>();

        for(WebElement libelleTimetracker : listLibelleTimetracker(wait)){
            listLibelleTimetrackerString.add(libelleTimetracker.getText());
        }
        for(WebElement decoupageTimetracker : listDecoupageTimetracker(wait)){
            listDecoupageTimetrackerString.add(decoupageTimetracker.getText());
        }
        LOGGER.info("Récupération fini");

        listRecuperationTimeTracker.add(listLibelleTimetrackerString);
        listRecuperationTimeTracker.add(listDecoupageTimetrackerString);

        return listRecuperationTimeTracker;

    }

}
