package com.github.gadzooks.data.entities;

import com.github.gadzooks.data.entities.ids.CurrencyId;
import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@Access(value = AccessType.FIELD)
@IdClass(value = CurrencyId.class)
@Entity
@Table(name = "CURRENCY")
public class Currency {

    //NOTE : NAME, COUNTRY_NAME Form the compound primary key
    @Id
    @Column(name = "NAME")
    private String name;

    @Id
    @Column(name = "COUNTRY_NAME")
    private String countryName;

    @Column(name = "SYMBOL")
    private String symbol;
}
