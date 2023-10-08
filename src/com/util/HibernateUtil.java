package com.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	public HibernateUtil() {
		entityManagerFactory = Persistence.createEntityManagerFactory("labSecond");
		entityManager = entityManagerFactory.createEntityManager();
	}

	public void destroy() {
		entityManager.close();
		entityManagerFactory.close();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}


}
