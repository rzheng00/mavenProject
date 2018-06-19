package test;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.signUpPage;
import pages.receiptPage;

import java.util.HashMap;
import java.util.Map;

//import org.assertj.core.*;

public class po_checklogin extends po_testcase_base{
    WebDriver driver;
    public po_checklogin(){
        this.driver=super.driver;
    }

    @Test
    @Parameters({"url"})
    public void signUpChecking(String url){
        driver.get(url);
        signUpPage s = new signUpPage(driver);
        Assert.assertEquals(s.isInitialized(),true,"signup page is not init. yet");

        s.enterName("Test123", "QAui");
        s.enterAddress("dgdfgygtrrgt","92121");

        s.submit();

        receiptPage r = new receiptPage(driver);
        Assert.assertEquals(r.isInitialized(),true,"receipt page is not init. yet");
        System.out.println("teh info is:" + r.getConfirmaton());


    }

    @BeforeTest
    @Parameters({"driverPath","browserType"})
    public void BeforeTest(String driverPath, String browserType){
        super.BeforeTest(driverPath, browserType);
        this.driver=super.driver;
    }

    @AfterTest
    public void AfterTest(){
        super.AfterTest();
    }

/*
    @BeforeTest
    @Parameters({"driverPath","browserType"})
    public void BeforeTest(String driverPath, String browserType){
        if (browserType.equals("fireFox")) {
         // init. Firefox driver.
            System.setProperty("webdriver.firefox.driver", driverPath+"geckodriver");
            driver = new FirefoxDriver();

        } else {
            // init. Chrome Driver
            ChromeOptions options = new ChromeOptions();
            options.addArguments("incognito");

            //options.addArguments("--headless");

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            options.setExperimentalOption("prefs", prefs);

            System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver");

            driver = new ChromeDriver(options);
        }

        System.out.println("Driver is initialized!");


    }

    @AfterTest
    public void AfterTest(){
        System.out.println("-------------Test Coomplete-----------");
        driver.quit();
    }
    */
}
