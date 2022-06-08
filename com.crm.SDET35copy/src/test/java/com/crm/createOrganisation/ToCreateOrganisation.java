package com.crm.createOrganisation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.crm.objectRepository.CreateNewOrganisationPage;
import com.crm.objectRepository.HomePage;
import com.crm.objectRepository.LoginPage;
import com.crm.objectRepository.OrganisationInfoPage;
import com.crm.objectRepository.OrganisationPage;

import genericUtilities.ExcelUtility;
import genericUtilities.FileUtility;
import genericUtilities.JavaUtility;
import genericUtilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ToCreateOrganisation {
	
public static void main(String[] args) throws Throwable {
	WebDriver driver=null;
	
	JavaUtility jLib = new JavaUtility();
	FileUtility fLib = new FileUtility();
	ExcelUtility eLib = new ExcelUtility();
	WebDriverUtility wLib = new WebDriverUtility();
	
	//Fetching data from PropertyFile
    String URL = fLib.getPropertKeyValue("url");
    String USERNAME = fLib.getPropertKeyValue("username");
    String PASSWORD = fLib.getPropertKeyValue("password");
    String BROWSER = fLib.getPropertKeyValue("browser");
	
	//To get random number
	int randNum = jLib.getRandomNumber();
	
	//Fetching data from excelSheet
	String orgName = eLib.readDataFromExcel("Sheet1", 4, 2)+randNum;
	
	//launch the browser(open)
	if(BROWSER.equalsIgnoreCase("firefox"))
	{
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
	}else if(BROWSER.equalsIgnoreCase("chrome"))
	{
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
	}else {
		driver=new ChromeDriver();
	}
	//use implicitly wait condition
	wLib.waitForPageToLoad(driver);
	//enter the url of the application
	driver.get(URL);
	
	//enter the username,password and click on submit button
	LoginPage loginPage = new LoginPage(driver);
	loginPage.loginToAppli(USERNAME, PASSWORD);
	
	//click on organisation link
	HomePage homePage = new HomePage(driver);
	homePage.getOrganisationsLnk().click();
	
	//click on create organisation icon
	OrganisationPage orgPage=new OrganisationPage(driver);
	orgPage.clickOnCreateOrgLkp();
	
	//enter the organisation name and click on save button
	CreateNewOrganisationPage cnewOrgPage=new CreateNewOrganisationPage(driver);
	cnewOrgPage.enterOrgInfo(orgName);
	
	//verify whether the organisation is created or not
	OrganisationInfoPage orgInfoPage = new OrganisationInfoPage(driver);
	String presentOrgName = orgInfoPage.getOgnHeaderTxt().getText();
	if(presentOrgName.contains(orgName))
	{
		System.out.println("organisation is created");
	}else {
		System.out.println("organisation is not created");
	}
	//mouse over on Administrator link and click on signout link
	homePage.logout(driver);
	//close the application
	driver.close();
}
}