package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.entities.User;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtils {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static final String PERSISTENCE_UNIT_NAME = "infinite-skills-pu";

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Bank.class);
            return configuration.
                    buildSessionFactory(new StandardServiceRegistryBuilder()
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating session factory");
        }
    }
}
