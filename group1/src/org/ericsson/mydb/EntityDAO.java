package org.ericsson.mydb;

import java.util.List;

import com.entities.CallFailure;
import com.entities.Device;
import com.entities.EventCause;
import com.entities.UserEquipment;


public class EntityDAO {
    
    public CallFailure findCallFailure(int id) {
        return (CallFailure) PersistenceUtil.findEntityByIntPK(CallFailure.class, id);
    }
    
    public int[] findUniqueCauseCodesForImsi(String imsi){
  	
		List resultSet = PersistenceUtil.findUniqueCauseCodesForIMSI(imsi);	
		
    	int[] causeCodes = new int[resultSet.size()];
    	int counter = 0;
		
		for (Object ob : resultSet){
			Object[] values = (Object[]) ob;
			EventCause ec = (EventCause) values[1];
			System.out.println(ec.getCauseCode());
			causeCodes[counter]=ec.getCauseCode();
		}
    	
    	return causeCodes;
    }
    
}
