package com.github.gadzooks.data.repository;

import com.github.gadzooks.data.entities.Bank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BankCrudRepository implements CrudRepository<Bank, Long>{
    private final SessionFactory sessionFactory;

    public BankCrudRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Bank save(Bank entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return entity;
    }
}