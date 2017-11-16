package com.wmc.UtilCommon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.mysql.jdbc.Statement;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Generic_methods {
	static Logger log=Logger.getLogger(Generic_methods.class.getName());
	public WebDriver driver;
	public static ExtentReports extents;
	public static ExtentTest test;
	HashMap<Integer,String> map;
	FileWriter flw;
	BufferedWriter bf;
	static{
		/*Calendar cal = Calendar.getInstance(Locale.US);
		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");*/
		extents=new ExtentReports(System.getProperty("user.dir")+"\\test-output\\TestResult.html");
	}
	public void getResult(ITestResult result){
		if(result.getStatus()==ITestResult.SUCCESS){
			test.log(LogStatus.PASS, result.getName());
		}else if(result.getStatus()==ITestResult.SKIP){
		test.log(LogStatus.SKIP, result.getName());
	}else if(result.getStatus()==ITestResult.FAILURE){
		test.log(LogStatus.FAIL, result.getName());
		
	}
	}
	public void fn_LaunchBrowser(String browserType){
		
	    getLoggerConfiguratot();
		if(browserType.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\browserDriver\\chromedriver_win32\\chromedriver.exe");
			driver =new ChromeDriver();
			log.info(browserType+":is Launching...");
		}else if(browserType.equalsIgnoreCase("firefox") ||browserType.equalsIgnoreCase("ff")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\browserDriver\\firefox\\geckodriver.exe");
			driver = new FirefoxDriver();
			
			log.info(browserType+":is Launching...");
		}else if(browserType.equalsIgnoreCase("ie")||browserType.equalsIgnoreCase("InternetExplorer")){
			String dir=System.getProperty("user.dir");
			System.setProperty("webdriver.ie.driver", dir+"\\browserDriver\\IEDriverServer_x64_3.6.0\\IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			driver = new InternetExplorerDriver();
			log.info(browserType+":is Launching...");
		}
		
		else{
			log.info("Provided browser Type invalid, Please check ");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(getPropertyVal("ImplicitWait")), TimeUnit.SECONDS);	
	}
	public boolean waitPageLoaded(){
		boolean isLoaded=false;
		int TimeOut=Integer.parseInt(getPropertyVal("timeOutInsecond"));
		int  iAttempt=0;
		
	try{
		while(iAttempt<3){
			log.info("Waiting For Page Load via JS Attempt"+iAttempt);
			ExpectedCondition <Boolean> pageLoadCondition=new ExpectedCondition<Boolean>(){

				public Boolean apply(WebDriver driver) {
					
					return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
				}
				
			};
			WebDriverWait wait=new WebDriverWait(driver,TimeOut);
			isLoaded=wait.until(pageLoadCondition);
			if(!isLoaded){
				driver.navigate().refresh();
			}
			iAttempt++;
			}
	      }
		catch(Exception e){
			
		log.info("Error Occured waiting for Page Load"+driver.getCurrentUrl());	
		log.error(e.getMessage());
		}
      return isLoaded;
		
		
	}
    public String getPropertyVal(String val){
	String path=System.getProperty("user.dir");
	Properties p=null;
	 try {
		File fl=new File(path+"\\Config\\Config.properties");
		FileInputStream infl=new FileInputStream(fl);
		p=new Properties();
		try {
			p.load(infl);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
		System.out.println("Please check property file");
	}
	 
	 return p.getProperty(val);
	 
 }
 public void getLoggerConfiguratot(){
		//String log4jPath=System.getProperty("user.dir")+"\\log4j.properties";
	   String path="log4j.properties";
		PropertyConfigurator.configure(path);
		
	}
 public  void getUrl(String url){
	 driver.navigate().to(url);
 }
 public String getTextFromWebElemet(WebElement element){
	return element.getText();
	 
 }
 public void clickElement(WebElement element){
	 element.click();
 }
 public void closeAllExistingBrowser(){
	 driver.quit();
 }
 public void scrollDown(){
	 JavascriptExecutor jse = (JavascriptExecutor)driver;
	 jse.executeScript("scroll(0, 250);");
 }
 public void switchWindowByPageTitle(String title){
	String pageTitle= getPropertyVal(title);
	String currentWindow=driver.getWindowHandle();
	for(String winHandle : driver.getWindowHandles()){
		   if (driver.switchTo().window(winHandle).getTitle().equals(pageTitle)) {
		     //This is the one you're looking for
		     break;
		   } 
		   else {
		      driver.switchTo().window(currentWindow);
		   } 
		}
      }
 public void getWebTableDataPutHashMap(){
	
	List <WebElement> list=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[1]"));
	List <WebElement> firstName=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[2]"));
	int rowNum=list.size();
	map=new HashMap<Integer,String>();
	
	for(int i=0;i<rowNum;i++){
		
		map.put(Integer.parseInt(list.get(i).getText()), firstName.get(i).getText());
	}
	
 }
 public void getWebTableDataWriteExcelFile() throws FilloException{
	 List <WebElement> CustomerID=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[1]"));
	 List <WebElement> CustomerName	=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[2]"));
	 List <WebElement> ContactName	=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[3]"));
	 List <WebElement> Address	=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[4]"));
	 List <WebElement> City	=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[5]"));
	 List <WebElement> PostalCode	=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[6]"));
	 List <WebElement> Country	=driver.findElements(By.xpath("//*[@id='divResultSQL']/div/table/tbody/tr//td[7]"));
	 int rowNum=CustomerID.size();
	 
	 
	 Fillo fillo=new Fillo();
	 String path=System.getProperty("user.dir")+"\\test-output\\WebTableData.xlsx";
	 Connection  con=fillo.getConnection(path);
	
	 try {
		
		String DelQuery="Delete from data";
		con.executeUpdate(DelQuery);
		
		
		for(int i=0;i<rowNum;i++){
			String customerID=CustomerID.get(i).getText();
			
			String customerName=CustomerName.get(i).getText();
			customerName=customerName.replaceAll("'", "");
			
			String contactName=ContactName.get(i).getText();
			contactName=contactName.replaceAll("'", "");
			
			String address=Address.get(i).getText();
			address=address.replaceAll("'", "");
			
			String city=City.get(i).getText();
			city=city.replaceAll("'", "");
			
			String postalCode=PostalCode.get(i).getText();
			postalCode=postalCode.replaceAll("'", "");
			String country=Country.get(i).getText();
			
			country=country.replaceAll("'", "");
			
			con.executeUpdate("INSERT INTO data(CustomerID,CustomerName,ContactName,Address,City,PostalCode,Country) VALUES("+"'"+customerID+"'"+","+"'"+customerName+"'"+","+"'"+contactName+"'"+","+"'"+address+"'"+","+"'"+city+"'"+","+"'"+postalCode+"'"+","+"'"+country+"'"+")");
			
		}	
	} catch (FilloException e) {
		e.printStackTrace();
	}
	 finally{
		 con.close();
	 }
		
	}
 public void writeHashMapValToTextFile() throws IOException {
	try{
		
		 File fl=new File(System.getProperty("user.dir")+"\\test-output\\TabledataUsingHashMap.txt");
	     flw=new FileWriter(fl);
		 bf=new BufferedWriter(flw);
		 
		 for(Map.Entry<Integer, String> mp:map.entrySet()){
			 
			 bf.write(mp.getKey()+" "+mp.getValue());
			 bf.newLine(); 
		 }
		
	}catch(Exception e){
		
	}
	finally{
		bf.flush();
		bf.close();
		flw.close();
		
	}
 }
public void sendUserText(WebElement elemenet,String data){
	elemenet.sendKeys(data);
}
 

}
