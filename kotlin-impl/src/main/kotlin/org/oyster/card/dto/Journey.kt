package org.oyster.card.dto

data class Journey(val from: Station, val to:Station? = null, val type: JourneyType)