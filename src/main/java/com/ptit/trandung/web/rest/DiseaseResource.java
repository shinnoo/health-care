package com.ptit.trandung.web.rest;

import com.ptit.trandung.service.DiseaseService;
import com.ptit.trandung.web.rest.errors.BadRequestAlertException;
import com.ptit.trandung.service.dto.DiseaseDTO;

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
 * REST controller for managing {@link com.ptit.trandung.domain.Disease}.
 */
@RestController
@RequestMapping("/api")
public class DiseaseResource {

    private final Logger log = LoggerFactory.getLogger(DiseaseResource.class);

    private static final String ENTITY_NAME = "disease";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiseaseService diseaseService;

    public DiseaseResource(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    /**
     * {@code POST  /diseases} : Create a new disease.
     *
     * @param diseaseDTO the diseaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diseaseDTO, or with status {@code 400 (Bad Request)} if the disease has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diseases")
    public ResponseEntity<DiseaseDTO> createDisease(@Valid @RequestBody DiseaseDTO diseaseDTO) throws URISyntaxException {
        log.debug("REST request to save Disease : {}", diseaseDTO);
        if (diseaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new disease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiseaseDTO result = diseaseService.save(diseaseDTO);
        return ResponseEntity.created(new URI("/api/diseases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diseases} : Updates an existing disease.
     *
     * @param diseaseDTO the diseaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diseaseDTO,
     * or with status {@code 400 (Bad Request)} if the diseaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diseaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diseases")
    public ResponseEntity<DiseaseDTO> updateDisease(@Valid @RequestBody DiseaseDTO diseaseDTO) throws URISyntaxException {
        log.debug("REST request to update Disease : {}", diseaseDTO);
        if (diseaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiseaseDTO result = diseaseService.save(diseaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diseaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /diseases} : get all the diseases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diseases in body.
     */
    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseDTO>> getAllDiseases(Pageable pageable) {
        log.debug("REST request to get a page of Diseases");
        Page<DiseaseDTO> page = diseaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /diseases/:id} : get the "id" disease.
     *
     * @param id the id of the diseaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diseaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diseases/{id}")
    public ResponseEntity<DiseaseDTO> getDisease(@PathVariable Long id) {
        log.debug("REST request to get Disease : {}", id);
        Optional<DiseaseDTO> diseaseDTO = diseaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diseaseDTO);
    }

    /**
     * {@code DELETE  /diseases/:id} : delete the "id" disease.
     *
     * @param id the id of the diseaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diseases/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        log.debug("REST request to delete Disease : {}", id);
        diseaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
