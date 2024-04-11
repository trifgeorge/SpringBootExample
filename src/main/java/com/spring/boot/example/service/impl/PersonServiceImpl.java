package com.spring.boot.example.service.impl;

import com.spring.boot.example.model.Person;
import com.spring.boot.example.repository.PersonRepository;
import com.spring.boot.example.service.PersonService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    PersonRepository personRepository;

    @PostConstruct
    public void init(){
        log.info( "PersonService Initialized" );
    }

    @Override
    public List<Person> getAll() {
        return this.personRepository.findAll();
    }
}
