package org.oyster.card;

import lombok.extern.slf4j.Slf4j;
import org.oyster.card.dto.Customer;
import org.oyster.card.dto.JourneyType;
import org.oyster.card.dto.Station;
import org.oyster.card.dto.Zone;
import org.oyster.card.service.CustomerJourney;
import org.oyster.card.service.CustomerJourneyImpl;
import org.oyster.card.service.FareCalculatorImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

@Slf4j
public class MainApplication {


    public static void main(String[] args) {
        Station holbornStation = Station.builder()
                .name("Holborn")
                .zone(Zone.ONE)
                .build();

        Station earlsCourtStation = Station.builder()
                .name("Earl's Court")
                .zones(new HashSet<>(Arrays.asList(Zone.ONE, Zone.TWO)))
                .build();

        Station wimbledonStation = Station.builder()
                .name("Wimbledon")
                .zone(Zone.THREE)
                .build();

        Station hammersmithStation = Station.builder()
                .name("Hammersmith")
                .zone(Zone.THREE)
                .build();

        Station chelseaStation = Station.builder()
                .name("Chelsea")
                .build();

        Customer customer = Customer.builder()
                .balance(BigDecimal.valueOf(30.00))
                .build();

        CustomerJourney customerJourney = new CustomerJourneyImpl(new FareCalculatorImpl());

        customerJourney.inwardTap(customer, holbornStation, JourneyType.TUBE);

        customerJourney.outwardTap(customer, earlsCourtStation);

        customerJourney.inwardTap(customer, earlsCourtStation, JourneyType.BUS);

        customerJourney.outwardTap(customer, chelseaStation);

        customerJourney.inwardTap(customer, earlsCourtStation, JourneyType.TUBE);

        customerJourney.outwardTap(customer, hammersmithStation);

        log.info("Customer balance is: £{}", customer.getBalance());
    }
}
