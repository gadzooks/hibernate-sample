package com.github.gadzooks.data.service;

import com.github.gadzooks.data.entities.Address;
import com.github.gadzooks.data.entities.AuditFields;
import com.github.gadzooks.data.entities.Bank;
import com.github.gadzooks.data.repository.BankCrudRepository;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.Map;

public class BankCrudService implements CrudService<Bank, Long> {
    private final BankCrudRepository bankCrudRepository;

    public BankCrudService(SessionFactory sessionFactory) {
        this.bankCrudRepository = new BankCrudRepository(sessionFactory);
    }

    @Override
    public Bank save(Bank entity) {
        return bankCrudRepository.save(entity);
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