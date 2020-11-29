package com.ptit.trandung.web.rest;

import com.ptit.trandung.HealthCareApp;
import com.ptit.trandung.domain.Nurse;
import com.ptit.trandung.repository.NurseRepository;
import com.ptit.trandung.service.NurseService;
import com.ptit.trandung.service.dto.NurseDTO;
import com.ptit.trandung.service.mapper.NurseMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NurseResource} REST controller.
 */
@SpringBootTest(classes = HealthCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NurseResourceIT {

    private static final String DEFAULT_ID_CARD = "AAAAAAAAAA";
    private static final String UPDATED_ID_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private NurseMapper nurseMapper;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNurseMockMvc;

    private Nurse nurse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nurse createEntity(EntityManager em) {
        Nurse nurse = new Nurse()
            .idCard(DEFAULT_ID_CARD)
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .level(DEFAULT_LEVEL)
            .experience(DEFAULT_EXPERIENCE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .address(DEFAULT_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return nurse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nurse createUpdatedEntity(EntityManager em) {
        Nurse nurse = new Nurse()
            .idCard(UPDATED_ID_CARD)
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .level(UPDATED_LEVEL)
            .experience(UPDATED_EXPERIENCE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return nurse;
    }

    @BeforeEach
    public void initTest() {
        nurse = createEntity(em);
    }

    @Test
    @Transactional
    public void createNurse() throws Exception {
        int databaseSizeBeforeCreate = nurseRepository.findAll().size();
        // Create the Nurse
        NurseDTO nurseDTO = nurseMapper.toDto(nurse);
        restNurseMockMvc.perform(post("/api/nurses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseDTO)))
            .andExpect(status().isCreated());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeCreate + 1);
        Nurse testNurse = nurseList.get(nurseList.size() - 1);
        assertThat(testNurse.getIdCard()).isEqualTo(DEFAULT_ID_CARD);
        assertThat(testNurse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNurse.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNurse.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testNurse.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testNurse.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testNurse.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testNurse.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createNurseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nurseRepository.findAll().size();

        // Create the Nurse with an existing ID
        nurse.setId(1L);
        NurseDTO nurseDTO = nurseMapper.toDto(nurse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNurseMockMvc.perform(post("/api/nurses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNurses() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);

        // Get all the nurseList
        restNurseMockMvc.perform(get("/api/nurses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nurse.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_ID_CARD)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getNurse() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);

        // Get the nurse
        restNurseMockMvc.perform(get("/api/nurses/{id}", nurse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nurse.getId().intValue()))
            .andExpect(jsonPath("$.idCard").value(DEFAULT_ID_CARD))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingNurse() throws Exception {
        // Get the nurse
        restNurseMockMvc.perform(get("/api/nurses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNurse() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);

        int databaseSizeBeforeUpdate = nurseRepository.findAll().size();

        // Update the nurse
        Nurse updatedNurse = nurseRepository.findById(nurse.getId()).get();
        // Disconnect from session so that the updates on updatedNurse are not directly saved in db
        em.detach(updatedNurse);
        updatedNurse
            .idCard(UPDATED_ID_CARD)
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .level(UPDATED_LEVEL)
            .experience(UPDATED_EXPERIENCE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        NurseDTO nurseDTO = nurseMapper.toDto(updatedNurse);

        restNurseMockMvc.perform(put("/api/nurses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseDTO)))
            .andExpect(status().isOk());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeUpdate);
        Nurse testNurse = nurseList.get(nurseList.size() - 1);
        assertThat(testNurse.getIdCard()).isEqualTo(UPDATED_ID_CARD);
        assertThat(testNurse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNurse.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNurse.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testNurse.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testNurse.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testNurse.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testNurse.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingNurse() throws Exception {
        int databaseSizeBeforeUpdate = nurseRepository.findAll().size();

        // Create the Nurse
        NurseDTO nurseDTO = nurseMapper.toDto(nurse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNurseMockMvc.perform(put("/api/nurses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNurse() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);

        int databaseSizeBeforeDelete = nurseRepository.findAll().size();

        // Delete the nurse
        restNurseMockMvc.perform(delete("/api/nurses/{id}", nurse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
