package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.entities.Market;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Slf4j
public class MarketRepository {
    public Market save(Market entity) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();
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

    public void delete(Long id) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            Market market = em.find(Market.class, id);
            em.remove(market);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }
}
