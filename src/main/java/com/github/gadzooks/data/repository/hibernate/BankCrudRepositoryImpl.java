package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Slf4j
public class BankCrudRepositoryImpl implements BankCrudRepository {
    private final SessionFactory sessionFactory;

    public BankCrudRepositoryImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public Bank save(Bank entity) {
        Transaction tx = null;
        //Session extends AutoCloseable
        try (Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();
            session.save(entity);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            log.info(e.getMessage());
        }

        return entity;
    }

    @Override
    public Bank findById(Long id) {
        //Session extends AutoCloseable
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.get(Bank.class, id);
        }
    }

    @Override
    public Bank update(Bank entity, Long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();
            Bank bank = session.get(Bank.class, id);

            // do this for all the attributes
            if(bank.getIsInternational() != entity.getIsInternational())
                bank.setIsInternational(entity.getIsInternational());
            if(!bank.getName().equals(entity.getName()))
                bank.setName(entity.getName());

            //save bank
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Bank bank = session.get(Bank.class, id);
            log.info(String.valueOf(session.contains("Bank",bank )));
            log.info("deleting bank : " + bank.getName());
            session.delete(bank);
            log.info(String.valueOf(session.contains("Bank",bank )));
            session.getTransaction().commit();
        }
    }
}
