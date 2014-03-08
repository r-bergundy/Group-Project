package com.test;

import static org.junit.Assert.*;

import org.ericsson.mydb.PersistenceUtil;
import org.junit.Test;

import com.entities.*;


public class TestEntitiyIntegrity {

	@Test
	public void testAccessCapabilityMerge() {

		AccessCapability ac1 = (AccessCapability) 
				PersistenceUtil.findEntityByPK(AccessCapability.class, 1);

		//Test reference to parent table
		assertNotNull(ac1.getUeaccesscapabilities());
		
		//Test edit/merge integrity
		String accessCapabilityName = ac1.getAccessName();
		ac1.setAccessName("test");		
		PersistenceUtil.merge(ac1);	
		AccessCapability ac2 = (AccessCapability) 
				PersistenceUtil.findEntityByPK(AccessCapability.class, 1);
		assertEquals("test", ac2.getAccessName());
		
		//Revert to original value
		ac1.setAccessName(accessCapabilityName);	
		PersistenceUtil.merge(ac1);	
		
		//Ensure reverting back has worked properly
		ac1 = (AccessCapability) 
				PersistenceUtil.findEntityByPK(AccessCapability.class, 1);
		assertEquals(accessCapabilityName, ac1.getAccessName());

	}
	
	@Test
	public void testAccessCapabilityAddRemove() {
		
		//Test adding an object
		AccessCapability ac1 = new AccessCapability();
		PersistenceUtil.persist(ac1);
		ac1 = (AccessCapability) PersistenceUtil.findEntityByPK(AccessCapability.class, 11);
		assertEquals(null, ac1.getAccessName());
		
		
		//Test removing an object
		PersistenceUtil.removeById(AccessCapability.class, 11);
		AccessCapability ac3 = (AccessCapability) 
				PersistenceUtil.findEntityByPK(AccessCapability.class, 11);
		assertNull(ac3);
		
	}

}
