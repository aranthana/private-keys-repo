package com.privatekeys.repository.service.impl;

import com.privatekeys.repository.dao.KeyDao;
import com.privatekeys.repository.model.Key;
import com.privatekeys.repository.service.KeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KeyServiceImpl implements KeyService {

    @Autowired
    KeyDao keyDao;

    @Override
    public Key insertKey(Key key) {
        return keyDao.saveKey(key);
    }

    @Override
    public List<Key> getAllKeys() {
        return keyDao.getAllKeys();
    }

    @Override
    public Key getKeyById(int id) {
        return keyDao.findById(id);
    }

    @Override
    public Key updateKey(Key key) {
        return keyDao.saveKey(key);
    }

    @Override
    public void deleteKey(int id) {
        keyDao.deleteById(id);
    }
}
