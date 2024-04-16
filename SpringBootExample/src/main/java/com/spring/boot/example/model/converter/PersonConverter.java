package com.spring.boot.example.model.converter;

import com.spring.boot.example.model.dto.PersonDTO;
import com.spring.boot.example.model.entity.Person;

public class PersonConverter implements BaseConverter<Person, PersonDTO>{
    @Override
    public PersonDTO toDto(Person person) {
        PersonDTO personDTO=new PersonDTO();
        personDTO.setEmail(personDTO.getEmail());
        personDTO.setAge(personDTO.getAge());
        personDTO.setName(personDTO.getName());
        personDTO.setId(personDTO.getId());
        return personDTO;
    }

    @Override
    public Person toEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setAge(personDTO.getAge());
        person.setId(person.getId());
        person.setEmail(person.getEmail());
        person.setName(person.getName());
        return person;
    }
}
