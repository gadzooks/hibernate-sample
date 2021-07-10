package com.github.gadzooks.data.repository.jpa;

import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankCrudRepositoryImpl extends AbstractJpaCrudRepository<Bank, Long> implements BankCrudRepository {

    @Override
    protected void updateEntity(Bank update, Bank updateFrom) {

    }
}
