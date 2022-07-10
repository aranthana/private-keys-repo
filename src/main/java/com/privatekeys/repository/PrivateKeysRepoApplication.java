package com.privatekeys.repository;

import org.conscrypt.OpenSSLProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
public class PrivateKeysRepoApplication {

    public static void main(String[] args) {

        //Adding open ssl provider from Conscrypt to handle EC
        Security.addProvider(new OpenSSLProvider());
        SpringApplication.run(PrivateKeysRepoApplication.class, args);
    }

}
