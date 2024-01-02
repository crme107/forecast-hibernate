package org.example.hibernate.Dao;

import jakarta.persistence.*;
import org.example.hibernate.Utility.SessionManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class GenericDaoImpl<T> implements GenericDao<T> {
    private final Class<T> tClass;

    public GenericDaoImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public Optional<T> getById(long id) {
        Session session = SessionManager.getSession();
        return Optional.ofNullable(session.get(tClass, id));
    }

    @Override
    public Optional<T> getByCode(String code) {
        Session session = SessionManager.getSession();

        TypedQuery<T> query = session.createQuery("from " + tClass.getSimpleName() + " t where t.code = :code", tClass);
        query.setParameter("code", code);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<T> getAll() {
        Session session = SessionManager.getSession();
        TypedQuery<T> query = session.createQuery("from " + tClass.getSimpleName(), tClass);

        return query.getResultList();
    }

    @Override
    public void save(T t) {
        Session session = SessionManager.getSession();
        session.beginTransaction();

        T entity = session.merge(t);
        session.persist(entity);
        session.flush();

        session.getTransaction().commit();
    }

    @Override
    public void update(T t) {
        Session session = SessionManager.getSession();
        session.beginTransaction();

        session.merge(t);
        session.flush();

        session.getTransaction().commit();
    }

    @Override
    public void delete(T t) {
        Session session = SessionManager.getSession();
        session.beginTransaction();

        T entity = session.merge(t);
        session.remove(entity);
        session.flush();

        session.getTransaction().commit();
    }
}
