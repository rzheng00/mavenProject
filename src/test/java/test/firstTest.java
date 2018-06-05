package test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;


 
public class firstTest {
 
 public WebDriver driver;
 public WebDriverWait wait; 	
 
  @Test (priority=0)
  public void openGoogleHomepage() {
	  
	  driver.get("https://www.googl.com/");
	  System.out.println("Accessing " + driver.getTitle());
	  Assert.assertEquals("Google", driver.getTitle(), "Unexpected Site is loaded " + driver.getTitle());
  }
  
  @Test (priority=1)
  public void hitGmailLink() {
	System.out.println("Accessing Gmail home page");  
	driver.findElement(By.linkText("Gmail")).click();
	System.out.println("Navigate to " + driver.getTitle());
	
	Assert.assertEquals("Gmail - Free Storage and Email from Google", driver.getTitle(),"Unexpected Site is loaded " + driver.getTitle());
  }
  
  @Test (priority=3)
  @Parameters({"username","password","driverPath"})
  public void loggingIn(String username, String password, String driverPath) {
	JavascriptExecutor js= (JavascriptExecutor) driver;
	
	//Click the sign in button on the Gmail home page
	driver.findElement(By.xpath("/html/body/nav/div/a[2]")).click();
	System.out.println("Navigate to " + driver.getTitle());
	
	//Valid the user input field is available on log in page
	Assert.assertEquals(driver.findElement(By.tagName("INPUT")).isEnabled(), true, "Usernname field can not be accessed");  
	Assert.assertEquals(driver.findElement(By.id("headingText")).getText(), "Sign in", "Unexpected page is loaded as " + driver.findElement(By.id("headingText")).getText());
	System.out.println("u is" + username);
	driver.findElement(By.tagName("INPUT")).sendKeys(username);
	
	// Click the next button to password input page
	Assert.assertEquals(driver.findElement(By.id("identifierNext")).isEnabled(), true,"Next step can not be accessed");
	driver.findElement(By.id("identifierNext")).click();
	
	
	//Valid the password field is available on welcome page
	try{
		Thread.sleep(1000);;
	} catch (Exception e) {
		e.printStackTrace();	
	}
	
	Assert.assertEquals(driver.findElement(By.id("headingText")).getText(), "Welcome","Unexpected page is loaded as " + driver.findElement(By.id("headingText")).getText());
	driver.findElement(By.id("headingText")).click();
	System.out.println("test pont1");
	
	//Assert.assertEquals(driver.findElement(By.tagName("INPUT"))..isEnabled(), true, "Password field can not be accessed");
	//String script;
	js.executeScript("if (document.getElementsByName(\"password\").length>0) {document.getElementsByName(\"password\")[0].focus()};");
	//take a screen shot
		try {
			  screenshot(driver,driverPath);
		  } catch (Exception e){
			  e.printStackTrace();
		  }

	
	js.executeScript("if (document.getElementsByTagName(\"INPUT\").length>0) {document.getElementsByTagName(\"INPUT\")[0].value='fx040506'};",password);
	
	//driver.findElement(By.tagName("INPUT")).sendKeys(password);
	//System.out.println("p is" + driver.findElement(By.tagName("INPUT")).getText());
	
	//take a screen shot
	try {
		  screenshot(driver,driverPath);
	  } catch (Exception e){
		  e.printStackTrace();
	  }

	//valid if the next button is there
	Assert.assertEquals(driver.findElement(By.id("passwordNext")).isEnabled(), true, "Can not submit form");
	System.out.println("test point");
	
	//click on the next button
	js.executeScript("arguments[0].click()", driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/content/span")));	
	System.out.println(driver.getTitle());
  }
  
  @Test (priority=4)
  public void loggingIn () {
	  driver.findElement(By.tagName("INPUT"));
  }
  
  @BeforeClass
  @Parameters ({"browserType","driverPath","enableLog"})
  public void beforeClass(String browserType, String driverPath,boolean enableLog ) {
	  System.out.println(browserType);
	  if ( browserType.equals("firefox")) {
		  System.setProperty("webdriver.gecko.driver",  driverPath+"geckodriver");
		  driver = new FirefoxDriver();
	  } else {
		  ChromeOptions options = new ChromeOptions();
		  options.addArguments("incognito");
		  options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		  System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
		  if (enableLog) {
			  System.setProperty("webdriver.chrome.logfile", "/Users/qa/eclipse-workspace/mavenProject/log/log.out");
			  System.setProperty("webdriver.chrome.verboselogging", "true");
		  }
		  driver = new ChromeDriver(options);
		  driver.manage().window().maximize();
	  }
	  wait = new WebDriverWait(driver,10);
	  
   
  }
 
  @AfterClass
  @Parameters("driverPath")
  public void afterClass(String driverPath) {
	//Take a screenshot
	  try {
		  screenshot(driver,driverPath);
	  } catch (Exception e){
		  e.printStackTrace();
	  }
	  
	  driver.quit();
  }
  
 
  public static void screenshot(WebDriver driver, String path ) throws Exception{
	  TakesScreenshot ts = (TakesScreenshot)driver;
	  File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);
	  
	  // create a file at destination
	  File dstScreenshot = new File(path+"/screenshot"+ new Timestamp(System.currentTimeMillis())+".jpg");
	  
	  //Copy file to destination
	  FileUtils.copyFile(srcScreenshot, dstScreenshot);
  }
 
}