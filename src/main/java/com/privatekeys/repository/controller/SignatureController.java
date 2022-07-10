package com.privatekeys.repository.controller;

import com.privatekeys.repository.service.SignatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/signature")
public class SignatureController {

    @Autowired
    SignatureService signatureService;

    @RequestMapping(method = RequestMethod.GET)
    public String getSignature(@RequestParam Integer keyId, @RequestParam String message) {
        log.info("Sign message using key {}, message: {}", keyId, message);
        return signatureService.generateSignature(keyId, message);
    }
}
