package org.ericsson.parser;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ValidateDataTypes {

	ReadFile readFile = new ReadFile();
	IterateThroughFile iterateThroughFile  = new IterateThroughFile();
	SimpleDateFormat dataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private XSSFSheet excelSheet;


	public ValidateDataTypes(){
		ValidateEventTableData();
		ValidateBaseData();
		ValidateFailureClassTableData();
		ValidateUETAbleData();
		ValidateMNCMCC_Data();

	}

	public XSSFSheet getExcelSheet() {
		return excelSheet;
	}

	public void setExcelSheet(XSSFSheet excelSheet) {
		this.excelSheet = excelSheet;
	}

	public void ValidateEventTableData(){
		try {
			ChooseSheet("Event-Cause Table");
			XSSFSheet excelsheet = this.getExcelSheet();
			
			for (int rowNumber = 1; rowNumber < excelsheet.getLastRowNum(); rowNumber++)
			{
				Row row = excelsheet.getRow(rowNumber);
				for (int colunmNumber = 0; colunmNumber < row.getLastCellNum(); colunmNumber++) {
					if (colunmNumber == 0 | colunmNumber == 1){
						Cell cell = row.getCell(colunmNumber);	 
						CheckforNUMERICValues(cell, row);
					}
					else if (colunmNumber == 2){
						Cell cell = row.getCell(colunmNumber);	 
						CheckforStringValues(cell, row);
					}
				}
			}

			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	public void ValidateBaseData(){
		try {
			ChooseSheet("Base Data");
			XSSFSheet excelsheet = this.getExcelSheet();
			
			Cell cell = null;
			for (int rowNumber = 1; rowNumber < excelsheet.getLastRowNum(); rowNumber++)
			{
				Row row = excelsheet.getRow(rowNumber);
				for (int colunmNumber = 0; colunmNumber < row.getLastCellNum(); colunmNumber++) {
					if(colunmNumber ==0){
						cell = row.getCell(colunmNumber);
						//CheckDateFormat(cell, row);	
					}
					else if(colunmNumber >=1 && colunmNumber <=8 ){
						cell = row.getCell(colunmNumber);
						//CheckforNUMERICValues(cell, row);
					}
					else if(colunmNumber >=9 && colunmNumber <=13 ){
						cell = row.getCell(colunmNumber);
						CheckforStringValues(cell, row);


					}
				}
			}

			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ValidateFailureClassTableData(){
		try {
			ChooseSheet("Failure Class Table");
			XSSFSheet excelsheet = this.getExcelSheet();
			
			for (int rowNumber = 1; rowNumber < excelsheet.getLastRowNum(); rowNumber++)
			{
				Row row = excelsheet.getRow(rowNumber);
				for (int colunmNumber = 0; colunmNumber < row.getLastCellNum(); colunmNumber++) {
					if (colunmNumber == 0){
						Cell cell = row.getCell(colunmNumber);	 
						CheckforNUMERICValues(cell, row);
					}
					else if (colunmNumber == 1){
						Cell cell = row.getCell(colunmNumber);	 
						CheckforStringValues(cell, row);
					}
				}
			}

			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ValidateUETAbleData(){
		try {
			ChooseSheet("UE Table");
			XSSFSheet excelsheet = this.getExcelSheet();
			
			for (int rowNumber = 1; rowNumber < excelsheet.getLastRowNum(); rowNumber++)
			{
				Row row = excelsheet.getRow(rowNumber);
				for (int colunmNumber = 0; colunmNumber < row.getLastCellNum(); colunmNumber++) {
					if (colunmNumber == 0){
						Cell cell = row.getCell(colunmNumber);	 
						CheckforNUMERICValues(cell, row);
					}
					else if (colunmNumber >=1 && colunmNumber <= 3){
						Cell cell = row.getCell(colunmNumber);	 
						CheckforStringValues(cell, row);
					}
				}
			}

			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ValidateMNCMCC_Data(){
		try {
			ChooseSheet("MCC - MNC Table");
			XSSFSheet excelsheet = this.getExcelSheet();
			;
			for (int rowNumber = 1; rowNumber < excelsheet.getLastRowNum(); rowNumber++)
			{
				Row row = excelsheet.getRow(rowNumber);
				for (int colunmNumber = 0; colunmNumber < row.getLastCellNum(); colunmNumber++) {
					if (colunmNumber == 0 || colunmNumber == 1){
						Cell cell = row.getCell(colunmNumber);	 
						CheckforNUMERICValues(cell, row);
					}
					else if (colunmNumber == 2 || colunmNumber == 3){
						Cell cell = row.getCell(colunmNumber);	 
						CheckforStringValues(cell, row);
					}
				}
			}

			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CheckforNUMERICValues(Cell cell, Row row){

		if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC){
			getCellReference(cell, row);						
			System.out.println("Invalid Record - Cell must be a NUMERIC type");

		}
	}

	public void CheckforStringValues(Cell cell, Row row){
		if(cell.getCellType() != Cell.CELL_TYPE_STRING){
			getCellReference(cell, row);							
			System.out.println("Invalid Record - Cell must be a STRING type");

		}
	}

	public void CheckDateFormat(Cell cell, Row row){
		if (!DateUtil.isCellDateFormatted(cell))
		{
			getCellReference(cell, row);
			System.out.println("Invalid Record - Cell must be a DATE type");
		}
	}

	public void getCellReference(Cell cell, Row row){
		CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
		System.out.print(cellRef.formatAsString());
		System.out.print(" - ");
	}

	public void ChooseSheet(String sheetName){ 
		String sheetN  = sheetName;
		setExcelSheet(iterateThroughFile.getWorkbook().getSheet(sheetName));
		System.out.println(getExcelSheet().getSheetName() + "\n---------" );
	}

	public void PrintSheetName(){
		System.out.println(getExcelSheet().getSheetName() + "\n---------" );
	}

}

