package com.test;

import static org.junit.Assert.fail;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.ericsson.mydb.EntityDAO;
import org.ericsson.mydb.PersistenceUtil;
import org.ericsson.parser.ImportData;
import org.ericsson.parser.ReadFile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class sampleTest {

	private static Connection connection;
	private static EntityDAO dao;
	static ReadFile id = new ReadFile();
	
	
	
	@BeforeClass
	public static void setup() throws SQLException {
		dao = new EntityDAO();
		PersistenceUtil.switchTestDatabase();
		id.LoadXLSXFile("datasets/testdataset.xlsx");
		//id.populateDatabase();
		connection = (Connection) DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/testdb", "root", "toor");
	}

	@AfterClass
	public static void afterClass() throws SQLException {
		Statement stmt = (Statement) connection.createStatement();
		stmt.execute("DROP DATABASE testdb");
		stmt.execute("CREATE DATABASE testdb");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
