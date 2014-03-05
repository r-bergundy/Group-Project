package org.ericsson.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFile {
	
	private FileInputStream xlsxfile;
	private XSSFWorkbook workbook;
	
	public ReadFile(){
		LoadXLSXFile();
	}
	
	public void LoadXLSXFile(){
		try {
			xlsxfile = new FileInputStream(new File("datasets/dit group project - sample dataset.xlsx"));
			//xlsxfile = new FileInputStream(new File("Dataset.xlsx"));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			System.out.println("In correct file passed to create workbook");
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
	
	

}
