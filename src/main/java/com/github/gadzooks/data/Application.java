package com.github.gadzooks.data;

import com.github.gadzooks.data.entities.Address;
import com.github.gadzooks.data.entities.AuditFields;
import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.entities.User;
import org.hibernate.Session;

import java.util.Date;

public class Application {
    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();
//        createUser(session);
        createBank(session);

        session.beginTransaction();
        User dbUser = session.get(User.class, 1L);
        System.out.println(dbUser);
        session.getTransaction().commit();

        session.close();
    }

    private static void createBank(Session session) {
        session.beginTransaction();
        Bank bank = Bank.builder().name("my bank").
                address(Address.builder().
                        addressLine1("line1").
                        addressLine2("line2").
                        city("seattle").state("WA").zip("98029").
                        build()).
                auditFields(AuditFields.builder().
                        createdBy("system").
                        createdDate(new Date()).
                        lastUpdatedBy("system").
                        lastUpdatedDate(new Date()).
                        build()).
                isInternational(true).
                addressType(Bank.AddressType.PRIMARY).
                build();

        session.save(bank);
        session.getTransaction().commit();
    }

    private static void createUser(Session session) {
        session.beginTransaction();
        User user = User.builder().
                firstName("amit").
                lastName("karwande").
                emailAddress("foo@bar.com").
                birthDate(new Date()).
                auditFields(
                        AuditFields.builder().
                                createdBy("someone").
                                createdDate(new Date()).
                                lastUpdatedBy("amit").
                                lastUpdatedDate(new Date()).
                                build()

                ).
                address(Address.builder().
                        addressLine1("line1").
                        addressLine2("line2").
                        build()).
                build();
        session.save(user);
        session.getTransaction().commit();
    }
}
