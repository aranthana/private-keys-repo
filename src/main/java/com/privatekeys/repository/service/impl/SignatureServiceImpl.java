package com.privatekeys.repository.service.impl;

import com.privatekeys.repository.dao.KeyDao;
import com.privatekeys.repository.model.Key;
import com.privatekeys.repository.service.SignatureService;

import com.privatekeys.repository.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import org.conscrypt.OpenSSLProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

import sun.security.util.ECUtil;


@Slf4j
@Service
public class SignatureServiceImpl implements SignatureService {

    @Autowired
    KeyDao keydao;

    @Override
    public String generateSignature(String keyId, String message) {
        Key key = keydao.findById(keyId);
        if (key != null) {
            try {
                String keyString = key.getKey();
                byte[] decoded = Base64.getDecoder().decode(keyString);
                String hexString = Hex.encodeHexString(decoded);
                ECParameterSpec ecParameterSpec = ECUtil.getECParameterSpec(new OpenSSLProvider(), Constants.SPEC); // Create EC Parameter Spec with prime256v1
                ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(new BigInteger(hexString, 16), ecParameterSpec);
                KeyFactory kf = KeyFactory.getInstance(Constants.EC);
                PrivateKey privateKey = kf.generatePrivate(privateKeySpec);
                Signature ecdsaSign = Signature.getInstance(Constants.ALGORITHM);  // Signature algorithm is initialized
                ecdsaSign.initSign(privateKey);    // Initialize Signature object with Private Key
                ecdsaSign.update(message.getBytes());  // Update the whole message to be signed.
                byte[] signature = ecdsaSign.sign();  // Sign the message. signArray, holds the actual signature.
                String encodedSignature = Base64.getEncoder().encodeToString(signature);
                return encodedSignature;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | InvalidKeyException e) {
                log.error("Error occurred while generating signature. {}", e.getMessage());
                return "Error occurred while signing message";
            }
        } else
            return "Invalid Key Id";

    }
}

