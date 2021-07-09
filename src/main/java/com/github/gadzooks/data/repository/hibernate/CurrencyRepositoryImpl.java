package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.entities.Currency;
import com.github.gadzooks.data.entities.ids.CurrencyId;
import com.github.gadzooks.data.repository.CurrencyCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Slf4j
public class CurrencyRepositoryImpl implements CurrencyCrudRepository {
    private final SessionFactory sessionFactory;

    public CurrencyRepositoryImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public Currency save(Currency currency) {
        Transaction tx = null;
        //Session extends AutoCloseable
        try (Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();
            session.save(currency);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            if (tx != null) tx.rollback();
        }
        return currency;
    }

    @Override
    public Currency update(Currency entity, CurrencyId id) {
        return null;
    }

    @Override
    public void delete(CurrencyId id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Currency currency = session.get(Currency.class, id);
            log.info(String.valueOf(session.contains("Currency", currency)));
            log.info("deleting currency : " + currency);
            session.delete(currency);
            log.info(String.valueOf(session.contains("Currency", currency)));
            session.getTransaction().commit();
        }

    }

    @Override
    public Currency findById(CurrencyId id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            return session.get(Currency.class, id);
        }
    }
}
