package com.github.gadzooks.data.entities;

import lombok.*;

import javax.persistence.*;

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

    private AuditFields auditFields;

    public enum AddressType {
        PRIMARY
    }

    @Column(name = "ADDRESS_TYPE")
    @Enumerated(value = EnumType.STRING)
    private AddressType addressType;

}
