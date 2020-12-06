package com.ptit.trandung.repository;

import com.ptit.trandung.domain.Nurse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Nurse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
}
