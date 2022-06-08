package jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.mysql.jdbc.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ToReadDataFromTheDB {

	public static void main(String[] args) throws Throwable {
	Driver driverRef = new Driver();
	DriverManager.registerDriver(driverRef);
	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
	Statement statement = connection.createStatement();
	String query = "insert into project values('TY_PROJ_878','ruru','23/06/2023','flinko','completed','15')";
	int result = statement.executeUpdate(query);
	if(result==1)
	{
		System.out.println("is created");
	}else{
		System.out.println("not created");
	}
	connection.close();
	}
}