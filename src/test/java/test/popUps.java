package test;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.sikuli.script.*;
import test.util.ScreenShot;

//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.asserts.SoftAssert;


import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.*;


public class popUps {
    WebDriver driver;
    WebDriverWait wait;

    @Test (priority=0, enabled=true)
    @Parameters ("url")
    public void gotoPage(String url){

        //url="ttps://www.httpwatch.com/httpgallery/authentication/"

        SoftAssertions sa = new SoftAssertions();
        driver.get(url);
        sa.assertThat(driver.getTitle()).isEqualTo("HTTP Authentication | HttpWatch");
        sa.assertThat(driver.findElement(By.id("displayImage")).isEnabled());

        String mainWindow = driver.getWindowHandle();
        System.out.println("mainWindow is " + mainWindow);

        driver.findElement(By.cssSelector("#displayImage")).click();
        //wait.until(ExpectedConditions.alertIsPresent());


        System.out.println("title after pop up is: " + driver.getTitle());

        String s= driver.getWindowHandles().toString();
        System.out.println("S message is "+s);
        //sa.assertAll();
        Assert.assertEquals(true,true);
        System.out.println("done with test Part 1");

        //Sikuli
        Screen screen = new Screen();
        Pattern username = new Pattern("/Users/qa/mavenProject/src/test/resources/elements/username.png");
        Pattern password = new Pattern ("/Users/qa/mavenProject/src/test/resources/elements/password.png");
        Pattern signin = new Pattern("/Users/qa/mavenProject/src/test/resources/elements/signin.png");
        Pattern cancel = new Pattern("/Users/qa/mavenProject/src/test/resources/elements/cancel.png");

        System.out.println(ImagePath.getBundlePath());
        driver.findElement(By.id("displayImage")).click();



        try {
            Thread.sleep(1000);
            screen.wait(username, 10);
            //invalid usernmae
            screen.type(username,"xyz");
            screen.type(password, "testts");
            screen.click(signin);
            Thread.sleep(500);
            sa.assertThat(screen.exists(username)).isNull();
            //valid username
            screen.type(username,"httpwatch");
            screen.type(password, "testts");
            screen.click(signin);
            Thread.sleep(500);
            sa.assertThat(screen.exists(username)).isNotNull();
            sa.assertAll();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }



    }

    @Test (enabled = false)
    @Parameters("url")
    public void AlertWindow(String url){
        url="https://www.w3schools.com/js/tryit.asp?filename=tryjs_alert";
        //Navigate to URL
        driver.navigate().to(url);
        driver.manage().window().maximize();

        //Switch to iframeResult iframe because all elements located in this iframe
        //It will be described in next topics
        driver.switchTo().frame("iframeResult");

        //Find "Try it" button
        WebElement alertButton = driver.findElement(By.cssSelector("html>body>button"));

        //Click alert button ("Try it" button)
        alertButton.click();

        //Alert Message (Expected Text)
        String expectedAlertMessage = "I am an alert box!";

        //Captured Alert Text (Actual Text)
        String actualAlertMessage = driver.switchTo().alert().getText();

        //Assertion
        assertThat(expectedAlertMessage.equals(actualAlertMessage));

        //Accept the alert (Click OK)
        driver.switchTo().alert().accept();
    }

    @Test(enabled = false)
    @Parameters("url")
    public void ConfirmationPage(){
        String url = "https://www.w3schools.com/js/tryit.asp?filename=tryjs_confirm";
        driver.get(url);
        //Switch to iframeResult iframe because all elements located in this iframe
        //It will be described in next topics
        driver.switchTo().frame("iframeResult");

        //Find "Try it" button
        WebElement confirmButton = driver.findElement(By.cssSelector("html>body>button"));

        //Actual Text Element
        WebElement actualConfirmMessage = driver.findElement(By.cssSelector("#demo"));

        //******************************
        // Accept Test (Test Scenario-1)
        //******************************

        //Click confirm button ("Try it" button)
        confirmButton.click();

        //Accept the alert (Click Ok button)
        driver.switchTo().alert().dismiss();

        //Assertion
        assertThat(actualConfirmMessage.getText().equals("You pressed cancel!"));

        //******************************
        // Dismiss Test (Test Scenario-2)
        //******************************

        //Click confirm button ("Try it" button)
        confirmButton.click();

        //Accept the alert (Click Cancel)
        driver.switchTo().alert().dismiss();

        //Assertion
        assertThat(("You pressed Cancel!").equals(actualConfirmMessage.getText()));

    }

    @BeforeTest
    @Parameters({"driverPath","browserType"})
    public void BeforeTest(String driverPath, String browserType){

        if (browserType.equals("Firefox")){

            System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
            driver = new FirefoxDriver();

        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("incognito");
            //add for running in Jenkin as background
            //options.addArguments("--headless");

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);


            System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
            //System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        wait = new WebDriverWait(driver,10);
        System.out.println("init done-------------");
    }

    @AfterTest
    public void AfterTest(){
        /*
        try {
            screenshot(driver, "/Users/qa/mavenProject/test-output");
        } catch (Exception e){
            System.out.println(e.toString());
        }
        */
        driver.quit();

        System.out.println("------Tests are completed---------------------------------");

    }

    public static void screenshot(WebDriver driver, String path ) throws Exception{
        TakesScreenshot ts = (TakesScreenshot)driver;
        File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);

        // create a file at destination
        File dstScreenshot = new File(path+"/screen_shots"+ new Timestamp(System.currentTimeMillis())+".jpg");

        //Copy file to destination
        FileUtils.copyFile(srcScreenshot, dstScreenshot);
    }


}
