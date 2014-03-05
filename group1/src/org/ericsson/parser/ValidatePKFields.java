package org.ericsson.parser;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ValidatePKFields {

	IterateThroughFile iterateThroughFile = new IterateThroughFile();
	ReadFile readFile = new ReadFile();
	private XSSFSheet excelsheet;
	


	public ValidatePKFields(){
		CheckISFailureClassTableValid();
		CheckIsIMSIValid();
		CheckIsTACValid();
	}

	public void ChooseSheet(String sheetName){ 
		String sheetN  = sheetName;
		excelsheet = iterateThroughFile.getWorkbook().getSheet(sheetName);
	}

	public void CheckISFailureClassTableValid(){
		ChooseSheet("Failure Class Table");
		PrintSheetName();
		System.out.println("Checking for Invalid Failure Class Number:");
		SearchColumn(0);
		try {
			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void CheckIsIMSIValid(){
		ChooseSheet("Base Data");
		PrintSheetName();		
		System.out.println("Checking for Invalid IMSI Number:");
		SearchColumn(10);
		try {
			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void CheckIsTACValid(){
		ChooseSheet("UE Table");
		PrintSheetName();
		System.out.println("Checking for Invalid TAC Number:");
		SearchColumn(0);
		try {
			 
			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SearchColumn(int columnNumber){
		for (int i = 1; i < excelsheet.getLastRowNum(); i++)
		{
			Row row = excelsheet.getRow(i);
			for (int j = columnNumber; j < row.getLastCellNum(); j++) {
				if (j == columnNumber){
					Cell cell = row.getCell(j);	  
					CheckForNULLorBlanks(row, cell);

				}
				
			}
		}
	}
	
	public void CheckForNULLorBlanks(Row row, Cell cell){
		if (cell.equals(null) || cell.getCellType() == Cell.CELL_TYPE_BLANK){
			CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
			System.out.print("\t" + cellRef.formatAsString());
			System.out.print(" - ");
			System.out.println("Invalid Record, Cannot be Blank");
		}
	}
	
	public void PrintSheetName(){
		System.out.println("\n" + excelsheet.getSheetName() + "\n" + "--------------------");
	}

}
