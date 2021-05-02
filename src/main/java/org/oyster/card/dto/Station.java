package org.oyster.card.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Set;

@Data
@Builder
public class Station {

    private String name;
    @Singular
    private Set<Zone> zones;
}
