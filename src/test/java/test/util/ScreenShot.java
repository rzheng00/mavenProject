package test.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.sql.Timestamp;

public class ScreenShot {

    public ScreenShot(){

    }

    public ScreenShot(WebDriver driver, String path ) throws Exception{
        TakesScreenshot ts = (TakesScreenshot)driver;
        File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);

        // create a file at destination
        File dstScreenshot = new File(path+"/screen_shots"+ new Timestamp(System.currentTimeMillis())+".jpg");

        //Copy file to destination
        FileUtils.copyFile(srcScreenshot, dstScreenshot);
    }

}
