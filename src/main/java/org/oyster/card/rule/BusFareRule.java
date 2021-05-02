package org.oyster.card.rule;

import org.oyster.card.dto.JourneyType;
import org.oyster.card.dto.Zone;

import java.math.BigDecimal;

public class BusFareRule implements FareRule{
    @Override
    public boolean shouldProcess(JourneyType type, Zone one, Zone two) {
        return JourneyType.BUS.equals(type);
    }

    @Override
    public BigDecimal getFare() {
        return BigDecimal.valueOf(1.8);
    }
}
