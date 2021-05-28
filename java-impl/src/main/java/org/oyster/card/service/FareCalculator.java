package org.oyster.card.service;

import org.oyster.card.dto.Journey;

import java.math.BigDecimal;

public interface FareCalculator {

    BigDecimal calculateJourney(Journey journey);
}
