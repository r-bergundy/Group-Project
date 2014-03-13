package org.ericsson.mydb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.entities.CallFailure;

@Path("/custservrep")
public class EntityResource {

	private EntityDAO dao = new EntityDAO();
	
	
	@GET  @Path("findimsi/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String findCallFailure(@PathParam("id") String id) {
		
		System.out.println("findCallFailure");
		CallFailure cf = dao.findCallFailure(Integer.parseInt(id));
		String imsi = cf.getDevice().getImsi();
		System.out.println("test" + imsi);
		return imsi;
		
	}
	
	
	@GET  @Path("findUniqueCauseCodes/{imsi}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int[] query6(@PathParam("imsi") String imsi) {
		
		System.out.println("findUniqueCauseCodes");	
		int[] resultSet = dao.findUniqueCauseCodesForImsi(imsi);	 
		return resultSet;
		
	}
	
	@GET  @Path("findEventIdsCauseCodes/{imsi}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String[] findEventIdsCauseCodes(@PathParam("imsi") String imsi) {
		
		System.out.println("findEventIdsCauseCodes");	
		String[] resultSet = dao.findEventIDCauseCodeForIMSI(imsi);	
		return resultSet;
		
	}
	
	@GET  @Path("query8/{tacTimes}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int query8(@PathParam("tacTimes") String tacTimes) {
		
		System.out.println("query8!!");	
		
		String[] data = tacTimes.split("|");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date startDate=null, endDate = null;
		try {
			startDate = sdf.parse(data[1]);
			endDate = sdf.parse(data[2]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return  dao.findCountFailuresForTacInTime(data[0], startDate, endDate);	
		
		
	}


}