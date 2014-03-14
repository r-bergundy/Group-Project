package org.ericsson.mydb;

import java.util.List;

import org.ericsson.parser.ImportData;
import org.ericsson.parser.ReadFile;

import com.entities.*;

public class Driver {
	
	private EntityDAO dao = new EntityDAO();
	
	public Driver(){
		
		//ImportData importData = new ImportData("datasets/dit group project - sample dataset.xlsx");
		//importData.populateDatabase();
//		sampleQueries();	
//		persistUser();
		ReadFile fileLoader = new ReadFile();
		fileLoader.LoadXLSXFile("datasets/ronansTestDataset.xlsx");
		//fileLoader.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
		

	}
	
	/**
	 * Four sample queries:
	 * 1 - Find all access capabilities in Access Capability table
	 * 2 - Find all user equipment that are handheld - this "tests" parameter input (HANDHELD)
	 * 3 - Find all user equipment that are handheld and that have access capability GSM 900. This 
	 * "tests" table joins (three tables are joined in this query
	 * 4 - User story #4 - find all call failures for an IMSI
	 */
	public void sampleQueries(){

		List<AccessCapability> resultSet = PersistenceUtil.findAllInTable("AccessCapability");	
		for (AccessCapability ac: resultSet){
			System.out.println("ID: " + ac.getAccessCapabilityID() + "\tName: " + ac.getAccessName());
		}
		
		System.out.println("-----------------");
		List<UserEquipment> resultSetForUEType = PersistenceUtil.findUEWithUEType("HANDHELD");	
		for (UserEquipment ue: resultSetForUEType){
			System.out.println("TAC: " + ue.getTac() + "\tUE Type: " + ue.getUEType());
		}
		
		System.out.println("-----------------");
		List<UserEquipment> resultSetForUETypeAC = PersistenceUtil.findUEWithUETypeAndAccessCapability("HANDHELD", "GSM 900");	
		for (Object ob : resultSetForUETypeAC){
			Object[] values = (Object[]) ob;
			UserEquipment ue = (UserEquipment) values[0];
			UEAccessCapability ueac = (UEAccessCapability) values[1];
			AccessCapability ac = (AccessCapability) values[2];
			System.out.println(ue.getTac());
			
		}
		
		System.out.println("-----------------");
		String imsi = "344930000000011";
		List<UserEquipment> eventCauseForIMSI = PersistenceUtil.findEventIDCauseCodeForIMSI(imsi);	
		System.out.println("Call failures For IMSI: " + imsi);
		System.out.println("Cause code\tEvent ID");
		
		for (Object ob : eventCauseForIMSI){
			Object[] values = (Object[]) ob;
			CallFailure cf = (CallFailure) values[0];
			EventCause ec = (EventCause) values[1];
			Device d = (Device) values[2];

			System.out.println(ec.getCauseCode() + "\t\t" + ec.getEventID());
			
		}
		
	}

	public void persistUser(){
		User user = new User();
		user.setUserType(UserType.CUSTOMER_SERVICE_REP);
		user.setUserName("Mike");
		user.setPassword("secret");
		PersistenceUtil.persist(user);
	}
	
	public static void main(String[] args){
		new Driver();
	}

}
