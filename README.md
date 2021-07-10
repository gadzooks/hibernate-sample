# Hibernat and JPA code samples

## Setup

### Hibernate (Session and SessionFactory)

```java
private static SessionFactory buildSessionFactory() {
    try {
        Configuration configuration = new Configuration();
        //add all @Entity classes that we want to track
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Bank.class);

        //Set up the service registry
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().build();

        SessionFactory sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);

        // register to close on jvm exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("SHUTDOWN HOOK - CLOSE SessionFactory");
            sessionFactory.close();
        }));

        return sessionFactory;
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error creating session factory");
    }
}
```

### JPA (EntityManager and EntityManagerFactory)

```java
private static EntityManagerFactory createEntityManagerFactory() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        log.info("SHUTDOWN HOOK - CLOSE EntityManagerFactory");
        emf.close();
    }));
    return emf;
}
```

```xml
<persistence xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
                      http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
             version="2.0" xmlns="http://java.sun.com/xml/ns/persistence">

    <persistence-unit name="test" transaction-type="RESOURCE_LOCAL">
<!--        <class>com.test.jpa.Student</class>-->
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver" />
            <property name="javax.persistence.jdbc.url"    value="jdbc:h2:mem:test" />
            <property name="javax.persistence.jdbc.user"   value="sa" />
            <property name="javax.persistence.jdbc.password" value="" />

            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
            <property name="hibernate.hbm2ddl.auto" value="create-drop" />
            <property name="show_sql" value="true"/>
            <property name="hibernate.temp.use_jdbc_metadata_defaults" value="false"/>
        </properties>
    </persistence-unit>
</persistence>
```

## CRUD Repository implementations :

### Using Hibernate APIs

```java
public class BankCrudRepositoryImpl implements BankCrudRepository {
    private final SessionFactory sessionFactory;

    public BankCrudRepositoryImpl() {
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public Bank save(Bank entity) {
        Transaction tx = null;
        //Session extends AutoCloseable
        try (Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();
            session.save(entity);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            log.info(e.getMessage());
        }

        return entity;
    }
    
    //other CRUD methods
}
```

### Using JPA APIs

```java
public class BankCrudRepositoryImpl implements BankCrudRepository {
    private final EntityManagerFactory emf;

    public BankCrudRepositoryImpl() {
        emf = HibernateUtils.getEntityManagerFactory();
    }

    @Override
    public Bank save(Bank entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        //EntityManagerFactory does not extend AutoCloseable so we need to close connections in finally
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            if (tx != null) tx.rollback();
        } finally {
            em.close();
        }

        return entity;
    }
    //other CRUD methods
}
```

## Annotations :

### Embeddable entity

```java
@Embeddable
public class Address {
    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP")
    private String zip;
}
```

### @Temporal

```java
@Temporal(value = TemporalType.TIMESTAMP)
@Column(name = "LAST_UPDATED_DATE")
private Date lastUpdatedDate;
```
