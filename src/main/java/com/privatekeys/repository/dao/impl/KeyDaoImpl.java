package com.privatekeys.repository.dao.impl;

import com.privatekeys.repository.dao.KeyDao;
import com.privatekeys.repository.model.Key;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class KeyDaoImpl implements KeyDao {

    Map<Integer, Key> keyData = new HashMap<>();
    static AtomicInteger incrementId = new AtomicInteger(0);
    Lock lock = new ReentrantLock();

    @Override
    public Key saveKey(Key key) {
        try {
            lock.lock();
            if (key.getId() == null ) {
                key.setId(incrementId.incrementAndGet());
            }

            keyData.put(key.getId(), key);
            return key;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Key> getAllKeys() {
        return new ArrayList<>(keyData.values());
    }

    @Override
    public Key findById(int id) {
        return keyData.get(id);
    }

    @Override
    public void deleteById(int id) {
        keyData.remove(id);
    }
}
