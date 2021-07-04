package com.github.gadzooks.data.entities;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@Setter(value = AccessLevel.PACKAGE)
@Setter
@Entity
@Table(name = "FINANCES_USER")
@Access(value = AccessType.FIELD)
@ToString
//NOTE : using lombok builder with JPA entity
//https://stackoverflow.com/questions/34241718/lombok-builder-and-jpa-default-constructor/35602246#35602246
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Formula("CONCAT(first_name, ' ', last_name)")
    private String fullName;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "BIRTH_DATE", nullable = false)
    private Date birthDate;

    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
}
