package com.picgure.persistence.dao.impl;

import com.picgure.persistence.config.PersistenceConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/*
 * @author Jeet Prakash
 * */

public class CommonDao<T> {

    private Session session;

    CommonDao() {
        session = PersistenceConfig.getSessionFactory().getCurrentSession();
    }

    protected Integer saveEntity(T obj) throws HibernateException {
        Transaction transaction = session.beginTransaction();
        Integer id = (Integer) session.save(obj);
        transaction.commit();
        return id;
    }

    protected List<T> listAll(Class<T> c) {
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(c);
        criteria.from(c);
        List<T> list = session.createQuery(criteria).getResultList();
        transaction.commit();
        return list;
    }
}
