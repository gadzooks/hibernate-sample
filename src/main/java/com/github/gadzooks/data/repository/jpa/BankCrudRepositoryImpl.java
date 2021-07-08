package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import javax.persistence.EntityManager;

@Slf4j
public class BankCrudRepositoryImpl implements BankCrudRepository {
    public Bank saveUsingSession(Bank entity) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();

        //NOTE : how to get session from EntityManager
        try(Session session = em.unwrap(Session.class)) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public Bank save(Bank entity) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return entity;
    }

    @Override
    public Bank findById(Long id) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            Bank entity = em.find(Bank.class, id);
            em.getTransaction().commit();
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    public Bank update(Bank entity, Long id) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            Bank bank = em.find(Bank.class, id);

            // do this for all the attributes
            if(bank.getIsInternational() != entity.getIsInternational())
                bank.setIsInternational(entity.getIsInternational());
            if(!bank.getName().equals(entity.getName()))
                bank.setName(entity.getName());

            em.persist(bank);
            em.getTransaction().commit();
            return bank;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = HibernateUtils.getEntityManagerFactory().createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            Bank bank = em.find(Bank.class,id);
            em.remove(bank);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
