package com.privatekeys.repository.service.impl;

import com.privatekeys.repository.dao.KeyDao;
import com.privatekeys.repository.model.Key;
import com.privatekeys.repository.service.SignatureService;

import com.privatekeys.repository.util.Constants;
import lombok.extern.slf4j.Slf4j;


import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;

import java.security.*;

import java.util.Base64;



@Slf4j
@Service
public class SignatureServiceImpl implements SignatureService {

    @Autowired
    KeyDao keydao;

    @Override
    public String generateSignature(int keyId, String message) {
        Key key = keydao.findById(keyId);
        if (key != null) {
            try {
                message = new String(Base64.getDecoder().decode(message), "UTF-8");

                String keyString = key.getKey();

                PEMParser pemParser = new PEMParser(new StringReader(keyString));

                PrivateKey privateKey = new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) pemParser.readObject()).getPrivate();
                Signature ecdsaSign = Signature.getInstance(Constants.ALGORITHM);
                ecdsaSign.initSign(privateKey);    // Initialize Signature object with Private Key
                ecdsaSign.update(message.getBytes());  // Update the whole message to be signed.
                byte[] signature = ecdsaSign.sign();  // Sign the message. signArray, holds the actual signature.
                String encodedSignature = Base64.getEncoder().encodeToString(signature);

                return encodedSignature;

            } catch (NoSuchAlgorithmException | IOException | SignatureException | InvalidKeyException e) {
                log.error("Error occurred while generating signature. {}", e.getMessage());
                return null;
            }
        } else
            return null;

    }
}

