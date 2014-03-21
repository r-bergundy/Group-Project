package com.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ericsson.mydb.EntityDAO;
import org.ericsson.parser.ReadFile;
import org.ericsson.parser.ValidateForeignKeys;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.jdbc.Connection;

public class TestReadFile {
	private static Connection connection;
	private static EntityDAO dao;
	private static ReadFile readFile = new ReadFile();
	private static ValidateForeignKeys testValidation;
	static XSSFWorkbook testWorkbook;	
	
	@BeforeClass
	public static void setup() throws SQLException {
		
		//readFile.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
		readFile.LoadXLSXFile("datasets/testDataset.xlsx");
		testWorkbook = readFile.getWorkbook();
		testValidation = new ValidateForeignKeys(testWorkbook);

	}	
	
	@Test
	public void testValidFile() throws FileNotFoundException {
		//FileInputStream file = new FileInputStream(new File("datasets/testDataset.xlsx"));
		String ValidfileExtension = FilenameUtils.getExtension("datasets/testDataset.xlsx");
		assertTrue(readFile.getValidFormat());
		
	}
	@Test
	public void testInValidFile() throws FileNotFoundException {
		
		String InvalidfileExtension = FilenameUtils.getExtension("datasets/testDataset.txt");		
		assertFalse(readFile.getValidFormat());
	}

}
