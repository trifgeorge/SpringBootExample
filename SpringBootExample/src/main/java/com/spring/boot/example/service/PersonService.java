package com.spring.boot.example.service;

import com.spring.boot.example.model.Person;

import java.util.List;

public interface PersonService extends BaseService{

     List<Person> getAll();
}
