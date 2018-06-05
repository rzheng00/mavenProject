package test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sikuli.script.Screen;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Pattern;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class herokuapp {
	public WebDriver driver;
	
	@BeforeClass
	@Parameters({"driverPath", "browserType", "enableLog"})
	public void beforeClass(String driverPath, String browserType, boolean enableLog) {
		if (browserType.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",driverPath+"gecoDriver");
			driver = new FirefoxDriver();
		} else {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("incognito");
			 System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
			  if (enableLog) {
				  System.setProperty("webdriver.chrome.logfile", "/Users/qa/eclipse-workspace/mavenProject/log/log.out");
				  System.setProperty("webdriver.chrome.verboselogging", "true");
			  }
			  driver = new ChromeDriver(options);
			  driver.manage().window().maximize();
		}
			
	}
	
	@AfterClass
	public void afterClass() {
		driver.close();
		
	}
	
	@Test (priority =1)
	@Parameters({"baseURL"})
	public void abTesting(String baseURL) {
		driver.get(baseURL+"abtest");
		//validate if the over lay is showing
		Assert.assertEquals(driver.findElement(By.xpath("*/body/div[2]/a/img")).isDisplayed(),true,"can not see the prom"); 
		System.out.println(driver.findElement(By.xpath("*/body/div[2]/a/img")).getText());
	}
	
	@Test (priority=2)
	@Parameters("baseURL")
	public void basicAuth(String baseURL) {
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		Screen screen = new Screen();
		//Pattern image1= new Pattern("field.png");
		//Pattern image2= new Pattern("signin.png");
		try {
			System.out.println(ImagePath.getBundlePath());

			screen.click("field.png");
			screen.type("admin");
			
			screenshot(driver,"/Users/qa/eclipse-workspace/lib");
			//ImagePath.add("herokuapp/images");
			//screen.wait(image1,10);
			
			//screen.type(image1,"admin");
		//Take a screenshot
		 
			  screenshot(driver,"/Users/qa/eclipse-workspace/lib");
		
		//screen.type(image1,"admin2");
		//Take a screenshot
		  
			  screenshot(driver,"/Users/qa/eclipse-workspace/lib");
			  
		//	  screen.click(image2);
		  
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		driver.getTitle();
		//Assert.assertEquals(driver.findElements(By.tagName("H3"))[0].getText().contains("Basic Auth"), true, "can not find");
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
