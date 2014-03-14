package org.ericsson.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ericsson.mydb.PersistenceUtil;

import com.entities.Device;
import com.entities.Operator;
import com.entities.UserEquipment;

public class ValidatePKFields {

	IterateThroughFile iterateThroughFile = new IterateThroughFile();
	ReadFile readFile = new ReadFile();
	XSSFWorkbook workBook;
	private XSSFSheet excelsheet;
	private ArrayList<CellReference> invalidCellRef = new ArrayList<CellReference>();
	private boolean isValid;

	public ValidatePKFields(){

	}	

	public ArrayList<CellReference> getInvalidCellRef() {
		return invalidCellRef;
	}



	public void setInvalidCellRef(ArrayList<CellReference> invalidCellRef) {
		this.invalidCellRef = invalidCellRef;
	}

	public XSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(XSSFWorkbook workBook) {
		this.workBook = workBook;
	} 

	public void ChooseSheet(String sheetName){ 
		excelsheet = workBook.getSheet(sheetName);
	}



	public void CheckISFailureClassTableValid(){
		ChooseSheet("Failure Class Table");
		PrintSheetName();
		System.out.println("Checking for Invalid Failure Class Number:");
		SearchColumn(0);

	}

	public void CheckIsIMSIValid(){
		ChooseSheet("Base Data");
		ArrayList<String> alreadyPersisted = new ArrayList<String>();
		PrintSheetName();		
		System.out.println("Checking for Invalid IMSI Number:");
		SearchColumn(10);
	}

	public void CheckIsTACValid(){
		ChooseSheet("UE Table");
		PrintSheetName();
		System.out.println("Checking for Invalid TAC Number:");
		SearchColumn(0);

	}

	public void SearchColumn(int columnNumber){
		Cell cell;
		System.out.println("Column NUMBER FOR TESTING = " + columnNumber);
		for (int rowIndex = 1; rowIndex <= excelsheet.getLastRowNum(); rowIndex++)
		{
			Row row = excelsheet.getRow(rowIndex);
			for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
				if (columnIndex == columnNumber){					
					cell = row.getCell(columnIndex);//in here lies the problem..cell is null					
					if(cell == null){
						System.out.println("Picking up null CELL");
					}
					//pass in blank null cell
					CheckForNULLorBlanks(row, cell, columnIndex);
				}

			}
		}
	}

	public void CheckForNULLorBlanks(Row row, Cell cell, int columnNumber){
		
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
			System.out.println("ERROR");
			System.out.println("ROW = " + row.getRowNum());
			//System.out.println("CELL = " +  cell.getColumnIndex());
			CellReference cellRef = new CellReference(row.getRowNum(), columnNumber);	
			System.out.print("\t- " + cellRef.formatAsString());
			System.out.print("\n" );
			invalidCellRef.add(cellRef);
		}
		
	}
	public void PrintSheetName(){
		System.out.println("\n" + excelsheet.getSheetName() + "\n" + "--------------------");
	}

	public void printArrayList(){
		for (CellReference invalid:invalidCellRef){
			System.out.println(invalid.formatAsString());
		}
	}

}
