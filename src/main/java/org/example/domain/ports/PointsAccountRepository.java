package org.example.domain.ports;

import org.example.domain.model.CustomerId;
import org.example.domain.model.PointsAccount;

import java.util.Optional;

public interface PointsAccountRepository {
    Optional<PointsAccount> findById(CustomerId id);
    void save(PointsAccount account);
}
