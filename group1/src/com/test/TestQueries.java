package com.test;

import org.ericsson.mydb.EntityDAO;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestQueries {
	
	private EntityDAO dao;
	
	@BeforeClass
	public void init(){
		
		dao = new EntityDAO();
		
	}
	
	@Test
	public void testQuery6(){
		
		int[] resultSet = dao.findUniqueCauseCodesForImsi("rr");
		
		
		
		
		
	}
	
	

}
