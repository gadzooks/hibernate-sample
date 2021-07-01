package com.github.gadzooks.data;

import org.hibernate.Session;

public class Application {
    public static void main(String[] args) {
        Session session = HibernateUtils.getSESSION_FACTORY().openSession();
        session.beginTransaction();
        session.close();
    }
}
