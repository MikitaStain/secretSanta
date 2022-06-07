package com.innowise.secret_santa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource
        (
                value =
                        {
                                "classpath:email.properties",
                                "classpath:database.properties",
                                "classpath:jwt_token.properties"
                        }
        )
public class SecretSantaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecretSantaApplication.class, args);
    }
}