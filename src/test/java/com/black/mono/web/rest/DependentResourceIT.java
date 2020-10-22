package com.black.mono.web.rest;

import com.black.mono.HealthApp;
import com.black.mono.domain.Dependent;
import com.black.mono.domain.Enrollee;
import com.black.mono.repository.DependentRepository;
import com.black.mono.service.DependentService;
import com.black.mono.service.dto.DependentDTO;
import com.black.mono.service.mapper.DependentMapper;

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
 * Integration tests for the {@link DependentResource} REST controller.
 */
@SpringBootTest(classes = HealthApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DependentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DependentRepository dependentRepository;

    @Autowired
    private DependentMapper dependentMapper;

    @Autowired
    private DependentService dependentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDependentMockMvc;

    private Dependent dependent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dependent createEntity(EntityManager em) {
        Dependent dependent = new Dependent()
            .name(DEFAULT_NAME)
            .birthDate(DEFAULT_BIRTH_DATE);
        // Add required entity
        Enrollee enrollee;
        if (TestUtil.findAll(em, Enrollee.class).isEmpty()) {
            enrollee = EnrolleeResourceIT.createEntity(em);
            em.persist(enrollee);
            em.flush();
        } else {
            enrollee = TestUtil.findAll(em, Enrollee.class).get(0);
        }
        dependent.setEnrollee(enrollee);
        return dependent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dependent createUpdatedEntity(EntityManager em) {
        Dependent dependent = new Dependent()
            .name(UPDATED_NAME)
            .birthDate(UPDATED_BIRTH_DATE);
        // Add required entity
        Enrollee enrollee;
        if (TestUtil.findAll(em, Enrollee.class).isEmpty()) {
            enrollee = EnrolleeResourceIT.createUpdatedEntity(em);
            em.persist(enrollee);
            em.flush();
        } else {
            enrollee = TestUtil.findAll(em, Enrollee.class).get(0);
        }
        dependent.setEnrollee(enrollee);
        return dependent;
    }

    @BeforeEach
    public void initTest() {
        dependent = createEntity(em);
    }

    @Test
    @Transactional
    public void createDependent() throws Exception {
        int databaseSizeBeforeCreate = dependentRepository.findAll().size();
        // Create the Dependent
        DependentDTO dependentDTO = dependentMapper.toDto(dependent);
        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependentDTO)))
            .andExpect(status().isCreated());

        // Validate the Dependent in the database
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeCreate + 1);
        Dependent testDependent = dependentList.get(dependentList.size() - 1);
        assertThat(testDependent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDependent.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void createDependentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dependentRepository.findAll().size();

        // Create the Dependent with an existing ID
        dependent.setId(1L);
        DependentDTO dependentDTO = dependentMapper.toDto(dependent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dependent in the database
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependentRepository.findAll().size();
        // set the field null
        dependent.setName(null);

        // Create the Dependent, which fails.
        DependentDTO dependentDTO = dependentMapper.toDto(dependent);


        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependentDTO)))
            .andExpect(status().isBadRequest());

        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependentRepository.findAll().size();
        // set the field null
        dependent.setBirthDate(null);

        // Create the Dependent, which fails.
        DependentDTO dependentDTO = dependentMapper.toDto(dependent);


        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependentDTO)))
            .andExpect(status().isBadRequest());

        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDependents() throws Exception {
        // Initialize the database
        dependentRepository.saveAndFlush(dependent);

        // Get all the dependentList
        restDependentMockMvc.perform(get("/api/dependents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDependent() throws Exception {
        // Initialize the database
        dependentRepository.saveAndFlush(dependent);

        // Get the dependent
        restDependentMockMvc.perform(get("/api/dependents/{id}", dependent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dependent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDependent() throws Exception {
        // Get the dependent
        restDependentMockMvc.perform(get("/api/dependents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDependent() throws Exception {
        // Initialize the database
        dependentRepository.saveAndFlush(dependent);

        int databaseSizeBeforeUpdate = dependentRepository.findAll().size();

        // Update the dependent
        Dependent updatedDependent = dependentRepository.findById(dependent.getId()).get();
        // Disconnect from session so that the updates on updatedDependent are not directly saved in db
        em.detach(updatedDependent);
        updatedDependent
            .name(UPDATED_NAME)
            .birthDate(UPDATED_BIRTH_DATE);
        DependentDTO dependentDTO = dependentMapper.toDto(updatedDependent);

        restDependentMockMvc.perform(put("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependentDTO)))
            .andExpect(status().isOk());

        // Validate the Dependent in the database
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeUpdate);
        Dependent testDependent = dependentList.get(dependentList.size() - 1);
        assertThat(testDependent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDependent.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDependent() throws Exception {
        int databaseSizeBeforeUpdate = dependentRepository.findAll().size();

        // Create the Dependent
        DependentDTO dependentDTO = dependentMapper.toDto(dependent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDependentMockMvc.perform(put("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dependent in the database
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDependent() throws Exception {
        // Initialize the database
        dependentRepository.saveAndFlush(dependent);

        int databaseSizeBeforeDelete = dependentRepository.findAll().size();

        // Delete the dependent
        restDependentMockMvc.perform(delete("/api/dependents/{id}", dependent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
