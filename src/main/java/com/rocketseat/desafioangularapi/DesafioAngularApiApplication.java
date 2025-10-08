package com.rocketseat.desafioangularapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DesafioAngularApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioAngularApiApplication.class, args);
    }

}
