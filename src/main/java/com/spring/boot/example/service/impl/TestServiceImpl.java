package com.spring.boot.example.service.impl;

import com.spring.boot.example.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class TestServiceImpl implements TestService {

    @Override
    public void test() {
        System.out.println("TEST Service");
    }

    @PostConstruct
    public void init(){
        test();
    }
}
