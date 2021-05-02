package org.oyster.card.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Journey {

    private Station from;
    private Station to;

    private JourneyType type;
}
