package org.ericsson.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.ericsson.mydb.PersistenceUtil;

import com.entities.Device;
import com.entities.Operator;
import com.entities.UserEquipment;

public class ValidatePKFields {

	IterateThroughFile iterateThroughFile = new IterateThroughFile();
	ReadFile readFile = new ReadFile();
	private XSSFSheet excelsheet;
	private ArrayList<CellReference> invalidCellRef = new ArrayList<CellReference>();
	private boolean isValid;




	public ValidatePKFields(){
		CheckISFailureClassTableValid();
		CheckIsIMSIValid();
		CheckIsTACValid();
		printArrayList();
	}



	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
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
		try {

			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SearchColumn(int columnNumber){

		for (int rowIndex = 1; rowIndex < excelsheet.getLastRowNum(); rowIndex++)
		{
			Row row = excelsheet.getRow(rowIndex);
			for (int columnIndex = columnNumber; columnIndex < row.getLastCellNum(); columnIndex++) {
				if (columnIndex == columnNumber){
					Cell cell = row.getCell(columnIndex);	  
					CheckForNULLorBlanks(row, cell);					

				}

			}
		}
	}

	public void CheckForNULLorBlanks(Row row, Cell cell){
		if ( cell.equals(null) || cell.getCellType() == Cell.CELL_TYPE_BLANK){
			CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
			//System.out.print("\t" + cellRef.formatAsString());
			//System.out.print(" - ");
			//System.out.println("Invalid Record, Cannot be Blank");
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
