package org.ericsson.mydb;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.entities.Device;

public class Driver {
	
	private EntityDAO dao = new EntityDAO();
	
	public Driver() throws IOException, ParseException{

//		ReadFile readFile = new ReadFile();
//		readFile.LoadXLSXFile("datasets/dit group project - sample dataset.xlsx");
//		XSSFWorkbook testWorkbook = readFile.getWorkbook();
//
//		ImportData importData = new ImportData(testWorkbook,new ValidateForeignKeys(), new ValidatePKFields());

		EntityResource res = new EntityResource();

		List resultSet = res.Query7("2013-01-01T00:00,2014-01-01T00:00");
		
		
    	String[] imsis = new String[resultSet.size()];

		for (Object ob : resultSet) {
			Device d = (Device) ob;
			System.out.println(d.getImsi());
		}

		
		//System.out.println(res.Query7("2013-01-01T00:00,2014-01-01T00:00"));
		
		//344930000000001,2013-01-01T00:00,2014-01-01T00:00
		
		
	}

	public static void main(String[] args) throws IOException, ParseException{
		new Driver();
	}

}
