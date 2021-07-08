package com.github.gadzooks.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "BANK")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_ID")
    private Long bankId;

//    @NotNull
//    @Size(min = 2, max = 256)
    @Column(name = "NAME")
    private String name;

    //example of embedded type
    @Embedded
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
    // Example for using List or Set
//    @ElementCollection
//    @CollectionTable(name = "BANK_CONTACT", joinColumns = @JoinColumn(name = "BANK_ID"))
//    @Column(name = "NAME")
//    private List<String> contacts;

    // table BANK_CONTACT has FK BANK_ID, and VARCHAR NAME to store the contact name
    // Example for using Map
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "BANK_CONTACT", joinColumns = @JoinColumn(name = "BANK_ID"))
    @Column(name = "NAME")
    @MapKeyColumn(name = "POSITION_TYPE")
    private Map<String, String> contacts;


}
