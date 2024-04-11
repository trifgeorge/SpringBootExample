package com.spring.boot.example.model;

/**
 * Base class for JPA Models
 *
 * @param <ID> Primary key used by the {@link jakarta.persistence.Entity Entity}
 */
public interface BaseModel<ID> {

    ID getId();
    void setId(ID id);
}
