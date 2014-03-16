package org.ericsson.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFile {

	private FileInputStream xlsxfile;
	private XSSFWorkbook workbook;
	private Boolean validFormat;
	private ValidatePKFields pkerror;
	private ValidateForeignKeys fkerrors;
	StartValidation startValidationProcess;
	private int totalNumberErrors;

	public ReadFile(){

	}	

	public int getTotalNumberErrors() {
		return totalNumberErrors;
	}

	public void setTotalNumberErrors(int totalNumberErrors) {
		this.totalNumberErrors = totalNumberErrors;
	}

	public Boolean getValidFormat() {
		return validFormat;
	}
	
	public void setValidFormat(Boolean validFormat) {
		this.validFormat = validFormat;
	}
	
	public FileInputStream getXlsxfile() {
		return xlsxfile;
	}

	public void setXlsxfile(FileInputStream xlsxfile) {
		this.xlsxfile = xlsxfile;
	}

	
	public void LoadXLSXFile(String filePath){
		try {
			System.out.println("reading");
			//xlsxfile = new FileInputStream(new File("datasets/Dataset.xlsx"));
			xlsxfile = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			System.out.println("In correct file passed to create workbook");
		}		
		System.out.println(getXlsxfile().toString() + "             " + filePath);		
		//workbookCreater = new IterateThroughFile();
		String fileExtension = FilenameUtils.getExtension(filePath);
		if(fileExtension.equals("xlsx")){
			setValidFormat(true);
			//workbookCreater.CreateWorkBook(xlsxfile);
			CreateWorkBook(xlsxfile);
			/*setWorkbook(workbookCreater.getWorkbook());
			setTotalErrors(workbookCreater.getTotalNumberErrors());
			setPkerror(workbookCreater.getPkerror());
			setFkerrors(workbookCreater.getFkerrors());*/
		}
		else{
			System.out.println("Wrong File Format");
			setValidFormat(false);
		}
	}
	
	public void CreateWorkBook(FileInputStream file){
		//readXLSXFile.LoadXLSXFile();
		
		try {
			setWorkbook(new XSSFWorkbook(file));
			System.out.println("WORKBOOK CREATED");
			System.out.println(getWorkbook().getSheetName(0));
			startValidationProcess = new StartValidation(getWorkbook());
			totalNumberErrors = startValidationProcess.getTotalErrors();
			setPkerror(startValidationProcess.getPkfields());
			setFkerrors(startValidationProcess.getFkfields());
			
			file.close();
		} catch (IOException e) {
			System.out.println("IO Exception");
		}

	}

	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}
	
	public ValidatePKFields getPkerror() {
		return pkerror;
	}

	public void setPkerror(ValidatePKFields pkerror) {
		this.pkerror = pkerror;
	}

	public ValidateForeignKeys getFkerrors() {
		return fkerrors;
	}

	public void setFkerrors(ValidateForeignKeys fkerrors) {
		this.fkerrors = fkerrors;
	}

	

}
