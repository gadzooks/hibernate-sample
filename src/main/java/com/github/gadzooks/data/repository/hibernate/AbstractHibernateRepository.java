package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.HibernateUtils;
import com.github.gadzooks.data.repository.CrudRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Slf4j
@Getter
public abstract class AbstractHibernateRepository<TD, ID extends Serializable> implements CrudRepository<TD, ID> {
    private final SessionFactory sessionFactory;
    private final Class<TD> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractHibernateRepository() {
        this.persistentClass = (Class<TD>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Override
    public TD save(TD entity) {
        Transaction tx = null;
        //Session extends AutoCloseable
        try (Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();
            session.save(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            log.info(e.getMessage());
        }

        return entity;
    }

    @Override
    public TD findById(ID id) {
        //Session extends AutoCloseable
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.get(this.getPersistentClass(), id);
        }
    }

    protected abstract void updateEntity(TD update, TD updateFrom);

    @Override
    public TD update(TD updatedEntity, ID id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.getTransaction();
            tx.begin();
            TD entity = session.get(this.getPersistentClass(), id);

            // do this for all the attributes
            updateEntity(entity, updatedEntity);

            //save entity
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(ID id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            TD entity = session.get(this.getPersistentClass(), id);
            session.delete(entity);
            session.getTransaction().commit();
        }
    }
}
