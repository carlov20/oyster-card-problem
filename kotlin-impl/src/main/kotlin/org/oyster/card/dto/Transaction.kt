package org.oyster.card.dto

import java.math.BigDecimal

data class Transaction (val fare :BigDecimal, val journey: Journey) {
}