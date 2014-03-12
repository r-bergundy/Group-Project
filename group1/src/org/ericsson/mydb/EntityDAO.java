package org.ericsson.mydb;

import java.util.List;

import com.entities.CallFailure;
import com.entities.Device;
import com.entities.EventCause;
import com.entities.UserEquipment;


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
			eventIdsCauseCodes[counter++]= ec.getEventID()+ "\t\t" + ec.getCauseCode();
			System.out.println(ec.getEventID() + ", " + ec.getCauseCode());
		}
    	return eventIdsCauseCodes;
    }
    
    
}
