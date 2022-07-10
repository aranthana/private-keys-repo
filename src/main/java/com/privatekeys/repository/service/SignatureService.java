package com.privatekeys.repository.service;

public interface SignatureService {

    /**
     * Sign the message passed using the key stored with keyId
     * @param keyId
     * @param message
     * @return Signature of the message
     */
    String generateSignature(int keyId, String message);
}
