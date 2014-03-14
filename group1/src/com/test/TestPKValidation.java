package com.test;

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ericsson.mydb.EntityDAO;
import org.ericsson.mydb.PersistenceUtil;
import org.ericsson.parser.ReadFile;
import org.ericsson.parser.ValidatePKFields;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TestPKValidation {

	private static Connection connection;
	private static EntityDAO dao;
	static ReadFile readFile = new ReadFile();
	private ValidatePKFields testValidation = new ValidatePKFields();
	static XSSFWorkbook testWorkbook;	
	
	
	@BeforeClass
	public static void setup() throws SQLException {
		
		//readFile.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
		readFile.LoadXLSXFile("datasets/ronansTestDataset.xlsx");
		testWorkbook = readFile.getWorkbook();

	}

	@AfterClass
	public static void afterClass() throws SQLException {
		
	}
	
	@Test
	public void TestCheckForNULLandBlanks() {
		XSSFSheet testsheet = testWorkbook.getSheetAt(2);
		Row row = testsheet.getRow(2);
		Cell cell = row.getCell(0);
		testValidation.CheckForNULLorBlanks(row, cell, 0);
		assertTrue(testValidation.getInvalidCellRef().size() >= 1);
		
	}
	
	@Test
	public void TestSearchColumn(){
		int columnIndex;
		XSSFSheet testsheet = testWorkbook.getSheetAt(0);
		testValidation.setWorkBook(testWorkbook);
		testValidation.ChooseSheet(testsheet.getSheetName());
		Row row = testsheet.getRow(0);
		testValidation.SearchColumn(7);
		assertTrue(testValidation.getInvalidCellRef().isEmpty());
		
	}
	
	@Test
	public void TestIsFailureClassVALID(){
		XSSFSheet testsheet = testWorkbook.getSheetAt(2);
		testValidation.setWorkBook(testWorkbook);
		testValidation.ChooseSheet(testsheet.getSheetName());
		testValidation.SearchColumn(0);
		assertTrue(testValidation.getInvalidCellRef().size() == 1);
	}

	
	@Test
	public void TestIsIMSIVALID(){
		XSSFSheet testsheet = testWorkbook.getSheetAt(0);
		testValidation.setWorkBook(testWorkbook);
		testValidation.ChooseSheet(testsheet.getSheetName());
		testValidation.SearchColumn(10);
		assertTrue(testValidation.getInvalidCellRef().size() == 2);
	}
	
	@Test
	public void TestIsTACVALID(){
		XSSFSheet testsheet = testWorkbook.getSheetAt(3);
		testValidation.setWorkBook(testWorkbook);
		testValidation.ChooseSheet(testsheet.getSheetName());
		testValidation.SearchColumn(0);
		assertTrue(testValidation.getInvalidCellRef().isEmpty());
	}
	

}
