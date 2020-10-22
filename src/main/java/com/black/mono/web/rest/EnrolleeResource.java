package com.black.mono.web.rest;

import com.black.mono.service.EnrolleeService;
import com.black.mono.web.rest.errors.BadRequestAlertException;
import com.black.mono.service.dto.EnrolleeDTO;

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
 * REST controller for managing {@link com.black.mono.domain.Enrollee}.
 */
@RestController
@RequestMapping("/api")
public class EnrolleeResource {

    private final Logger log = LoggerFactory.getLogger(EnrolleeResource.class);

    private static final String ENTITY_NAME = "enrollee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnrolleeService enrolleeService;

    public EnrolleeResource(EnrolleeService enrolleeService) {
        this.enrolleeService = enrolleeService;
    }

    /**
     * {@code POST  /enrollees} : Create a new enrollee.
     *
     * @param enrolleeDTO the enrolleeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enrolleeDTO, or with status {@code 400 (Bad Request)} if the enrollee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enrollees")
    public ResponseEntity<EnrolleeDTO> createEnrollee(@Valid @RequestBody EnrolleeDTO enrolleeDTO) throws URISyntaxException {
        log.debug("REST request to save Enrollee : {}", enrolleeDTO);
        if (enrolleeDTO.getId() != null) {
            throw new BadRequestAlertException("A new enrollee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnrolleeDTO result = enrolleeService.save(enrolleeDTO);
        return ResponseEntity.created(new URI("/api/enrollees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enrollees} : Updates an existing enrollee.
     *
     * @param enrolleeDTO the enrolleeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrolleeDTO,
     * or with status {@code 400 (Bad Request)} if the enrolleeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enrolleeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enrollees")
    public ResponseEntity<EnrolleeDTO> updateEnrollee(@Valid @RequestBody EnrolleeDTO enrolleeDTO) throws URISyntaxException {
        log.debug("REST request to update Enrollee : {}", enrolleeDTO);
        if (enrolleeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnrolleeDTO result = enrolleeService.save(enrolleeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, enrolleeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enrollees} : get all the enrollees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrollees in body.
     */
    @GetMapping("/enrollees")
    public ResponseEntity<List<EnrolleeDTO>> getAllEnrollees(Pageable pageable) {
        log.debug("REST request to get a page of Enrollees");
        Page<EnrolleeDTO> page = enrolleeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enrollees/:id} : get the "id" enrollee.
     *
     * @param id the id of the enrolleeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enrolleeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enrollees/{id}")
    public ResponseEntity<EnrolleeDTO> getEnrollee(@PathVariable Long id) {
        log.debug("REST request to get Enrollee : {}", id);
        Optional<EnrolleeDTO> enrolleeDTO = enrolleeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enrolleeDTO);
    }

    /**
     * {@code DELETE  /enrollees/:id} : delete the "id" enrollee.
     *
     * @param id the id of the enrolleeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enrollees/{id}")
    public ResponseEntity<Void> deleteEnrollee(@PathVariable Long id) {
        log.debug("REST request to delete Enrollee : {}", id);
        enrolleeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
