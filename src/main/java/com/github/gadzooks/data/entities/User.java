package com.github.gadzooks.data.entities;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@ToString
@Access(value = AccessType.FIELD)
@Entity
@Table(name = "FINANCES_USER")
//NOTE : using lombok builder with JPA entity
//https://stackoverflow.com/questions/34241718/lombok-builder-and-jpa-default-constructor/35602246#35602246
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

//    @NotNull
//    @Size(min = 2, max = 256)
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

//    @NotNull
//    @Size(min = 2, max = 256)
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Formula("CONCAT(first_name, ' ', last_name)")
    private String fullName;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "BIRTH_DATE", nullable = false)
    private Date birthDate;

//    @NotNull
    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;

    @Embedded
    private AuditFields auditFields;

    // Example of Embedded address
//    @AttributeOverrides({
//            @AttributeOverride(name="addressLine1", column=@Column(name = "USER_ADDRESS_LINE_1")),
//            @AttributeOverride(name="addressLine2", column=@Column(name = "USER_ADDRESS_LINE_2")),
//            @AttributeOverride(name="zip", column=@Column(name = "ZIP_CODE")),
//    })
//    @Embedded
//    private Address address;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "USER_ADDRESS", joinColumns = @JoinColumn(name = "USER_ID"))
    @AttributeOverride(name="addressLine1", column=@Column(name = "USER_ADDRESS_LINE_1"))
    @AttributeOverride(name="addressLine2", column=@Column(name = "USER_ADDRESS_LINE_2"))
    @AttributeOverride(name="zip", column=@Column(name = "ZIP_CODE"))
    private List<Address> addresses;

}
