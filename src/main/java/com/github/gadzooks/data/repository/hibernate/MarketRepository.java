package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.entities.Market;
import com.github.gadzooks.data.repository.MarketCrudRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarketRepository extends AbstractHibernateRepository<Market, Long> implements MarketCrudRepository {

    @Override
    protected void updateEntity(Market update, Market updateFrom) {

    }
}
