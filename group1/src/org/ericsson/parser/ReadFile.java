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
			System.out.println("reading");
			//xlsxfile = new FileInputStream(new File("datasets/Dataset.xlsx"));
			xlsxfile = new FileInputStream(new File("datasets/DIT Group Project - Dataset 3A 2014.xlsx"));
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
