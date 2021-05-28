package org.oyster.card

import org.oyster.card.dto.Customer
import org.oyster.card.dto.JourneyType
import org.oyster.card.dto.Station
import org.oyster.card.dto.Zone
import org.oyster.card.service.CustomerJourneyImpl
import org.oyster.card.service.FareCalculatorImpl
import java.math.BigDecimal

fun main() {
    val holbornStation = Station("Holborn", setOf(Zone.ONE))
    val earlsCourtStation = Station("Earl's Court", setOf(Zone.ONE, Zone.TWO))
    val wimbledonStation = Station("Wimbledon", setOf(Zone.THREE))
    val hammersmithStation = Station("Hammersmith", setOf(Zone.THREE))
    val chelseaStation = Station("Chelsea", setOf())

    val customer = Customer(balance = BigDecimal.valueOf(30.00))

    val customerJourney = CustomerJourneyImpl(FareCalculatorImpl())

    customerJourney.inwardTap(customer,holbornStation,JourneyType.TUBE)

    customerJourney.outwardTap(customer, earlsCourtStation)

    customerJourney.inwardTap(customer, earlsCourtStation, JourneyType.BUS)

    customerJourney.outwardTap(customer, chelseaStation)

    customerJourney.inwardTap(customer, earlsCourtStation, JourneyType.TUBE)

    customerJourney.outwardTap(customer, hammersmithStation)

    println("Customer balance is: £${customer.balance}")
}