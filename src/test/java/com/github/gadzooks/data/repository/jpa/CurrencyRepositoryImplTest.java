package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.entities.Currency;
import com.github.gadzooks.data.entities.ids.CurrencyId;
import com.github.gadzooks.data.repository.CurrencyCrudRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CurrencyRepositoryImplTest {
    private static CurrencyCrudRepository currencyRepository;

    @BeforeAll
    static void setUp() {
        currencyRepository = new CurrencyRepositoryImpl();
    }

    @Test
    void delete() {
        Currency currency = Currency.builder().
                countryName("India").name("Rupee").symbol("Rs").build();

        currencyRepository.save(currency);
        currencyRepository.delete(new CurrencyId(currency.getName(), currency.getCountryName()));
    }
}