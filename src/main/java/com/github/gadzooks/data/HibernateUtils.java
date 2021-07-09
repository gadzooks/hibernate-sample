package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.entities.Currency;
import com.github.gadzooks.data.entities.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HibernateUtils {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /*
    An entity manager factory is typically created at application initialization time and closed at application end.
    It's creation is an expensive process. For those who are familiar with Hibernate, an entity manager factory is very
    much like a session factory. Actually, an entity manager factory is a wrapper on top of a session factory.
    Calls to the entityManagerFactory are thread safe.
     */
    @Getter
    private static final EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

    private static final String PERSISTENCE_UNIT_NAME = "infinite-skills-pu";
    private static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                log.info("SHUTDOWN HOOK - CLOSE EntityManagerFactory");
                emf.close();
            }
        });
        return emf;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Bank.class);
            configuration.addAnnotatedClass(Currency.class);
//            configuration.addAnnotatedClass(CurrencyId.class);
            // register to close on jvm exit

            //Set up the service registry
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().build();

            SessionFactory sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    log.info("SHUTDOWN HOOK - CLOSE SessionFactory");
                    sessionFactory.close();
                }
            });

            return sessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating session factory");
        }
    }
}
