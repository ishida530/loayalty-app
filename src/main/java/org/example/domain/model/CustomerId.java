package org.example.domain.model;

import java.util.Objects;

public final class CustomerId {
    private final String value;

    private CustomerId(String value) {
        this.value = value;
    }

    public static CustomerId of(String raw) {
        String v = raw == null ? "" : raw.trim();
        if (v.isEmpty()) {
            throw new IllegalArgumentException("CustomerId cannot be empty");
        }
        return new CustomerId(v);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerId)) return false;
        CustomerId that = (CustomerId) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
