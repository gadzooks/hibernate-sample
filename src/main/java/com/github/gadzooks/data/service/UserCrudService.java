package com.github.gadzooks.data.service;

import com.github.gadzooks.data.entities.Address;
import com.github.gadzooks.data.entities.AuditFields;
import com.github.gadzooks.data.entities.User;
import com.github.gadzooks.data.repository.UserCrudRepository;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

public class UserCrudService implements CrudService<User, Long> {
    private final UserCrudRepository userCrudRepository;

    public UserCrudService(SessionFactory sessionFactory) {
        this.userCrudRepository = new UserCrudRepository(sessionFactory);
    }

    @Override
    public User save(User entity) {
        return userCrudRepository.save(entity);
    }

    @Override
    public User findById(Long id) {
        return userCrudRepository.findById(id);
    }

    @Override
    public User update(User entity, Long aLong) {
        return null;
    }

    public static User createUser() {
        List<Address> addresses = List.of(
                Address.builder().
                        addressLine1("10041 42nd ave sw").
                        addressLine2("--").
                        city("seattle").state("WA").zip("98146").
                        build(),
                Address.builder().
                        addressLine1("2589 ne jewell ln").
                        addressLine2("--").
                        city("issaquah").state("WA").zip("98029").
                        build()
        );

        return User.builder().
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
                addresses(addresses).
                build();
    }

}
