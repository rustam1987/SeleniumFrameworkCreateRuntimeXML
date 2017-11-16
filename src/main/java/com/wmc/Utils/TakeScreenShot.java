package com.wmc.Utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenShot {
	public static void takeScreenShots(WebDriver driver,String screenShotName){
	  TakesScreenshot ts=(TakesScreenshot)driver;
	   File source=ts.getScreenshotAs(OutputType.FILE);
	   String path=System.getProperty("user.dir")+"/ErrorCapture/"+screenShotName+".png";
	   File destination=new File(path);
	   try {
		FileUtils.copyFile(source, destination);
	} catch (IOException e) {
		
		e.printStackTrace();
	}

}
}
