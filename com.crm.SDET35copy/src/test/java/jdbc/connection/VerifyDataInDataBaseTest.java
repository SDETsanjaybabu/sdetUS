package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.mysql.jdbc.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VerifyDataInDataBaseTest {

	public static void main(String[] args) throws SQLException {
		String project_name = "sdet";
		Connection connection = null;
		WebDriver driver=null;
		try {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get("http://localhost:8084");
		driver.findElement(By.name("username")).sendKeys("rmgyantra");
		driver.findElement(By.name("password")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[.='Sign in']")).click();
		driver.findElement(By.xpath("//a[.='Projects']")).click();
		driver.findElement(By.xpath("//span[.='Create Project']")).click();
		driver.findElement(By.name("projectName")).sendKeys("skill");
		driver.findElement(By.name("createdBy")).sendKeys("mohan");
		WebElement element = driver.findElement(By.xpath("//label[.='Status']/following-sibling::select"));
		Select select=new Select(element);
		select.selectByValue("Created");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.close();
		Driver driverRef=new Driver();
		DriverManager.registerDriver(driverRef);
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
		Statement statement = connection.createStatement();
		String query = "select * from project";
		ResultSet result = statement.executeQuery(query);
		boolean flag=false;
		while(result.next())
		{
			String actualProject = result.getString(4);
			if(project_name.equals(actualProject))
			{
				flag=true;
				System.out.println("project is present");
			}
		}
		}
		catch (Exception e) 
		{
		}
		connection.close();
		driver.close();
	}
}