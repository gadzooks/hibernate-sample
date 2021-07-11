package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.gadzooks.data.service.BankCrudService.createBank;

class BankCrudRepositoryImplTest {

    private static BankCrudRepositoryImpl hibernateBankRepo;

    @BeforeAll
    static void setup() {
        hibernateBankRepo = new BankCrudRepositoryImpl();
    }

    @Test
    void saveAndDelete() {
        Bank transientState = createBank();
        Assertions.assertNull(transientState.getBankId());
        Bank savedState = hibernateBankRepo.save(transientState);
        Assertions.assertNotNull(savedState.getBankId());
        hibernateBankRepo.delete(savedState.getBankId());
    }

    @Test
    void size() {
        long count = hibernateBankRepo.size();
        Assertions.assertTrue(count > 0);
    }

    @Test
    void update() {
    }

    @Test
    void criteriaQueryExample() {
        List<Bank> banks = hibernateBankRepo.criteriaQueryExample("Third", "WA");
        for (Bank bank : banks) {
            System.out.println(bank);
        }
    }
}