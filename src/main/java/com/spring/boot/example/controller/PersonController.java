package com.spring.boot.example.controller;

import com.spring.boot.example.model.dto.PersonDTO;
import com.spring.boot.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController implements BaseController<PersonDTO,Long>{

    @Autowired
    PersonService personService;

    @Override
    public ResponseEntity<PersonDTO> findOne(Long id) {
       PersonDTO personDTO = this.personService.findOne(id);
       if(personDTO == null){

       }
       return null;
    }

    @Override
    public ResponseEntity<PersonDTO> update(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<PersonDTO> create(PersonDTO personDTO) {
        return null;
    }
}
