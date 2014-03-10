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
	
	private static EntityDAO dao;
	
	@BeforeClass
	public static void init(){
		dao = new EntityDAO();
	}	
	

	public void testSingleQuery6(String imsi) throws SQLException{		
		
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
	public void testQuery6() throws SQLException{
		
		testSingleQuery6("344930000000001");
		testSingleQuery6("344930000000002");
		testSingleQuery6("240210000000003");
		testSingleQuery6("badImsi");
		testSingleQuery6("badImsi");
		
		
	}
	

}
