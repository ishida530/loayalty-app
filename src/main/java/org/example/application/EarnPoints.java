package org.example.application;

import org.example.domain.model.CustomerId;
import org.example.domain.model.PointsAccount;
import org.example.domain.ports.PointsAccountRepository;

public final class EarnPoints {
    private final PointsAccountRepository repo;

    public EarnPoints(PointsAccountRepository repo) {
        this.repo = repo;
    }

    public Result execute(String customerIdRaw, int points) {
        CustomerId id = CustomerId.of(customerIdRaw);
        PointsAccount acc = repo.findById(id).orElse(PointsAccount.create(id));

        acc.earn(points);
        repo.save(acc);

        return new Result(acc.getBalance());
    }

    public record Result(int balance) {}
}
