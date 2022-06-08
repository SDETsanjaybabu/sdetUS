package vtiger.vendor.product;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
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

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateAProductByUsingVendorNameTest {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		WebDriver driver=null;
		FileInputStream fs=new FileInputStream(".\\src\\test\\resources\\data.properties.txt");
		Properties pres=new Properties();
		pres.load(fs);
		
		String URL = pres.getProperty("url");
		String USERNAME = pres.getProperty("username");
		String PASSWORD = pres.getProperty("password");
		String BROWSER = pres.getProperty("browser");
		
		FileInputStream fstream=new FileInputStream(".\\src\\test\\resources\\TestData.xlsx");
		Workbook workbook = WorkbookFactory.create(fstream);
		Sheet sheet = workbook.getSheet("Sheet1");
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(2);
		String productName = cell.getStringCellValue();
		
		Random random=new Random();
		int randNum = random.nextInt(100);
		
		if(BROWSER.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}else if(BROWSER.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}else
		{
			driver=new ChromeDriver();
		}
		//WebDriverManager.chromedriver().setup()
		//WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);
		//enter a username
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		//enter the password
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).submit();
		WebElement element1 = driver.findElement(By.xpath("//img[@src='themes/softed/images/menuDnArrow.gif']"));
		Actions action=new Actions(driver);
		action.moveToElement(element1).perform();
		driver.findElement(By.name("Vendors")).click();
		driver.findElement(By.xpath("//img[@title='Create Vendor...']")).click();
		driver.findElement(By.name("vendorname")).sendKeys("redmii");
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		String element2 = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(element2.contains("redmi"))
		{
			System.out.println("vendor is created");
		}else {
			System.out.println("vendor is not created");
		}
		driver.findElement(By.xpath("//a[.='Products']")).click();
		driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		driver.findElement(By.name("productname")).sendKeys(productName+randNum);
		driver.findElement(By.xpath("//img[@title='Select']")).click();
		Set<String> element3 = driver.getWindowHandles();
		for (String string : element3) 
		{
		driver.switchTo().window(string);
		String title = driver.getTitle();
		if(title.contains("Vendors&action"))
		{
			break;
		}
		}
		driver.findElement(By.name("search_text")).sendKeys("redmi");
		driver.findElement(By.name("search")).click();
		driver.findElement(By.xpath("//a[.='redmi']")).click();
		Set<String> element4 = driver.getWindowHandles();
		for (String string : element4) 
		{
		driver.switchTo().window(string);
		String title = driver.getTitle();
		if(title.contains("Products&action"))
		{
			break;
		}
		}
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		String element5 = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		if(element5.contains("redmi-note10"))
		{
			System.out.println("test case is pass");
		}else {
			System.out.println("test case is fail");
		}
	    WebElement element6 = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
	    Actions act=new Actions(driver);
	    act.moveToElement(element6).perform();
	    driver.findElement(By.xpath("//a[.='Sign Out']")).click();
	}
}