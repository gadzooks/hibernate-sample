package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.entities.Market;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Slf4j
public class MarketRepository {
    private final SessionFactory sessionFactory;

    public MarketRepository() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    public Market save(Market entity) {
        Transaction tx = null;
        //Session extends AutoCloseable
        try (Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();
            session.save(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            log.info(e.getMessage());
        }

        return entity;
    }

    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Market market = session.get(Market.class, id);
            log.info(String.valueOf(session.contains("Market", market)));
            log.info("deleting bank : " + market.getMarketName());
            session.delete(market);
            log.info(String.valueOf(session.contains("Market", market)));
            session.getTransaction().commit();
        }
    }

}
