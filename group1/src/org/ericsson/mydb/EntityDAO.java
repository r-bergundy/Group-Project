package org.ericsson.mydb;

import java.util.Date;
import java.util.List;

import com.entities.CallFailure;
import com.entities.EventCause;


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

    
    
}
