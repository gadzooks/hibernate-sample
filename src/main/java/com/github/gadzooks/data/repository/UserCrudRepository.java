package com.github.gadzooks.data.repository;

import com.github.gadzooks.data.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserCrudRepository implements CrudRepository<User, Long>{
    private final SessionFactory sessionFactory;

    public UserCrudRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        return entity;
    }
}
