package com.test;

import static org.junit.Assert.assertArrayEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.ericsson.mydb.EntityDAO;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class TestQueries {
	
//	@BeforeClass
//	public void init(){
//		System.out.println("hi");
//	}
	private EntityDAO dao = new EntityDAO();		
	
	@Test
	public void testQuery6() throws SQLException{	
		
		String imsi = "344930000000001";		
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "toor");
		Statement stmt = (Statement) conn.createStatement() ;
		String query = "select ec.causecode from callfailure cf, eventcause ec, device d where cf.eventcauseid = "
				+ "ec.eventcauseid and cf.imsi = d.imsi and d.imsi = '" + imsi + "' group by ec.causecode;" ;
		ResultSet rs = (ResultSet) stmt.executeQuery(query) ;
		
	    int size =0;  
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
	public void testQuery4() throws SQLException{	
		
		String imsi = "344930000000001";		

		String[] actualResults = dao.findEventIDCauseCodeForIMSI(imsi);

		
	}

}
