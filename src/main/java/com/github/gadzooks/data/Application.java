package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.entities.User;
import com.github.gadzooks.data.service.BankCrudService;
import com.github.gadzooks.data.service.UserCrudService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import static com.github.gadzooks.data.service.BankCrudService.createBank;
import static com.github.gadzooks.data.service.UserCrudService.createUser;

@Slf4j
public class Application {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

        BankCrudService bankService = new BankCrudService(sessionFactory);
        Bank bank = createBank();
        Bank savedBank = bankService.save(bank);
        log.info("saved bank is : " + savedBank);

        UserCrudService userService = new UserCrudService(sessionFactory);
        User user = createUser();
        User savedUser = userService.save(user);
        log.info("saved user is : " + savedUser);

//        User dbUser = session.get(User.class, 1L);
//        System.out.println(dbUser);
    }



}
