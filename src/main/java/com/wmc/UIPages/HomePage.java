package com.wmc.UIPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	private WebDriver driver;
	public  HomePage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="/html/body/div[1]/div[2]/div")
	public WebElement  header;
	
	@FindBy(linkText="Learn SQL")
	public WebElement LearnSQL;
	
	@FindBy(linkText="Try it Yourself »")
	public WebElement TryItYourself;
	
	@FindBy(xpath="/html/body/div[2]/div/div[1]/div[1]/button")
	public WebElement RunSql;
	

	
	
	
	
}
