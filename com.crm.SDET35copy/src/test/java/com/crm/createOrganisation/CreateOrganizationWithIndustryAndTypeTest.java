package com.crm.createOrganisation;

import java.io.FileInputStream;
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
import org.openqa.selenium.support.ui.Select;

import com.crm.objectRepository.CreateNewOrganisationPage;
import com.crm.objectRepository.HomePage;
import com.crm.objectRepository.LoginPage;
import com.crm.objectRepository.OrganisationInfoPage;
import com.crm.objectRepository.OrganisationPage;

import bsh.Capabilities;
import genericUtilities.ExcelUtility;
import genericUtilities.FileUtility;
import genericUtilities.JavaUtility;
import genericUtilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateOrganizationWithIndustryAndTypeTest {
	public static void main(String[] args) throws Throwable 
	{
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
		String orgName = eLib.readDataFromExcel("Organisation", 1, 2)+randNum;
		String industryName = eLib.readDataFromExcel("Organisation", 4, 3);
		String typeName = eLib.readDataFromExcel("Organisation", 4, 4);
	
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
		LoginPage lpage=new LoginPage(driver);
		lpage.loginToAppli(USERNAME, PASSWORD);
		
		//click on organisation link
		HomePage hpage =new HomePage(driver);
		hpage.getOrganisationsLnk().click();
		
		//click on create organisation icon
		OrganisationPage opage= new OrganisationPage(driver);
		opage.clickOnCreateOrgLkp();
		
		//enter the organisation name with industry and type
		CreateNewOrganisationPage cpage=new CreateNewOrganisationPage(driver);
		cpage.enterOrgInfo(orgName);
		cpage.selectIndustry(industryName);
		cpage.selectType(typeName);
		
		//verify whether the organisation is created or not
		OrganisationInfoPage oginfo=new OrganisationInfoPage(driver);
		String presentOrgName=oginfo.getOgnHeaderTxt().getText();
		if(presentOrgName.contains(orgName))
		{
			System.out.println("organisation with industry and type is created");
		}else {
			System.out.println("organisation is not created");
		}
		//mouse over on Administrator link and click on signout link
		hpage.logout(driver);
		
		//close the application
		driver.close();
	}
}
