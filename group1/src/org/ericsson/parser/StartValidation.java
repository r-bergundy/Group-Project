package org.ericsson.parser;

import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StartValidation {

	public static void main(String args[]){
		//new StartValidation();
	}
	private int totalErrors;
	XSSFWorkbook workbook;
	ValidatePKFields pkfields = new ValidatePKFields();
	ValidateForeignKeys fkfields = new ValidateForeignKeys();

	public StartValidation(XSSFWorkbook workBook){
		this.workbook = workBook;
		pkfields.setWorkBook(workbook);
		pkfields.CheckISFailureClassTableValid();
		pkfields.CheckIsIMSIValid();
		pkfields.CheckIsTACValid(); 
		fkfields = new ValidateForeignKeys(workbook);
		
		
		CalculateTotalNumberOfErrors(pkfields, fkfields);
		ImportData importData = new ImportData(workbook, fkfields, pkfields);
		
		System.out.println("PK Errors");
		pkfields.printArrayList();
		
		System.out.println("\nFK ERRORS");
		fkfields.printArrayList();
		//fkfields.PrintFailures();
		importData.populateDatabase();
		//Menu();
	}	
	
	public int getTotalErrors() {
		return totalErrors;
	}

	public void setTotalErrors(int totalErrors) {
		this.totalErrors = totalErrors;
	}


	public void CalculateTotalNumberOfErrors(ValidatePKFields pkkeys, 	ValidateForeignKeys fkkeys){
		setTotalErrors(pkkeys.getInvalidCellRef().size() + fkkeys.getInvlaidCellRef().size());
		System.out.println("Total Numbmer of Errors: " + totalErrors);
	}

	
}


