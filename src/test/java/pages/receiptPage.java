package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;


public class receiptPage extends pageObject {
    WebDriver driver;

    @FindBy(tagName= "h1")
    private WebElement header;

    public receiptPage(WebDriver driver){
        super(driver);
    }

    public boolean isInitialized (){
        return header.isDisplayed();
    }

    public String getConfirmaton(){
        return header.getLocation().toString();
    }

}
