package com.spring.boot.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Base Controller for CRUD operations
 *
 * @param <DTO> {@code DTO} class
 * @param <ID> primary key for {@link jakarta.persistence.Entity Entity} class
 */
public interface BaseController<DTO,ID> {

    ResponseEntity<DTO> findOne(ID id);
    ResponseEntity<DTO> update(ID id);
    ResponseEntity<HttpStatus> delete(ID id);
    ResponseEntity<DTO> create(DTO t);
}
