package org.oyster.card.rule

import org.oyster.card.dto.JourneyType
import org.oyster.card.dto.Zone
import java.math.BigDecimal

interface FareRule {

    fun shouldProcess(type :JourneyType, one :Zone, two :Zone) : Boolean
    fun getFare() : BigDecimal
}