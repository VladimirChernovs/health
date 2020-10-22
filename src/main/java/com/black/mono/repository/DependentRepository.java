package com.black.mono.repository;

import com.black.mono.domain.Dependent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dependent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DependentRepository extends JpaRepository<Dependent, Long> {
}
