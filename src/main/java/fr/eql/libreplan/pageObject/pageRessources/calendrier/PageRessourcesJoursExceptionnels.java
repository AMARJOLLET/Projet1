package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageRessourcesJoursExceptionnels extends AbstractFullPage {
    public PageRessourcesJoursExceptionnels(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    // Titre
    public String titreDeLaPage(WebDriverWait wait, String idCommune){
        return wait.until(ExpectedConditions.elementToBeClickable(By.id(idCommune + "j4-cap"))).getText();
    }

}
