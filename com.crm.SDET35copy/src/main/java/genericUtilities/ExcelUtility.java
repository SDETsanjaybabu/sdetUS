package genericUtilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * @author SanjayBabu
 *
 */
public class ExcelUtility {
	/**
	 *its used to read the data from excel file  
	 * @return
	 */
public String readDataFromExcel(String sheetName,int rowNum,int cellNum) throws Throwable{
	FileInputStream fileInputStream = new FileInputStream(IPathConstants.excelPath);
	Workbook workbook = WorkbookFactory.create(fileInputStream);
	Sheet sheet = workbook.getSheet(sheetName);
	Row row = sheet.getRow(rowNum);
	Cell cell = row.getCell(cellNum);
	String data = cell.toString();
	return data;
}
/**
 * its used to write data into excel file
 * @param sheetName
 * @return
 * @throws Throwable
 */
public void writeDataIntoExcel(String sheetName,int rowNum,int cellNum,String data) throws Throwable {
	FileInputStream fileInputStream=new FileInputStream(IPathConstants.excelPath);
	Workbook workbook = WorkbookFactory.create(fileInputStream);
	Sheet sheet = workbook.getSheet(sheetName);
	Row row = sheet.getRow(rowNum);
	Cell cell = row.createCell(cellNum);
	cell.setCellValue(data);
	FileOutputStream fileOutputStream = new FileOutputStream(".\\src\\test\\resources\\TestData.xlsx");
	workbook.write(fileOutputStream);
}
}
