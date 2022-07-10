package com.privatekeys.repository;

import com.privatekeys.repository.dao.KeyDao;
import com.privatekeys.repository.model.Key;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@SpringBootTest
public class KeyDaoTest {

    @Autowired
    private KeyDao keyDao;

    @Test
    public void testDaoOperations() {
        Key key1 = new Key();
        key1.setKey(UUID.randomUUID().toString());

        Key key2 = new Key();
        key2.setKey(UUID.randomUUID().toString());
        key2.setId(3);


        keyDao.saveKey(key1);
        keyDao.saveKey(key2);

        assertEquals(2, keyDao.getAllKeys().size());
        assertEquals(key1.getKey(), keyDao.findById(1).getKey());
        assertEquals(key2.getKey(), keyDao.findById(3).getKey());

        keyDao.deleteById(1);

        assertEquals(1, keyDao.getAllKeys().size());
        assertNull(keyDao.findById(1));

        String newKey = UUID.randomUUID().toString();
        key2.setKey(newKey);
        keyDao.saveKey(key2);

        assertEquals(1, keyDao.getAllKeys().size());
        assertEquals(newKey, keyDao.findById(3).getKey());
    }
}
