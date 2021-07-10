package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.entities.Currency;
import com.github.gadzooks.data.entities.Market;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MarketRepositoryTest {

    private final MarketRepository marketRepository = new MarketRepository();

    @Test
    void delete() {

        Currency indianRupee = Currency.builder().countryName("India").name("Rupee").symbol("Rs").build();
        Market market = Market.builder().marketName("new market").currency(indianRupee).build();

        marketRepository.save(market);
        Assertions.assertNotNull(market.getId());
        marketRepository.delete(market.getId());
    }
}