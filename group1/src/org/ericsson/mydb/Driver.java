package org.ericsson.mydb;

import java.io.IOException;
import org.ericsson.parser.ReadFile;

import com.entities.User;
import com.entities.UserType;

public class Driver {
	
	private EntityDAO dao = new EntityDAO();
	
	public Driver() throws IOException{

//		ImportData importData = new ImportData("datasets/dit group project - sample dataset.xlsx");
//		importData.populateDatabase();
//		persistUser();
		ReadFile fileLoader = new ReadFile();
		fileLoader.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
		

	}

	public void persistUser(){
		User user = new User();
		user.setUserType(UserType.CUSTOMER_SERVICE_REP);
		user.setUserName("Mike");
		user.setPassword("secret");
		PersistenceUtil.persist(user);
	}
	
	public static void main(String[] args) throws IOException{
		new Driver();
	}

}
