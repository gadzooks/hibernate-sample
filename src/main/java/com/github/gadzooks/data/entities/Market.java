package com.github.gadzooks.data.entities;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@ToString
@Access(value = AccessType.FIELD)
@Entity
@Table(name = "MARKET")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_ID")
    private Long id;

    @Column(name = "MARKET_NAME")
    private String marketName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CURRENCY_NAME", referencedColumnName = "NAME")
    @JoinColumn(name = "COUNTRY_NAME", referencedColumnName = "COUNTRY_NAME")
    private Currency currency;

}
