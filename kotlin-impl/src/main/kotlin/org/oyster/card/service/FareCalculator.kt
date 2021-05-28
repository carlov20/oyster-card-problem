package org.oyster.card.service

import org.oyster.card.dto.Journey
import java.math.BigDecimal

interface FareCalculator {
    fun calculateJourney(journey: Journey): BigDecimal
}