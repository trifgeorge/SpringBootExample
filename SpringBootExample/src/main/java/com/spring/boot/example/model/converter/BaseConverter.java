package com.spring.boot.example.model.converter;

/**
 * Base converter to transform an {@code Entity} to its respective {@code DTO} class
 *
 * @param <ENTITY> {@link jakarta.persistence.Entity Entity} class
 * @param <DTO> {@code DTO} class
 */
public interface BaseConverter<ENTITY,DTO> {

    DTO toDto(ENTITY e);
    ENTITY toEntity(DTO d);
}
