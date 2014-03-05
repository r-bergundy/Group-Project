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
	ReadFile readXLSXFile = new ReadFile();


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

	public IterateThroughFile(){
		CreateWorkBook();

	}

	public void ChooseSheet(String sheetName){ 
		String sheetN  = sheetName;
		setExcelSheet(workbook.getSheet(sheetName));
	}

	public void CreateWorkBook(){
		xlsxFile = readXLSXFile.getXlsxfile();
		try {
			setWorkbook(new XSSFWorkbook(xlsxFile));


		} catch (IOException e) {
			System.out.println("IO Exception");
		}

	}

	public void PrintColumnInFile(XSSFSheet sheet, int columnNumber){

		for(Row row: sheet)
		{

			for(Cell cell : row)
			{				
				//cell = row.getCell(0);
				if(cell == row.getCell(columnNumber)){
					SwitchCellTypes(cell);
				}
				//System.out.println("\n");
			}
		}
		try {
			readXLSXFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void PrintAllItemsInFile(XSSFSheet sheet){

		for(Row row: sheet)
		{

			for(Cell cell : row)
			{				
				SwitchCellTypes(cell);
			}
		}
		try {
			readXLSXFile.getXlsxfile().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SwitchCellTypes(Cell cell){
		switch (cell.getCellType()) 
		{

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell))
			{
				System.out.println(cell.getDateCellValue() + " |\n ");
			}

			else {
				System.out.print(cell.getNumericCellValue() + " |\n ");
			}

			break;
		case Cell.CELL_TYPE_STRING:
			System.out.print(cell.getStringCellValue() + " |\n ");
			break;

		case Cell.CELL_TYPE_BLANK:
			System.out.print(" |\n ");
			break;


		}
	}

	public void checkDateFormat(XSSFSheet sheet){

	}






}
