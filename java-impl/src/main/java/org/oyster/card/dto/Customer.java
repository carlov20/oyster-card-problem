package org.oyster.card.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

@Data
@Builder
public class Customer {

    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0L);
    @Builder.Default
    private Deque<Transaction> transactions = new LinkedList<>();

    public void addLastTransaction(Transaction transaction) {
        transactions.addLast(transaction);
    }

    public Transaction popLastTransaction() {
        return transactions.pollLast();
    }

    public void chargeFare(BigDecimal fare) {
        this.balance = this.balance.subtract(fare);

    }

    public void chargeBackFare(BigDecimal fare) {
        this.balance = this.balance.add(fare);
    }
}
