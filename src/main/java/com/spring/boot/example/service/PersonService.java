package com.spring.boot.example.service;

import com.spring.boot.example.model.dto.PersonDTO;
import com.spring.boot.example.model.entity.Person;

import java.util.List;

public interface PersonService extends BaseService{

     List<Person> getAll();

     PersonDTO findOne(Long id);
}
