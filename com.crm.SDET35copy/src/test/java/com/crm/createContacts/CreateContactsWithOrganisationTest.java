package com.crm.createContacts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import genericUtilities.ExcelUtility;
import genericUtilities.FileUtility;
import genericUtilities.IPathConstants;
import genericUtilities.JavaUtility;
import genericUtilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateContactsWithOrganisationTest {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		 WebDriver driver=null;
		 
		 JavaUtility jLib=new JavaUtility();
		 ExcelUtility eLib=new ExcelUtility();
		 FileUtility fLib=new FileUtility();
		 WebDriverUtility wLib = new WebDriverUtility();
			
			//get the properties of property file using getProperty method
		    String URL = fLib.getPropertKeyValue("url");
		    String USERNAME = fLib.getPropertKeyValue("username");
		    String PASSWORD = fLib.getPropertKeyValue("password");
		    String BROWSER = fLib.getPropertKeyValue("browser");
			
			//To get random number
		    int randNum = jLib.getRandomNumber();
			
			//Fetching the firstName from excelSheet
		    String FirstName = eLib.readDataFromExcel("Sheet1", 7, 2)+randNum;
//			
//			
			//fetching the lastname from excelSheet
		    String LastName = eLib.readDataFromExcel("Sheet1", 8, 2)+randNum;
		    
			//launch the browser(open)
			if(BROWSER.equalsIgnoreCase("firefox"))
			{
				WebDriverManager.firefoxdriver().setup();
				driver=new FirefoxDriver();
			}else if(BROWSER.equalsIgnoreCase("chrome"))
			{
				System.setProperty(IPathConstants.chromeKey, IPathConstants.chromePath);
				//WebDriverManager.chromedriver().setup();
				driver=new ChromeDriver();
			}else {
				driver=new ChromeDriver();
			}
			//use implicitly wait condition
			wLib.waitForPageToLoad(driver);
			//enter the url of the application
			driver.get(URL);
			//enter the username
			driver.findElement(By.name("user_name")).sendKeys(USERNAME);
			//enter the password
			driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
			//click on submit button
			driver.findElement(By.id("submitButton")).click();
			//take screenShot
			wLib.takeScreenShot(driver, "homepage");
			//click on contacts link
			driver.findElement(By.xpath("//a[.='Contacts']")).click();
			//click on create contacts icon
			driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
			//enter the first name
			driver.findElement(By.name("firstname")).sendKeys(FirstName);
			//enter the lastName name
			driver.findElement(By.name("lastname")).sendKeys(LastName);
			//click on select icon in the organisation name
			driver.findElement(By.xpath("//img[@src='themes/softed/images/select.gif']")).click();
			//switch to Organisation window
			wLib.switchToWindow(driver,"Accounts&action");
		//enter the organisation name in the search textbox
			 driver.findElement(By.id("search_txt")).sendKeys("TYSS");
			 //click on search button
			 driver.findElement(By.name("search")).click();
			 //select the created organisation
			 driver.findElement(By.xpath("//a[.='TYSS24']")).click();
			 //switch back to create contacts window
			 wLib.switchToWindow(driver, "Contacts&action");
				//click on save button
				driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				//verify whether the contact is created or not
				String presentContactName = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
				if(presentContactName.contains(LastName))
				{
				System.out.println("Contact is created");
				}else {
				System.out.println("Contacts is not created");
				}
				//mouse over on Administrator link
				WebElement mouseOver = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
				wLib.mouseOverAnElement(driver, mouseOver);
				driver.findElement(By.xpath("//a[.='Sign Out']")).click();
				//close the application
				driver.close();			
	}
}