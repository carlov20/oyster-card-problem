package org.oyster.card.rule;

import org.oyster.card.dto.JourneyType;
import org.oyster.card.dto.Zone;

import java.math.BigDecimal;

public interface FareRule {
    boolean shouldProcess(JourneyType type, Zone one, Zone two);
    BigDecimal getFare();
}
