package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
public class BankCrudRepositoryImpl extends AbstractHibernateRepository<Bank, Long> implements BankCrudRepository {

    @Override
    protected void updateEntity(Bank update, Bank updateFrom) {
        if (update.getIsInternational() != updateFrom.getIsInternational())
            update.setIsInternational(updateFrom.getIsInternational());
        if (!update.getName().equals(updateFrom.getName()))
            update.setName(updateFrom.getName());
    }

    @Override
    protected String namedQueryForCount() {
        return "Bank.count";
    }

    public List<Bank> criteriaQueryExample(final String startsWith, final String state) {

        Transaction tx;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            //setup criteria builder, criteria query and root
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Bank> cr = cb.createQuery(Bank.class);
            Root<Bank> root = cr.from(Bank.class);
            cr.multiselect(root.get("name"), root.get("address").get("state"));

            //select criteria
            Predicate nameStartsWith = cb.like(root.get("name"), startsWith + "%");
            Predicate stateEquals = cb.equal(root.get("address").get("state"), state);

            //setup query
            cr.select(root).where(cb.or(nameStartsWith, stateEquals));
            Query<Bank> query = session.createQuery(cr);

            //execute query
            List<Bank> results = query.getResultList();
            tx.commit();

            return results;
        }
    }

    //all banks that start with Third and are in PA or WI state
}
