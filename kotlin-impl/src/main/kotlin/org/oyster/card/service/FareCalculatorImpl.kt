package org.oyster.card.service

import org.oyster.card.dto.Journey
import org.oyster.card.rule.*
import java.math.BigDecimal

class FareCalculatorImpl : FareCalculator {

    private val maximumFare :BigDecimal
    private val fareRules : List<FareRule>

    init {
        fareRules = listOf(BusFareRule(), SameZoneInsideZoneOneFareRule(), SameZoneOutsideZoneOneFareRule(), TwoZonesIncludingOneFareRule(), TwoZonesExcludingOneFareRule())
        maximumFare = BigDecimal.valueOf(3.20)
    }


    override fun calculateJourney(journey: Journey): BigDecimal {
        var fare : BigDecimal = maximumFare

        if(journey.to != null) {
            for(toZone in journey.to.zones) {
                for(fromZone in journey.from.zones) {
                    for (rule in fareRules) {
                        if(rule.shouldProcess(journey.type, fromZone, toZone)) {
                            val ruleFare = rule.getFare()
                            fare = fare.min(ruleFare)
                        }
                    }
                }
            }
        }


        return fare
    }

}
