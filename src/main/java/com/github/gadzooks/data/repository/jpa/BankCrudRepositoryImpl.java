package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.github.gadzooks.data.HibernateUtils.PERSISTENCE_UNIT_NAME;

@Slf4j
public class BankCrudRepositoryImpl implements BankCrudRepository {
    @Override
    public Bank save(Bank entity) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }

        return entity;
    }

    @Override
    public Bank findById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            Bank entity = em.find(Bank.class, id);
            em.getTransaction().commit();
            return entity;
        } finally {
            em.close();
            emf.close();
        }
    }

    @Override
    public Bank update(Bank entity, Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

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
            emf.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            em.getTransaction().begin();
            Bank bank = em.find(Bank.class,id);
            em.remove(bank);
            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
}
