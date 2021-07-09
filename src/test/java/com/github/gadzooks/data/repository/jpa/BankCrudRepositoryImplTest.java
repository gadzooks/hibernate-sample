package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.gadzooks.data.service.BankCrudService.createBank;

class BankCrudRepositoryImplTest {

    private static BankCrudRepository jpaBankRepo;

    @BeforeAll
    static void setUp() {
        jpaBankRepo = new BankCrudRepositoryImpl();
    }

    @Test
    void save() {
        Bank transientState = createBank();
        Assertions.assertNull(transientState.getBankId());
        Bank savedState = jpaBankRepo.save(transientState);
        Assertions.assertNotNull(savedState.getBankId());
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}