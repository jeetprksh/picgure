package com.picgure.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@SuppressWarnings("deprecation")
@Component
public abstract class DataAccess {
	
	@Autowired
	protected EntityManager entityManager;
	
	/**
	 * This method accept the object and executes save/update/delete/get operation on the current
	 * database session
	 */
	private Object executeOperation(final Operation operation, 
	                                final Object obj, 
	                                final String namedQuery, 
	                                final Map<String, Object> params, int maxResults) throws HibernateException {
		Session session = getSession();
		switch (operation) {
		case SAVE:
			return session.save(obj);
		case UPDATE:
			session.update(obj);
			return null;
		case DELETE:
			session.delete(obj);
			return null;
		case GET:
			Query query = session.getNamedQuery(namedQuery).setProperties(params);	
			if(maxResults != -1){
				query.setMaxResults(maxResults);
			}
			List queryResult = query.list();
			return queryResult;
		case EXECUTE_UPDATE :
			Query updateQuery = session.getNamedQuery(namedQuery).setProperties(params);
			return updateQuery.executeUpdate();
		default:
			throw new AssertionError();
		}
	}
	
	protected final Session getSession() {
		return entityManager.unwrap(Session.class);
	}		

	/**
	 * This method accept the object to call save operation on the current database session
	 */	
	protected final Object saveObject(Object obj)throws HibernateException{
		return this.executeOperation(Operation.SAVE, obj,null, null, -1);	
	}

	/**
	 * This method accept the object to call get operation on the current database session
	 */		
	protected final Object getObject(String namedQuery, Map<String, Object> params)throws HibernateException{
		return getObject(namedQuery, params, -1);
				//this.executeOperation(Op.GET, null, namedQuery, params, null);
	}

	/**
	 * This method accept the object to call get operation on the current database session
	 */		
	protected final Object getObject(String namedQuery, Map<String, Object> params, int maxResults)throws HibernateException{
		return this.executeOperation(Operation.GET, null, namedQuery, params, maxResults);
	}

	/**
	 * Executes a named query with one parameter, and returns the resulting object. If the query
	 * returns more than one object, the first one is returned.
	 * <p>
	 * Use
	 * {@link #getSingleObjectFromNamedQuery(String, Class, String, Object, String, Object)}
	 * to execute a query that takes two parameters as input, and
	 * {@link #getSingleObjectFromNamedQuery(String, Class, Parameter...)} for queries that take
	 * none, or three or more parameters as input.
	 */
	protected final <T> T getSingleObjectFromNamedQuery(String namedQuery, 
	                                                    Class<T> objectType,
	                                                    String paramName,
	                                                    Object paramValue) throws HibernateException {
		return getSingleObjectFromNamedQuery(
				namedQuery, 
				objectType, 
				new Parameter(paramName, paramValue));
	}
	
	/**
	 * Executes a named query with two parameters, and returns the resulting object. If the query
	 * returns more than one object, the first one is returned. Use
	 * {@link #getSingleObjectFromNamedQuery(String, Class, String, Object)} to execute a
	 * query that takes a single parameter as input, and
	 * {@link #getSingleObjectFromNamedQuery(String, Class, Parameter...)} for queries that take
	 * none, or three or more parameters as input.
	 */
	protected final <T> T getSingleObjectFromNamedQuery(String namedQuery, 
	                                                    Class<T> objectType,
	                                                    String paramName1,
	                                                    Object paramValue1,
	                                                    String paramName2,
	                                                    Object paramValue2) throws HibernateException {
		return getSingleObjectFromNamedQuery(
				namedQuery, 
				objectType, 
				new Parameter(paramName1, paramValue1),
				new Parameter(paramName2, paramValue2));
	}

	/**
	 * Executes a named query with the given parameters, and returns the resulting object. If the
	 * query returns more than one object, the first one is returned.
	 */
	protected final <T> T getSingleObjectFromNamedQuery(String namedQuery,
	                                                    Class<T> objectType,
	                                                    Parameter... params) throws HibernateException {
		Iterable<T> objects = executeObjectListQuery(namedQuery, objectType, params);
		Iterator<T> it = objects.iterator();
		return it.hasNext() ? it.next() : null;
	}
	
	private <T> Iterable<T> executeObjectListQuery(String namedQuery,
	                                               Class<T> objectType,
	                                               Parameter... params) throws HibernateException {
		return executeObjectListQuery(namedQuery, objectType,-1, params);
	}

	private <T> Iterable<T> executeObjectListQuery(String namedQuery,
														Class<T> objectType,
														int maxResults,
														Parameter... params) throws HibernateException {
		Map<String, Object> paramsMap = new HashMap<>();
		for (Parameter p : params) {
			paramsMap.put(p.name, p.value);
		}
		Object returnObject = this.getObject(namedQuery, paramsMap, maxResults);
		if (returnObject != null) {
			List result = (List) returnObject;
			return Iterables.filter(result, objectType);
		} else {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Executes a named query with one parameter, and returns a list of the matching objects.
	 * <p>
	 * Use {@link #getObjectList(String, Class, String, Object, String, Object)} to execute
	 * a query that takes two parameters as input, and
	 * {@link #getObjectList(String, Class, Parameter...)} for queries that take none, or three
	 * or more parameters as input.
	 */
	protected final <T> List<T> getObjectList(String namedQuery,
	 		  								  Class<T> objectType,
	 		  								  String paramName,
	 		  								  Object paramValue) throws HibernateException {
		return getObjectList(namedQuery, objectType, -1, paramName, paramValue);
	}
	
	/**
	 * Executes a named query with one parameter, and returns a list of the matching objects.
	 * <p>
	 * Use {@link #getObjectList(String, Class, String, Object, String, Object)} to execute
	 * a query that takes two parameters as input, and
	 * {@link #getObjectList(String, Class, Parameter...)} for queries that take none, or three
	 * or more parameters as input.
	 */
	protected final <T> List<T> getObjectList(String namedQuery,
	 		  								  Class<T> objectType,
	 		  								  int maxResults,
	 		  								  String paramName,
	 		  								  Object paramValue) throws HibernateException {
		return getObjectList(namedQuery, objectType, maxResults, new Parameter(paramName, paramValue));
	}
	
	/**
	 * Executes a named query with two parameters, and returns a list of the matching objects.
	 * <p>
	 * Use {@link #getObjectList(String, Class, String, Object)} to execute a query that
	 * takes one parameter as input, and {@link #getObjectList(String, Class, Parameter...)} for
	 * queries that take none, or three or more parameters as input.
	 */
	protected final <T> List<T> getObjectList(String namedQuery,
	 		  								  Class<T> objectType,
	 		  								  String paramName1,
	 		  								  Object paramValue1,
	 		  								  String paramName2,
	 		  								  Object paramValue2) throws HibernateException {
		return getObjectList(namedQuery, objectType, -1,
								paramName1, paramValue1, paramName2, paramValue2);
	}
	
	/**
	 * Executes a named query with two parameters, and returns a list of the matching objects.
	 * <p>
	 * Use {@link #getObjectList(String, Class, String, Object)} to execute a query that
	 * takes one parameter as input, and {@link #getObjectList(String, Class, Parameter...)} for
	 * queries that take none, or three or more parameters as input.
	 */
	protected final <T> List<T> getObjectList(String namedQuery,
	 		  								  Class<T> objectType,
	 		  								  int maxResults,
	 		  								  String paramName1,
	 		  								  Object paramValue1,
	 		  								  String paramName2,
	 		  								  Object paramValue2) throws HibernateException {
		return getObjectList(namedQuery, objectType, maxResults,
				new Parameter(paramName1, paramValue1), new Parameter(paramName2, paramValue2));
	}
	
	/**
	 * Executes a named query with the given parameters, and returns a list of the matching objects.
	 */
	protected final <T> List<T> getObjectList(String namedQuery,
									 		  Class<T> objectType,
									 		  int maxResults,
									 		  Parameter... params) throws HibernateException {
		Iterable<T> objects = executeObjectListQuery(namedQuery, objectType,maxResults, params);
		return Lists.newArrayList(objects);
	}
	
	/**
	 * Executes a named query with the given parameters, and returns a list of the matching objects.
	 */
	protected final <T> List<T> getObjectList(String namedQuery,
									 		  Class<T> objectType,
									 		  Parameter... params) throws HibernateException {
	
		return getObjectList(namedQuery, objectType, -1, params);
	}
	
}
