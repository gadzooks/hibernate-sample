package com.github.gadzooks.data.service;

import com.github.gadzooks.data.entities.Address;
import com.github.gadzooks.data.entities.AuditFields;
import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;

import java.util.Date;
import java.util.Map;

public class BankCrudService implements CrudService<Bank, Long> {
    private final BankCrudRepository bankCrudRepository;

    public BankCrudService(BankCrudRepository repository) {
        this.bankCrudRepository = repository;
    }

    @Override
    public Bank save(Bank entity) {
        return bankCrudRepository.save(entity);
    }

    @Override
    public Bank findById(Long aLong) {
        return bankCrudRepository.findById(aLong);
    }

    @Override
    public Bank update(Bank entity, Long aLong) {
        return bankCrudRepository.update(entity, aLong);
    }

    @Override
    public void delete(Long aLong) {
        bankCrudRepository.delete(aLong);
    }

    public static Bank createBank() {
        return Bank.builder().name("my bank").
                address(Address.builder().
                        addressLine1("line1").
                        addressLine2("line2").
                        city("seattle").state("WA").zip("98029").
                        build()).
                auditFields(AuditFields.builder().
                        createdBy("system").
                        createdDate(new Date()).
                        lastUpdatedBy("system").
                        lastUpdatedDate(new Date()).
                        build()).
                isInternational(true).
                addressType(Bank.AddressType.PRIMARY).
                contacts(Map.of("MANAGER", "Kim", "TELLER", "Amit")).
                build();

    }

}
