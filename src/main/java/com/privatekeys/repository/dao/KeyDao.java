package com.privatekeys.repository.dao;

import com.privatekeys.repository.model.Key;

import java.util.List;

public interface KeyDao {

    Key saveKey(Key key);

    List<Key> getAllKeys();

    Key findById(int id);

    void deleteById(int id);
}
