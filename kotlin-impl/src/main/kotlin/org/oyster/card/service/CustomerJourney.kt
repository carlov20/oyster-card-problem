package org.oyster.card.service

import org.oyster.card.dto.Customer
import org.oyster.card.dto.JourneyType
import org.oyster.card.dto.Station

interface CustomerJourney {

    fun inwardTap(customer : Customer, station: Station, journeyType: JourneyType)

    fun outwardTap(customer: Customer, station: Station)
}
