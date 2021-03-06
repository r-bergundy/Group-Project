package org.ericsson.parser;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StartValidation {
 	
	private int totalErrors;
	XSSFWorkbook workbook;
	private ValidatePKFields pkfields = new ValidatePKFields();
	private ValidateForeignKeys fkfields = new ValidateForeignKeys();
	private ValidateMNC_MCC_Combination mncmccCombo = new ValidateMNC_MCC_Combination();
	private ArrayList<CellReference> errorsList;
	
	
	
	public ValidatePKFields getPkfields() {
		return pkfields;
	}	
	

	public ValidateMNC_MCC_Combination getMncmccCombo() {
		return mncmccCombo;
	}


	public void setMncmccCombo(ValidateMNC_MCC_Combination mncmccCombo) {
		this.mncmccCombo = mncmccCombo;
	}



	public void setPkfields(ValidatePKFields pkfields) {
		this.pkfields = pkfields;
	}

	public ValidateForeignKeys getFkfields() {
		return fkfields;
	}

	public void setFkfields(ValidateForeignKeys fkfields) {
		this.fkfields = fkfields;
	}

	public StartValidation(XSSFWorkbook workBook){
		this.workbook = workBook;
		
		pkfields.setWorkBook(workbook);
		pkfields.CheckISFailureClassTableValid();
		pkfields.CheckIsIMSIValid();
		pkfields.CheckIsTACValid(); 
		fkfields = new ValidateForeignKeys(workbook);		
		
		CalculateTotalNumberOfErrors(pkfields, fkfields);
		
		System.out.println("----------------------------NEW CHECK-----------------------------------------");
		mncmccCombo = new ValidateMNC_MCC_Combination(workbook);
		
		
		//System.out.println("PK Errors");
		//pkfields.printArrayList();
		
	//	System.out.println("\nFK ERRORS");
		//fkfields.printArrayList();		
		
		
		
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


