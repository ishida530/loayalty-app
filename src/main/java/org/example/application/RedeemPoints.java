package org.example.application;

import org.example.domain.model.CustomerId;
import org.example.domain.model.PointsAccount;
import org.example.domain.ports.EventPublisher;
import org.example.domain.ports.PointsAccountRepository;


public final class RedeemPoints {
    private final PointsAccountRepository repo;
    private final EventPublisher publisher;

    public RedeemPoints(PointsAccountRepository repo, EventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    public Result execute(String customerIdRaw, int points) {
        CustomerId id = CustomerId.of(customerIdRaw);
        PointsAccount acc = repo.findById(id).orElse(PointsAccount.create(id));

        var events = acc.redeem(points);
        repo.save(acc);
        publisher.publishAll(events);

        return new Result(acc.getBalance());
    }

    public record Result(int balance) {}
}
