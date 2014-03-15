package org.ericsson.mydb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.entities.IEntity;

public class PersistenceUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mydb"); 
	 
	public static boolean switchTestDatabase(){
		emf = Persistence.createEntityManagerFactory("testdb"); 
		return true;
	}
	
	public static void persist(IEntity entity) {
		EntityManager em = emf.createEntityManager();
		if (em.find(entity.getClass(), entity.getPrimaryKey()) == null){
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		}
		em.close();
	}
	
	public static void persistTrust(Object entity) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		
		em.close();
	}

	public static void remove(Object entity) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Object mergedEntity = em.merge(entity);
		em.remove(mergedEntity);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void removeById(Class entityClass, Object PK){
		remove(entityClass.cast(findEntityByPK(entityClass, PK)));
	}
	
	public static Object merge(Object entity) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		entity = em.merge(entity);
		em.getTransaction().commit();		
		em.close();
		return entity;
	}
	
	public static Object findEntityByPK(Class entityClass, Object PK){
		EntityManager em = createEM();
		Object obj = em.find(entityClass, PK);
		em.close();
		return obj;
	}

	public static EntityManager createEM() {
		return emf.createEntityManager();
	}	
	
	public static List findEventIDCauseCodeForIMSI(String IMSI){
		EntityManager em = createEM();
		List resultSet = (List) em.createNamedQuery("Find EventID/Cause Code for IMSI").setParameter("paramIMSI", IMSI).getResultList();
		em.close();

		return resultSet;
		}
	
	public static int findCountFailuresForTacInTime(int tac, Date startTime, Date endTime){
		EntityManager em = createEM(); 	
		int result = em.createNamedQuery("Find Count Failures For UE in Time").setParameter("tac", tac)
				.setParameter("startTime", startTime).setParameter("endTime", endTime).getResultList().size();
		em.close();
		return result;
	}

	public static List findUniqueCauseCodesForIMSI(String IMSI){
		EntityManager em = createEM();
		List resultSet = (List) em.createNamedQuery("Find unique Cause Codes for IMSI").setParameter("paramIMSI", IMSI).getResultList();
		em.close();

		return resultSet;
	}

	public static void switchTestDatabase() {
		emf = Persistence.createEntityManagerFactory("testdb"); 	
		
	}
	
	
}

