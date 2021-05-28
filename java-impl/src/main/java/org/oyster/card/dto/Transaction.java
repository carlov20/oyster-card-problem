package org.oyster.card.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Transaction {

    private Journey journey;
    private BigDecimal fare;
}
