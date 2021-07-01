package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.User;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtils {

    @Getter
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            return configuration.
                    buildSessionFactory(new StandardServiceRegistryBuilder()
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating session factory");
        }
    }
}
