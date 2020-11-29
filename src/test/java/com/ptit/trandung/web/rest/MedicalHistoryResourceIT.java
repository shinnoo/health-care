package com.ptit.trandung.web.rest;

import com.ptit.trandung.HealthCareApp;
import com.ptit.trandung.domain.MedicalHistory;
import com.ptit.trandung.repository.MedicalHistoryRepository;
import com.ptit.trandung.service.MedicalHistoryService;
import com.ptit.trandung.service.dto.MedicalHistoryDTO;
import com.ptit.trandung.service.mapper.MedicalHistoryMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MedicalHistoryResource} REST controller.
 */
@SpringBootTest(classes = HealthCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicalHistoryResourceIT {

    private static final Instant DEFAULT_JOINED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOINED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LEAVED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LEAVED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TOTAL_PRICE = 1L;
    private static final Long UPDATED_TOTAL_PRICE = 2L;

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private MedicalHistoryMapper medicalHistoryMapper;

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalHistoryMockMvc;

    private MedicalHistory medicalHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalHistory createEntity(EntityManager em) {
        MedicalHistory medicalHistory = new MedicalHistory()
            .joinedAt(DEFAULT_JOINED_AT)
            .leavedAt(DEFAULT_LEAVED_AT)
            .totalPrice(DEFAULT_TOTAL_PRICE);
        return medicalHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalHistory createUpdatedEntity(EntityManager em) {
        MedicalHistory medicalHistory = new MedicalHistory()
            .joinedAt(UPDATED_JOINED_AT)
            .leavedAt(UPDATED_LEAVED_AT)
            .totalPrice(UPDATED_TOTAL_PRICE);
        return medicalHistory;
    }

    @BeforeEach
    public void initTest() {
        medicalHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalHistory() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryRepository.findAll().size();
        // Create the MedicalHistory
        MedicalHistoryDTO medicalHistoryDTO = medicalHistoryMapper.toDto(medicalHistory);
        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalHistory testMedicalHistory = medicalHistoryList.get(medicalHistoryList.size() - 1);
        assertThat(testMedicalHistory.getJoinedAt()).isEqualTo(DEFAULT_JOINED_AT);
        assertThat(testMedicalHistory.getLeavedAt()).isEqualTo(DEFAULT_LEAVED_AT);
        assertThat(testMedicalHistory.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void createMedicalHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory with an existing ID
        medicalHistory.setId(1L);
        MedicalHistoryDTO medicalHistoryDTO = medicalHistoryMapper.toDto(medicalHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicalHistories() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        // Get all the medicalHistoryList
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].joinedAt").value(hasItem(DEFAULT_JOINED_AT.toString())))
            .andExpect(jsonPath("$.[*].leavedAt").value(hasItem(DEFAULT_LEAVED_AT.toString())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories/{id}", medicalHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalHistory.getId().intValue()))
            .andExpect(jsonPath("$.joinedAt").value(DEFAULT_JOINED_AT.toString()))
            .andExpect(jsonPath("$.leavedAt").value(DEFAULT_LEAVED_AT.toString()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMedicalHistory() throws Exception {
        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        int databaseSizeBeforeUpdate = medicalHistoryRepository.findAll().size();

        // Update the medicalHistory
        MedicalHistory updatedMedicalHistory = medicalHistoryRepository.findById(medicalHistory.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalHistory are not directly saved in db
        em.detach(updatedMedicalHistory);
        updatedMedicalHistory
            .joinedAt(UPDATED_JOINED_AT)
            .leavedAt(UPDATED_LEAVED_AT)
            .totalPrice(UPDATED_TOTAL_PRICE);
        MedicalHistoryDTO medicalHistoryDTO = medicalHistoryMapper.toDto(updatedMedicalHistory);

        restMedicalHistoryMockMvc.perform(put("/api/medical-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeUpdate);
        MedicalHistory testMedicalHistory = medicalHistoryList.get(medicalHistoryList.size() - 1);
        assertThat(testMedicalHistory.getJoinedAt()).isEqualTo(UPDATED_JOINED_AT);
        assertThat(testMedicalHistory.getLeavedAt()).isEqualTo(UPDATED_LEAVED_AT);
        assertThat(testMedicalHistory.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalHistory() throws Exception {
        int databaseSizeBeforeUpdate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory
        MedicalHistoryDTO medicalHistoryDTO = medicalHistoryMapper.toDto(medicalHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalHistoryMockMvc.perform(put("/api/medical-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        int databaseSizeBeforeDelete = medicalHistoryRepository.findAll().size();

        // Delete the medicalHistory
        restMedicalHistoryMockMvc.perform(delete("/api/medical-histories/{id}", medicalHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
