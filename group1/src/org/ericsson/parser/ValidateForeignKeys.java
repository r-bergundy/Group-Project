package org.ericsson.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ValidateForeignKeys {

	IterateThroughFile iterateThroughFile = new IterateThroughFile();
	ReadFile readFile = new ReadFile();
	private XSSFSheet baseData = iterateThroughFile.getWorkbook().getSheetAt(0);
	private XSSFSheet eventData = iterateThroughFile.getWorkbook().getSheetAt(1);
	private XSSFSheet ueType = iterateThroughFile.getWorkbook().getSheetAt(3);
	private XSSFSheet failureClassTable = iterateThroughFile.getWorkbook().getSheetAt(2);
	private XSSFSheet mcc_mncTable = iterateThroughFile.getWorkbook().getSheet("MCC - MNC Table");
	private ArrayList<Double> failureClassValues = new ArrayList<Double>();
	private ArrayList <Double>eventIDs = new ArrayList<Double>();
	private ArrayList <Double>TAC_Values = new ArrayList<Double>();
	private ArrayList<Double> causeCodes = new ArrayList<Double>();
	private ArrayList<Double> mccValues = new ArrayList<Double>();
	private ArrayList<Double> mncValues = new ArrayList<Double>();
	private ArrayList<CellReference> invalidCellRef = new ArrayList<CellReference>();


	public ValidateForeignKeys() {
		
		populateArrayList(failureClassTable, failureClassValues, 0);
		CompareValues(2, failureClassValues);
				
		populateArrayList(eventData, eventIDs, 1);
		CompareValues(1, eventIDs);		
		
		populateArrayList(ueType, TAC_Values, 0);
		CompareValues(3, TAC_Values);		
		
		populateArrayList(eventData, causeCodes, 0);		
		CompareValues(8, causeCodes);
		
		populateArrayList(mcc_mncTable, mccValues, 0);
		CompareValues(4, mccValues);
		
		populateArrayList(mcc_mncTable, mncValues, 1);
		CompareValues(5, mncValues);
		
		//CheckForFKNullValues(8, "Cause Code");
		//CheckForFKNullValues(2, "Failure Class");
		
		printArrayList();
	}	

	public ArrayList<CellReference> getInvlaidCellRef() {
		return invalidCellRef;
	}

	public void setInvlaidCellRef(ArrayList<CellReference> invlaidCellRef) {
		this.invalidCellRef = invlaidCellRef;
	}

	public void CompareValues(int  baseDataColumnIndex, ArrayList<Double> foreignKeys){
		Cell cell = null;
		
		for (int rowNumber = 1; rowNumber < baseData.getLastRowNum(); rowNumber++)
		{
			Row row = baseData.getRow(rowNumber);
			for (int colunmNumber = 0; colunmNumber < row.getLastCellNum(); colunmNumber++) {
				if (colunmNumber == baseDataColumnIndex){
					cell = row.getCell(colunmNumber);
					if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
						if (!foreignKeys.contains(cell.getNumericCellValue())){
							getCellReference(cell, row);
							
						}
					}
				}
			}
		}
	}

	public void populateArrayList(XSSFSheet workbookSheet, ArrayList<Double> arr, int columnIndex){
		try{
			Cell cell = null;
			
			for (int rowNumber = 1; rowNumber < workbookSheet.getLastRowNum(); rowNumber++)
			{
				Row row = workbookSheet.getRow(rowNumber);
				for (int colunmNumber = 0; colunmNumber < row.getLastCellNum(); colunmNumber++) {
					if (colunmNumber == columnIndex){
						cell = row.getCell(colunmNumber);					
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							arr.add(cell.getNumericCellValue());					
						}					
					}
				}
			}
			System.out.println("\nComparing BaseData to " + workbookSheet.getSheetName() + "\n--------------");
			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void PrintArrayList(ArrayList<Double> arr){
		for (double d: arr){
			System.out.println(d);
		}
	}

	public void CheckForFKNullValues(int  baseDataColumnIndex, String columnDescription){
		try{
			Cell cell = null;
			System.out.println("\nChecking if Foriegn Key: " + columnDescription+ ", in Base Data is NULL/Blank\n--------------");
			for (int rowNumber = 1; rowNumber < baseData.getLastRowNum(); rowNumber++)
			{
				Row row = baseData.getRow(rowNumber);
				for (int colunmNumber = 0; colunmNumber < row.getLastCellNum(); colunmNumber++) {
					if (colunmNumber == baseDataColumnIndex){
						cell = row.getCell(colunmNumber);					
						if(cell.getCellType() == Cell.CELL_TYPE_STRING){
							if(cell.getStringCellValue().equals("(null)")){					
								getCellReference(cell, row);
							}
						}
						else if (cell.getCellType() == Cell.CELL_TYPE_BLANK){
							getCellReference(cell, row);
						}
					}
				}
			}
			
			readFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getCellReference(Cell cell, Row row){
		CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
		//System.out.print("\t- " + cellRef.formatAsString());
		//System.out.print("\n" );
		invalidCellRef.add(cellRef);
		//System.out.println("The following recording " + cell.getNumericCellValue() +" is not found elsewhere");
		
	}
	
	public void printArrayList(){
		System.out.println("\tThe Following records are only found in Base Data and need to be removed!!");
		for (CellReference invalid:invalidCellRef){
			System.out.println(invalid.formatAsString());
		}
		System.out.println("Total Numbmer of Errors: " + invalidCellRef.size());
	}



}
