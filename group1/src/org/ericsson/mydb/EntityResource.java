package org.ericsson.mydb;

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
		return imsi;
	}
	
	
	@GET  @Path("findUniqueCauseCodes/{imsi}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int[] query6(@PathParam("imsi") String imsi) {
		System.out.println("findUniqueCauseCodes");
		int[] resultSet = dao.findUniqueCauseCodesForImsi(imsi);
		return resultSet;
	}

}
