package com.ptit.trandung.service;

import com.ptit.trandung.service.dto.DiseaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ptit.trandung.domain.Disease}.
 */
public interface DiseaseService {

    /**
     * Save a disease.
     *
     * @param diseaseDTO the entity to save.
     * @return the persisted entity.
     */
    DiseaseDTO save(DiseaseDTO diseaseDTO);

    /**
     * Get all the diseases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiseaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" disease.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiseaseDTO> findOne(Long id);

    /**
     * Delete the "id" disease.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
