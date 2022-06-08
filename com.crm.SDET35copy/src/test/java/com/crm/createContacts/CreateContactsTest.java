package com.crm.createContacts;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.google.common.io.Files;

import genericUtilities.ExcelUtility;
import genericUtilities.FileUtility;
import genericUtilities.IPathConstants;
import genericUtilities.JavaUtility;
import genericUtilities.WebDriverUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateContactsTest {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
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
		
		//Fetch first name from excelSheet
		String FirstName = eLib.readDataFromExcel("Sheet1", 7, 2)+randNum;

		//Fetch LastName from excelSheet
		String LastName = eLib.readDataFromExcel("Sheet1", 8, 2)+randNum;
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
		//enter the username
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		//enter the password
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		//click on submit button
		driver.findElement(By.id("submitButton")).click();	
		//Takeing screenshot
		wLib.takeScreenShot(driver, "homePage");
		//click on contacts link
		driver.findElement(By.xpath("//a[.='Contacts']")).click();
		//click on create contacts icon
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		//enter the first name
		driver.findElement(By.name("firstname")).sendKeys(FirstName);
		//enter the lastName name
		driver.findElement(By.name("lastname")).sendKeys(LastName);
		//click on save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		//verify whether the Contacts is created or not
		String presentContactName = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(presentContactName.contains(LastName))
		{
		System.out.println("Contact is created");
		}else {
		System.out.println("Contact is not created");
		}
		//mouse over on Administrator link
		WebElement mouseOver = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		wLib.mouseOverAnElement(driver, mouseOver);
		//click on signOut button
		driver.findElement(By.xpath("//a[.='Sign Out']")).click();
		//close the application
		driver.close();	
	}
}
