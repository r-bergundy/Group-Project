package org.ericsson.mydb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.login.LoginException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.omg.CORBA.portable.ApplicationException;

import com.entities.CallFailure;
import com.entities.User;

@Path("/custservrep")
public class EntityResource{

	private EntityDAO dao = new EntityDAO();
	
	@GET  @Path("findUser/{uname}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public User findUser(@PathParam("uname") String userName) throws Exception {
		
		System.out.println("findUser!");
        User user = (User) PersistenceUtil.findEntityByPK(User.class, userName);

        if (user == null){
            System.out.println("User not found");
            throw new Exception("User '" + userName + "' not found");
            }
        else {
            return user;
        }
        
	}
	
	@POST @Path("addUser/")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public User create(User user) {

        PersistenceUtil.persistTrust(user);
        return user;
    }


	@GET
	@Path("findimsi/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String findCallFailure(@PathParam("id") String id) {

		System.out.println("findCallFailure");
		CallFailure cf = dao.findCallFailure(Integer.parseInt(id));
		String imsi = cf.getDevice().getImsi();
		System.out.println("test" + imsi);
		return imsi;

	}



	@GET
	@Path("findUniqueCauseCodes/{imsi}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int[] query6(@PathParam("imsi") String imsi) {
		
		System.out.println("findUniqueCauseCodesee");
		int[] resultSet = dao.findUniqueCauseCodesForImsi(imsi);

		
		return resultSet;

	}

	@GET
	@Path("findEventIdsCauseCodes/{imsi}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String[] findEventIdsCauseCodes(@PathParam("imsi") String imsi) {

		System.out.println("findEventIdsCauseCodes");
		String[] resultSet = dao.findEventIDCauseCodeForIMSI(imsi);
		return resultSet;

	}

	@GET
	@Path("query8/{tacTimes}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int query8(@PathParam("tacTimes") String tacTimes) throws ParseException {

		String[] data = tacTimes.split(",");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date startDate = sdf.parse(data[1]);
		Date endDate = sdf.parse(data[2]);

		return dao.findCountFailuresForTacInTime(Integer.parseInt(data[0]), startDate, endDate);

	}

}
