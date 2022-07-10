package com.privatekeys.repository;

import com.privatekeys.repository.dao.KeyDao;
import com.privatekeys.repository.model.Key;
import com.privatekeys.repository.service.impl.SignatureServiceImpl;
import com.privatekeys.repository.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.conscrypt.OpenSSLProvider;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import sun.security.util.ECUtil;

import java.security.*;
import java.security.spec.*;
import java.util.Base64;

@Slf4j
@SpringBootTest
public class PrivateKeysRepoApplicationTests {

    @InjectMocks
    SignatureServiceImpl signatureServiceImpl;

    @Mock
    KeyDao keyDao;

    @BeforeAll
    public static void setUp(){
        Security.addProvider(new OpenSSLProvider());
    }

    @Test
    public void signatureTest() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, InvalidAlgorithmParameterException {
        ECParameterSpec ecParameterSpec = ECUtil.getECParameterSpec(new OpenSSLProvider(), Constants.SPEC); // Parameters specified by spec of prime256v1

        KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance(Constants.EC); // Initialize to generate asymmetric keys to be used with one of the Elliptic Curve algorithms
        keyPairGenerator.initialize(ecParameterSpec, new SecureRandom());
        KeyPair keypair = keyPairGenerator.generateKeyPair(); // Generate asymmetric keys.
        PublicKey publicKey = keypair.getPublic();
        PrivateKey privateKey = keypair.getPrivate();
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        String message = "Hello World";
        String encodedMsg = Base64.getEncoder().withoutPadding().encodeToString(message.getBytes());

        Key keyObject = new Key();
        keyObject.setId("1");
        keyObject.setKey(privateKeyString);
        when(keyDao.findById(keyObject.getId())).thenReturn(keyObject);

        String signature = signatureServiceImpl.generateSignature(keyObject.getId(),encodedMsg);

        Signature ecdsaVerify = Signature.getInstance(Constants.ALGORITHM);
        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(encodedMsg.getBytes());
        boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(signature));  // if true, authenticity, integrity and non-repudiation of sender and message verified. This method computes the signature on receiver side as well.


        assertNotNull(encodedMsg);
        assertEquals(result,true);
    }


}
