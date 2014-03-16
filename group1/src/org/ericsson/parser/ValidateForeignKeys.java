package org.ericsson.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ValidateForeignKeys {

	//IterateThroughFile iterateThroughFile = new IterateThroughFile();
	ReadFile readFile = new ReadFile();
	XSSFWorkbook workBook;
	private XSSFSheet baseData;
	private XSSFSheet eventData;
	private XSSFSheet ueType;
	private XSSFSheet failureClassTable;
	private XSSFSheet mcc_mncTable;
	private ArrayList<Double> failureClassValues = new ArrayList<Double>();
	private ArrayList <Double>eventIDs = new ArrayList<Double>();
	private ArrayList <Double>TAC_Values = new ArrayList<Double>();
	private ArrayList<Double> causeCodes = new ArrayList<Double>();
	private ArrayList<Double> mccValues = new ArrayList<Double>();
	private ArrayList<Double> mncValues = new ArrayList<Double>();

	public ArrayList<Double> getMccValues() {
		return mccValues;
	}

	public void setMccValues(ArrayList<Double> mccValues) {
		this.mccValues = mccValues;
	}

	public XSSFSheet getBaseData() {
		return baseData;
	}

	public void setBaseData(XSSFWorkbook xssfWorkbook) {
		this.baseData = xssfWorkbook.getSheetAt(0);;
	}

	public XSSFSheet getEventData() {
		return eventData;
	}

	public void setEventData(XSSFWorkbook xssfWorkbook) {
		this.eventData = xssfWorkbook.getSheetAt(1);
	}

	public XSSFSheet getUeType() {
		return ueType;
	}

	public void setUeType(XSSFWorkbook xssfWorkbook) {
		this.ueType = xssfWorkbook.getSheetAt(3);
	}

	public XSSFSheet getFailureClassTable() {
		return failureClassTable;
	}

	public void setFailureClassTable(XSSFWorkbook xssfWorkbook) {
		this.failureClassTable = xssfWorkbook.getSheetAt(2);
	}

	public XSSFSheet getMcc_mncTable() {
		return mcc_mncTable;
	}

	public void setMcc_mncTable(XSSFWorkbook xssfWorkbook) {
		this.mcc_mncTable = xssfWorkbook.getSheet("MCC - MNC Table");
	}

	private ArrayList<CellReference> invalidCellRef = new ArrayList<CellReference>();

	public ValidateForeignKeys(){

	}
	public ValidateForeignKeys(XSSFWorkbook workbook) {
		setWorkBook(workbook);
		setBaseData(getWorkBook());
		setEventData(getWorkBook());
		setUeType(getWorkBook());
		setMcc_mncTable(getWorkBook());
		setFailureClassTable(getWorkBook());

		populateArrayList(getFailureClassTable(), failureClassValues, 0);
		CompareValues(2, failureClassValues);

		populateArrayList(getEventData(), eventIDs, 1);
		CompareValues(1, eventIDs);		

		populateArrayList(getUeType(), TAC_Values, 0);
		CompareValues(3, TAC_Values);		

		populateArrayList(getEventData(), causeCodes, 0);		
		CompareValues(8, causeCodes);

		populateArrayList(getMcc_mncTable(), mccValues, 0);
		CompareValues(4, mccValues);

		populateArrayList(getMcc_mncTable(), mncValues, 1);
		CompareValues(5, mncValues);

		//CheckForFKNullValues(8, "Cause Code");
		//CheckForFKNullValues(2, "Failure Class");

		//printArrayList();
	}	

	public XSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(XSSFWorkbook workBook) {
		this.workBook = workBook;
	}



	public ArrayList<CellReference> getInvlaidCellRef() {
		return invalidCellRef;
	}

	public void setInvlaidCellRef(ArrayList<CellReference> invlaidCellRef) {
		this.invalidCellRef = invlaidCellRef;
	}

	public void CompareValues(int  baseDataColumnIndex, ArrayList<Double> foreignKeys){
		Cell cell = null;
		XSSFSheet baseData = getBaseData();
		for (int rowNumber = 1; rowNumber <= baseData.getLastRowNum(); rowNumber++)
		{
			Row row = baseData.getRow(rowNumber);
			for (int colunmNumber = 0; colunmNumber <= row.getLastCellNum(); colunmNumber++) {
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

		Cell cell = null;

		for (int rowNumber = 1; rowNumber <= workbookSheet.getLastRowNum(); rowNumber++)
		{
			Row row = workbookSheet.getRow(rowNumber);
			for (int colunmNumber = 0; colunmNumber <= row.getLastCellNum(); colunmNumber++) {
				if (colunmNumber == columnIndex){
					cell = row.getCell(colunmNumber);
					if(!(cell == null)){
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							arr.add(cell.getNumericCellValue());					
						}
					}
				}
			}
		}
		System.out.println("ArrayList Size = " + getInvlaidCellRef().size());
		System.out.println("\nComparing BaseData to " + workbookSheet.getSheetName() + "\n--------------");


	}

	public void PrintArrayList(ArrayList<Double> arr){
		for (double d: arr){
			System.out.println(d);
		}
	}
	

	public void getCellReference(Cell cell, Row row){
		CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
		System.out.print("\t- " + cellRef.formatAsString());
		System.out.print("\n" );
		invalidCellRef.add(cellRef);
		//System.out.println("The following recording " + cell.getNumericCellValue() +" is not found elsewhere");

	}

	public void printArrayList(){
		System.out.println("\tThe Following records are only found in Base Data and need to be removed!!");
		for (CellReference invalid:invalidCellRef){
			System.out.println(invalid.formatAsString());
		}

	}

	



}
