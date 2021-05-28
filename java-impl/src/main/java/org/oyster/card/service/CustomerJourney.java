package org.oyster.card.service;

import org.oyster.card.dto.Customer;
import org.oyster.card.dto.JourneyType;
import org.oyster.card.dto.Station;

public interface CustomerJourney {

    void inwardTap(Customer customer, Station station, JourneyType journeyType);

    void outwardTap(Customer customer, Station station);
}
