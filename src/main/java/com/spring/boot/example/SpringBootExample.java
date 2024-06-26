package com.spring.boot.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExample {
    private static final Logger log = LoggerFactory.getLogger(SpringBootExample.class);

    public static void main(String[] args) {

        SpringApplication.run(SpringBootExample.class, args);
        log.info("APP STARTED");
    }
}
