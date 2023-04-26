package com.spring.boot.example;


import com.spring.boot.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExample {

    @Autowired
    private TestService testService;

    public static void main(String[] args) {

        SpringApplication.run(SpringBootExample.class, args);
        System.out.println("TEST");
    }
}
