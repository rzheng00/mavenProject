package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class pageObject {

    protected WebDriver driver;


    public pageObject(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
