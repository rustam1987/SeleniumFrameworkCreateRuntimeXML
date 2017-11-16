package com.wmc.UIPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageAmazon {
	private WebDriver driver;
	public  HomePageAmazon(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//*[@id='nav-link-accountList']/span[1]")
	public WebElement  SingIn;
	
	@FindBy(id="createAccountSubmit")
	public WebElement CreateAccountbutton;
	
	@FindBy(id="ap_customer_name")
	public WebElement custName;
	
	@FindBy(id="ap_email")
	public WebElement email;
	
	@FindBy(id="ap_password")
	public WebElement ap_password;
	
	@FindBy (id="ap_password_check")
	public WebElement ap_password_check;

	
	
}
