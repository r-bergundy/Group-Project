package org.ericsson.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream.PutField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class IterateThroughFile {

	private FileInputStream xlsxFile;
	private XSSFWorkbook workbook;
	private XSSFSheet excelSheet;
	private XSSFSheet eventTypeSheet;
	private int totalNumberErrors;
	StartValidation startValidationProcess;

	public IterateThroughFile(){
		//CreateWorkBook();
	}	
	
	public int getTotalNumberErrors() {
		return totalNumberErrors;
	}

	public void setTotalNumberErrors(int totalNumberErrors) {
		this.totalNumberErrors = totalNumberErrors;
	}

	public XSSFSheet getExcelSheet() {
		return excelSheet;
	}

	public void setExcelSheet(XSSFSheet excelSheet) {
		this.excelSheet = excelSheet;
	}
	public XSSFWorkbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	

	public void CreateWorkBook(FileInputStream file){
		//readXLSXFile.LoadXLSXFile();
		xlsxFile = file;
		try {
			setWorkbook(new XSSFWorkbook(xlsxFile));
			System.out.println("WORKBOOK CREATED");
			System.out.println(getWorkbook().getSheetName(0));
			startValidationProcess = new StartValidation(getWorkbook());
			setTotalNumberErrors(startValidationProcess.getTotalErrors());
			
			xlsxFile.close();
		} catch (IOException e) {
			System.out.println("IO Exception");
		}

	}

}
