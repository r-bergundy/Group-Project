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
	private int totalErrors;
	IterateThroughFile workbookCreater;

	public ReadFile(){

	}

	public void LoadXLSXFile(String fileName, String filePath){
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
		workbookCreater = new IterateThroughFile();
		String fileExtension = FilenameUtils.getExtension(filePath);
		if(fileExtension.equals("xlsx")){
			workbookCreater.CreateWorkBook(xlsxfile);
			setTotalErrors(workbookCreater.getTotalNumberErrors());
		}
		else{
			System.out.println("Wrong File Format");
		}
	}

	public FileInputStream getXlsxfile() {
		return xlsxfile;
	}

	public void setXlsxfile(FileInputStream xlsxfile) {
		this.xlsxfile = xlsxfile;
	}

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}


	public int getTotalErrors() {
		return totalErrors;
	}

	public void setTotalErrors(int totalErrors) {
		this.totalErrors = totalErrors;
	}

}
