package org.oyster.card.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.oyster.card.dto.Journey;
import org.oyster.card.dto.JourneyType;
import org.oyster.card.dto.Station;
import org.oyster.card.dto.Zone;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

class FareCalculatorTests {

    private FareCalculator fareCalculator;

    @BeforeEach
    void setup() {
        fareCalculator = new FareCalculatorImpl();
    }

    @DisplayName("Any bus journey £1.80")
    @Test
    void busJourneysMustBeOnePoundEighty() {
        Journey busJourney = Journey.builder()
                .from(generateStation())
                .to(generateStation())
                .type(JourneyType.BUS)
                .build();

        BigDecimal expected = BigDecimal.valueOf(1.80);

        BigDecimal result = fareCalculator.calculateJourney(busJourney);

        Assertions.assertEquals(expected,result);
    }

    @DisplayName("Anywhere in Zone 1 £2.50")
    @Test
    void tubeJourneyInZoneOneMustBeTwoPoundsFifty() {
        Journey journey = Journey.builder()
                .from(Station.builder()
                        .name("DefaultStation")
                        .zone(Zone.ONE)
                        .build())
                .to(Station.builder()
                        .name("DefaultStation")
                        .zone(Zone.ONE)
                        .build())
                .type(JourneyType.TUBE)
                .build();

        BigDecimal expected = BigDecimal.valueOf(2.50);

        BigDecimal result = fareCalculator.calculateJourney(journey);

        Assertions.assertEquals(expected, result);
    }

    @DisplayName("Any one zone outside zone 1 £2.00")
    @Test
    void tubeJourneyInOneZoneOutsideOfOneMustBeTwoPounds() {
        Journey journey = Journey.builder()
                .from(Station.builder()
                        .name("DefaultStation")
                        .zone(Zone.TWO)
                        .build())
                .to(Station.builder()
                        .name("DefaultStation")
                        .zone(Zone.TWO)
                        .build())
                .type(JourneyType.TUBE)
                .build();

        BigDecimal expected = BigDecimal.valueOf(2.00);

        BigDecimal result = fareCalculator.calculateJourney(journey);

        Assertions.assertEquals(expected, result);
    }

    @DisplayName("Any two zones including zone 1 £3.00")
    @Test
    void tubeJourneyInTwoZonesIncludingZoneOneMustBeThreePounds() {
        Journey journey = Journey.builder()
                .from(Station.builder()
                        .name("DefaultStation")
                        .zone(Zone.ONE)
                        .build())
                .to(Station.builder()
                        .name("DefaultStation")
                        .zone(Zone.TWO)
                        .build())
                .type(JourneyType.TUBE)
                .build();

        BigDecimal expected = BigDecimal.valueOf(3.00);

        BigDecimal result = fareCalculator.calculateJourney(journey);

        Assertions.assertEquals(expected, result);
    }

    @DisplayName("Any two zones excluding zone 1 £2.25")
    @Test
    void tubeJourneyInTwoZonesExcludingZoneOneMustBeTwoPoundsTwentyFive() {
            Journey journey = Journey.builder()
                    .from(Station.builder()
                            .name("DefaultStation")
                            .zone(Zone.TWO)
                            .build())
                    .to(Station.builder()
                            .name("DefaultStation")
                            .zone(Zone.THREE)
                            .build())
                    .type(JourneyType.TUBE)
                    .build();

            BigDecimal expected = BigDecimal.valueOf(2.25);

            BigDecimal result = fareCalculator.calculateJourney(journey);

            Assertions.assertEquals(expected, result);

    }

    @DisplayName("Any three zones £3.20")
    @Test
    void tubeJourneyWithoutAToStationMustBeThreePoundsTwenty() {
        Journey journey = Journey.builder()
                .from(Station.builder()
                        .name("DefaultStation")
                        .zone(Zone.TWO)
                        .build())
                .type(JourneyType.TUBE)
                .build();

        BigDecimal expected = BigDecimal.valueOf(3.20);

        BigDecimal result = fareCalculator.calculateJourney(journey);

        Assertions.assertEquals(expected, result);
    }

    @DisplayName("The system should favour the customer where more than one fare is possible for a given " +
            "journey.")
    @Test
    void tubeJourneyWithStationsWithMultipleZonesMustFavourLowestFare() {
        Journey journey = Journey.builder()
                .from(Station.builder()
                        .name("DefaultStation")
                        .zone(Zone.ONE)
                        .build())
                .to(Station.builder()
                        .name("DefaultStation")
                        .zones(new HashSet<>(Arrays.asList(Zone.ONE, Zone.TWO)))
                        .build())
                .type(JourneyType.TUBE)
                .build();

        BigDecimal expected = BigDecimal.valueOf(2.50);

        BigDecimal result = fareCalculator.calculateJourney(journey);

        Assertions.assertEquals(expected, result);
    }

    @DisplayName("When the user passes through the inward barrier at the station, their oyster card is charged " +
            "the maximum fare.")
    @Test
    void mustChargeMaximumFareForOnlyInwardUse() {
        Journey journey = Journey.builder()
                .from(Station.builder().build())
                .build();
        BigDecimal expected = BigDecimal.valueOf(3.20);
        BigDecimal result = fareCalculator.calculateJourney(journey);
        Assertions.assertEquals(expected, result);
    }

    private Station generateStation() {
        return Station.builder()
                .name("DefaultStation")
                .zone(Zone.ONE)
                .build();
    }
}
