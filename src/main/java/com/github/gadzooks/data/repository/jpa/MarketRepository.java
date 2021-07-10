package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.entities.Market;
import com.github.gadzooks.data.repository.MarketCrudRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarketRepository extends AbstractJpaCrudRepository<Market, Long> implements MarketCrudRepository {

    @Override
    protected void updateEntity(Market update, Market updateFrom) {

    }

    @Override
    protected String namedQueryForCount() {
        return "Market.count";
    }
}
