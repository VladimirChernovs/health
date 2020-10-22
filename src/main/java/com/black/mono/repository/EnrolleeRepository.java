package com.black.mono.repository;

import com.black.mono.domain.Enrollee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Enrollee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
}
