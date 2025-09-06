package org.example.domain.model;

public class InsufficientPointsException extends DomainException {
    private final int currentBalance;
    private final int attempted;

    public InsufficientPointsException(int currentBalance, int attempted) {
        super("Cannot redeem " + attempted + " points from balance " + currentBalance);
        this.currentBalance = currentBalance;
        this.attempted = attempted;
    }

    public int getCurrentBalance() { return currentBalance; }
    public int getAttempted() { return attempted; }
}
