package com.privatekeys.repository;

import com.privatekeys.repository.dao.KeyDao;
import com.privatekeys.repository.model.Key;
import com.privatekeys.repository.service.impl.SignatureServiceImpl;
import com.privatekeys.repository.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.*;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
public class PrivateKeysRepoApplicationTests {

    @InjectMocks
    SignatureServiceImpl signatureServiceImpl;

    @Mock
    KeyDao keyDao;

    @Test
    public void signatureTest2() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, InvalidAlgorithmParameterException, NoSuchProviderException, IOException {
        Security.addProvider(new BouncyCastleProvider());

        ClassLoader classLoader = getClass().getClassLoader();
        File privateKeyFile = new File(classLoader.getResource("private.pem").getFile());
        String privateKey = new String(Files.readAllBytes(privateKeyFile.toPath()), Charset.defaultCharset());

        PEMParser pemParser = new PEMParser(new StringReader(privateKey));
        PublicKey publicKey = new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) pemParser.readObject()).getPublic();

        String message = "Hello World " + UUID.randomUUID().toString();
        String encodedMsg = Base64.getEncoder().withoutPadding().encodeToString(message.getBytes());

        Key keyObject = new Key();
        keyObject.setId(1);

        keyObject.setKey(
                privateKey
        );
        when(keyDao.findById(keyObject.getId())).thenReturn(keyObject);

        String signature = signatureServiceImpl.generateSignature(keyObject.getId(), encodedMsg);

        Signature ecdsaVerify = Signature.getInstance(Constants.ALGORITHM);

        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(message.getBytes());
        boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(signature));  // if true, authenticity, integrity and non-repudiation of sender and message verified. This method computes the signature on receiver side as well.

        assertNotNull(encodedMsg);
        assertEquals(result, true);
    }


}
