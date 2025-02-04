package com.oj_timer.server.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RefreshTokenRepository {
    private ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    public void save(String email, String token) {
        store.put(email, token);
    }

    public void deleteByEmail(String email) {
        store.remove(email);
    }

    public void deleteByToken(String token) {
        store.keys().asIterator().forEachRemaining(key -> {
            if (store.get(key).equals(token)) store.remove(key);
        });
    }

    public String findOne(String email) {
        return store.getOrDefault(email, null);
    }

    public List<String> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clear() {
        store.clear();
    }
}
