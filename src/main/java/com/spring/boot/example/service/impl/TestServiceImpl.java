package com.spring.boot.example.service.impl;

import com.spring.boot.example.service.TestService;
import com.spring.boot.example.util.SpringUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class TestServiceImpl implements TestService {

    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Value("${env}")
    private String env;
    @Autowired
    private SpringUtils springUtils;

    @Override
    public void test() {
        log.info(springUtils.getProperty("env"));
        log.info(env);
    }

    @PostConstruct
    public void init(){
        test();
    }
}
