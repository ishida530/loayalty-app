package org.example.infra;

import org.example.domain.model.CustomerId;
import org.example.domain.model.PointsAccount;
import org.example.domain.ports.PointsAccountRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryPointsAccountRepository implements PointsAccountRepository {
    private final Map<String, Integer> store = new ConcurrentHashMap<>();

    @Override
    public Optional<PointsAccount> findById(CustomerId id) {
        Integer bal = store.get(id.toString());
        return bal == null ? Optional.empty() : Optional.of(PointsAccount.restore(id, bal));
    }

    @Override
    public void save(PointsAccount account) {
        store.put(account.getCustomerId().toString(), account.getBalance());
    }
}
