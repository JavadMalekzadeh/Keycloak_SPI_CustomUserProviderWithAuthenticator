package org.example.userstoragespi.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExpiringMap <K, V> {
    private final Map<K, ExpiringEntry<K, V>> map = new ConcurrentHashMap<>();

    public void put(K key, V value, long timeToLiveMillis) {
        long expirationTime = System.currentTimeMillis() + timeToLiveMillis;
        ExpiringEntry<K, V> entry = new ExpiringEntry<>(key, value, expirationTime);
        map.put(key, entry);
    }

    public V get(K key) {
        ExpiringEntry<K, V> entry = map.get(key);
        if (entry != null && System.currentTimeMillis() <= entry.getExpirationTime()) {
            return entry.getValue();
        } else {
            map.remove(key);
            return null;
        }
    }
}
