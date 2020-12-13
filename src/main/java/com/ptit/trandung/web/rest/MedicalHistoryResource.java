package com.ptit.trandung.web.rest;

import com.ptit.trandung.service.MedicalHistoryService;
import com.ptit.trandung.web.rest.errors.BadRequestAlertException;
import com.ptit.trandung.service.dto.MedicalHistoryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ptit.trandung.domain.MedicalHistory}.
 */
@RestController
@RequestMapping("/api")
public class MedicalHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryResource.class);

    private static final String ENTITY_NAME = "medicalHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalHistoryService medicalHistoryService;

    public MedicalHistoryResource(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    /**
     * {@code POST  /medical-histories} : Create a new medicalHistory.
     *
     * @param medicalHistoryDTO the medicalHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalHistoryDTO, or with status {@code 400 (Bad Request)} if the medicalHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-histories")
    public ResponseEntity<MedicalHistoryDTO> createMedicalHistory(@Valid @RequestBody MedicalHistoryDTO medicalHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalHistory : {}", medicalHistoryDTO);
        if (medicalHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalHistoryDTO result = medicalHistoryService.save(medicalHistoryDTO);
        return ResponseEntity.created(new URI("/api/medical-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-histories} : Updates an existing medicalHistory.
     *
     * @param medicalHistoryDTO the medicalHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the medicalHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-histories")
    public ResponseEntity<MedicalHistoryDTO> updateMedicalHistory(@Valid @RequestBody MedicalHistoryDTO medicalHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalHistory : {}", medicalHistoryDTO);
        if (medicalHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalHistoryDTO result = medicalHistoryService.save(medicalHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicalHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-histories} : get all the medicalHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalHistories in body.
     */
    @GetMapping("/medical-histories")
    public ResponseEntity<List<MedicalHistoryDTO>> getAllMedicalHistories(Pageable pageable) {
        log.debug("REST request to get a page of MedicalHistories");
        Page<MedicalHistoryDTO> page = medicalHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medical-histories/:id} : get the "id" medicalHistory.
     *
     * @param id the id of the medicalHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-histories/{id}")
    public ResponseEntity<MedicalHistoryDTO> getMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to get MedicalHistory : {}", id);
        Optional<MedicalHistoryDTO> medicalHistoryDTO = medicalHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalHistoryDTO);
    }

    /**
     * {@code DELETE  /medical-histories/:id} : delete the "id" medicalHistory.
     *
     * @param id the id of the medicalHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-histories/{id}")
    public ResponseEntity<Void> deleteMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to delete MedicalHistory : {}", id);
        medicalHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/medical-histories/{userId}/patients")
    public ResponseEntity<List<MedicalHistoryDTO>> getAllByPatientId(Pageable pageable, @PathVariable Long userId) {
        log.debug("REST request to get a page of MedicalHistories");
        Page<MedicalHistoryDTO> page = medicalHistoryService.getAllByPatientId(userId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
