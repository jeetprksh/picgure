package com.picgure.persistence.dao.impl;

import com.picgure.persistence.config.PersistenceConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
        Long id = (Long) session.save(obj);
        session.close();
        return id;
    }

    protected <T> void update(T obj) throws HibernateException {
        Session session = sessionFactory.openSession();
        session.update(obj);
        session.close();
    }

    protected <T> void delete(T obj) throws HibernateException {
        Session session = sessionFactory.openSession();
        session.delete(obj);
        session.close();
    }

    protected <T> T getSingle(String namedQuery, Class<T> objectType,
                              Map<String, String> params) throws HibernateException {
        Session session = sessionFactory.openSession();
        T object = session.createNamedQuery(namedQuery, objectType).setProperties(params).getSingleResult();
        session.close();
        return object;
    }

    protected <T> List<T> list(String namedQuery, Class<T> objectType,
                               Map<String, String> params) throws HibernateException {
        Session session = sessionFactory.openSession();
        List<T> list = session.createNamedQuery(namedQuery, objectType).setProperties(params).getResultList();
        session.close();
        return list;
    }
}
