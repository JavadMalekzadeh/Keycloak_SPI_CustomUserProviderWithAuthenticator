package org.example.userstoragespi.domain;

public class ExpiringEntry <K,V>{
    private final K key;
    private final V value;
    private final long expirationTime;

    public ExpiringEntry(K key, V value, long expirationTime) {
        this.key = key;
        this.value = value;
        this.expirationTime = expirationTime;
    }

    public long getExpirationTime() {
        return this.expirationTime;
    }

    public V getValue() {
        return this.value;
    }
}
