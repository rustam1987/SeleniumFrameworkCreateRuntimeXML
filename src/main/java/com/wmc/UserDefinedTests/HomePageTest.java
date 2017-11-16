package com.wmc.UserDefinedTests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.codoid.products.exception.FilloException;
import com.relevantcodes.extentreports.LogStatus;
import com.wmc.UIPages.HomePage;
import com.wmc.UIPages.HomePageAmazon;
import com.wmc.UtilCommon.Generic_methods;
import com.wmc.Utils.SqlDataBaseCommon;
import com.wmc.Utils.TakeScreenShot;

import junit.framework.Assert;

public class HomePageTest extends Generic_methods {

	static Logger log=Logger.getLogger(HomePageTest.class.getName());
	String env;
	HomePage oHomePage;
	HomePageAmazon oHomePageAmazon;
	SoftAssert oSoftAsser;
	
	@BeforeSuite
	public void beforeSuite(){
		env=getPropertyVal("environment");
	}
	@BeforeMethod
	public void beforeMethods(Method result){
		test=extents.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName());
	}
	@AfterMethod
	public void afterMethod(ITestResult result){
		getResult(result);
		if(result.getStatus()==ITestResult.FAILURE){
			test.log(LogStatus.FAIL, result.getName());
			TakeScreenShot.takeScreenShots(driver, result.getName());
		}
		closeAllExistingBrowser();
	}
	@AfterClass
	public void afterClass(){
		extents.endTest(test);
		extents.flush();
	}
	
	@Test(groups={"HomePage"},priority=1)
	public void validateHomePageComponents() throws Exception{
		getLoggerConfiguratot();
		fn_LaunchBrowser("chrome");
		if(env.equalsIgnoreCase("PROD")){
			String url=getPropertyVal("Prod_URL");
			getUrl(url);
			waitPageLoaded();
			log.info(url);
		}else if(env.equalsIgnoreCase("STAGE")){
			String url=getPropertyVal("Stage_URL");
			getUrl(url);
		}
		else{
			log.info("Provided environment is not valid,please check");
			}
		oHomePage=new HomePage(driver);
		String actual=getTextFromWebElemet(oHomePage.header);
		String expected=getPropertyVal("ExpectedHeader");
		oSoftAsser=new SoftAssert();
		Thread.sleep(5000);
		oSoftAsser.assertEquals(actual, expected);
		
		Thread.sleep(5000);
		clickElement(oHomePage.LearnSQL);
		scrollDown();
		clickElement(oHomePage.TryItYourself);
		switchWindowByPageTitle("pageTitle");
		waitPageLoaded();
		clickElement(oHomePage.RunSql);
		Thread.sleep(5000);
		
	    try {
	    	getWebTableDataWriteExcelFile();
			getWebTableDataPutHashMap();
			writeHashMapValToTextFile();
		} catch (IOException e) {

			e.printStackTrace();
		}
	    oSoftAsser.assertAll();
	}	
	@Test(groups={"AmazonAccount"},description="get Test data From Database")
	public void creaAccountUsingDataBase() throws InterruptedException, SQLException{
		
		getLoggerConfiguratot();
		fn_LaunchBrowser("chrome");
		if(env.equalsIgnoreCase("PROD")){
			String url=getPropertyVal("Prod_URL_Amazon");
			getUrl(url);
			waitPageLoaded();
			log.info(url);
		}else if(env.equalsIgnoreCase("STAGE")){
			String url=getPropertyVal("Prod_URL_Amazon");
			getUrl(url);
		}
		else{
			log.info("Provided environment is not valid,please check");
			}
		oHomePageAmazon=new HomePageAmazon(driver);
		Thread.sleep(5000);
		clickElement(oHomePageAmazon.SingIn);
		waitPageLoaded();
		clickElement(oHomePageAmazon.CreateAccountbutton);
		SqlDataBaseCommon oSqlDataBaseCommon=new SqlDataBaseCommon();
		
		String name=oSqlDataBaseCommon.getSingDataFromDataBase(2);
		sendUserText(oHomePageAmazon.custName, name);
		
		String email=oSqlDataBaseCommon.getSingDataFromDataBase(4);
		sendUserText(oHomePageAmazon.email, email);
		
		String password=oSqlDataBaseCommon.getSingDataFromDataBase(5);
		sendUserText(oHomePageAmazon.ap_password, password);
		
		String Conpassword=oSqlDataBaseCommon.getSingDataFromDataBase(5);
		sendUserText(oHomePageAmazon.ap_password_check, Conpassword);
	
	}
	
}