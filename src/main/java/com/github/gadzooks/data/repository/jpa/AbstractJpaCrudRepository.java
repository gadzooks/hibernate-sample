package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.repository.CrudRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;

@Slf4j
public abstract class AbstractJpaCrudRepository<TD, ID> implements CrudRepository<TD, ID> {
    @Getter
    private final EntityManagerFactory emf;
    private final Class<TD> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractJpaCrudRepository() {
        emf = HibernateUtils.getEntityManagerFactory();
        this.persistentClass = (Class<TD>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected abstract void updateEntity(TD update, TD updateFrom);

    protected abstract String namedQueryForCount();

    public TD saveUsingSession(TD entity) {
        EntityManager em = emf.createEntityManager();

        //NOTE : how to get session from EntityManager
        try (Session session = em.unwrap(Session.class)) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public Long size() {
        EntityManager em = getEmf().createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            Query query = em.createNamedQuery(namedQueryForCount());
            return (Long) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public TD save(TD entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            if (tx != null) tx.rollback();
        } finally {
            em.close();
        }

        return entity;
    }

    @Override
    public TD findById(ID id) {
        EntityManager em = emf.createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            return em.find(persistentClass, id);
        } finally {
            em.close();
        }
    }


    @Override
    public TD update(TD updateFrom, ID id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            tx = em.getTransaction();
            tx.begin();
            TD entity = em.find(persistentClass, id);

            updateEntity(entity, updateFrom);
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            if (tx != null) tx.rollback();
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public void delete(ID id) {
        EntityManager em = emf.createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            TD entity = em.find(persistentClass, id);
            em.remove(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
