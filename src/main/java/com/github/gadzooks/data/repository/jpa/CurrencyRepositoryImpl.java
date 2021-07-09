package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.entities.Currency;
import com.github.gadzooks.data.entities.ids.CurrencyId;
import com.github.gadzooks.data.repository.CurrencyCrudRepository;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Slf4j
public class CurrencyRepositoryImpl implements CurrencyCrudRepository {
    @Override
    public Currency save(Currency entity) {
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

    @Override
    public Currency findById(CurrencyId currencyId) {
        return null;
    }

    @Override
    public Currency update(Currency entity, CurrencyId currencyId) {
        return null;
    }

    @Override
    public void delete(CurrencyId currencyId) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            Currency currency = em.find(Currency.class, currencyId);
            em.remove(currency);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }
}
