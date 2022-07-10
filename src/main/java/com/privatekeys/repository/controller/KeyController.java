package com.privatekeys.repository.controller;

import com.privatekeys.repository.model.Key;
import com.privatekeys.repository.service.KeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/Key")
public class KeyController {

    @Autowired
    KeyService keyService;

    @RequestMapping( method = RequestMethod.POST)
    public Key addNewKey(@RequestBody Key key) {
        log.info("Key insertion workflow, {}", key);

        // As per assignment description, there won't be any key id when inserting. But integration tests passing 0 always. Therefore making it null.
        key.setId(null);

        return keyService.insertKey(key);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Key> getAllKeys() {
        log.info("Get all keys workflow");
        return keyService.getAllKeys();
    }

    @RequestMapping(value="/{keyId}", method=RequestMethod.GET)
    public Key getKey(@PathVariable int keyId) {
        log.info("Get key workflow, keyId: {}", keyId);
        return keyService.getKeyById(keyId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Key updateKey(@RequestBody Key key) {
        log.info("Update key workflow, {}", key);
        return keyService.updateKey(key);
    }

    @RequestMapping(value="/{keyId}", method=RequestMethod.DELETE)
    public void deleteKey(@PathVariable int keyId) {
        log.info("Delete key workflow, {}", keyId);
        keyService.deleteKey(keyId);
    }

}
