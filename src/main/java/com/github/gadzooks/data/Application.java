package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.User;
import org.hibernate.Session;

import java.util.Date;

public class Application {
    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();
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
        session.close();
    }
}
