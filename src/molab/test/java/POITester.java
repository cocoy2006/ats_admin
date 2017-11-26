package molab.test.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class POITester {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String template = "C:\\Development\\cfca\\package\\cfca.xls";
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(template));
		Workbook wb = new HSSFWorkbook(fs);
		Sheet sheet = wb.getSheet("硬件信息");
		Row row = sheet.createRow(2);
//		row = sheet.getRow(2);
		Cell cell = row.createCell(0);
		System.out.println(cell.getStringCellValue());
		cell.setCellValue("cfca");
		System.out.println(cell.getStringCellValue());
		
		wb.write(new FileOutputStream("C:\\Development\\cfca\\package\\text.xls"));
		wb.close();
		
		System.out.println("Done.");
		System.exit(0);
	}
	
}
