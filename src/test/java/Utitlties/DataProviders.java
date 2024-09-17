package Utitlties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviders {
	@DataProvider(name="LoginData1")	
	public  static  String[][]  getdata() throws IOException
	{   
		File excelfile=new File(".\\testData\\OrangeHRM_LoginData.xlsx");
		FileInputStream file=new FileInputStream(excelfile);
		XSSFWorkbook book=new XSSFWorkbook(file);
		XSSFSheet sheet=book.getSheet("sheet1");
		 int totalrows= sheet.getPhysicalNumberOfRows();
		 System.out.println(totalrows);
		 int totalclms=sheet.getRow(0).getLastCellNum();
		 System.out.println(totalclms);
		 String[][] data1=new String[totalrows-1][totalclms];
		 for(int i=0;i<totalrows-1;i++)
		 {
			 /* here zeroth row is eleminated
			  1 0   1 1
			  2 0   2 1
			  3 0   3 1
			  4 0   4 1
			  5 0   5 1
			  */
			 for(int j=0;j<totalclms;j++)
			 {
				DataFormatter df= new DataFormatter() ;
				data1[i][j]=df.formatCellValue(sheet.getRow(i+1).getCell(j));
			//	System.out.print(data1[i][j]+" ");
			 }
		//	 System.out.println();
		 }
		book.close(); 
		file.close();
		
		return data1;

	}
	public static void main(String[] args) throws Throwable {
		getdata();
	}

}
