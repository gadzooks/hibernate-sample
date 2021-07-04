package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.User;
import org.hibernate.Session;

import java.util.Date;

public class Application {
    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();
//        createUser(session);


        session.beginTransaction();
        User dbUser = session.get(User.class, 1L);
        System.out.println(dbUser);
        session.getTransaction().commit();

        session.close();
    }

    private static void createUser(Session session) {
        session.beginTransaction();
        User user = User.builder().
                firstName("amit").
                lastName("karwande").
                emailAddress("foo@bar.com").
                birthDate(new Date()).
                createdBy("amit").
                createdDate(new Date()).
                lastUpdatedBy("amit").
                lastUpdatedDate(new Date()).
                build();
        session.save(user);
        session.getTransaction().commit();
    }
}
