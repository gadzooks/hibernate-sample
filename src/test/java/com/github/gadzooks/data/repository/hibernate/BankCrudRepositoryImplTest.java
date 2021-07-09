package com.github.gadzooks.data.repository.hibernate;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.gadzooks.data.service.BankCrudService.createBank;

class BankCrudRepositoryImplTest {

    private static BankCrudRepository hibernateBankRepo;

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
    void findById() {
    }

    @Test
    void update() {
    }

}