package com.spring.boot.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base class for {@link JpaRepository JpaRepository} and {@link JpaSpecificationExecutor JpaSpecificationExecutor}
 * @param <ENTITY> {@link jakarta.persistence.Entity Entity} class
 * @param <ID> Primary key used by the {@link jakarta.persistence.Entity Entity} class
 */
@NoRepositoryBean
public interface BaseRepository<ENTITY,ID> extends JpaRepository<ENTITY,ID>, JpaSpecificationExecutor<ENTITY> {

}
