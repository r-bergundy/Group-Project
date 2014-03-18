package com.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ericsson.mydb.EntityDAO;
import org.ericsson.mydb.PersistenceUtil;
import org.ericsson.parser.ImportData;
import org.ericsson.parser.ReadFile;
import org.ericsson.parser.ValidateForeignKeys;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class TestQueries {

	private static EntityDAO dao;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
	private static Connection connection;
	private static ReadFile readFile = new ReadFile();
	private static ValidateForeignKeys testValidation;
	static XSSFWorkbook testWorkbook;	

	@BeforeClass
	public static void setup() throws SQLException {
		dao = new EntityDAO();
		PersistenceUtil.switchTestDatabase();

		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "toor");
		/*dao = new EntityDAO();
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "toor");
		Statement stmt = (Statement) connection.createStatement();
		 stmt.execute("DROP DATABASE testdb");
			Thread.sleep(5000);
		 stmt.execute("CREATE DATABASE testdb");


		readFile.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
		testWorkbook = readFile.getWorkbook();
				PersistenceUtil.switchTestDatabase();
		importData = new ImportData(testWorkbook,new ValidateForeignKeys(), new ValidatePKFields());
		importData.populateDatabase();/*

		/*FOR RONAN*/ //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "");
	}

	@Test
	public void testQuery4() throws SQLException {

		testSingleQuery4("344930000000001");
		testSingleQuery4("344930000000002");
		testSingleQuery4("240210000000003");
		testSingleQuery4("badImsi");

	}

	public void testSingleQuery4(String imsi) throws SQLException {

		Statement stmt = (Statement) connection.createStatement();
		String myQuery = "select ec.causecode, ec.eventid from device d, callfailure cf, eventcause ec where "
				+ "cf.eventcauseid = ec.eventcauseid and cf.imsi=d.imsi and d.imsi='" + imsi + "';";
		ResultSet rs = (ResultSet) stmt.executeQuery(myQuery);

		int size = 0;
		if (rs != null) {
			rs.beforeFirst();
			rs.last();
			size = rs.getRow();
		}

		String[] expectedResults = new String[size];
		int index = 0;
		rs.beforeFirst();
		while (rs.next()) {
			expectedResults[index++] = (rs.getString(2) + " " + rs.getString(1));
			System.out.println(rs.getString(2) + " " + rs.getString(1));
		}

		String[] actualResults = dao.findEventIDCauseCodeForIMSI(imsi);

		assertArrayEquals(expectedResults, actualResults);

	}

	@Test
	public void testQuery5() throws SQLException, ParseException {
		Date startDate = sdf.parse("2013-01-11 17:00:00");
		Date endDate = sdf.parse("2013-01-11 18:00:00");
		testSingleQuery5("344930000000001", startDate, endDate);
		testSingleQuery5("344930000000001", startDate, endDate);
		testSingleQuery5("344930000000001", startDate, endDate);
		testSingleQuery5("310560000000012", startDate, endDate);
		testSingleQuery5("-1", startDate, endDate);
	}


	public void testSingleQuery5(String imsi, Date startTime, Date endTime) throws SQLException{
		Statement stmt = (Statement) connection.createStatement() ;
		String query = "SELECT distinct imsi from callfailure cf WHERE cf.dateTime >='"+ startTime +"' AND cf.dateTime <='"+ endTime + "';";
		ResultSet rs = (ResultSet) stmt.executeQuery(query) ;
		rs.next();
		int actualResult = dao.findCountFailuresForImsiInTime(imsi, startTime, endTime);
		assertEquals(rs.getInt(1), actualResult);
	}



	@Test
	public void testQuery6() throws SQLException {

		testSingleQuery6("344930000000001");
		testSingleQuery6("344930000000002");
		testSingleQuery6("240210000000003");
		testSingleQuery6("badImsi");

	}

	public void testSingleQuery6(String imsi) throws SQLException {

		Statement stmt = (Statement) connection.createStatement();
		String query = "select ec.causecode from callfailure cf, eventcause ec, device d where cf.eventcauseid = "
				+ "ec.eventcauseid and cf.imsi = d.imsi and d.imsi = '" + imsi + "' group by ec.causecode;";
		ResultSet rs = (ResultSet) stmt.executeQuery(query);

		int size = 0;
		if (rs != null) {
			rs.beforeFirst();
			rs.last();
			size = rs.getRow();
		}

		int[] expectedResults = new int[size];
		int index = 0;
		rs.beforeFirst();
		while (rs.next()) {
			expectedResults[index++] = Integer.parseInt(rs.getString(1));
		}

		int[] actualResults = dao.findUniqueCauseCodesForImsi(imsi);

		assertArrayEquals(expectedResults, actualResults);

	}


	@Test
	public void testQuery7() throws SQLException, ParseException{

		Date startDate = sdf.parse("2013-01-11 17:00:00");
		Date endDate = sdf.parse("2013-01-11 18:00:00");

		testSingleQuery7(startDate, endDate);
		testSingleQuery7(new Date(), endDate);
		testSingleQuery7(startDate, new Date());
		testSingleQuery7(startDate, startDate);
		testSingleQuery7(endDate, endDate);
	}

	public void testSingleQuery7(Date startTime, Date endTime) throws SQLException{ 

		Statement stmt = (Statement) connection.createStatement() ;
		String query = "SELECT distinct imsi from callfailure cf WHERE cf.dateTime >='"+ startTime +"' AND cf.dateTime <='"+ endTime + "';";
		ResultSet rs = (ResultSet) stmt.executeQuery(query) ;
		rs.next();	
		int size = 0;
		if (rs != null) {
			rs.beforeFirst();
			rs.last();
			size = rs.getRow();
		}
		String[] expectedResults = new String[size];
		int index = 0;
		rs.beforeFirst();
		while (rs.next()) {
			expectedResults[index++] = rs.getString(1);
		}
		String[] actualResult = dao.returnIMSIsWithFailureInTime(startTime, endTime);
		assertEquals(expectedResults, actualResult);


	}


	@Test
	public void testQuery8() throws SQLException, ParseException {

		Date startDate = sdf.parse("2013-01-11 17:15:00");
		Date endDate = sdf.parse("2013-01-11 17:20:00");

		testSingleQuery8(21060800, startDate, endDate);
		testSingleQuery8(21060800, new Date(), endDate);
		testSingleQuery8(21060800, startDate, new Date());
		testSingleQuery8(33000153, startDate, endDate);
		testSingleQuery8(-1, startDate, endDate);

	}

	public void testSingleQuery8(int tac, Date startTime, Date endTime) throws SQLException {

		Statement stmt = (Statement) connection.createStatement();

		String query = "SELECT COUNT(*) from callfailure cf, userequipment ue, device d WHERE cf.imsi = d.imsi AND d.tac = "
				+ "ue.tac AND ue.tac = "
				+ tac
				+ " AND cf.dateTime >= '"
				+ sdf.format(startTime)
				+ "' AND cf.dateTime <= '" + sdf.format(endTime) + "';";

		ResultSet rs = (ResultSet) stmt.executeQuery(query);
		rs.next();

		int actualResult = dao.findCountFailuresForTacInTime(tac, startTime, endTime);

		assertEquals(rs.getInt(1), actualResult);

	}

}
