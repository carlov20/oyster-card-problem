package org.oyster.card.rule

import org.oyster.card.dto.JourneyType
import org.oyster.card.dto.Zone
import java.math.BigDecimal

class SameZoneInsideZoneOneFareRule : FareRule {
    override fun shouldProcess(type: JourneyType, one: Zone, two: Zone): Boolean {
        return JourneyType.TUBE == type && Zone.ONE == one && one == two
    }

    override fun getFare(): BigDecimal {
        return BigDecimal.valueOf(2.50)
    }
}