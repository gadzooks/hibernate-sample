package com.github.gadzooks.data.entities;

import com.github.gadzooks.data.repository.hibernate.BankCrudRepositoryImpl;
import org.hibernate.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BankTest {

    private static BankCrudRepositoryImpl hibernateBankRepo;

    @BeforeAll
    static void setup() {
        hibernateBankRepo = new BankCrudRepositoryImpl();
    }

    @Test
    public void testL2Caching() {
        Long bankId = 1L;
        Cache cache = hibernateBankRepo.getSessionFactory().getCache();

        //Before fetching entity
        Assertions.assertFalse(cache.containsEntity(Bank.class, bankId));

        //fetched
        Bank bank = hibernateBankRepo.findById(bankId);
        Assertions.assertNotNull(bank);
        Assertions.assertTrue(cache.containsEntity(Bank.class, bank.getBankId()));

        bank = hibernateBankRepo.findById(bankId);
        bank = hibernateBankRepo.findById(1L);

    }

}