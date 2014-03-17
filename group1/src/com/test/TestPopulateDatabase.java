package com.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ericsson.mydb.EntityDAO;
import org.ericsson.mydb.PersistenceUtil;
import org.ericsson.parser.ImportData;
import org.ericsson.parser.ReadFile;
import org.ericsson.parser.ValidateForeignKeys;
import org.ericsson.parser.ValidatePKFields;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.entities.AccessCapability;
import com.entities.CallFailure;
import com.entities.Device;
import com.entities.EventCause;
import com.entities.FailureClass;
import com.entities.Operator;
import com.entities.UEAccessCapability;
import com.entities.UserEquipment;
import com.mysql.jdbc.Statement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPopulateDatabase {

	private static EntityDAO dao;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
	private static Connection connection;
	private static ImportData importData;
	private static ReadFile readFile = new ReadFile();
	private static XSSFWorkbook testWorkbook;	

	@BeforeClass
	public static void setup() throws SQLException, InterruptedException {
		dao = new EntityDAO();

		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "toor");
		Statement stmt = (Statement) connection.createStatement();
		 stmt.execute("DROP DATABASE testdb");
			Thread.sleep(5000);
		 stmt.execute("CREATE DATABASE testdb");

		readFile.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
		testWorkbook = readFile.getWorkbook();
		PersistenceUtil.switchTestDatabase();
		importData = new ImportData(testWorkbook,new ValidateForeignKeys(), new ValidatePKFields());
	}

	@Test
	public void test1PopulateEventCause() {

		importData.populateEventCause();

		EventCause ec = (EventCause) PersistenceUtil.findEntityByPK(EventCause.class, 1);

		assertEquals(1, ec.getEventCauseID());
		assertEquals(0, ec.getCauseCode());
		assertEquals(4097, ec.getEventID());
		assertEquals("RRC CONN SETUP-SUCCESS", ec.getDescription());

	}

	@Test
	public void test2PopulateAccessCapability() throws SQLException {

		importData.populateAccessCapability();

		AccessCapability ac = (AccessCapability) PersistenceUtil.findEntityByPK(AccessCapability.class, 1);

		assertEquals(1, ac.getAccessCapabilityID());
		assertEquals("GSM 1800", ac.getAccessName());

	}

	@Test
	public void test3PopulateUserEquipment() throws SQLException {

		importData.populateUserEquipment();

		UserEquipment ue = (UserEquipment) PersistenceUtil.findEntityByPK(UserEquipment.class, 100100);

		assertEquals(100100, ue.getTac());
		assertEquals("G410", ue.getMarketingName());
		assertEquals("Mitsubishi", ue.getManufacturer());
		assertEquals("Mitsubishi", ue.getVenderName());
		assertEquals("(null)", ue.getUEType());
		assertEquals("(null)", ue.getOperatingSystem());
		assertEquals("(null)", ue.getInputMode());

	}

	@Test
	public void test4PopulateFailureClass() throws SQLException {

		importData.populateFailureClass();

		FailureClass fc = (FailureClass) PersistenceUtil.findEntityByPK(FailureClass.class, 0);

		assertEquals(0, fc.getFailureClassID());
		assertEquals("EMERGENCY", fc.getDescription());

	}

	@Test
	public void test5PopulateUEAccessCapability() throws SQLException {

		importData.populateUEAccessCapability();

		UEAccessCapability ueac = (UEAccessCapability) PersistenceUtil.findEntityByPK(UEAccessCapability.class, 1);

		assertEquals(1, ueac.getUEAccessCapabilityID());
		assertEquals(1, ueac.getAccesscapability().getAccessCapabilityID());
		assertEquals(100100, ueac.getUserequipment().getTac());

	}
	
	@Test
	public void test6PopulateOperator() throws SQLException {

		importData.populateOperator();

		Operator o = (Operator) PersistenceUtil.findEntityByPK(Operator.class, 1);

		assertEquals(1, o.getOperatorID());
		assertEquals(238, o.getMcc());
		assertEquals(1, o.getMnc());
		assertEquals("Denmark", o.getCountry());
		assertEquals("TDC-DK", o.getOperatorName());

	}
	
	@Test
	public void test7Device() throws SQLException {

		importData.populateDevice();

		Device d = (Device) PersistenceUtil.findEntityByPK(Device.class, "344930000000011");

		System.out.println();
		assertEquals("344930000000011", d.getImsi());
		assertEquals("AT&T Wireless-Antigua AG ", d.getOperator().getOperatorName());
		assertEquals(21060800, d.getUserequipment().getTac());

	}
	
	@Test
	public void test8CallFailure() throws SQLException, ParseException {

		importData.populateCallFailure();

		CallFailure cf = (CallFailure) PersistenceUtil.findEntityByPK(CallFailure.class, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		Date date = sdf.parse("2013-01-11 17:15:00");
		
		assertEquals(1, cf.getCallFailureID());
		assertEquals(4, cf.getCellID());
		assertEquals(date, cf.getDateTime());
		assertEquals("344930000000011", cf.getDevice().getImsi());
		assertEquals(1000, cf.getDuration());
		assertEquals(18, cf.getEventcause().getEventCauseID());
		assertEquals(1, cf.getFailureclass().getFailureClassID());
		assertTrue(cf.getHier321id().contains("1.150444940909"));
		assertTrue(cf.getHier32id().contains("8.2268963609474"));
		assertTrue(cf.getHier3id().contains("4.8095320816149"));
		assertEquals("11B", cf.getNEVersion());

	}
	
	@Test
	public void test9AccessCapabilityMerge() {

		AccessCapability ac1 = (AccessCapability) 
				PersistenceUtil.findEntityByPK(AccessCapability.class, 1);

		//Test reference to parent table
		assertNotNull(ac1.getUeaccesscapabilities());
		
		//Test edit/merge integrity
		String accessCapabilityName = ac1.getAccessName();
		ac1.setAccessName("test");		
		PersistenceUtil.merge(ac1);	
		AccessCapability ac2 = (AccessCapability) 
				PersistenceUtil.findEntityByPK(AccessCapability.class, 1);
		assertEquals("test", ac2.getAccessName());
		
		//Revert to original value
		ac1.setAccessName(accessCapabilityName);	
		PersistenceUtil.merge(ac1);	
		
		//Ensure reverting back has worked properly
		ac1 = (AccessCapability) 
				PersistenceUtil.findEntityByPK(AccessCapability.class, 1);
		assertEquals(accessCapabilityName, ac1.getAccessName());

	}
	
	@Test
	public void testXAccessCapabilityAddRemove() {
		
		//Test adding an object
		AccessCapability ac1 = new AccessCapability();
		PersistenceUtil.persist(ac1);
		ac1 = (AccessCapability) PersistenceUtil.findEntityByPK(AccessCapability.class, 11);
		assertEquals(null, ac1.getAccessName());
		
		
		//Test removing an object
		PersistenceUtil.removeById(AccessCapability.class, 11);
		AccessCapability ac3 = (AccessCapability) 
				PersistenceUtil.findEntityByPK(AccessCapability.class, 11);
		assertNull(ac3);
		
	}

}
