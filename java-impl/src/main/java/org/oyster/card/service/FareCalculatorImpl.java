package org.oyster.card.service;

import org.oyster.card.dto.Journey;
import org.oyster.card.dto.Zone;
import org.oyster.card.rule.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class FareCalculatorImpl implements FareCalculator {

    private BigDecimal maximumFare;

    private List<FareRule> fareRules;

    public FareCalculatorImpl() {
        fareRules = Arrays.asList(new SameZoneInsideZoneOneFareRule(),
                new SameZoneOutsideZoneOneFareRule(),
                new BusFareRule(),
                new TwoZonesExcludingOneFareRule(),
                new TwoZonesIncludingOneFareRule());
        this.maximumFare = BigDecimal.valueOf(3.20);
    }

    @Override
    public BigDecimal calculateJourney(Journey journey) {
        BigDecimal fare = maximumFare;

        if(journey.getTo() != null) {

            for(FareRule rule : fareRules) {
                for(Zone fromZone : journey.getFrom().getZones()) {
                    for(Zone toZone : journey.getTo().getZones()) {
                        if(rule.shouldProcess(journey.getType(), fromZone, toZone)) {
                            BigDecimal ruleFare = rule.getFare();
                            fare = fare.min(ruleFare);
                        }
                    }
                }

            }
        }
        return fare;
    }
}
