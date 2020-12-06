package com.ptit.trandung.service;

import com.ptit.trandung.service.dto.NurseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ptit.trandung.domain.Nurse}.
 */
public interface NurseService {

    /**
     * Save a nurse.
     *
     * @param nurseDTO the entity to save.
     * @return the persisted entity.
     */
    NurseDTO save(NurseDTO nurseDTO);

    /**
     * Get all the nurses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NurseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" nurse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NurseDTO> findOne(Long id);

    /**
     * Delete the "id" nurse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
