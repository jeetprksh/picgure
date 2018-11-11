package com.picgure.persistence.dao.impl;

import com.picgure.persistence.config.PersistenceConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;

/*
 * @author Jeet Prakash
 * */
public class CommonDao {

    private SessionFactory sessionFactory;

    CommonDao() {
        sessionFactory = PersistenceConfig.getSessionFactory();
    }

    protected <T> Long saveEntity(T obj) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        Long id = (Long) session.save(obj);
        session.close();
        transaction.commit();
        return id;
    }

    protected <T> void update(T obj) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        session.update(obj);
        transaction.commit();
    }

    protected <T> void delete(T obj) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        session.delete(obj);
        transaction.commit();
    }

    protected <T> T getSingle(String namedQuery, Class<T> objectType, Map<String, String> params) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        T object = session.createNamedQuery(namedQuery, objectType).setProperties(params).getSingleResult();
        transaction.commit();
        return object;
    }

    protected <T> List<T> list(String namedQuery, Class<T> objectType, Map<String, String> params) throws HibernateException {
        Session session = sessionFactory.openSession();
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        List<T> list = session.createNamedQuery(namedQuery, objectType).setProperties(params).getResultList();
        transaction.commit();
        return list;
    }
}
