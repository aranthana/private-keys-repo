package com.privatekeys.repository.service;

import com.privatekeys.repository.model.Key;

import java.util.List;

public interface KeyService {

    Key insertKey(Key key);

    List<Key> getAllKeys();

    Key getKeyById(int id);

    Key updateKey(Key key);

    void deleteKey(int id);
}
