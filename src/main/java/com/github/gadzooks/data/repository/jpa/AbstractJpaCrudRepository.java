package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.repository.CrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;

@Slf4j
public abstract class AbstractJpaCrudRepository<TD, ID> implements CrudRepository<TD, ID> {
    private final EntityManagerFactory emf;
    private final Class<TD> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractJpaCrudRepository() {
        emf = HibernateUtils.getEntityManagerFactory();
        this.persistentClass = (Class<TD>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

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
            em.getTransaction().begin();
            TD entity = em.find(persistentClass, id);
            em.getTransaction().commit();
            return entity;
        } finally {
            em.close();
        }
    }

    protected abstract void updateEntity(TD update, TD updateFrom);

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
            // do this for all the attributes
//            if(bank.getIsInternational() != entity.getIsInternational())
//                bank.setIsInternational(entity.getIsInternational());
//            if(!bank.getName().equals(entity.getName()))
//                bank.setName(entity.getName());

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
