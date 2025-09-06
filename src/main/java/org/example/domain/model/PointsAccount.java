package org.example.domain.model;

import java.util.ArrayList;
import java.util.List;


public final class PointsAccount {
    private final CustomerId id;
    private int balance;

    private PointsAccount(CustomerId id, int balance) {
        if (balance < 0) {
            throw new DomainException("Balance must be non-negative");
        }
        this.id = id;
        this.balance = balance;
    }

    public static PointsAccount create(CustomerId id) {
        return new PointsAccount(id, 0);
    }

    public static PointsAccount restore(CustomerId id, int balance) {
        return new PointsAccount(id, balance);
    }

    public CustomerId getCustomerId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void earn(int points) {
        assertValid(points);
        balance += points;
    }

    public List<DomainEvent> redeem(int points) {
        assertValid(points);
        if (points > balance) {
            throw new InsufficientPointsException(balance, points);
        }

        balance -= points;

        List<DomainEvent> events = new ArrayList<>();
        if (balance < 10) {
            events.add(new LowBalanceWarning(id.toString(), balance));
        }
        return events;
    }

    private void assertValid(int points) {
        if (points <= 0) {
            throw new DomainException("Points must be a positive integer");
        }
    }
}
