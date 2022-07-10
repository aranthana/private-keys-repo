package com.privatekeys.repository;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
public class PrivateKeysRepoApplication {

    public static void main(String[] args) {

        //Adding BouncyCastle provider
        Security.addProvider(new BouncyCastleProvider());
        SpringApplication.run(PrivateKeysRepoApplication.class, args);
    }

}
