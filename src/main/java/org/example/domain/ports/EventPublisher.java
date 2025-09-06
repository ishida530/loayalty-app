package org.example.domain.ports;

import org.example.domain.model.DomainEvent;

import java.util.Collection;


public interface EventPublisher {
    void publish(DomainEvent event);

    default void publishAll(Collection<? extends DomainEvent> events) {
        events.forEach(this::publish);
    }
}
