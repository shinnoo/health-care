package com.ptit.trandung.web.rest;

import com.ptit.trandung.service.NurseService;
import com.ptit.trandung.web.rest.errors.BadRequestAlertException;
import com.ptit.trandung.service.dto.NurseDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ptit.trandung.domain.Nurse}.
 */
@RestController
@RequestMapping("/api")
public class NurseResource {

    private final Logger log = LoggerFactory.getLogger(NurseResource.class);

    private static final String ENTITY_NAME = "nurse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NurseService nurseService;

    public NurseResource(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    /**
     * {@code POST  /nurses} : Create a new nurse.
     *
     * @param nurseDTO the nurseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nurseDTO, or with status {@code 400 (Bad Request)} if the nurse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nurses")
    public ResponseEntity<NurseDTO> createNurse(@RequestBody NurseDTO nurseDTO) throws URISyntaxException {
        log.debug("REST request to save Nurse : {}", nurseDTO);
        if (nurseDTO.getId() != null) {
            throw new BadRequestAlertException("A new nurse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NurseDTO result = nurseService.save(nurseDTO);
        return ResponseEntity.created(new URI("/api/nurses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nurses} : Updates an existing nurse.
     *
     * @param nurseDTO the nurseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nurseDTO,
     * or with status {@code 400 (Bad Request)} if the nurseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nurseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nurses")
    public ResponseEntity<NurseDTO> updateNurse(@RequestBody NurseDTO nurseDTO) throws URISyntaxException {
        log.debug("REST request to update Nurse : {}", nurseDTO);
        if (nurseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NurseDTO result = nurseService.save(nurseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nurseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nurses} : get all the nurses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nurses in body.
     */
    @GetMapping("/nurses")
    public ResponseEntity<List<NurseDTO>> getAllNurses(Pageable pageable) {
        log.debug("REST request to get a page of Nurses");
        Page<NurseDTO> page = nurseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nurses/:id} : get the "id" nurse.
     *
     * @param id the id of the nurseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nurseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nurses/{id}")
    public ResponseEntity<NurseDTO> getNurse(@PathVariable Long id) {
        log.debug("REST request to get Nurse : {}", id);
        Optional<NurseDTO> nurseDTO = nurseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nurseDTO);
    }

    /**
     * {@code DELETE  /nurses/:id} : delete the "id" nurse.
     *
     * @param id the id of the nurseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nurses/{id}")
    public ResponseEntity<Void> deleteNurse(@PathVariable Long id) {
        log.debug("REST request to delete Nurse : {}", id);
        nurseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
