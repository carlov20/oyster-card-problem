package org.oyster.card.rule;

import org.oyster.card.dto.JourneyType;
import org.oyster.card.dto.Zone;

import java.math.BigDecimal;

public class TwoZonesIncludingOneFareRule implements FareRule {
    @Override
    public boolean shouldProcess(JourneyType type, Zone one, Zone two) {
        return JourneyType.TUBE.equals(type) && (Zone.ONE.equals(one) || Zone.ONE.equals(two)) && !one.equals(two);
    }

    @Override
    public BigDecimal getFare() {
        return BigDecimal.valueOf(3.0);
    }
}
