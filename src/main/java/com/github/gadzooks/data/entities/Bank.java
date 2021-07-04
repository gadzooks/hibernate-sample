package com.github.gadzooks.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "BANK")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_ID")
    private Long bankId;

    @Column(name = "NAME")
    private String name;

    @AttributeOverride(name = "zip", column = @Column(name = "ZIP_CODE"))
    private Address address;

    @Column(name = "IS_INTERNATIONAL")
    private Boolean isInternational;

    @Embedded
    private AuditFields auditFields;

    public enum AddressType {
        PRIMARY
    }

    @Column(name = "ADDRESS_TYPE")
    @Enumerated(value = EnumType.STRING)
    private AddressType addressType;

    // table BANK_CONTACT has FK BANK_ID, and VARCHAR NAME to store the contact name
    // this works for List and Set. It will NOT work for Map type
    @ElementCollection
    @CollectionTable(name = "BANK_CONTACT", joinColumns = @JoinColumn(name = "BANK_ID"))
    @Column(name = "NAME")
    private List<String> contacts;

}
