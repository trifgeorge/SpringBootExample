package com.spring.boot.example.service.impl;

import com.spring.boot.example.DefaultAbstractContextTestClass;
import com.spring.boot.example.model.Person;
import com.spring.boot.example.service.PersonService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonServiceImplTest extends DefaultAbstractContextTestClass {

    @Autowired
    PersonService personService;
    @Test
    public void testGetAll(){
        List<Person> persons =  this.personService.getAll();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(persons));
        System.out.println(persons);
    }

}