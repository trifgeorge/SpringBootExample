package com.spring.boot.example.model.entity;

/**
 * Base class for JPA Entities
 *
 * @param <ID> Primary key used by the {@link jakarta.persistence.Entity Entity}
 */
public interface BaseEntity<ID> {

    ID getId();
    void setId(ID id);
}
