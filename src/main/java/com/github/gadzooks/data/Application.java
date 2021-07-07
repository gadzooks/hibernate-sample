package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.entities.User;
import com.github.gadzooks.data.service.BankCrudService;
import com.github.gadzooks.data.service.UserCrudService;
import org.hibernate.SessionFactory;

import static com.github.gadzooks.data.service.BankCrudService.createBank;
import static com.github.gadzooks.data.service.UserCrudService.createUser;

public class Application {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

        BankCrudService bankService = new BankCrudService(sessionFactory);
        Bank bank = createBank();
        Bank savedBank = bankService.save(bank);
        System.out.println("saved bank is : " + savedBank);

        UserCrudService userService = new UserCrudService(sessionFactory);
        User user = createUser();
        User savedUser = userService.save(user);
        System.out.println("saved user is : " + savedUser);

//        User dbUser = session.get(User.class, 1L);
//        System.out.println(dbUser);
    }



}
