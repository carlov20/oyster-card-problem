package org.oyster.card.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oyster.card.dto.*;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class CustomerJourneyImpl implements CustomerJourney {

    private final FareCalculator fareCalculator;

    @Override
    public void inwardTap(Customer customer, Station station, JourneyType journeyType) {
        log.info("Customer inward tap at station {} for journey type {}",station.getName(), journeyType.name());
        Journey journey = Journey.builder()
                .from(station)
                .type(journeyType)
                .build();
        BigDecimal fare = fareCalculator.calculateJourney(journey);
        customer.addLastTransaction(Transaction.builder().fare(fare).journey(journey).build());
        customer.chargeFare(fare);
    }

    @Override
    public void outwardTap(Customer customer, Station station) {
        log.info("Customer outward tap at station {}",station.getName());
        Transaction lastTransaction = customer.popLastTransaction();
        Journey completedJourney =
                Journey.builder()
                        .from(lastTransaction.getJourney().getFrom())
                        .type(lastTransaction.getJourney().getType())
                        .to(station)
                        .build();

        customer.chargeBackFare(lastTransaction.getFare());
        BigDecimal fare = fareCalculator.calculateJourney(completedJourney);
        customer.addLastTransaction(Transaction.builder().journey(completedJourney).fare(fare).build());
        customer.chargeFare(fare);
    }
}
