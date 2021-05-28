package org.oyster.card.rule

import org.oyster.card.dto.JourneyType
import org.oyster.card.dto.Zone
import java.math.BigDecimal

class BusFareRule : FareRule{

    override fun shouldProcess(type: JourneyType, one: Zone, two: Zone): Boolean {
        return JourneyType.BUS == type

    }

    override fun getFare(): BigDecimal {
        return BigDecimal.valueOf(1.80)
    }
}