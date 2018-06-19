package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map;

public class po_testcase_base {
    WebDriver driver;
    @BeforeTest
    @Parameters({"driverPath", "browserType"})
    public void BeforeTest(String driverPath, String browserType){

        if (browserType.equals("fireFox")){
            System.setProperty("webdriver.firefox.driver",driverPath + "geckodriver");
            driver = new FirefoxDriver();
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("incognito");
            //options.addArguments("--headless");

            Map<String, Object> prefs = new HashMap<String,Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            options.setExperimentalOption("prefs",prefs);

            System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver");

            driver = new ChromeDriver(options);

        }
        System.out.println("Driver is initialized!");

    }

    @AfterTest
    public void AfterTest(){
        System.out.println("---------------Test is Completed-------------------");
        driver.quit();
    }
}
