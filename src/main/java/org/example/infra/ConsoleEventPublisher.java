package org.example.infra;

import org.example.domain.model.DomainEvent;
import org.example.domain.model.LowBalanceWarning;
import org.example.domain.ports.EventPublisher;

public final class ConsoleEventPublisher implements EventPublisher {
    @Override
    public void publish(DomainEvent event) {
        if (event instanceof LowBalanceWarning lbw) {
            System.out.println("Warning: Customer " + lbw.getCustomerId()
                    + " has a low balance: " + lbw.getBalance() + " points.");
        }
    }
}
