package org.oyster.card.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.oyster.card.dto.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;

public class CustomerJourneyTests {

    private FareCalculator fareCalculator;
    private CustomerJourney customerJourney;

    @BeforeEach
    public void setup() {
        fareCalculator = Mockito.mock(FareCalculator.class);
        customerJourney = new CustomerJourneyImpl(fareCalculator);
    }


    @Test
    void mustAddTransactionWhenDoingInwardTap() {
        Customer customer = Customer.builder().build();
        Station station = Station.builder().name("First Station").build();
        JourneyType type = JourneyType.TUBE;
        BigDecimal expectedFare = BigDecimal.valueOf(10.0);

        Mockito.when(fareCalculator.calculateJourney(Mockito.any())).thenReturn(expectedFare);

        customerJourney.inwardTap(customer,station,type);

        Assertions.assertFalse(customer.getTransactions().isEmpty());
        Transaction transaction = customer.getTransactions().getLast();
        Assertions.assertEquals(expectedFare, transaction.getFare());
        Journey transactionJourney = transaction.getJourney();
        Assertions.assertEquals(station, transactionJourney.getFrom());
        Assertions.assertEquals(type, transactionJourney.getType());
    }

    @Test
    void mustChargeFareOnInwardsTap() {
        BigDecimal customerBalance = BigDecimal.valueOf(50.0);
        Customer customer = Customer.builder().balance(customerBalance).build();
        BigDecimal calculatedFare = BigDecimal.valueOf(10.0);
        BigDecimal expectedBalance = customerBalance.subtract(calculatedFare);
        Mockito.when(fareCalculator.calculateJourney(Mockito.any())).thenReturn(calculatedFare);

        customerJourney.inwardTap(customer, Station.builder().build(), JourneyType.TUBE);


        Assertions.assertEquals(expectedBalance, customer.getBalance());
    }

    @Test
    void mustReplaceLastTransactionOnOutwardsTap() {
        JourneyType journeyType = JourneyType.TUBE;
        Station journeyStation = Station.builder().name("First Station").build();
        Station journeyDestination = Station.builder().name("Second Station").build();
        Journey lastTransactionJourney = Journey.builder()
                .from(journeyStation)
                .type(journeyType)
                .build();
        BigDecimal lastTransactionFare = BigDecimal.valueOf(50.0);
        Transaction lastTransaction = Transaction.builder().fare(lastTransactionFare).journey(lastTransactionJourney).build();

        Customer customer = Customer.builder()
                .transactions(new LinkedList<>(Collections.singleton(lastTransaction)))
                .build();

        BigDecimal newTransactionFare = BigDecimal.valueOf(20.0);

        Mockito.when(fareCalculator.calculateJourney(Mockito.any())).thenReturn(newTransactionFare);

        customerJourney.outwardTap(customer, journeyDestination);

        Assertions.assertEquals(1, customer.getTransactions().size());

        Transaction resultTransaction = customer.getTransactions().getLast();

        Assertions.assertEquals(newTransactionFare, resultTransaction.getFare());
        Journey resultTransactionJourney = resultTransaction.getJourney();

        Assertions.assertEquals(journeyStation, resultTransactionJourney.getFrom());
        Assertions.assertEquals(journeyType, resultTransactionJourney.getType());
        Assertions.assertEquals(journeyDestination, resultTransactionJourney.getTo());
    }

    @Test
    void mustChargeBackPreviousTransactionFareThenChargeNewTransactionFareOnOutwardsTap() {

        BigDecimal lastTransactionFare = BigDecimal.valueOf(20.0);
        Transaction lastTransaction = Transaction.builder().fare(lastTransactionFare).journey(Journey.builder().build()).build();

        BigDecimal customerBalance = BigDecimal.valueOf(50.0);
        Customer customer = Customer.builder().balance(customerBalance)
                .transactions(new LinkedList<>(Collections.singleton(lastTransaction)))
                .build();
        BigDecimal calculatedFare = BigDecimal.valueOf(10.0);
        BigDecimal expectedBalance = customerBalance.add(lastTransactionFare).subtract(calculatedFare);
        Mockito.when(fareCalculator.calculateJourney(Mockito.any())).thenReturn(calculatedFare);

        customerJourney.outwardTap(customer, Station.builder().build());

        Assertions.assertEquals(expectedBalance, customer.getBalance());
    }
}
