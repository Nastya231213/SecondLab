package com.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JpaDAO<T> {
    private EntityManager entityManager;

    public JpaDAO(EntityManager entityManager) {
        this.entityManager=entityManager;
    }

    public T create(T t) {
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.flush();
        entityManager.refresh(t);
        entityManager.getTransaction().commit();
        return t;
    }

    public T update(T entity) {
    
		entityManager.getTransaction().begin();
		entity=(T) entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return entity;
    }

    public T find(Class<T> type, Object id) {

        entityManager.getTransaction().begin();
        T entity = entityManager.find(type, id);
        entityManager.refresh(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    public void delete(Class<T> type, Object id) {

        entityManager.getTransaction().begin();
        Object reference = entityManager.getReference(type, id);
        entityManager.remove(reference);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public List<T> findWithNamedQuery(String queryName, String paramName, Object paramValue) {
    
        Query query = entityManager.createNamedQuery(queryName);
        query.setParameter(paramName, paramValue);
        List<T> resultList = query.getResultList();
        return resultList;
    }
    public List<T> findWithNamedQuerySeveralParameters(String queryName, Map<String, Object> parameters) {
        
        Query query = entityManager.createNamedQuery(queryName);
        for(Map.Entry<String,Object> parameter:parameters.entrySet()) {
        	query.setParameter(parameter.getKey(), parameter.getValue());
        }
        List<T> resultList=query.getResultList();      
        return resultList;
    }
    
}
