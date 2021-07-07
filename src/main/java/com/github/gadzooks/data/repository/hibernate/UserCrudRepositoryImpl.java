package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.entities.User;
import com.github.gadzooks.data.repository.UserCrudRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserCrudRepositoryImpl implements UserCrudRepository {
    private final SessionFactory sessionFactory;

    public UserCrudRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public User findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public User update(User entity, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }
}
