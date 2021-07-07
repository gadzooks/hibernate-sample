package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.service.BankCrudService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static com.github.gadzooks.data.service.BankCrudService.createBank;

@Slf4j
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("infinite-skills-pu");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Bank bank = createBank();
        em.persist(bank);
        log.info("saved bank is : " + bank);
        tx.commit();
        em.close();
        emf.close();
    }

    public static void main_with_hibernate(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

        BankCrudService bankService = new BankCrudService(sessionFactory);
        Bank bank = createBank();
        Bank savedBank = bankService.save(bank);
        log.info("saved bank is : " + savedBank);

        Bank retrievedBank = bankService.findById(savedBank.getBankId());
        log.info("retrieved bank for id " + retrievedBank);

        retrievedBank.setName("new name");
        bankService.update(retrievedBank, retrievedBank.getBankId());

        log.info("updated name is : " + bankService.findById(retrievedBank.getBankId()).getName());
        bankService.delete(retrievedBank.getBankId());

//        UserCrudService userService = new UserCrudService(sessionFactory);
//        User user = createUser();
//        User savedUser = userService.save(user);
//        log.info("saved user is : " + savedUser);
//        userService.delete(savedUser.getUserId());

//        User dbUser = session.get(User.class, 1L);
//        System.out.println(dbUser);
    }



}
