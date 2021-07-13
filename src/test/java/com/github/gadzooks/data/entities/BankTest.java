package com.github.gadzooks.data.entities;

import com.github.gadzooks.data.repository.hibernate.BankCrudRepositoryImpl;
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
        Bank bank = hibernateBankRepo.findById(1L);
        bank = hibernateBankRepo.findById(1L);
        bank = hibernateBankRepo.findById(1L);
        Assertions.assertNotNull(bank);

//        int size = CacheManager.ALL_CACHE_MANAGERS.get(0)
//                .getCache("com.github.gadzooks.data.entities.Bank").getSize();
//        Assertions.assertTrue(size > 0);
    }

}