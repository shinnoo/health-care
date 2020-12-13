package com.ptit.trandung.repository;

import com.ptit.trandung.domain.MedicalHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MedicalHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

    Page<MedicalHistory> findAllByPatientId(Long id, Pageable pageable);
}
