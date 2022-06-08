package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.mysql.jdbc.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class s1 {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String project = "amazon1";
		Connection connection = null;
		WebDriver driver=null;
		try {
		Driver driverRef = new Driver();
		DriverManager.registerDriver(driverRef);
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
		Statement statement = connection.createStatement();
		String query = "insert into project values('TY_PROJ_56','ggg','23/06/2023','"+project+"','completed','15')";
		statement.executeUpdate(query);
		
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get("http://localhost:8084");
		driver.findElement(By.name("username")).sendKeys("rmgyantra");
		driver.findElement(By.name("password")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[.='Sign in']")).click();
		driver.findElement(By.xpath("//a[.='Projects']")).click();
		List<WebElement> element = driver.findElements(By.xpath("//table[@class='table table-striped table-hover']/tbody/tr/td[2]"));
		for (WebElement webElement : element)
		{
			String text = webElement.getText();
			if(text.equalsIgnoreCase(project));
			{
				System.out.println("project is created");
				break;
			}
		}
		}
		catch (Exception e) 
		{
			
		}finally {
			connection.close();
			driver.close();
		}
	}
	}

