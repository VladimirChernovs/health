package com.black.mono.web.rest;

import com.black.mono.HealthApp;
import com.black.mono.domain.Enrollee;
import com.black.mono.repository.EnrolleeRepository;
import com.black.mono.service.EnrolleeService;
import com.black.mono.service.dto.EnrolleeDTO;
import com.black.mono.service.mapper.EnrolleeMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EnrolleeResource} REST controller.
 */
@SpringBootTest(classes = HealthApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnrolleeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATION_STATUS = false;
    private static final Boolean UPDATED_ACTIVATION_STATUS = true;

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE_NUMBER = "88505";
    private static final String UPDATED_PHONE_NUMBER = "+40)5 ";

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @Autowired
    private EnrolleeMapper enrolleeMapper;

    @Autowired
    private EnrolleeService enrolleeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnrolleeMockMvc;

    private Enrollee enrollee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enrollee createEntity(EntityManager em) {
        Enrollee enrollee = new Enrollee()
            .name(DEFAULT_NAME)
            .activationStatus(DEFAULT_ACTIVATION_STATUS)
            .birthDate(DEFAULT_BIRTH_DATE)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return enrollee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enrollee createUpdatedEntity(EntityManager em) {
        Enrollee enrollee = new Enrollee()
            .name(UPDATED_NAME)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .birthDate(UPDATED_BIRTH_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return enrollee;
    }

    @BeforeEach
    public void initTest() {
        enrollee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollee() throws Exception {
        int databaseSizeBeforeCreate = enrolleeRepository.findAll().size();
        // Create the Enrollee
        EnrolleeDTO enrolleeDTO = enrolleeMapper.toDto(enrollee);
        restEnrolleeMockMvc.perform(post("/api/enrollees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrolleeDTO)))
            .andExpect(status().isCreated());

        // Validate the Enrollee in the database
        List<Enrollee> enrolleeList = enrolleeRepository.findAll();
        assertThat(enrolleeList).hasSize(databaseSizeBeforeCreate + 1);
        Enrollee testEnrollee = enrolleeList.get(enrolleeList.size() - 1);
        assertThat(testEnrollee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEnrollee.isActivationStatus()).isEqualTo(DEFAULT_ACTIVATION_STATUS);
        assertThat(testEnrollee.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEnrollee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createEnrolleeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrolleeRepository.findAll().size();

        // Create the Enrollee with an existing ID
        enrollee.setId(1L);
        EnrolleeDTO enrolleeDTO = enrolleeMapper.toDto(enrollee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrolleeMockMvc.perform(post("/api/enrollees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrolleeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enrollee in the database
        List<Enrollee> enrolleeList = enrolleeRepository.findAll();
        assertThat(enrolleeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrolleeRepository.findAll().size();
        // set the field null
        enrollee.setName(null);

        // Create the Enrollee, which fails.
        EnrolleeDTO enrolleeDTO = enrolleeMapper.toDto(enrollee);


        restEnrolleeMockMvc.perform(post("/api/enrollees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrolleeDTO)))
            .andExpect(status().isBadRequest());

        List<Enrollee> enrolleeList = enrolleeRepository.findAll();
        assertThat(enrolleeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivationStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrolleeRepository.findAll().size();
        // set the field null
        enrollee.setActivationStatus(null);

        // Create the Enrollee, which fails.
        EnrolleeDTO enrolleeDTO = enrolleeMapper.toDto(enrollee);


        restEnrolleeMockMvc.perform(post("/api/enrollees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrolleeDTO)))
            .andExpect(status().isBadRequest());

        List<Enrollee> enrolleeList = enrolleeRepository.findAll();
        assertThat(enrolleeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrolleeRepository.findAll().size();
        // set the field null
        enrollee.setBirthDate(null);

        // Create the Enrollee, which fails.
        EnrolleeDTO enrolleeDTO = enrolleeMapper.toDto(enrollee);


        restEnrolleeMockMvc.perform(post("/api/enrollees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrolleeDTO)))
            .andExpect(status().isBadRequest());

        List<Enrollee> enrolleeList = enrolleeRepository.findAll();
        assertThat(enrolleeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnrollees() throws Exception {
        // Initialize the database
        enrolleeRepository.saveAndFlush(enrollee);

        // Get all the enrolleeList
        restEnrolleeMockMvc.perform(get("/api/enrollees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getEnrollee() throws Exception {
        // Initialize the database
        enrolleeRepository.saveAndFlush(enrollee);

        // Get the enrollee
        restEnrolleeMockMvc.perform(get("/api/enrollees/{id}", enrollee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enrollee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.booleanValue()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingEnrollee() throws Exception {
        // Get the enrollee
        restEnrolleeMockMvc.perform(get("/api/enrollees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollee() throws Exception {
        // Initialize the database
        enrolleeRepository.saveAndFlush(enrollee);

        int databaseSizeBeforeUpdate = enrolleeRepository.findAll().size();

        // Update the enrollee
        Enrollee updatedEnrollee = enrolleeRepository.findById(enrollee.getId()).get();
        // Disconnect from session so that the updates on updatedEnrollee are not directly saved in db
        em.detach(updatedEnrollee);
        updatedEnrollee
            .name(UPDATED_NAME)
            .activationStatus(UPDATED_ACTIVATION_STATUS)
            .birthDate(UPDATED_BIRTH_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        EnrolleeDTO enrolleeDTO = enrolleeMapper.toDto(updatedEnrollee);

        restEnrolleeMockMvc.perform(put("/api/enrollees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrolleeDTO)))
            .andExpect(status().isOk());

        // Validate the Enrollee in the database
        List<Enrollee> enrolleeList = enrolleeRepository.findAll();
        assertThat(enrolleeList).hasSize(databaseSizeBeforeUpdate);
        Enrollee testEnrollee = enrolleeList.get(enrolleeList.size() - 1);
        assertThat(testEnrollee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnrollee.isActivationStatus()).isEqualTo(UPDATED_ACTIVATION_STATUS);
        assertThat(testEnrollee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEnrollee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollee() throws Exception {
        int databaseSizeBeforeUpdate = enrolleeRepository.findAll().size();

        // Create the Enrollee
        EnrolleeDTO enrolleeDTO = enrolleeMapper.toDto(enrollee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrolleeMockMvc.perform(put("/api/enrollees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrolleeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enrollee in the database
        List<Enrollee> enrolleeList = enrolleeRepository.findAll();
        assertThat(enrolleeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnrollee() throws Exception {
        // Initialize the database
        enrolleeRepository.saveAndFlush(enrollee);

        int databaseSizeBeforeDelete = enrolleeRepository.findAll().size();

        // Delete the enrollee
        restEnrolleeMockMvc.perform(delete("/api/enrollees/{id}", enrollee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enrollee> enrolleeList = enrolleeRepository.findAll();
        assertThat(enrolleeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
