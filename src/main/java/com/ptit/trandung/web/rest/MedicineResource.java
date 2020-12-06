package com.ptit.trandung.web.rest;

import com.ptit.trandung.service.MedicineService;
import com.ptit.trandung.web.rest.errors.BadRequestAlertException;
import com.ptit.trandung.service.dto.MedicineDTO;

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
 * REST controller for managing {@link com.ptit.trandung.domain.Medicine}.
 */
@RestController
@RequestMapping("/api")
public class MedicineResource {

    private final Logger log = LoggerFactory.getLogger(MedicineResource.class);

    private static final String ENTITY_NAME = "medicine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicineService medicineService;

    public MedicineResource(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    /**
     * {@code POST  /medicines} : Create a new medicine.
     *
     * @param medicineDTO the medicineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicineDTO, or with status {@code 400 (Bad Request)} if the medicine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicines")
    public ResponseEntity<MedicineDTO> createMedicine(@Valid @RequestBody MedicineDTO medicineDTO) throws URISyntaxException {
        log.debug("REST request to save Medicine : {}", medicineDTO);
        if (medicineDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicineDTO result = medicineService.save(medicineDTO);
        return ResponseEntity.created(new URI("/api/medicines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicines} : Updates an existing medicine.
     *
     * @param medicineDTO the medicineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicineDTO,
     * or with status {@code 400 (Bad Request)} if the medicineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicines")
    public ResponseEntity<MedicineDTO> updateMedicine(@Valid @RequestBody MedicineDTO medicineDTO) throws URISyntaxException {
        log.debug("REST request to update Medicine : {}", medicineDTO);
        if (medicineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicineDTO result = medicineService.save(medicineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicines} : get all the medicines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicines in body.
     */
    @GetMapping("/medicines")
    public ResponseEntity<List<MedicineDTO>> getAllMedicines(Pageable pageable) {
        log.debug("REST request to get a page of Medicines");
        Page<MedicineDTO> page = medicineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicines/:id} : get the "id" medicine.
     *
     * @param id the id of the medicineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicines/{id}")
    public ResponseEntity<MedicineDTO> getMedicine(@PathVariable Long id) {
        log.debug("REST request to get Medicine : {}", id);
        Optional<MedicineDTO> medicineDTO = medicineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicineDTO);
    }

    /**
     * {@code DELETE  /medicines/:id} : delete the "id" medicine.
     *
     * @param id the id of the medicineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        log.debug("REST request to delete Medicine : {}", id);
        medicineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
