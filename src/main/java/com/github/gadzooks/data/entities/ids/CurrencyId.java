package com.github.gadzooks.data.entities.ids;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
//Note @Embeddable is required to allow this class to represent a Composite key
@Embeddable
public class CurrencyId implements Serializable {
    private String name;
    private String countryName;
}
