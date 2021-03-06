package org.ericsson.mydb;

import java.util.Date;
import java.util.List;

import com.entities.CallFailure;
import com.entities.Device;
import com.entities.EventCause;
import com.entities.User;


public class EntityDAO {
    
    public CallFailure findCallFailure(int id) {
        return (CallFailure) PersistenceUtil.findEntityByPK(CallFailure.class, id);
    }
    
    public int[] findUniqueCauseCodesForImsi(String imsi){
  	
		List resultSet = PersistenceUtil.findUniqueCauseCodesForIMSI(imsi);	
		
    	int[] causeCodes = new int[resultSet.size()];
    	int counter = 0;
		
		for (Object ob : resultSet){
			Object[] values = (Object[]) ob;
			EventCause ec = (EventCause) values[1];
			causeCodes[counter++]=ec.getCauseCode();
		}
    	return causeCodes;
    }
    
    public String[] findEventIDCauseCodeForIMSI(String imsi){

    	List resultSet = PersistenceUtil.findEventIDCauseCodeForIMSI(imsi);

    	String[] eventIdsCauseCodes = new String[resultSet.size()];
    	int counter = 0;

    	for (Object ob : resultSet){
    	Object[] values = (Object[]) ob;

    	EventCause ec = (EventCause) values[1];
    	eventIdsCauseCodes[counter++]= ec.getEventID() + " " + ec.getCauseCode();

    	}
    	return eventIdsCauseCodes;
    	}
    
    public int findCountFailuresForTacInTime(int tac, Date startTime, Date endTime){
    	return PersistenceUtil.findCountFailuresForTacInTime(tac, startTime, endTime);
    }

    //Query 5
    public int findCountFailuresForImsiInTime(String imsi, Date startTime, Date endTime){
    	return PersistenceUtil.findCountFailuresForImsiInTime(imsi, startTime, endTime);
    	}
    
    //Query 7
    public String[] returnIMSIsWithFailureInTime(Date startTime, Date endTime){
    	
		List resultSet = PersistenceUtil.returnIMSIsWithFailureInTime(startTime, endTime);
    	
    	String[] imsis = new String[resultSet.size()];
    	int counter = 0;
		for (Object ob : resultSet) {
			Device d = (Device) ob;
			imsis[counter++] = d.getImsi();
		}
    	
    	return imsis;
    }
    
 
    
    
    public User addUser(User user){
    	
    	PersistenceUtil.persist(user);
    	
    	return findUser(user.getUserName());
    }
    
    
    public User findUser(String username){
        return (User) PersistenceUtil.findEntityByPK(User.class, username);	
    }
    
}
