package com.spring.boot.example.service.impl;

import com.spring.boot.example.service.TestService;
import com.spring.boot.example.util.SpringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class TestServiceImpl implements TestService {

    @Value("${test}")
    private String env;
    @Autowired
    private SpringUtils springUtils;

    @Override
    public void test() {
        System.out.println(springUtils.getProperty("env"));
        System.out.println(env);
    }

    @PostConstruct
    public void init(){
        test();
    }
}
