package org.example.application;

import org.example.domain.model.DomainEvent;
import org.example.domain.ports.EventPublisher;
import org.example.infra.InMemoryPointsAccountRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RedeemPointsTest {

    static class CapturingPublisher implements EventPublisher {
        final List<DomainEvent> events = new ArrayList<>();
        @Override public void publish(DomainEvent event) { events.add(event); }
    }

    @Test
    void publishesLowBalanceEvent() {
        var repo = new InMemoryPointsAccountRepository();
        var publisher = new CapturingPublisher();
        var earn = new EarnPoints(repo);
        var redeem = new RedeemPoints(repo, publisher);

        earn.execute("u1", 12);
        var result = redeem.execute("u1", 5);

        assertEquals(7, result.balance());
        assertFalse(publisher.events.isEmpty(), "Expected at least one event");
    }
}
