package fr.eql.libreplan.pageObject;


import org.openqa.selenium.WebDriver;
import utils.InstanciationDriver;
import utils.OutilsProjet;
import utils.SeleniumTools;

public abstract class AbstractFullPage extends InstanciationDriver {
    // Objet
    protected OutilsProjet outilsProjet = new OutilsProjet();
    protected SeleniumTools seleniumTools = new SeleniumTools(driver);

    public AbstractFullPage(WebDriver driver) {
        super(driver);
    }


    public HeaderPage getHeader() {
        return new HeaderPage(driver);
    }
}

