package org.example.domain.model;

public final class LowBalanceWarning implements DomainEvent {
    private final String customerId;
    private final int balance;

    public LowBalanceWarning(String customerId, int balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    public String getCustomerId() { return customerId; }
    public int getBalance() { return balance; }
}
