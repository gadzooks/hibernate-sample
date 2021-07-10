package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.entities.Currency;
import com.github.gadzooks.data.entities.Market;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class MarketRepositoryTest {

    private static MarketRepository marketRepository;

    @BeforeAll
    static void setUp() {
        marketRepository = new MarketRepository();
    }

    @Test
    @Disabled("java.lang.IllegalStateException: org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl@4c07d1fc is closed")
    void delete() {
        Currency indianRupee = Currency.builder().countryName("India").name("Rupee").symbol("Rs").build();
        Market market = Market.builder().marketName("new market").currency(indianRupee).build();

        marketRepository.save(market);
        Assertions.assertNotNull(market.getId());
        marketRepository.delete(market.getId());
    }
}