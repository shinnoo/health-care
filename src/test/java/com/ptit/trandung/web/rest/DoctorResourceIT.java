package com.ptit.trandung.web.rest;

import com.ptit.trandung.HealthCareApp;
import com.ptit.trandung.domain.Doctor;
import com.ptit.trandung.repository.DoctorRepository;
import com.ptit.trandung.service.DoctorService;
import com.ptit.trandung.service.dto.DoctorDTO;
import com.ptit.trandung.service.mapper.DoctorMapper;

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
 * Integration tests for the {@link DoctorResource} REST controller.
 */
@SpringBootTest(classes = HealthCareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DoctorResourceIT {

    private static final String DEFAULT_ID_CARD = "AAAAAAAAAA";
    private static final String UPDATED_ID_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACADEMIC_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMIC_LEVEL = "BBBBBBBBBB";

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorMockMvc;

    private Doctor doctor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createEntity(EntityManager em) {
        Doctor doctor = new Doctor()
            .idCard(DEFAULT_ID_CARD)
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .address(DEFAULT_ADDRESS)
            .level(DEFAULT_LEVEL)
            .experience(DEFAULT_EXPERIENCE)
            .type(DEFAULT_TYPE)
            .academicLevel(DEFAULT_ACADEMIC_LEVEL);
        return doctor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createUpdatedEntity(EntityManager em) {
        Doctor doctor = new Doctor()
            .idCard(UPDATED_ID_CARD)
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .level(UPDATED_LEVEL)
            .experience(UPDATED_EXPERIENCE)
            .type(UPDATED_TYPE)
            .academicLevel(UPDATED_ACADEMIC_LEVEL);
        return doctor;
    }

    @BeforeEach
    public void initTest() {
        doctor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctor() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();
        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);
        restDoctorMockMvc.perform(post("/api/doctors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate + 1);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getIdCard()).isEqualTo(DEFAULT_ID_CARD);
        assertThat(testDoctor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDoctor.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testDoctor.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDoctor.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testDoctor.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testDoctor.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDoctor.getAcademicLevel()).isEqualTo(DEFAULT_ACADEMIC_LEVEL);
    }

    @Test
    @Transactional
    public void createDoctorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // Create the Doctor with an existing ID
        doctor.setId(1L);
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorMockMvc.perform(post("/api/doctors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoctors() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList
        restDoctorMockMvc.perform(get("/api/doctors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCard").value(hasItem(DEFAULT_ID_CARD)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].academicLevel").value(hasItem(DEFAULT_ACADEMIC_LEVEL)));
    }
    
    @Test
    @Transactional
    public void getDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctor.getId().intValue()))
            .andExpect(jsonPath("$.idCard").value(DEFAULT_ID_CARD))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.academicLevel").value(DEFAULT_ACADEMIC_LEVEL));
    }
    @Test
    @Transactional
    public void getNonExistingDoctor() throws Exception {
        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor
        Doctor updatedDoctor = doctorRepository.findById(doctor.getId()).get();
        // Disconnect from session so that the updates on updatedDoctor are not directly saved in db
        em.detach(updatedDoctor);
        updatedDoctor
            .idCard(UPDATED_ID_CARD)
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .level(UPDATED_LEVEL)
            .experience(UPDATED_EXPERIENCE)
            .type(UPDATED_TYPE)
            .academicLevel(UPDATED_ACADEMIC_LEVEL);
        DoctorDTO doctorDTO = doctorMapper.toDto(updatedDoctor);

        restDoctorMockMvc.perform(put("/api/doctors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getIdCard()).isEqualTo(UPDATED_ID_CARD);
        assertThat(testDoctor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDoctor.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testDoctor.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDoctor.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testDoctor.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testDoctor.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDoctor.getAcademicLevel()).isEqualTo(UPDATED_ACADEMIC_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorMockMvc.perform(put("/api/doctors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeDelete = doctorRepository.findAll().size();

        // Delete the doctor
        restDoctorMockMvc.perform(delete("/api/doctors/{id}", doctor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
