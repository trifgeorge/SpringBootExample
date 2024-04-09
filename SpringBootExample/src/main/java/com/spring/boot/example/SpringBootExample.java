package com.spring.boot.example;

import com.spring.boot.example.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExample {

    private static final Logger log = LoggerFactory.getLogger(SpringBootExample.class);
    @Autowired
    private TestService testService;

    public static void main(String[] args) {

        SpringApplication.run(SpringBootExample.class, args);
        log.info("APP STARTED");
    }
}
