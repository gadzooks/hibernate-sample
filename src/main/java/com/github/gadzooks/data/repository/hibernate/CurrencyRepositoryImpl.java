package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.entities.Currency;
import com.github.gadzooks.data.entities.ids.CurrencyId;
import com.github.gadzooks.data.repository.CurrencyCrudRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrencyRepositoryImpl extends AbstractHibernateRepository<Currency, CurrencyId> implements CurrencyCrudRepository {

    @Override
    protected void updateEntity(Currency update, Currency updateFrom) {

    }
}
