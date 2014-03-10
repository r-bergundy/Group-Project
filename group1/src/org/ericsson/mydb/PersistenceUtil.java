package org.ericsson.mydb;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class PersistenceUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mydb"); 	
	
	public static void persist(Object entity) {
		EntityManager em = emf.createEntityManager();
		Session session = (Session) em.getDelegate();
		Object primaryKey  = session.getIdentifier(entity);
		if (em.find(entity.getClass(), primaryKey) == null){
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
	
	public static List findAllInTable(String className){
		EntityManager em = createEM();
		List resultSet = (List) em.createNamedQuery(className + ".findAll").getResultList();
		em.close();

		return resultSet;
	}
	
	public static List findUEWithUEType(String UEType){
		EntityManager em = createEM();
		List resultSet = (List) em.createNamedQuery("Find UE With UEType").setParameter("paramUEType", UEType).getResultList();
		em.close();

		return resultSet;
	}
	
	public static List findUEWithUETypeAndAccessCapability(String UEType, String accessCapability){
		EntityManager em = createEM();
		List resultSet = (List) em.createNamedQuery("Find UE With UEType and AccessCapability").setParameter("paramUEType", UEType)
				.setParameter("paramAC", accessCapability).getResultList();
		em.close();

		return resultSet;
	}
	
	
	public static List findEventIDCauseCodeForIMSI(String IMSI){
		EntityManager em = createEM();
		System.out.println("findEventIDCauseCodeForIMSI method from PersistenceUtil class");
		List resultSet = (List) em.createNamedQuery("Find EventID/Cause Code for IMSI").setParameter("paramIMSI", IMSI).getResultList();
		System.out.println("hi");
		em.close();

		return resultSet;
	}
	
	public static List findUniqueCauseCodesForIMSI(String IMSI){
		EntityManager em = createEM();
		List resultSet = (List) em.createNamedQuery("Find unique Cause Codes for IMSI").setParameter("paramIMSI", IMSI).getResultList();
		em.close();

		return resultSet;
	}
	
}

