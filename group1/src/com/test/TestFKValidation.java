package com.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ericsson.mydb.EntityDAO;
import org.ericsson.parser.ReadFile;
import org.ericsson.parser.ValidateForeignKeys;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.jdbc.Connection;

public class TestFKValidation {

	private static Connection connection;
	private static EntityDAO dao;
	private static ReadFile readFile = new ReadFile();
	private static ValidateForeignKeys testValidation;
	static XSSFWorkbook testWorkbook;	
	
	
	@BeforeClass
	public static void setup() throws SQLException {
		
		//readFile.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
		readFile.LoadXLSXFile("datasets/ronansTestDataset.xlsx");
		testWorkbook = readFile.getWorkbook();
		testValidation = new ValidateForeignKeys(testWorkbook);

	}

	@AfterClass
	public static void afterClass() throws SQLException {
		
	}
	
	@Test
	public void TestPopulateArrayList(){
		XSSFSheet testSheet = testWorkbook.getSheetAt(4);
		ArrayList<Double> testDescription = new ArrayList<Double>();
		testValidation.populateArrayList(testSheet, testDescription, 0);
		assertTrue(testValidation.getMccValues().size() > 0);
		
	}
	
	@Test
	public void testGetCellReference(){
		XSSFSheet testSheet = testWorkbook.getSheetAt(4);
		Row row = testSheet.getRow(2);
		Cell cell = row.getCell(2);
		testValidation.getCellReference(cell, row);
		assertTrue(!testValidation.getInvlaidCellRef().isEmpty());
		
	}
	
	@Test
	public void testCompareMethod(){
		XSSFSheet testSheet = testWorkbook.getSheetAt(1);
		ArrayList<Double> testValues = new ArrayList<Double>();
		testValidation.populateArrayList(testSheet, testValues, 1);
		testValidation.CompareValues(1, testValues);
		assertTrue(!testValidation.getInvlaidCellRef().isEmpty());
	}
	
}
