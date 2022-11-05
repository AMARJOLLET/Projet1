package fr.eql.libreplan.pageObject.pageRessources.calendrier;

import fr.eql.libreplan.pageObject.AbstractFullPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageRessourcesCalendrier extends AbstractFullPage {
    public PageRessourcesCalendrier(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }



}
