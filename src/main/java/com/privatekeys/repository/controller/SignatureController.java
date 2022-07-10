package com.privatekeys.repository.controller;

import com.privatekeys.repository.service.SignatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/signature")
public class SignatureController {

    @Autowired
    SignatureService signatureService;

    @RequestMapping(value="/keyId/{keyId}/message/{message}", method= RequestMethod.GET)
    public String getSignature(@PathVariable String keyId, @PathVariable String message)  {
        log.info("Sign message using key {}, message: {}", keyId, message);
        return signatureService.generateSignature(keyId,message);
    }
}
