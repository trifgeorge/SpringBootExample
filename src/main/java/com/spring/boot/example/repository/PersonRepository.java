package com.spring.boot.example.repository;

import com.spring.boot.example.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<Person,Long>{

}
