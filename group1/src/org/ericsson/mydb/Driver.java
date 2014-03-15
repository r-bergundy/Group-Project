package org.ericsson.mydb;

import java.util.List;

import org.ericsson.parser.ImportData;

import com.entities.*;

public class Driver {
	
	private EntityDAO dao = new EntityDAO();
	
	public Driver(){
		
		ImportData importData = new ImportData("datasets/dit group project - sample dataset.xlsx");
		importData.populateDatabase();
//		persistUser();

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
