package org.ericsson.mydb;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ericsson.parser.ImportData;
import org.ericsson.parser.ReadFile;
import org.ericsson.parser.ValidateForeignKeys;
import org.ericsson.parser.ValidatePKFields;

import com.entities.User;
import com.entities.UserType;

public class Driver {
	
	private EntityDAO dao = new EntityDAO();
	
	public Driver() throws IOException{

//		ImportData importData = new ImportData("datasets/dit group project - sample dataset.xlsx");
//		importData.populateDatabase();
//		persistUser();
		ReadFile readFile = new ReadFile();
		readFile.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
		XSSFWorkbook testWorkbook = readFile.getWorkbook();

		ImportData importData = new ImportData(testWorkbook,new ValidateForeignKeys(), new ValidatePKFields());
		importData.populateDatabase();
		

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
