package com.ptit.trandung.repository;

import com.ptit.trandung.domain.Disease;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Disease entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
